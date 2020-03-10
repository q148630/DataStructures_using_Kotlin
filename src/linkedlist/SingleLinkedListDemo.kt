package linkedlist

import java.util.*

fun main() {
    // 測試
    // 先創建節點
    val hero1 = HeroNode(1, "1", "1n")
    val hero2 = HeroNode(2, "2", "2n")
    val hero3 = HeroNode(3, "3", "3n")
    val hero4 = HeroNode(4, "4", "4n")

    // 創建單向鏈表
    val singleLinkedList = SingleLinkedList()
    // 加入
//    singleLinkedList.add(hero1)
//    singleLinkedList.add(hero2)
//    singleLinkedList.add(hero3)
//    singleLinkedList.add(hero4)

    // 加入，按照編號的順序
    singleLinkedList.addByOrder(hero1)
    singleLinkedList.addByOrder(hero4)
    singleLinkedList.addByOrder(hero2)
    singleLinkedList.addByOrder(hero3)

    // 測試一下，單鏈表的反轉功能
    println("原來鏈表的情況~")
    singleLinkedList.list()

    println("反轉單鏈表~")
    reverseList(singleLinkedList.head)
    singleLinkedList.list()

    println("測試逆序 print 單鏈表，沒有改變鏈表的結構~")
    reversePrint(singleLinkedList.head)
    println() // 版面換行用

    // 顯示鏈表
    singleLinkedList.list()

    // 測試修改節點的代碼
    val newHeroNode = HeroNode(2, "2_1", "2n_1")
    singleLinkedList.update(newHeroNode)

    println("修改後的鏈表情況~")
    singleLinkedList.list()

    // 測試刪除節點的代碼
    singleLinkedList.del(1)
    singleLinkedList.del(4)
    println("刪除後的鏈表情況!")
    singleLinkedList.list()

    // 測試一下，求單鏈表中有效節點的個數
    println("有效的節點個數=${getLength(singleLinkedList.head)}")

    // 測試一下，看看是否得到了倒數第 k 個節點
    val res = findLastIndexNode(singleLinkedList.head, 2)
    println("res=$res")
}

// 定義 SingleLinkedList 管理我們的英雄
class SingleLinkedList {
    // 先初始化一個頭節點，頭節點不要動，不存放具體數據
    private val _head = HeroNode(0, "", "")
    // 返回頭節點，公開變數
    val head: HeroNode
        get() = _head

    // 添加節點到單向鏈表
    // 思路: 當不考慮編號順序時
    // 1. 找到當前鏈表的最後節點
    // 2. 將最後這個節點的 next 指向新的節點
    fun add(heroNode: HeroNode) {
        // 因為 head 節點不能動，所以我們需要一個輔助變數 temp
        var temp = _head
        // 遍歷鏈表，找到最後
        while (temp.next != null) {
            temp = temp.next!! // 如果沒有找到最後，將 temp 後移
        }
        // 當退出 while 循環時，temp 就指向了鏈表最後一個節點
        // 將最後這個節點的 next 指向要新加入的節點
        temp.next = heroNode
    }

    // 第二種方式在添加英雄時，根據排名將英雄插入到指定位置
    // (如果已存在這個排名，則添加失敗，並給出提示訊息)
    fun addByOrder(heroNode: HeroNode) {
        // 因為 head 節點不能動，所以我們需要一個輔助變數 temp 來幫助找到添加的位置
        // 由於單鏈表，因為我們找的 temp 是位於添加位置的前一個節點，否則插入不了
        var temp = _head
        var flag = false // flag 標誌添加的編號是否存在，默認為false

        while (temp.next != null/*當遍歷到最後一個節點時停止*/) {
            if (temp.next!!.no > heroNode.no) { // 位置找到，就在 temp 的後面插入
                break
            } else if (temp.next!!.no == heroNode.no) { // 說明希望添加的 heroNode 的編號已存在
                flag = true // 說明編號已存在
                break
            }
            temp = temp.next!! // 將 temp 後移，進入下個循環
        }
        // 判斷 flag 的值
        if (flag) { // 不能添加，說明編號已存在
            println("準備插入的英雄的編號 ${heroNode.no} 已經存在了，不能加入")
        } else {
            // 插入到鏈表中，temp 的後面
            heroNode.next = temp.next
            temp.next = heroNode
        }
    }

    // 修改節點的數據，根據 no 編號來修改，即 no 編號不能改
    // 說明
    // 1. 根據 newHeroNode 的 no 來修改即可
    fun update(newHeroNode: HeroNode) {
        // 判斷鏈表是否為空
        if (_head.next == null) {
            println("鏈表為空")
            return
        }
        // 找到需要修改的節點，根據 no 編號
        // 因為 head 節點不能動，所以我們需要一個輔助變數 temp 來尋找該編號
        var temp = _head.next
        var flag = false // 表示是否找到該節點
        while (temp != null/*不為空表示該節點有數據*/) {
            if (temp.no == newHeroNode.no) {
                flag = true // 找到
                break
            }
            temp = temp.next // 將 temp 節點後移，進入下個循環
        }
        // 根據 flag 判斷是否找到要修改的節點
        if (flag) {
            temp?.name = newHeroNode.name
            temp?.nickname = newHeroNode.nickname
        } else { // 沒有找到
            println("沒要找到編號 ${newHeroNode.no} 的節點，不能修改")
        }
    }

    // 刪除節點
    // 思路:
    // 1. 因為 head 節點不能動，所以我們需要一個輔助變數 temp 來尋找待刪除節點的前一個節點
    // 2. 說明我們在比較時，是 temp.next.no 和 需要刪除的節點的 no 比較
    fun del(no: Int) {
        var temp = _head
        var flag = false // 標誌是否找到待刪除的節點
        while (temp.next != null/*當遍歷到最後一個節點時停止*/) {
            if (temp.next!!.no == no) {
                flag = true // 找到待刪除節點的前一個節點 temp
                break
            }
            temp = temp.next!! // 將 temp 後移，進入下個循環
        }
        // 判斷 flag
        if (flag) { // 找到
            // 可以刪除
            temp.next = temp.next!!.next
        } else {
            println("要刪除的 $no 節點不存在")
        }
    }

    // 顯示鏈表[遍歷]
    fun list() {
        // 判斷鏈表是否為空
        if (_head.next == null) {
            println("鏈表為空")
            return
        }
        // 因為 head 節點不能動，所以我們需要一個輔助變數 temp 來遍歷
        var temp = _head.next
        while (temp != null/*不為空表示該節點有數據*/) {
            println(temp) // 輸出節點訊息
            temp = temp.next // 將 temp 節點後移，進入下個循環
        }
    }
}

// 定義 HeroNode，每個 HeroNode 對象就是一個節點
data class HeroNode(
    val no: Int,
    var name: String,
    var nickname: String,
    var next: HeroNode? = null/*指向下一個節點(預設為null)*/
) {
    // 為了自訂對象輸出字串，改寫toString()方法
    override fun toString(): String {
        return "HeroNode [no=$no, name=$name, nickname=$nickname]"
    }
}

// 單鏈表從尾到頭(逆序) print 訊息
// 方式2:
// 可以利用堆疊這個資料結構，將各個節點加入到堆疊中，然後利用堆疊的後進先出(LIFO)的特點，就實現了逆序 print 效果
fun reversePrint(head: HeroNode) {
    if (head.next == null) {
        return // 空鏈表，不能 print
    }
    // 創建堆疊，將各個節點加入到堆疊中
    val stack = Stack<HeroNode>()
    var cur = head.next
    // 將鏈表的所有節點加入到堆疊中
    while (cur != null/*cur 不為空，表示有數據*/) {
        stack.push(cur)
        cur = cur.next // cur 後移，進入下個循環
    }
    // 將堆疊中的節點進行 print，pop 出堆疊
    while (stack.size > 0) {
        println(stack.pop()) // stack 的特點是後進先出(LIFO)
    }
}

// 將單鏈表反轉 [面試題]
fun reverseList(head: HeroNode) {
    // 如果當前鏈表為空，或者只有一個節點，無須反轉，直接返回
    if (head.next == null || head.next?.next == null) {
        return
    }

    // 定義一個輔助變數，幫助我們遍歷原來的鏈表
    var cur = head.next
    var next: HeroNode? // 指向當前節點 [cur] 的下一個節點
    val reverseHead = HeroNode(0, "", "")
    // 遍歷原來的鏈表，每遍歷一個節點，就將其取出，並放在新的鏈表 reverseHead 的最前端
    while (cur != null) {
        next = cur.next // 先暫時保存當前節點的下一個節點，因為後面需要使用
        cur.next = reverseHead.next // 將 cur 的下一個節點指向新的鏈表的最前端
        reverseHead.next = cur // 將 cur 連接到新的鏈表上
        cur = next // 將 cur 後移
    }
    // 將 head.next 指向 reverseHead.next，實現單鏈表的反轉
    head.next = reverseHead.next
}

// 查找單鏈表中的倒數第 k 個節點 [面試題]
// 思路:
// 1. 編寫一個方法，接收 head 節點，同時接收一個 index
// 2. index 表示是倒數第 index 個節點
// 3. 先把鏈表從頭到尾遍歷，得到鏈表的總長度 getLength
// 4. 得到 size 後，我們從鏈表的第一個節點開始遍歷 (size - index) 個，就可以得到
// 5. 如果找到了，則返回該節點，否則返回 null
fun findLastIndexNode(head: HeroNode, index: Int): HeroNode? {
    // 判斷如果鏈表為空，返回 null
    if (head.next == null) {
        return null // 沒有找到
    }
    // 第一次遍歷，得到鏈表的總長度(有效節點個數)
    val size = getLength(head)
    // 第二次遍歷 (size - index) 位置，就是我們倒數第 k 個節點
    // 先對 index 做驗證
    if (index <= 0 || index > size) {
        return null
    }
    // 定義輔助變數，for 循環定位到倒數的 index
    var cur = head.next // 3 // 3 - 1 = 2
    for (i in 0 until size - index) {
        cur = cur!!.next
    }
    return cur
}

/**
 * 方法: 獲取到單鏈表的節點的個數(如果是帶頭節點的鏈表，需求不統計頭節點)
 * @param head 鏈表的頭節點
 * @return 返回的就是有效節點的個數
 */
fun getLength(head: HeroNode): Int {
    if (head.next == null) { // 空鏈表
        return 0
    }
    var length = 0
    var cur = head.next // 定義一個輔助變數，這裡我們沒有統計頭節點
    while (cur != null) {
        length++
        cur = cur.next
    }
    return length
}
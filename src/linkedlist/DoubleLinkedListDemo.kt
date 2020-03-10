package linkedlist

fun main() {
    // 測試
    println("雙向鏈表的測試")
    // 先創建節點
    val hero1 = HeroNode2(1, "1", "1n")
    val hero2 = HeroNode2(2, "2", "2n")
    val hero3 = HeroNode2(3, "3", "3n")
    val hero4 = HeroNode2(4, "4", "4n")
    // 創建雙向鏈表
    val doubleLindedList = DoubleLinkedList()
    // 加入
//    doubleLindedList.add(hero1)
//    doubleLindedList.add(hero2)
//    doubleLindedList.add(hero3)
//    doubleLindedList.add(hero4)

    // 加入，按照編號的順序
    doubleLindedList.addByOrder(hero1)
    doubleLindedList.addByOrder(hero4)
    doubleLindedList.addByOrder(hero2)
    doubleLindedList.addByOrder(hero3)

    // 顯示雙向鏈表
    doubleLindedList.list()

    // 修改
    val newHeroNode2 = HeroNode2(4, "4_1", "4n_1")
    doubleLindedList.update(newHeroNode2)
    println("修改後的鏈表情況")
    doubleLindedList.list()

    // 刪除
    doubleLindedList.del(3)
    println("刪除後的鏈表情況")
    doubleLindedList.list()
}

// 創建一個雙向鏈表的類別
class DoubleLinkedList {
    // 先初始化一個頭節點，頭節點不要動，不存放具體數據
    private val _head = HeroNode2(0, "", "")
    // 返回頭節點，公開變數
    val head: HeroNode2
        get() = _head

    // 添加一個節點到雙向鏈表的最後
    fun add(heroNode: HeroNode2) {
        // 因為 head 節點不能動，所以我們需要一個輔助變數 temp
        var temp = _head
        // 遍歷鏈表，找到最後
        while (temp.next != null) {
            temp = temp.next!! // 如果沒有找到最後，將 temp 後移
        }
        // 當退出 while 循環時，temp 就指向了鏈表最後一個節點
        // 將最後這個節點的 next 指向要新加入的節點
        // 並將新加入的節點的 pre 指向最後這個節點，形成雙向鏈結
        temp.next = heroNode
        heroNode.pre = temp
    }

    // 第二種方式在添加英雄時，根據排名將英雄插入到指定位置
    // (如果已存在這個排名，則添加失敗，並給出提示訊息)
    fun addByOrder(heroNode: HeroNode2) {
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
            heroNode.pre = temp
            temp.next = heroNode
            temp.next!!.pre = heroNode

        }
    }

    // 修改一個節點的數據，可以看到雙向鏈表的節點數據修改和單向鏈表一樣
    // 只是節點類型改成 HeroNode2
    fun update(newHeroNode: HeroNode2) {
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

    // 從雙向鏈表中刪除一個節點
    // 說明
    // 1. 對於雙向鏈表，我們可以直接找到要刪除的這個節點
    // 2. 找到後，自我刪除即可
    fun del(no: Int) {
        var temp = _head.next
        var flag = false // 標誌是否找到待刪除的節點
        while (temp != null/*當遍歷到最後一個節點時停止*/) {
            if (temp.no == no) {
                flag = true // 找到待刪除的節點 temp
                break
            }
            temp = temp.next // 將 temp 後移，進入下個循環
        }
        // 判斷 flag
        if (flag) { // 找到
            // 可以刪除
            //temp.next = temp.next!!.next
            temp!!.pre!!.next = temp.next
            // 這裡我們的代碼有問題?
            // 如果是最後一個節點，就不需要執行下面這句話，否則會出現空指針異常
            if (temp.next != null) {
                temp!!.next!!.pre = temp.pre
            }
        } else {
            println("要刪除的 $no 節點不存在")
        }
    }

    // 遍歷雙向鏈表的方法
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
data class HeroNode2(
    val no: Int,
    var name: String,
    var nickname: String,
    var next: HeroNode2? = null, /*指向下一個節點(預設為 null)*/
    var pre: HeroNode2? = null /*指向前一個節點(預設為 null)*/
) {
    // 為了自訂對象輸出字串，改寫toString()方法
    override fun toString(): String {
        return "HeroNode2 [no=$no, name=$name, nickname=$nickname]"
    }
}
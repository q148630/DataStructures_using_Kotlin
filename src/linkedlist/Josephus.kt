package linkedlist

/**
 * 方法: Josephus(約瑟夫、約瑟夫環)問題
 */
fun main() {
    // 測試看看構建單向環形鏈表，和遍歷是否 OK
    val circleSingleLindedList = CircleSingleLindedList()
    circleSingleLindedList.addBoy(5)
    circleSingleLindedList.showBoy()

    println()
    // 測試 Boy 出圈是否正確
    circleSingleLindedList.countBoy(1, 2, 5) // 2->4->1->5->3
}

// 創建一個環形單向鏈表
class CircleSingleLindedList {
    // 創建一個 first 節點，當前沒有編號
    private var first: Boy? = null

    // 添加 Boy 節點，構建成一個單向環形鏈表
    fun addBoy(nums: Int) {
        // 對 nums 做驗證
        if (nums < 1) {
            println("nums 的值不正確")
            return
        }
        var curBoy: Boy? = null // 輔助變數，幫助構建單向環形鏈表
        // 使用 for 循環來創建單向環形鏈表
        for (i in 1..nums) {
            val boy = Boy(i) // 根據編號，創建 Boy 節點
            // 如果是第一個 Boy
            if (i == 1) {
                first = boy
                first!!.next = first // 單獨構成環形
                curBoy = first // 將 curBoy 指向 first，幫助添加之後的 Boy，因為 first 不能動
            } else {
                curBoy!!.next = boy
                boy.next = first
                curBoy = boy
            }
        }
    }

    // 遍歷當前的單向環形鏈表
    fun showBoy() {
        // 判斷鏈表是否為空
        if (first == null) {
            println("沒有任何 Boy~")
            return
        }
        // 因為 first 不能動，因此我們仍然使用一個輔助變數來完成遍歷
        var curBoy = first
        do {
            println("Boy 的編號 ${curBoy!!.no}")
            curBoy = curBoy.next // 將 curBoy 後移
        } while (curBoy != first) // 當 curBoy 回到 first 時停止 while 循環
    }

    // 根據用戶的輸入，計算出 Boy 出圈的順序
    fun countBoy(startNo: Int, countNum: Int, nums: Int) {
        // 先驗證數據正確性
        if (first == null || startNo < 1 || startNo > nums) {
            println("參數輸入有誤(或單向環形鏈表為空)，請重新輸入")
            return
        }

        // Boy 報數前，先讓 first 移動到要開始(startNo)的編號，移動 startNo - 1 次
        for (i in 0 until startNo - 1) {
            first = first!!.next
        }

        // 創建輔助變數，幫助完成 Boy 出圈
        var helper = first
        // 將 helper 指向 first 的前一個節點
        while (helper!!.next != first) {
            helper = helper.next
        }

        // 當 Boy 報數時，將 first 和 helper 指針同時移動 countNum - 1 次，然後出圈
        // 這裡是一個循環操作，直到鏈表中只剩一個節點
        while (helper != first/*helper 與 first 相等表示只剩一個節點*/) {
            // 將 first 和 helper 指針同時移動 countNum - 1 次
            for (i in 0 until countNum - 1) {
                first = first!!.next
                helper = helper!!.next
            }
            // 這時 first 指向的節點，就是要出圈的 Boy 節點
            println("Boy ${first!!.no} 出圈")
            // 這時將 first 指向的 Boy 節點出圈
            first = first!!.next
            helper!!.next = first
        }
        println("最後留在圈中的 Boy 編號${first!!.no}")
    }

}

// 創建一個 Boy 類別，表示一個節點
data class Boy(val no: Int, var next: Boy? = null/*指向下一個節點(預設為 null)*/)
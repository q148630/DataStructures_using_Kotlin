package hashtable

fun main() {
    // 創建 HashTable
    val hashTable = HashTable(7)

    // 定義一個簡單的菜單
    var key: String
    var loop = true
    while (loop) {
        println("add: 添加員工")
        println("list: 顯示員工")
        println("find: 查找員工")
        println("update: 修改員工")
        println("delete: 刪除員工")
        println("exit: 退出程式")
        key = readLine()!!
        when (key) {
            "add" -> {
                println("輸入 id")
                val id = readLine()!!.toInt()
                println("輸入名字")
                val name = readLine()!!

                // 創建員工
                val emp = Emp(id, name)
                hashTable.add(emp)
            }
            "list" -> {
                hashTable.list()
            }
            "find" -> {
                println("請輸入要查找的 id")
                val id = readLine()!!.toInt()
                hashTable.findEmpById(id)
            }
            "update" -> {
                println("請輸入要修改的 id")
                val id = readLine()!!.toInt()
                println("請輸入修改後的名字")
                val name = readLine()!!

                // 修改員工
                val emp = Emp(id, name)
                hashTable.updateEmp(emp)
            }
            "delete" -> {
                println("請輸入要刪除的 id")
                val id = readLine()!!.toInt()
                hashTable.deleteEmp(id)
            }
            "exit" -> {
                loop = false
            }
        }
    }
}

// 創建 HashTable 管理多條鏈表
class HashTable(private val size: Int) {
    // 初始化 empLinkedListArray
    private val empLinkedListArray: Array<EmpLinkedList> =
        Array(size) { EmpLinkedList()/*一定要初始化 empLinkedListArray()，否則會出現空指針異常*/ }

    // 添加員工
    fun add(emp: Emp) {
        val empLinkedListNo = hashFun(emp.id) // 根據員工的 id，得到該員工應當添加到哪一條鏈表
        empLinkedListArray[empLinkedListNo].add(emp)
    }

    // 遍歷所有的鏈表，遍歷 HashTable
    fun list() {
        for (i in 0 until size) {
            empLinkedListArray[i].list(i)
        }
    }

    // 根據輸入的 id，查找員工
    fun findEmpById(id: Int) {
        val empLinkedListNo = hashFun(id) // 使用 Hash Function 來確定到哪一條鏈表中查找
        val emp = empLinkedListArray[empLinkedListNo].findEmpById(id)
        if (emp != null) { // 找到
            println("在第${empLinkedListNo + 1}條鏈表中找到 員工 id = $id")
        } else {
            println("在 HashTable 中，沒有找到該員工~")
        }
    }

    // 根據輸入的 emp，修改員工
    fun updateEmp(emp: Emp) {
        val empLinkedListNo = hashFun(emp.id) // 使用 Hash Function 來確定到哪一條鏈表中修改
        val isUpdate = empLinkedListArray[empLinkedListNo].updateEmp(emp)
        if (isUpdate) { // 已更新
            println("在第${empLinkedListNo + 1}條鏈表中更新 員工 id = ${emp.id}")
        } else {
            println("在 HashTable 中，沒有找到該員工~")
        }
    }

    // 根據輸入的 id，刪除員工
    fun deleteEmp(id: Int) {
        val empLinkedListNo = hashFun(id) // 使用 Hash Function 來確定到哪一條鏈表中刪除
        val isDelete = empLinkedListArray[empLinkedListNo].deleteEmp(id)
        if (isDelete) { // 已刪除
            println("在第${empLinkedListNo + 1}條鏈表中刪除 員工 id = $id")
        } else {
            println("在 HashTable 中，沒有找到該員工~")
        }
    }

    // 定義 Hash Function，使用簡單的取模法
    fun hashFun(id: Int) = id % size
}

// 表示一個員工
data class Emp(val id: Int, var name: String, var next: Emp? = null/*next 預設為 null*/)

// 創建 EmpLinkedList，表示鏈表
class EmpLinkedList {
    private var head: Emp? = null // 頭節點，這個鏈表的 head 是直接指向第一個 Emp，沒有使用佔位(虛擬)頭節點

    // 添加員工到鏈表
    // 說明
    // 1. 假設，當添加員工時，id 是自增的，即 id 的分配總是由小到大
    //    因此我們將該員工直接加入到該鏈表的最後即可
    fun add(emp: Emp) {
        // 如果是添加該鏈表的第一個員工
        if (head == null) {
            head = emp
            return
        }
        // 如果不是該鏈表的第一個員工，則使用一個輔助變數，幫助定位到最後
        var curEmp = head!!
        while (curEmp.next != null/*當 curEmp.next 為空時表示已經定位到最後一個節點*/) {
            curEmp = curEmp.next!! // 後移
        }
        curEmp.next = emp // 退出時直接將 emp 加入到該鏈表
    }

    // 遍歷鏈表的員工訊息
    fun list(no: Int) {
        if (head == null) { // 說明鏈表為空
            println("第 ${no + 1} 鏈表為空")
            return
        }
        print("第 ${no + 1} 鏈表的訊息為")
        var curEmp = head // 輔助變數
        while (curEmp != null/*判斷當前節點是否為 null*/) {
            print("=> id=${curEmp.id} name=${curEmp.name}\t")
            curEmp = curEmp.next // 後移，遍歷
        }
        println()
    }

    // 根據 id 查找員工
    // 如果找到，就返回 Emp，否則返回 null
    fun findEmpById(id: Int): Emp? {
        // 判斷鏈表是否為空
        if (head == null) {
            println("鏈表為空")
            return null
        }
        var curEmp = head // 輔助變數
        while (curEmp != null/*判斷當前節點是否為 null*/) {
            if (curEmp.id == id) { // 找到
                break // 這時 curEmp 就指向要查找的員工
            }
            curEmp = curEmp.next // 後移
        }
        // 如果找到最後一個都不符合，則 curEmp 會是 null
        return curEmp
    }

    // 根據 emp.id 修改員工訊息
    fun updateEmp(emp: Emp): Boolean {
        if (head == null) {
            println("鏈表為空")
            return false
        }
        var curEmp = head // 輔助變數
        var flag = false
        while (curEmp != null/*判斷當前節點是否為 null*/) {
            if (curEmp.id == emp.id) { // 找到
                flag = true
                curEmp.name = emp.name // 這時 curEmp 就指向要更新的員工
                break
            }
            curEmp = curEmp.next // 後移
        }
        return flag
    }

    // 根據 id 刪除員工訊息
    fun deleteEmp(id: Int): Boolean {
        if (head == null) {
            println("鏈表為空")
            return false
        }
        if (head!!.id == id) { // 要刪除的節點是頭節點時，直接將頭節點後移
            head = head!!.next
            return true
        }
        var curEmp = head // 輔助變數
        var flag = false
        while (curEmp!!.next != null) {
            if (curEmp.next!!.id == id) { //找到欲刪除節點的前一個節點
                flag = true
                curEmp.next = curEmp.next!!.next // 這時 curEmp 就指向要刪除節點的前一個節點
                break
            }
            curEmp = curEmp.next // 後移
        }
        return flag
    }
}
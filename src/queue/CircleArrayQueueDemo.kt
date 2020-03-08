package queue

import java.lang.NumberFormatException
import java.lang.RuntimeException

fun main() {
    //測試
    //創建一個環形佇列
    val queue = CircleArrayQueue(4) // 說明設置4，其佇列的有效數據最大為3
    var key: Char = ' ' // 接收用戶輸入
    var loop = true
    while (loop) {
        // 輸出菜單
        println("s(show): 顯示佇列")
        println("e(exit): 退出程式")
        println("a(add): 添加數據到佇列")
        println("g(get): 從佇列取出數據")
        println("h(head): 查看佇列頭的數據")
        key = readLine()!!.get(0) // 接收一個字符
        when (key) {
            's' -> {
                queue.showQueue()
            }
            'a' -> {
                println("輸入一個數")
                try {
                    val value = readLine()!!.toInt()
                    queue.addQueue(value)
                } catch (e: NumberFormatException) {
                    println("請輸入有效的數字~")
                }
            }
            'g' -> { // 取出數據
                try {
                    val res = queue.getQueue()
                    println("取出的數據是$res")
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            'h' -> { // 查看佇列頭的數據
                try {
                    val res = queue.headQueue()
                    println("佇列頭的數據是$res")
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            'e' -> { // 退出
                loop = false
            }
        }
    }
    println("程式退出~")
}

class CircleArrayQueue(private var maxSize: Int /*表示陣列的最大容量*/) {
    // front 變數的含義做一個調整: front 指向佇列的第一個元素, 也就是說 arr[front] 就是佇列的第一個元素
    // front 的初始值 = 0
    private var front: Int
    // rear 變數的含義做一個調整: rear 指向隊列的最後一個元素的後一個位置. 因為希望空出一個空間做為約定
    // rear 的初始值 = 0
    private var rear: Int
    private val arr: Array<Int> // 該陣列用於存放數據，模擬佇列

    init {
        front = 0
        rear = 0
        arr = Array(maxSize) { 0 }
    }

    // 判斷佇列是否滿
    fun isFull() = (rear + 1) % maxSize == front

    // 判斷佇列是否為空
    fun isEmpty() = rear == front

    // 添加數據到佇列
    fun addQueue(n: Int) {
        // 判斷佇列是否滿
        if (isFull()) {
            println("佇列滿，不能加入數據~")
            return
        }
        arr[rear] = n // 直接將數據加入
        rear = (rear + 1) % maxSize // 將 rear 後移，這裡必須考慮取模
    }

    // 取得佇列頭的數據，移出佇列
    fun getQueue(): Int {
        // 判斷佇列是否為空
        if (isEmpty()) {
            // 通過拋出異常
            throw RuntimeException("佇列空，不能取數據~")
        }
        // 這裡需要分析出 front 是指向佇列的第一個元素
        // 1. 先把 front 對應的值保留到一個臨時變數
        // 2. 將 front 後移，這裡必須考慮取模
        // 3. 將臨時保存的變數返回
        val value = arr[front]
        front = (front + 1) % maxSize
        return value
    }

    // 顯示佇列的所有數據
    fun showQueue() {
        // 判斷佇列是否為空
        if (isEmpty()) {
            println("佇列空，沒有數據~")
            return
        }
        // 遍歷佇列
        // 思路: 從 front 開始遍歷，遍歷多少個元素
        for (i in front until front + size()) {
            println("arr[${i % maxSize}]=${arr[i % maxSize]}")
        }
    }

    // 求出當前佇列有效數據的個數
    fun size() = (rear + maxSize - front) % maxSize

    // 顯示佇列頭的數據，注意不是取出數據
    fun headQueue(): Int {
        // 判斷佇列是否為空
        if (isEmpty()) {
            throw RuntimeException("佇列空，沒有數據~")
        }
        return arr[front]
    }

}
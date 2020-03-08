package queue

import java.lang.NumberFormatException
import java.lang.RuntimeException

fun main() {
    //測試
    //創建一個佇列
    val queue = ArrayQueue(3)
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

// 使用陣列模擬佇列 - 編寫一個 ArrayQueue 類別
class ArrayQueue(private var maxSize: Int /*表示陣列的最大容量*/) {
    private var front: Int // 佇列頭
    private var rear: Int // 佇列尾
    private val arr: Array<Int> // 該陣列用於存放數據，模擬佇列

    init {
        front = -1 // 指向佇列頭部,分析出 front 是指向佇列頭的前一個位置
        rear = -1 // 指向佇列尾,指向佇列尾的數據(即就是佇列最後一個數據)
        arr = Array(maxSize) { 0 }
    }

    // 判斷佇列是否滿
    fun isFull() = rear == maxSize - 1

    // 判斷佇列是否為空
    fun isEmpty() = rear == front

    // 添加數據到佇列
    fun addQueue(n: Int) {
        // 判斷佇列是否滿
        if (isFull()) {
            println("佇列滿，不能加入數據~")
            return
        }
        rear++ // 將 rear 後移
        arr[rear] = n
    }

    // 取得佇列頭的數據，移出佇列
    fun getQueue(): Int {
        // 判斷佇列是否為空
        if (isEmpty()) {
            // 通過拋出異常
            throw RuntimeException("佇列空，不能取數據~")
        }
        front++ // 將 front 後移
        return arr[front]
    }

    // 顯示佇列的所有數據
    fun showQueue() {
        // 判斷佇列是否為空
        if (isEmpty()) {
            println("佇列空，沒有數據~")
            return
        }
        // 遍歷佇列
        for ((index, data) in arr.withIndex()) {
            println("arr[$index]=$data")
        }
    }

    // 顯示佇列頭的數據，注意不是取出數據
    fun headQueue(): Int {
        // 判斷佇列是否為空
        if (isEmpty()) {
            throw RuntimeException("佇列空，沒有數據~")
        }
        return arr[front + 1]
    }

}
package stack

import java.lang.NumberFormatException
import java.lang.RuntimeException

fun main() {
    // 測試一下 ArrayStack 是否正確
    // 先創建一個 ArrayStack 對象 -> 表示堆疊
    val stack = ArrayStack(4)
    var key = ""
    var loop = true
    while (loop) {
        println("show: 表示顯示堆疊")
        println("exit: 退出程式")
        println("push: 表示添加數據到堆疊(入堆疊)")
        println("pop: 表示從堆疊取出數據(出堆疊)")
        println("請輸入您的選擇")
        key = readLine()!!
        when (key) {
            "show" -> {
                stack.list()
            }
            "push" -> {
                println("請輸入一個數")
                try {
                    val value = readLine()!!.toInt()
                    stack.push(value)
                } catch (e: NumberFormatException) {
                    println("請輸入有效的數字~")
                }
            }
            "pop" -> {
                try {
                    val res = stack.pop()
                    println("出堆疊的數據是 $res")
                } catch (e: Exception) {
                    println(e.message)
                }
            }
            "exit" -> {
                loop = false
            }
            else -> {
            }
        }
    }
    println("程式退出~")
}

// 定義一個 ArrayStack 表示堆疊
class ArrayStack(private val maxSize: Int/*堆疊的大小*/) {
    private val stack: Array<Int> // 陣列模擬堆疊，數據放在該陣列
    private var top = -1 // top 表示堆疊頂部，初始化為-1

    init {
        stack = Array(maxSize) { 0 }
    }

    // 堆疊滿
    fun isFull() = top == maxSize - 1

    // 堆疊空
    fun isEmpty() = top == -1

    // 入堆疊 - push
    fun push(value: Int) {
        // 先判斷堆疊是否滿
        if (isFull()) {
            println("堆疊滿")
            return
        }
        top++
        stack[top] = value
    }

    // 出堆疊 - pop，將堆疊頂部的數據返回
    fun pop(): Int {
        // 先判斷堆疊是否空
        if (isEmpty()) {
            throw RuntimeException("堆疊空，沒有數據~")
        }
        val value = stack[top]
        top--
        return value
    }

    // 顯示堆疊的情況[遍歷堆疊]，遍歷時需要從堆疊頂部開始顯示數據
    fun list() {
        if (isEmpty()) {
            println("堆疊空，沒有數據~")
            return
        }
        // 需要從堆疊頂部開始顯示數據
        for (i in top downTo 0) {
            println("stack[$i]=${stack[i]}")
        }
    }
}
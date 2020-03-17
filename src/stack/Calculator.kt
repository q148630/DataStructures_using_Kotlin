package stack

import java.lang.RuntimeException

fun main() {
    // 測試表達式的運算
    val expression = "3+2*6-2"
    // 創建兩個堆疊，數值堆疊 & 運算符堆疊
    val numStack = ArrayStack2(10)
    val operStack = ArrayStack2(10)
    // 定義需要的相關變數
    var num1 = 0
    var num2 = 0
    var oper = 0
    var res = 0
    var keepNum = "" // 用於拼接多位數

    // 開始 for 循環，掃描 expression
    for ((i, ch) in expression.withIndex()/*依次得到 expression 的每一個字符*/) {
        // 判斷 ch 是什麼，然後做相應的處理
        if (operStack.isOper(ch)) { // 如果是運算符
            // 判斷當前的運算符堆疊是否為空
            if (!operStack.isEmpty()) {
                // 如果運算符堆疊有 operator，就進行比較，如果當前的 operator 優先級 <= 堆疊頂部的 operator，
                // 就需要從數值堆疊中 pop 出兩個數，在從運算符堆疊中 pop 出一個 operator，進行運算，
                // 將得到的數值結果 push 入數值堆疊，然後將當前的 operator push入運算符堆疊
                if (operStack.priority(ch.toInt()) <= operStack.priority(operStack.peek())) {
                    num1 = numStack.pop()
                    num2 = numStack.pop()
                    oper = operStack.pop()
                    res = numStack.cal(num1, num2, oper)
                    numStack.push(res) // 將運算的結果 push 入數值堆疊
                    operStack.push(ch.toInt()) // 然後將當前的 operator push入運算符堆疊
                } else {
                    // 如果當前的 operator 優先級大於堆疊中的 operator，就直接 push 入運算符堆疊
                    operStack.push(ch.toInt())
                }
            } else {
                // 運算符堆疊如果為空直接把 operator push入即可
                operStack.push(ch.toInt())
            }
        } else {
            // 如果是數值，則直接 push 入數值堆疊 (處理單位數的思路)
            // numStack.push(ch.toInt() - 48) // ch.toInt() - 48 (0 的 ASCII 碼)

            // 處理多位數的思路
            // 1. 當處理多位數時，不能發現是一個數就立即入堆疊，因為有可能是多位數
            // 2. 在處理時，需要向 expression 表達式的 index 後再看一位，如果是數值就繼續掃描，如果是 operator 才入堆疊
            // 3. 因此我們需要定義一個字串變數，用於拼接
            keepNum += ch // 處理多位數

            // 1. 如果已經掃描到 expression 的最後一位數，就直接入數值堆疊 (避免 StringIndexOutOfBoundsException)
            // 2. 判斷下一個字符是不是數字，如果是數字就繼續掃描，如果是 operator 則入堆疊
            if (i == expression.lastIndex || operStack.isOper(expression[i + 1])) {
                numStack.push(keepNum.toInt()) // 如果後一位是 operator，則入數值堆疊
                keepNum = "" // 將 keepNum 清空，很重要!
            }
        }
    }

    // 當表達式掃描完畢後，就順序的從數值堆疊和運算符堆疊中 pop 出相應的數值和 operator，並運算
    while (!operStack.isEmpty()/*如果運算符堆疊為空，表示計算到最後的結果，數值堆疊中只有一個數字[結果]*/) {
        num1 = numStack.pop()
        num2 = numStack.pop()
        oper = operStack.pop()
        res = numStack.cal(num1, num2, oper)
        numStack.push(res) // 運算結果 push 入堆疊
    }
    // 將數值堆疊的最後一個數值 pop 出，就是結果
    val res2 = numStack.pop()
    println("表達式$expression = $res2")
}

// 先創建一個堆疊，直接使用先前創建好的(ArrayStack)
// 定義一個 ArrayStack2 表示堆疊，需要擴展功能
class ArrayStack2(private val maxSize: Int/*堆疊的大小*/) {
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


    // 返回運算符(operator)的優先級，優先級是 Programmer 來確定，優先級使用數字表示
    // 數字越大，則優先級越高
    fun priority(oper: Int) = when (oper) {
        '*'.toInt(), '/'.toInt() -> {
            1
        }
        '+'.toInt(), '-'.toInt() -> {
            0
        }
        else -> {
            -1 // 假定目前的 operator 只有 +, -, *, /
        }
    }

    // 判斷是不是一個運算符(operator)
    fun isOper(value: Char) = value == '+' || value == '-' || value == '*' || value == '/'

    // 計算方法
    fun cal(num1: Int, num2: Int, oper: Int): Int {
        var res = 0 // res 用於存放計算的結果
        when (oper) {
            '+'.toInt() -> {
                res = num1 + num2
            }
            '-'.toInt() -> {
                res = num2 - num1 // 注意順序(先進堆疊的數擺前面)
            }
            '*'.toInt() -> {
                res = num1 * num2
            }
            '/'.toInt() -> {
                res = num2 / num1
            }
            else -> {
            }
        }
        return res
    }

    // 增加一個方法，可以返回當前堆疊頂部的值，但不是真正的 pop
    fun peek() = stack[top]

}
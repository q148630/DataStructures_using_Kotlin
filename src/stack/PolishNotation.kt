package stack

import java.beans.Expression
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    // 完成一個中序表達式轉後序表達式的功能
    // 說明
    // 1. 1+((2+3)x4)-5 => 轉成 1 2 3 + 4 x + 5 -
    // 2. 因為直接對 string 進行操作不方便，因此先將 "1+((2+3)x4)-5" => 轉成中序表達式對應的 List
    // 即 "1+((2+3)x4)-5" => ArrayList [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5]
    // 3. 將得到的中序表達式對應的 List => 轉換成後序表達式的 List
    // 即 ArrayList [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5] => ArrayList [1, 2, 3, +, 4, x, +, 5, -]

    val expression = "1+((2+3)x4)-5"
    val infixExpressionList = toInfixExpressionList(expression)
    println("後序表達式對應的 List=$infixExpressionList") // ArrayList [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5]
    val suffixExpression = parseSuffixExpressionList(infixExpressionList)
    println("後序表達式對應的 List=$suffixExpression") // ArrayList [1, 2, 3, +, 4, x, +, 5, -]

    println("expression=${calculate(suffixExpression)}")

    /*
    // 先定義 逆波蘭表達式
    // (3+4)x5-6 => 3 4 + 5 x 6 - => 29
    // 4 x 5 - 8 + 60 + 8 / 2 => 4 5 x 8 - 60 + 8 2 / + => 76
    // 說明為了方便，逆波蘭表達式的數字和 operator 用空白隔開
    val suffixExpression = "3 4 + 5 x 6 -"
    // 思路
    // 1. 先將 "3 4 + 5 x 6 -" => 放到 ArrayList 中
    // 2. 將 ArrayList 傳遞給一個方法，遍歷 ArrayList 配合堆疊完成計算
    val rpnList = getListString(suffixExpression)
    println("rpnList=$rpnList")
    val res = calculate(rpnList)
    println("計算的結果是=$res")
    */
}


// 即 ArrayList [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5] => ArrayList [1, 2, 3, +, 4, x, +, 5, -]
// 方法: 將得到的中序表達式對應的 List => 轉換成後序表達式的 List
fun parseSuffixExpressionList(ls: List<String>): List<String> {
    // 定義兩個堆疊
    val s1 = Stack<String>() // 運算符堆疊
    // 說明: 因為 s2 這個堆疊，在整個轉換當中，沒有 pop 操作，而且後面我們還需要逆序輸出
    // 因此比較麻煩，這裡我們就不用 Stack<String>，而是用 List<String> s2
//    val s2 = Stack<String>() // 儲存中間結果的堆疊 s2
    val s2 = ArrayList<String>() // 儲存中間結果的List s2

    // 遍歷 ls
    for (item in ls) {
        // 如果是一個數字，直接加入 s2
        if (item.matches(Regex("\\d+"))) {
            s2.add(item)
        } else if (item == "(") {
            s1.push(item)
        } else if (item == ")") {
            // 如果是右括號")"，則依次 pop 出 s1 堆疊頂的運算符，並加入 s2，直到遇到左括號為止，此時將這一對括號丟棄
            while (s1.peek() != "(") {
                s2.add(s1.pop())
            }
            s1.pop() // 將 "(" pop 出 s1 堆疊，消除小括號
        } else {
            // 當 item 的優先級 <= s1 堆疊頂部的 operator，將 s1 堆疊頂部的 operator pop 出並加入到 s2 中，
            // 再次進入下個循環與 s1 中新的堆疊頂部的 operator 相比較
            // 問題: 我們要加入一個比較優先級高低的方法
            while (s1.size != 0 && Operator.getValue(s1.peek()) >= Operator.getValue(item)) {
                s2.add(s1.pop())
            }
            // 還需要將 item push 入 s1 堆疊
            s1.push(item)
        }
    }

    // 將 s1 中剩餘的 operator 依次 pop 出並加入到 s2
    while (s1.size != 0) {
        s2.add(s1.pop())
    }
    return s2 // 注意: 因為是存放到 List，所以按順序輸出就是對應的後序表達式的 List
}


// 方法: 將中序表達式轉成對應的 List
// s = "1+((2+3)x4)-5"
fun toInfixExpressionList(s: String): List<String> {
    // 定義一個 List，存放中序表達式對應的內容
    val ls = ArrayList<String>()
    var str = "" // 對多位數的拼接

    for ((i, item) in s.withIndex()) {
        // 如果是一個非數字(即為 operator)，就直接加入到 ls
        if (item.toInt() !in 48..57) { // '0'[48] -> '9'[57]
            ls.add(item.toString())
        } else { // 如果是一個數字，則需要考慮多位數問題
            str += item // 拼接

            // 1. 如果已經掃描到 expression 的最後一位數，就直接入堆疊 (避免 StringIndexOutOfBoundsException)
            // 2. 判斷下一個字符是不是數字，如果是數字就繼續掃描，如果是 operator 則入堆疊
            if (i == s.lastIndex || (s[i+1].toInt() !in 48..57)) {
                ls.add(str)
                str = "" // 將 str 清空，很重要!
            }
        }
    }
    return ls
}

// 將一個逆波蘭表達式，依次將數值和 operator 放入 ArrayList 中
fun getListString(suffixExpression: String): List<String> {
    // 將 suffixExpression 分割
    val split = suffixExpression.split(" ")
    return ArrayList<String>(split)

}


/**
 * 方法: 完成對逆波蘭表達式的運算 (3 4 + 5 x 6 -)
 * 1. 從左到右掃描，將 3 和 4 push 入堆疊
 * 2. 遇到 + 運算符，因此 pop 出 4 和 3 (4 為堆疊頂部元素，3 為次頂部元素)，計算 3 + 4 的值，得 7，再將 7 push 入堆疊
 * 3. 將 5 push 入堆疊
 * 4. 接下來是 x 運算符，因此 pop 出 5 和 7，計算出 7 x 5 = 35，將 35 push 入堆疊
 * 5. 將 6 push 入堆疊
 * 6. 最後是 - 運算符，因此 pop 出 6 和 35，計算出 35 - 6 = 29，將 29 push 入堆疊
 * 7. 返回堆疊中最後一個數值 (該數值就是計算結果)
 */
fun calculate(ls: List<String>): Int {
    // 創建堆疊，只需要一個堆疊即可
    val stack = Stack<String>()
    // 遍歷 ls
    for (item in ls) {
        // 這裡使用正則表達式來取出數字
        if (item.matches(Regex("\\d+"))) { // 匹配的是多位數
            stack.push(item) // push 入堆疊
        } else {
            // pop 出兩個數，並運算，再將結果 push 入堆疊
            val num2 = stack.pop().toInt()
            val num1 = stack.pop().toInt()
            val res = when (item) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "x" -> num1 * num2
                "/" -> num1 / num2
                else -> throw RuntimeException("運算符有誤")
            }
            stack.push(res.toString()) // 將運算結果 push 入堆疊
        }
    }
    // 最後留在 stack 中的數值，就是運算結果
    return stack.pop().toInt()
}

// 定義一個類別 Operator 可以返回一個運算符對應的優先級
class Operator {
    companion object {
        private const val ADD = 1
        private const val SUB = 1
        private const val MUL = 2
        private const val DIV = 2

        // 定義一個方法，返回對應的優先級數字
        fun getValue(operator: String) = when(operator) {
            "+" -> ADD
            "-" -> SUB
            "x" -> MUL
            "/" -> DIV
            else -> {
                println("不存在該運算符: \"$operator\"")
                0 // 要比上面的 ADD、SUB、MUL、DIV 都還要小，
                  // 因為 "(" 也會在 s1 堆疊裡，如果比較時優先級比他們高，會被 pop 出來並加入到 s2 裡面，會產生 Bug
            }
        }
    }
}
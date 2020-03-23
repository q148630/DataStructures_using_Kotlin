package recursion

import kotlin.math.abs

const val max = 8 // 定義一個 max 表示共有幾個皇后
val array = IntArray(max) // 定義陣列 array，保存皇后擺放位置的結果，例如 arr = {0, 4, 7, 5, 2, 6, 1, 3}
var count = 0
var judgeCount = 0
fun main() {
    // 測試，8皇后是否正確
    check(0)
    println("總共有${count}種擺法") // 92
    println("總共判斷衝突的次數${judgeCount}次") // 15720
}

// 定義一個方法，放置第 n 個皇后
// 特別注意: check 是每一次遞迴時，進入到 check 中都有 for(i in 0 until max)，因此會有回溯
fun check(n: Int) {
    if (n == max) { // n = 8，表示 8 個皇后已擺放好
        printArr()
        return
    }

    // 依次擺放皇后，並判斷是否有衝突
    for (i in 0 until max) {
        array[n] = i // 先把當前這個皇后 n，擺放到該列的第 1 行
        // 判斷當擺放第 n 個到 i 行時，是否有衝突
        if (judge(n)) { // 不衝突
            check(n + 1) // 接著擺放 n + 1 個皇后，即開始遞迴
        }
        // 如果衝突，就繼續執行下個循環 array[n] = i，即是將第 n 個皇后，擺放在本列中的下一行位置(後移)
    }
}

/**
 * 方法: 查看當我們擺放第 n 個皇后，就去判斷該皇后是否和前面已經擺放的皇后衝突
 * 衝突表示與前面擺放的皇后在同一列、或同一行、或同一斜線上
 * @param n 表示第 n + 1 個皇后
 * @return true:表示沒有衝突，false:表示有衝突
 */
fun judge(n: Int): Boolean {
    judgeCount++ // 表示總共判斷衝突幾次
    for (i in 0 until n) {
        // 說明
        // 1. array[i] == array[n] 表示判斷第 n 個皇后是否和前面的 n - 1 個皇后在同一行
        // 2. abs(n - i) == abs(array[n] - array[i]) 表示判斷第 n 個皇后是否和第 i 個皇后在同一斜線
        // 假設 n = 1、i = 1，擺放第 2 列第 2 行，array[1] = 1
        // abs(1 - 0) = 1，abs(array[n] - array[i]) = abs(1 - 0) = 1
        // 3. 判斷是否在同一列，沒有必要，因為 n 每次都在遞增
        if (array[i] == array[n] || abs(n - i) == abs(array[n] - array[i])) {
            return false
        }
    }
    return true
}

// 定義一個方法，可以將皇后擺放的位置輸出
fun printArr() {
    count++ // 表示找到其中一種沒有衝突的擺法
    for (i in 0 until max) {
        print("${array[i]} ")
    }
    println()
}
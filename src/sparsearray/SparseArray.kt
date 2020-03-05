package sparsearray

fun main(args: Array<String>) {
    // 創建一個原始的二維陣列 11 * 11
    // 0: 表示沒有棋子, 1: 表示黑子, 2: 表示藍子
    val chessArr1 = Array(11) { IntArray(11) { 0 } }
    chessArr1[1][2] = 1
    chessArr1[2][3] = 2
    chessArr1[4][5] = 2
    // 輸出原始的二維陣列
    println("原始的二維陣列")
    for (row in chessArr1) {
        for (data in row) {
            print("$data\t")
        }
        println()
    }

    // 將二元陣列 轉 稀疏陣列的思路
    // 1. 先遍歷二元陣列，得到非0數據的個數
    var sum = 0
    for (row in chessArr1) {
        for (data in row) {
            if (data != 0) {
                sum++
            }
        }
    }

    // 2. 創建對應的稀疏陣列
    val sparseArr = Array(sum + 1) { Array(3) { 0 } }
    // 給稀疏陣列賦值
    sparseArr[0][0] = 11
    sparseArr[0][1] = 11
    sparseArr[0][2] = sum

    // 遍歷二維陣列，將非0的值存放到 sparseArr 中
    var count = 0 // count 用於紀錄是第幾個非0數據
    for (rowIndex in chessArr1.indices) {
        for (colIndex in chessArr1[rowIndex].indices) {
            if (chessArr1[rowIndex][colIndex] != 0) {
                count++
                sparseArr[count][0] = rowIndex
                sparseArr[count][1] = colIndex
                sparseArr[count][2] = chessArr1[rowIndex][colIndex]
            }
        }
    }

    //輸出稀疏陣列的形式
    println()
    println("得到的稀疏陣列為~~~")
    for (row in sparseArr) {
        print("${row[0]}\t${row[1]}\t${row[2]}\n")
    }
    println()

    // 將稀疏陣列 ==> 恢復成原始的二維陣列
    /*
        1. 先讀取稀疏陣列的第一列,根據第一列的數據,創建原始的二維陣列,比如上面的 chessArr2 = int
        2. 在讀取稀疏陣列後幾列的數據,並賦給原始的二維陣列即可.
    */
    // 1. 先讀取稀疏陣列的第一列,根據第一列的數據,創建原始的二維陣列
    val chessArr2 = Array(sparseArr[0][0]) {Array(sparseArr[0][1]) {0} }

    // 2. 在讀取稀疏陣列後幾列的數據(從第二列開始),並賦給原始的二維陣列即可
    for (rowIndex in 1..sparseArr.lastIndex) {
        chessArr2[sparseArr[rowIndex][0]][sparseArr[rowIndex][1]] = sparseArr[rowIndex][2]
    }

    // 輸出恢復後的二維陣列
    println()
    println("恢復後的二維陣列")

    for (row in chessArr2) {
        for (data in row) {
            print("$data\t")
        }
        println()
    }
}

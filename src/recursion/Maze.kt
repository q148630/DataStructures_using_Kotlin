package recursion

fun main() {
    // 先創建一個二維陣列，模擬迷宮
    // 地圖
    val map = Array(8) { IntArray(7) { 0 } }
    // 使用 1 表示牆
    // 上下全部設置為 1
    for (i in 0..map[0].lastIndex) {
        map[0][i] = 1
        map[map.lastIndex][i] = 1
    }
    // 左右全部設置為 1
    for (i in 0..map.lastIndex) {
        map[i][0] = 1
        map[i][map[i].lastIndex] = 1
    }
    // 設置擋板，表示: 1
    map[3][1] = 1
    map[3][2] = 1
    // 輸出地圖
    println("地圖的情況")
    for (i in 0..map.lastIndex) {
        for (j in 0..map[i].lastIndex) {
            print("${map[i][j]} ")
        }
        println()
    }

    // 使用遞迴回溯給小球找路
    setWay(map, 1, 1)

    // 輸出新的地圖，小球走過，並標記過的遞迴路徑
    println("地圖的情況")
    for (i in 0..map.lastIndex) {
        for (j in 0..map[i].lastIndex) {
            print("${map[i][j]} ")
        }
        println()
    }
}

// 使用遞迴回溯來給小球找路
// 說明
// 1. map 表示地圖
// 2. i, j 表示從地圖的哪個位置開始出發 (1, 1)
// 3. 如果小球能到 map[6][5] 位置，則說明找到道路
// 4. 約定: 當 map[i][j] 為 0 表示該點沒有走過、為 1 表示牆、為 2 表示道路可以走(下、右、上、左還沒全部遞迴完)、為 3 表示該點已經走過但是不通
// 5. 在走迷宮時，需要確定一個策略(方法) 下->右->上->左，如果該點走不通，再回溯
/**
 * 該方法所產生的路徑與所決定的策略有關，例如:(下->右->上->左) 與 (上->右->下->左) 所產生的路徑可能不同
 * 由於還沒有利用其他尋找最短路徑的演算法，此時可以利用已走過的點(2)的個數來判斷路徑長短，在此我們的策略使用(下->右->上->左)
 * @param map 表示地圖
 * @param i 從哪個位置開始找 (列)
 * @param i 從哪個位置開始找 (行)
 * @return 如果找到通路，就返回 true，否則返回 false
 */
fun setWay(map: Array<IntArray>, i: Int, j: Int): Boolean {
    if (map[6][5] == 2) { // 道路已經找到了
        return true
    } else {
        if (map[i][j] == 0) { // 如果當前這個點還沒有走過
            // 按照策略 (下->右->上->左) 走
            map[i][j] = 2 // 假定該點是可以走通，但(下->右->上->左)還沒全部遞迴完
            if (setWay(map, i + 1, j)) { // 向下走
                return true
            } else if (setWay(map, i, j + 1)) { // 向右走
                return true
            } else if (setWay(map, i - 1, j)) { // 向上走
                return true
            } else if (setWay(map, i, j - 1)) { // 向左走
                return true
            } else {
                map[i][j] = 3 // 說明該點走不通，是死路
                return false
            }
        } else { // 如果 map[i][j] != 0，可能是 (1、2、3)
            return false
        }
    }
}
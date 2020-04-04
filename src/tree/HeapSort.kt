package tree

import java.text.SimpleDateFormat
import java.util.*

/**
 * Heap Sort 是一種選擇排序
 * 它的 最壞、最好、平均 時間複雜度都是 O(nlogn)
 * 屬於不穩定排序
 */
fun main() {
    // 要求將陣列進行升序排序
//    val arr = intArrayOf(4, 6, 8, 5, 9)

    // 建立 8000000 個隨機值的陣列
    val arr = IntArray(8000000)
    for (i in 0 until 8000000) {
        arr[i] = (Math.random() * 8000000).toInt() // 隨機產生一個 [0, 8000000) 的值
    }
//    println("排序前")
    val date1 = Date()
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date1String = simpleDateFormat.format(date1)
    println("排序前的時間是=$date1String")

    heapSort(arr)

    val date2 = Date()
    val date2String = simpleDateFormat.format(date2)
    println("排序後的時間是=$date2String")
}

// 定義一個 Heap Sort 方法
// Max Heap(升序)，Min Heap(降序)
fun heapSort(arr: IntArray) {
    // 分步完成
//    adjustHeap(arr, 1, arr.size)
//    println("第一次: ${arr.contentToString()}") // 4, 9, 8, 5, 6
//    adjustHeap(arr, 0, arr.size)
//    println("第二次: ${arr.contentToString()}") // 9, 6, 8, 5, 4

    // 1. 將無序陣列調整成 Max Heap，根據升序或降序需求選擇 Max Heap 或 Min Heap
    for (i in (arr.size / 2) - 1 downTo 0) { // 從最後一個非葉子節點開始調整
        adjustHeap(arr, i, arr.size)
    }

    // 2. 將 Heap 頂元素與最後一個節點交換，將最大的元素交換到陣列末端
    // 3. 重新調整結構，使其滿足 Max Heap 定義，然後繼續交換 Heap 頂元素與最後一個節點，反覆執行挑整+交換步驟，直到排序完成
    for (j in arr.lastIndex downTo 1) {
        arr[j] = arr[0].also { arr[0] = arr[j] } // 交換 Heap 頂元素與最後一個節點
        adjustHeap(arr, 0, j) // 因為只改變了頂元素的值(非葉子節點)，所以只要調整 index為 i 的樹就好
    }

//    println("陣列=${arr.contentToString()}") // 這裡因為要測試執行速度(資料量太大)，所以就不輸出內容
}

// 將一個陣列(二元樹)，調整成一個 Max Heap
/**
 * 方法: 將 i 對應的非葉子節點的樹調整成 Max Heap
 * 舉例: arr {4, 6, 8, 5, 9} => i=1 => adjustHeap => 得到 {4, 9, 8, 5, 6}
 * 如果再次調用 adjustHeap 傳入的是 i=0 =>得到 {4, 9, 8, 5, 6} => {9, 6, 8, 5, 4}
 * @param arr 待調整的陣列
 * @param i 表示非葉子節點在陣列中的索引
 * @param length 表示對多少個元素繼續調整，length 是在逐漸地減少
 */
fun adjustHeap(arr: IntArray, i: Int, length: Int) {
    val temp = arr[i] // 先取出當前元素的值，保存在臨時變數
    // 開始調整
    // 說明:
    // 1. k = i * 2 + 1，k 是 i 節點的左子節點
    var k = (i * 2) + 1
    var currentChangeIndex = i // 保存所找到最大值的 index
    while (k < length) {
        if (k + 1 < length && arr[k] < arr[k + 1]) { // 說明左子節點的值小於右子節點的值
            k++ // k 指向右子節點
        }
        if (arr[k] > temp) { // 如果子節點大於父節點
            arr[currentChangeIndex] = arr[k] // 把較大的值賦值給當前節點
            currentChangeIndex = k //!!! currentChangeIndex 指向 k，繼續循環比較
        } else {
            break
        }
        k = k * 2 + 1
    }
    // 當循環結束後，我們已經將以 i 做為父節點的樹的最大值，放在了最頂部(i 位置)，局部子樹
    arr[currentChangeIndex] = temp // 將 temp 值放到調整後的位置
}
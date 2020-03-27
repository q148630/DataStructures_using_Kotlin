package tree

fun main() {
    val arr = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    // 創建一個 ArrBinaryTree
    val arrBinaryTree = ArrBinaryTree(arr)
    println("順序儲存二元樹，前序遍歷")
    arrBinaryTree.preOrder() // 1, 2, 4, 5, 3, 6, 7

    println("順序儲存二元樹，中序遍歷")
    arrBinaryTree.infixOrder() // 4, 2, 5, 1, 6, 3, 7

    println("順序儲存二元樹，後序遍歷")
    arrBinaryTree.postOrder() // 4, 5, 2, 6, 7, 3, 1
}

// 定義一個 ArrayBinaryTree，實現順序儲存二元樹遍歷
/**
 * 順序儲存二元樹的特點:
 * 1. 順序儲存二元樹通常只考慮完全二元樹
 * 2. 第 n 個元素的左子節點為 2 * n + 1
 * 3. 第 n 個元素的右子節點為 2 * n + 2
 * 4. 第 n 個元素的復節點為 (n - 1) / 2
 * 5. n: 表示二元樹中的第 n+1 個元素(因為下標從 0 開始)
 * Heap Sort 會使用到
 */
class ArrBinaryTree(private val arr: IntArray) {

    // 多載(Overload) preOrder
    fun preOrder() {
        this.preOrder(0)
    }

    // 多載(Overload) infixOrder
    fun infixOrder() {
        this.infixOrder(0)
    }

    // 多載(Overload) postOrder
    fun postOrder() {
        this.postOrder(0)
    }

    /**
     * 方法: 完成順序儲存二元樹的前序遍歷
     * @param index 陣列的下標
     */
    fun preOrder(index: Int) {
        // 如果陣列為空 arr.isEmpty()
        if (arr.isEmpty()) {
            println("陣列為空，不能按照二元樹的前序遍歷")
        }
        // 輸出當前這個元素
        println(arr[index])
        // 向左遞迴遍歷
        if ((2 * index + 1) < arr.size) {
            preOrder(2 * index + 1)
        }
        // 向右遞迴遍歷
        if ((2 * index + 2) < arr.size) {
            preOrder(2 * index + 2)
        }
    }

    /**
     * 方法: 完成順序儲存二元樹的中序遍歷
     * @param index 陣列的下標
     */
    fun infixOrder(index: Int) {
        // 如果陣列為空 arr.isEmpty()
        if (arr.isEmpty()) {
            println("陣列為空，不能按照二元樹的前序遍歷")
        }
        // 向左遞迴遍歷
        if ((2 * index + 1) < arr.size) {
            infixOrder(2 * index + 1)
        }
        // 輸出當前這個元素
        println(arr[index])
        // 向右遞迴遍歷
        if ((2 * index + 2) < arr.size) {
            infixOrder(2 * index + 2)
        }
    }

    /**
     * 方法: 完成順序儲存二元樹的後序遍歷
     * @param index 陣列的下標
     */
    fun postOrder(index: Int) {
        // 如果陣列為空 arr.isEmpty()
        if (arr.isEmpty()) {
            println("陣列為空，不能按照二元樹的前序遍歷")
        }
        // 向左遞迴遍歷
        if ((2 * index + 1) < arr.size) {
            postOrder(2 * index + 1)
        }
        // 向右遞迴遍歷
        if ((2 * index + 2) < arr.size) {
            postOrder(2 * index + 2)
        }
        // 輸出當前這個元素
        println(arr[index])
    }

}
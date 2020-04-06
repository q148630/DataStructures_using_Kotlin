package huffmantree

import kotlin.collections.ArrayList

/**
 * 路徑長度 = 該節點所在層數 - root所在節點的層數(1)
 * 帶權路徑長度 = 該節點權值 * 路徑長度
 * 所有葉子節點的帶權路徑長度之合，稱為 WPL(weighted path length)
 * WPL 最小的就是 Huffman Tree(霍夫曼樹)
 */
fun main() {
    val arr = intArrayOf(13, 7, 8, 3, 29, 6, 1)
    val root = createHuffmanTree(arr)

    // 測試
    preOrder(root) // 67, 29, 38, 15, 7, 8, 23, 10, 4, 1, 3, 6, 13
}

// 定義一個前序遍歷的方法
fun preOrder(root: Node?) {
    if (root != null) {
        root.preOrder()
    } else {
        println("是空樹，不能遍歷~")
    }
}

/**
 * 方法: 建立霍夫曼樹
 * @param arr 需要建立成霍夫曼樹的陣列
 * @return 建立好霍夫曼樹後的 root 節點
 */
fun createHuffmanTree(arr: IntArray): Node? {
    // 第一步為了操作方便
    // 1. 遍歷 arr 陣列
    // 2. 將 arr 中的每個元素建立成一個 Node
    // 3. 將 Node 放入到 ArrayList 中
    val nodes = ArrayList<Node>()
    for (value in arr) {
        nodes.add(Node(value))
    }

    // 建立霍夫曼樹的過程是一個循環
    while (nodes.size > 1) { // 當 ArrayList 中只剩一個節點，表示霍夫曼樹已經建立完成
        // 排序，從小到大
        nodes.sort() // 之所以可以使用這個方法，是因為 Node 實現了 Comparable 接口
//        println("nodes = $nodes")

        // 取出根節點權值最小的兩顆二元樹
        // (1) 取出權值最小的節點 (二元樹)
        val leftNode = nodes[0]
        // (2) 取出權值第二小的節點 (二元樹)
        val rightNode = nodes[1]

        // (3) 建立一個新的二元樹
        val parent = Node(leftNode.value + rightNode.value)
        parent.left = leftNode
        parent.right = rightNode

        // (4) 從 ArrayList 刪除處理過的兩個二元樹
        nodes.remove(leftNode)
        nodes.remove(rightNode)

        // (5) 將 parent 加入到 nodes，之後進行下一次的循環
        nodes.add(parent)
    }
    return nodes[0] // 返回霍夫曼樹的 root 節點
}

// 定義節點類別
// 為了讓 Node 支持排序，Collections 集合排序
// 讓 Node 實現 Comparable 接口
data class Node(
    var value: Int, // 節點權值
    var left: Node? = null, // 指向左子節點
    var right: Node? = null // 指向右子節點
) : Comparable<Node> {

    // 定義一個前序遍歷方法
    fun preOrder() {
        println(this)
        if (this.left != null) {
            this.left!!.preOrder()
        }
        if (this.right != null) {
            this.right!!.preOrder()
        }
    }

    override fun toString(): String {
        return "Node [value=$value]"
    }

    override fun compareTo(other: Node): Int {
         return this.value - other.value // 表示從小到大排序
    }

}
package tree

fun main() {
    // 創建需要的節點
    val root = HeroNode(1, "Hero1")
    val node2 = HeroNode(2, "Hero2")
    val node3 = HeroNode(3, "Hero3")
    val node4 = HeroNode(4, "Hero4")
    val node5 = HeroNode(5, "Hero5")

    // 說明: 我們先手動創建該二元樹，後面我們學習用遞迴的方式創建二元樹
    root.left = node2
    root.right = node3
    node3.right = node4
    node3.left = node5
    // 創建一顆二元樹
    val binaryTree = BinaryTree(root)

    // 測試
    println("前序遍歷") // 1, 2, 3, 5, 4
    binaryTree.preOrder()

    // 測試
    println("中序遍歷") // 2, 1, 5, 3, 4
    binaryTree.infixOrder()

    // 測試
    println("後序遍歷") // 2, 5, 4, 3, 1
    binaryTree.postOrder()
}

// 定義 BinaryTree 二元樹
class BinaryTree(private var _root:HeroNode? = null) {
    val root: HeroNode?
        get() = _root

    // 前序遍歷
    fun preOrder() {
        if (_root != null) {
            _root!!.preOrder()
        } else {
            println("二元樹為空，無法遍歷")
        }
    }

    // 中序遍歷
    fun infixOrder() {
        if (_root != null) {
            _root!!.infixOrder()
        } else {
            println("二元樹為空，無法遍歷")
        }
    }

    // 後序遍歷
    fun postOrder() {
        if (_root != null) {
            _root!!.postOrder()
        } else {
            println("二元樹為空，無法遍歷")
        }
    }
}

// 先創建 HeroNode 節點
data class HeroNode(val no: Int, var name: String, var left: HeroNode? = null, var right: HeroNode? = null) {
    override fun toString(): String {
        return "HeroNode [no=$no, name=$name]"
    }

    // 定義前序遍歷的方法
    fun preOrder() {
        println(this) // 先輸出父節點
        // 遞迴向左子樹前序遍歷
        if (left != null) {
            left!!.preOrder()
        }
        // 遞迴向右子樹前序遍歷
        if (right != null) {
            right!!.preOrder()
        }
    }

    // 定義中序遍歷的方法
    fun infixOrder() {
        // 遞迴向左子樹中序遍歷
        if (left != null) {
            left!!.infixOrder()
        }
        println(this) // 輸出父節點
        // 遞迴向右子樹中序遍歷
        if (right != null) {
            right!!.infixOrder()
        }
    }

    // 定義後序遍歷的方法
    fun postOrder() {
        // 遞迴向左子樹後序遍歷
        if (left != null) {
            left!!.postOrder()
        }
        // 遞迴向右子樹後序遍歷
        if (right != null) {
            right!!.postOrder()
        }
        println(this) // 輸出父節點
    }
}
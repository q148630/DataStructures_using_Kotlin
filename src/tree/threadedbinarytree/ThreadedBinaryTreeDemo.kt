package tree.threadedbinarytree

fun main() {
    // 測試中序引線二元樹的功能 {8, 3, 10, 1, 14, 6}
    val root = HeroNode(1, "tom")
    val node2 = HeroNode(3, "jack")
    val node3 = HeroNode(6, "smith")
    val node4 = HeroNode(8, "mary")
    val node5 = HeroNode(10, "king")
    val node6 = HeroNode(14, "dim")

    // 二元樹
    root.left = node2
    root.right = node3
    node2.left = node4
    node2.right = node5
    node3.left = node6

    // 測試中序引線化 {8, 3, 10, 1, 14, 6}
    val threadedBinaryTree = ThreadedBinaryTree(root)
    threadedBinaryTree.infixOrderThreadedNodes()
    // 測試: 以 10 號節點來測試
    val leftNode = node5.left
    val rightNode = node5.right
    println("10 號節點的前驅節點是 = $leftNode") // 3
    println("10 號節點的後繼節點是 = $rightNode") // 1

    // 當引線化二元樹後，不能再使用原來的遍歷方法
//    threadedBinaryTree.infixOrder()
    println("使用引線化的方式遍歷 引線二元樹")
    threadedBinaryTree.infixOrderThreadedList() // 8, 3, 10, 1, 14, 6
}

// 定義 ThreadedBinaryTree 引線二元樹
/**
 * 引線二元樹
 * 由於 n 個元素的一般二元樹建立好之後，會有 n + 1 個節點沒有指向子節點
 * 利用中序引線化來建立引線二元樹，讓原本為 null 的 pointer 指向 前驅 或 後繼節點
 * 以便讓我們能不用遞迴的來遍歷二元樹
 */
class ThreadedBinaryTree(private var _root: HeroNode? = null) {
    val root: HeroNode?
        get() = _root

    // 為了實現引線化，需要創建要給指向當前節點的前驅節點的變數(指針)
    // 在遞迴進行引線化時，pre 總是保留前一個節點
    private var pre: HeroNode? = _root

    // 遍歷中序引線二元樹的方法
    fun infixOrderThreadedList() {
        // 定義一個變數，儲存當前遍歷的節點，從 root 開始
        var node = _root
        while (node != null) {
            // 循環找到 leftType == 1 的節點(指向前驅節點)，第一個找到的就是 8 節點
            // 後面隨著遍歷而變化，因為當 leftType == 1 時，說明該節點是按照引線化處理後的有效節點(前驅節點)
            while (node!!.leftType == 0) {
                node = node.left
            }

            // 輸出當前節點
            println(node)

            // 如果當前節點的右指針指向的是後繼節點，就一直輸出
            while (node!!.rightType == 1) {
                // 獲取到當前節點的後繼節點
                node = node.right
                println(node)
            }
            // 替換這個遍歷的節點(當右指針指向右子樹，而非後繼節點時)
            node = node.right
        }
    }

    // 遍歷前序引線二元樹的方法
    fun preOrderThreadedList() {
        // 定義一個變數，儲存當前遍歷的節點，從 root 開始
        var node = _root
        while (node != null) {
            // 循環找到 leftType == 1 的節點(指向前驅節點)，表示左子樹已找完
            // 後面隨著遍歷而變化，因為當 leftType == 1 時，說明該節點是按照引線化處理後的有效節點(前驅節點)
            while (node!!.leftType == 0) {
                // 輸出當前節點
                println(node)
                node = node.left
            }

            // 輸出最左邊節點
            println(node)

            // 如果當前節點的右指針指向的是後繼節點，就一直輸出
            while (node!!.rightType == 1) {
                // 獲取到當前節點的後繼節點
                node = node.right
                println(node)
            }
            // 替換這個遍歷的節點(當右指針指向右子樹，而非後繼節點時)
            node = node.right
        }
    }

    // 遍歷後序引線二元樹的方法
    fun postOrderThreadedList() {
        postOrderThreadedList1(_root?.left)
        postOrderThreadedList1(_root?.right)
    }
    fun postOrderThreadedList1(curNode: HeroNode?) {
        // 定義一個變數，儲存當前遍歷的節點，從 root 開始
        var node = curNode
        do {
            // 循環找到 leftType == 1 的節點(指向前驅節點)，第一個找到的就是 8 節點
            // 後面隨著遍歷而變化，因為當 leftType == 1 時，說明該節點是按照引線化處理後的有效節點(前驅節點)
            while (node!!.leftType == 0) {
                node = node.left
            }

            // 輸出當前節點
            println(node)

            // 如果當前節點的右指針指向的是後繼節點，就一直輸出
            while (node!!.rightType == 1) {
                // 獲取到當前節點的後繼節點
                node = node.right
                println(node)
            }
            // 替換這個遍歷的節點(當右指針指向右子樹，而非後繼節點時)
            node = node.right
        } while (node != null && node.rightType != 1)
    }

    /**
     * 方法: 對二元樹進行中序引線化
     * @param node 就是當前需要引線化的節點
     */
    fun infixOrderThreadedNodes(node: HeroNode? = _root) {
        // 如果 node == null，不能引線化
        if (node == null) {
            return
        }

        // (一)先引線化左子樹
        infixOrderThreadedNodes(node.left)

        // (二)引線化當前節點[有難度]

        // 處理當前節點的前驅節點
        // 以 8 節點來理解 {8, 3, 10, 1, 14, 6}
        // 8 節點的 left = null，8 節點的 leftType = 1
        if (node.left == null) {
            // 讓當前節點的左指針指向前驅節點
            node.left = pre
            // 修改當前節點的左指針類型，1 表示指向前驅節點，而非指向左子樹
            node.leftType = 1
        }

        // 處理當前節點的後繼節點
        if (pre != null && pre!!.right == null) {
            // 讓前驅節點的右指針指向當前節點
            pre!!.right = node
            // 修改前驅節點的右指針類型
            pre!!.rightType = 1
        }
        // !!! 每處理一個節點後，讓當前節點成為下一個節點的前驅節點
        pre = node

        // (三)再引線化右子樹
        infixOrderThreadedNodes(node.right)
    }

    /**
     * 方法: 對二元樹進行前序引線化
     * @param node 就是當前需要引線化的節點
     */
    fun preOrderThreadedNodes(node: HeroNode? = _root) {
        // 如果 node == null，不能引線化
        if (node == null) {
            return
        }
        // (一)先引線化當前節點[有難度]

        // 處理當前節點的前驅節點
        // 以 1 節點來理解 {1, 3, 8, 10, 6, 14}
        // 1 節點的 left != null 所以不處理
        if (node.left == null) {
            // 讓當前節點的左指針指向前驅節點
            node.left = pre
            // 修改當前節點的左指針類型，1 表示指向前驅節點，而非指向左子樹
            node.leftType = 1
        }

        // 處理當前節點的後繼節點
        if (pre != null && pre!!.right == null) {
            // 讓前驅節點的右指針指向當前節點
            pre!!.right = node
            // 修改前驅節點的右指針類型
            pre!!.rightType = 1
        }
        // !!! 每處理一個節點後，讓當前節點成為下一個節點的前驅節點
        pre = node

        // (二)引線化左子樹
        if (node.leftType != 1) { // 這邊需要判斷是因為當前驅節點接上時，會與之前的節點形成無窮遞迴
            preOrderThreadedNodes(node.left)
        }

        // (三)再引線化右子樹
        if (node.rightType != 1) { // 這邊需要判斷是因為當後繼節點接上時，會與之前的節點形成無窮遞迴
            preOrderThreadedNodes(node.right)
        }
    }

    /**
     * 方法: 對二元樹進行後序引線化
     * @param node 就是當前需要引線化的節點
     */
    fun postOrderThreadedNodes(node: HeroNode? = _root) {
        // 如果 node == null，不能引線化
        if (node == null) {
            return
        }

        // (一)先引線化左子樹
        postOrderThreadedNodes(node.left)

        // (二)引線化右子樹
        postOrderThreadedNodes(node.right)

        // (三)再引線化當前節點[有難度]

        // 處理當前節點的前驅節點
        // 以 8 節點來理解 {8, 10, 3, 14, 6, 1}
        // 8 節點的 left = null，8 節點的 leftType = 1
        if (node.left == null) {
            // 讓當前節點的左指針指向前驅節點
            node.left = pre
            // 修改當前節點的左指針類型，1 表示指向前驅節點，而非指向左子樹
            node.leftType = 1
        }

        // 處理當前節點的後繼節點
        if (pre != null && pre!!.right == null) {
            // 讓前驅節點的右指針指向當前節點
            pre!!.right = node
            // 修改前驅節點的右指針類型
            pre!!.rightType = 1
        }
        // !!! 每處理一個節點後，讓當前節點成為下一個節點的前驅節點
        pre = node
    }

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

    // 前序搜尋
    fun preOrderSearch(no: Int): HeroNode? {
        if (_root != null) {
            return _root!!.preOrderSearch(no)
        } else {
            return null
        }
    }

    // 中序搜尋
    fun infixOrderSearch(no: Int): HeroNode? {
        if (_root != null) {
            return _root!!.infixOrderSearch(no)
        } else {
            return null
        }
    }

    // 後序搜尋
    fun postOrderSearch(no: Int): HeroNode? {
        if (_root != null) {
            return _root!!.postOrderSearch(no)
        } else {
            return null
        }
    }

    // 刪除節點
    fun delNode(no: Int) {
        if (_root != null) {
            // 如果只有一個 root 節點，這裡需要立刻判斷 root 是否就是要刪除的節點
            if (_root!!.no == no) {
                _root = null
            } else {
                _root!!.delNode(no) // 遞迴刪除
            }
        } else {
            println("空樹，不能刪除~")
        }
    }
}

// 先創建 HeroNode 節點
data class HeroNode(
    val no: Int,
    var name: String,
    var left: HeroNode? = null,
    var right: HeroNode? = null,
    // 說明
    // 1. 如果 leftType == 0 表示指向的是左子樹，如果為 1 則表示指向前驅節點
    // 1. 如果 rightType == 0 表示指向的是右子樹，如果為 1 則表示指向後繼節點
    var leftType: Int = 0,
    var rightType: Int = 0
) {
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

    /**
     * 方法: 前序遍歷搜尋
     * @param no 搜尋 no
     * @return 如果找到就返回該節點，否則返回 null
     */
    fun preOrderSearch(no: Int): HeroNode? {
        println("進入前序搜尋")
        // 判斷當前節點是不是要找的
        if (this.no == no) {
            return this
        }
        // 1. 則判斷當前節點的左子節點是否為空，如果不為空，則遞迴前序搜尋
        // 2. 如果左遞迴前序搜尋，找到節點，則返回
        var resNode: HeroNode? = null
        if (this.left != null) {
            resNode = this.left!!.preOrderSearch(no)
        }
        if (resNode != null) { // 說明我們在左子樹中找到
            return resNode
        }
        // 1. 左遞迴前序搜尋，找到節點，則返回，否則繼續判斷
        // 2. 當前的節點的右子節點是否為空，如果不為空，則繼續向右遞迴前序搜尋
        if (this.right != null) {
            resNode = this.right!!.preOrderSearch(no)
        }
        return resNode
    }

    /**
     * 方法: 中序遍歷搜尋
     * @param no 搜尋 no
     * @return 如果找到就返回該節點，否則返回 null
     */
    fun infixOrderSearch(no: Int): HeroNode? {
        // 1. 判斷當前節點的左子節點是否為空，如果不為空，則遞迴中序搜尋
        // 2. 如果左遞迴中序搜尋，找到節點，則返回
        var resNode: HeroNode? = null
        if (this.left != null) {
            resNode = this.left!!.infixOrderSearch(no)
        }
        if (resNode != null) { // 說明我們在左子樹中找到
            return resNode
        }
        println("進入中序搜尋")
        // 如果找到，則返回，如果沒有找到，則判斷當前節點是不是要找的
        if (this.no == no) {
            return this
        }
        // 1. 如果當前節點不是，則繼續判斷
        // 2. 當前的節點的右子節點是否為空，如果不為空，則繼續向右遞迴中序搜尋
        if (this.right != null) {
            resNode = this.right!!.infixOrderSearch(no)
        }
        return resNode
    }

    /**
     * 方法: 後序遍歷搜尋
     * @param no 搜尋 no
     * @return 如果找到就返回該節點，否則返回 null
     */
    fun postOrderSearch(no: Int): HeroNode? {

        // 1. 判斷當前節點的左子節點是否為空，如果不為空，則遞迴後序搜尋
        // 2. 如果左遞迴後序搜尋，找到節點，則返回
        var resNode: HeroNode? = null
        if (this.left != null) {
            resNode = this.left!!.postOrderSearch(no)
        }
        if (resNode != null) { // 說明我們在左子樹中找到
            return resNode
        }
        // 1. 如果左子樹沒有找到，則繼續判斷
        // 2. 當前的節點的右子節點是否為空，如果不為空，則繼續向右遞迴後序搜尋
        if (this.right != null) {
            resNode = this.right!!.postOrderSearch(no)
        }
        if (resNode != null) { // 說明我們在右子樹中找到
            return resNode
        }
        println("進入後序搜尋")
        // 如果左右子樹都沒有找到，則判斷當前節點是不是要找的
        if (this.no == no) {
            return this
        }
        return resNode
    }

    // 遞迴刪除節點
    // 1. 如果刪除的節點是葉子節點，則刪除該節點
    // 2. 如果刪除的節點是非葉子節點，則刪除該子樹 (二元搜尋樹時會視情況將左子樹或右子樹拉到該節點上，這裡先簡單處理)
    fun delNode(no: Int) {
        /**
         * 思路:
         * 1. 因為我們的二元樹是單向的，所以我們是判斷當前節點的子節點是否需要刪除，而不能去判斷當前這個節點是不是需要刪除
         * 2. 如果當前節點的左子節點不為空，並且左子節點就是要刪除的節點，就將 this.left = null，並且返回(結束遞迴)
         * 3. 如果當前節點的右子節點不為空，並且右子節點就是要刪除的節點，就將 this.right = null，並且返回(結束遞迴)
         * 4. 如果第 2 和 3 步沒有刪除節點，那麼我們就需要向左子樹進行遞迴刪除
         * 5. 如果第 4 步也沒有刪除節點，則要向右子樹進行遞迴刪除
         */
        // 2. 如果當前節點的左子節點不為空，並且左子節點就是要刪除的節點，就將 this.left = null，並且返回(結束遞迴)
        if (this.left != null && this.left!!.no == no) {
            this.left = null
            return
        }
        // 3. 如果當前節點的右子節點不為空，並且右子節點就是要刪除的節點，就將 this.right = null，並且返回(結束遞迴)
        if (this.right != null && this.right!!.no == no) {
            this.right = null
            return
        }
        // 4. 我們就需要向左子樹進行遞迴刪除
        if (this.left != null) {
            this.left!!.delNode(no)
        }
        // 5. 則要向右子樹進行遞迴刪除
        if (this.right != null) {
            this.right!!.delNode(no)
        }
    }
}
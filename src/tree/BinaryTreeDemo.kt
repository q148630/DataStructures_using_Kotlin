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

    // 前序搜尋
    // 前序遍歷的次數: 4
//    println("前序搜尋方式~")
//    val resNode = binaryTree.preOrderSearch(5)
//    if (resNode != null) {
//        println("找到了，訊息為 no=${resNode.no} name=${resNode.name}")
//    } else {
//        println("沒有找到 no = ${5} 的英雄")
//    }

    // 中序搜尋
    // 中序遍歷的次數: 3
    println("中序搜尋方式~")
    val resNode = binaryTree.infixOrderSearch(5)
    if (resNode != null) {
        println("找到了，訊息為 no=${resNode.no} name=${resNode.name}")
    } else {
        println("沒有找到 no = ${5} 的英雄")
    }

    // 後序搜尋
    // 後序遍歷的次數: 2
//    println("後序搜尋方式~")
//    val resNode = binaryTree.postOrderSearch(5)
//    if (resNode != null) {
//        println("找到了，訊息為 no=${resNode.no} name=${resNode.name}")
//    } else {
//        println("沒有找到 no = ${5} 的英雄")
//    }

    // 測試刪除節點
    println("刪除前，前序遍歷")
    binaryTree.preOrder() // 1, 2, 3, 5, 4
    binaryTree.delNode(5)
//    binaryTree.delNode(3)
    println("刪除後，前序遍歷")
    binaryTree.preOrder() // 1, 2, 3, 4
}

// 定義 BinaryTree 二元樹
class BinaryTree(private var _root: HeroNode? = null) {
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
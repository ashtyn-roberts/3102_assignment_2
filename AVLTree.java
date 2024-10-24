


class AVLTreeNode{
    int orderID;
    String bookName;
    AVLTreeNode left, right;
    int height;

    AVLTreeNode(int orderID, String bookName){
        this.orderID = orderID;
        this.bookName = bookName;
        this.height = 1;
    }
}

class AVLTree{
    private AVLTreeNode root;

   public void insert(int orderID, String bookName){
        root = insert(root, orderID, bookName);
        printTreeDetails();
    }

    private AVLTreeNode insert (AVLTreeNode node, int orderID, String bookName){
        if (node == null){
            return new AVLTreeNode(orderID, bookName);
        }

        if (orderID < node.orderID){
            node.left = insert(node.left, orderID, bookName);
        }else if (orderID > node.orderID){
            node.right = insert(node.right, orderID, bookName);
        }else {
            return node;
        }
        // Ignore this for now need to add individual classes later on :)
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        if (balance > 1 && orderID < node.left.orderID){
            return rotateRight(node);
        }

        if (balance < -1 && orderID > node.right.orderID){
            return rotateLeft(node);
        }

        if (balance > 1 && orderID > node.left.orderID){
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && orderID < node.right.orderID){
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void delete(int orderID){
        root = delete(root, orderID);
        printTreeDetails();
    }

    private AVLTreeNode delete(AVLTreeNode root, int orderID){
        if (root == null){
            return root;
        }
        //Will look into this ???
        if (orderID < root.orderID){
            root.left = delete(root.left, orderID);
        }else if (orderID > root.orderID){
            root.right = delete(root.right, orderID);
        }else {
            if (root.left == null || root.right == null){
                AVLTreeNode temp = null;
                if (temp == root.left){
                    temp = root.right;
                }else{
                    temp = root.left;
                }

                if (temp == null){
                    temp = root;
                    root = null;
                }else{
                    root = temp;
                }
            }else{
                AVLTreeNode temp = findMin(root.right);
                root.orderID = temp.orderID;
                root.bookName = temp.bookName;
                root.right = delete(root.right, temp.orderID);
            }
        }

        if (root == null){
            return root;
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0){
            return rotateRight(root);
        }

        if (balance > 1 && getBalance(root.left) < 0){
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0){
            return rotateLeft(root);
        }

        if (balance < -1 && getBalance(root.right) > 0){
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    public void inOrderTraversal(){
        inOrderTraversal(root);
    }

    private void inOrderTraversal(AVLTreeNode node){
        if (node != null){
            inOrderTraversal(node.left);
            System.out.println("Order ID: " + node.orderID + " Book Name: " + node.bookName);
            inOrderTraversal(node.right);
        }
    }

    public String search(int orderID){
        AVLTreeNode node = search(root, orderID);
        return node != null ? node.bookName : null;
    }

    private AVLTreeNode search(AVLTreeNode node, int orderID){
        if (node == null || node.orderID == orderID){
            return node;
        }

        if (orderID < node.orderID){
            return search(node.left, orderID);
        }

        return search(node.right, orderID);
    }

    public String findMin(){
        AVLTreeNode node = findMin(root);
        return node != null ? node.bookName : null;
    }

    private AVLTreeNode findMin(AVLTreeNode node){
        while (node.left != null){
            node = node.left;
        }

        return node;
    }

    public String findMax(){
        AVLTreeNode node = findMax(root);
        return node != null ? node.bookName : null;
    }

    private AVLTreeNode findMax(AVLTreeNode node){
        while (node.right != null){
            node = node.right;
        }

        return node;
    }

    private int getHeight(AVLTreeNode node){
        return node == null ? 0 : node.height;
    }

    private int getBalance (AVLTreeNode node){
        return node == null ? 0: getHeight(node.left) - getHeight(node.right);
    }

    private AVLTreeNode rotateLeft(AVLTreeNode y){
        AVLTreeNode x= y.right;
        AVLTreeNode T2 = x.left;

        x.left = y;
        y.right = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    private AVLTreeNode rotateRight(AVLTreeNode y){
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    public void printTreeDetails(){
        System.out.print("Number of Nodes: " + countNodes(root));
        System.out.println(" Height of Tree: " + getHeight(root));
    }

    private int countNodes(AVLTreeNode node){
        if (node == null){
            return 0;
        }

        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}


/**
* Class for a node in the AVL Tree. Each node represents one book order
*/
class AVLTreeNode{
    int orderID; 
    String bookName;
    AVLTreeNode left, right; // Left and right children of node
    int height; 

    /**
    * Constructor for node in AVL Tree
    */
    AVLTreeNode(int orderID, String bookName){
        this.orderID = orderID;
        this.bookName = bookName;
        this.height = 1;
    }
}

class AVLTree{
    private AVLTreeNode root;
    
    /**
    * Insert a book order into the AVL tree as a new node with a specified orderID and bookName
    */
   public void insert(int orderID, String bookName){
        root = insert(root, orderID, bookName);       //Recursively traverses the tree starting from the root to insert node correctly
        printTreeDetails();                           // Print the details of updated tree
    }
    
    /**
    * Helper function for insert
    */
    private AVLTreeNode insert (AVLTreeNode node, int orderID, String bookName){
        if (node == null){                                        // Create new AVL tree if the root does not exist already
            return new AVLTreeNode(orderID, bookName);
        }

        if (orderID < node.orderID){                              // If the orderID is less than the current node insert in the left subtree
            node.left = insert(node.left, orderID, bookName);
        }else if (orderID > node.orderID){                        // If the orderID is greater than the current node insert in the right subtree
            node.right = insert(node.right, orderID, bookName);
        }else {
            return node;   // OrderID already exists in tree, do not insert
        }
        // Ignore this for now need to add individual classes later on :)

        // Perform necessary rotations for an unbalanced AVL tree
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right)); // Calculate the height of the node
        int balance = getBalance(node);
        
        // Left-Left unbalance
        if (balance > 1 && orderID < node.left.orderID){
            return rotateRight(node); 
        }
        
        // Right Right Unbalance
        if (balance < -1 && orderID > node.right.orderID){
            return rotateLeft(node); 
        }

        // Left Right Unbalance
        if (balance > 1 && orderID > node.left.orderID){
            node.left = rotateLeft(node.left);
            return rotateRight(node);    
        }
        
        // Right Left Unbalance
        if (balance < -1 && orderID < node.right.orderID){
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;    // Return the updated node after balancing tree
    }

    /**
    * Delete a node within the AVL tree based on orderID
    */
    public void delete(int orderID){
        root = delete(root, orderID); //Traverse tree, starting from root, until deletion target node is found
        printTreeDetails();           // Print the details of updated tree
    }

    /**
    * Helper function for delete
    */
    private AVLTreeNode delete(AVLTreeNode root, int orderID){
        if (root == null){
            return root;     // Return if tree does not exist
        }
        //Will look into this ???
        if (orderID < root.orderID){
            root.left = delete(root.left, orderID);    // Traverse left subtree if target orderID is less than current orderID
        }else if (orderID > root.orderID){
            root.right = delete(root.right, orderID);  // Traverse right subtree if target orderID is greater than current orderID
        }else {

            // Node with orderID is found
            if (root.left == null || root.right == null){ // Node with 1 child or no child
                AVLTreeNode temp = null;
                if (temp == root.left){ // Only right child or no child
                    temp = root.right;
                }else{
                    temp = root.left; // Only left child
                }

                // If there are no children
                if (temp == null){
                    temp = root;
                    root = null;    // Delete the node
                }else{
                    root = temp;    // Deleted node is replaced by the child
                }
            }else{

                // Deletion Target node has two chidlren, get in-order sucessor as minimum node in right subtree
                AVLTreeNode temp = findMin(root.right);    // Find the minimum node in right subtree
                root.orderID = temp.orderID;    // Replace with the sucessor data
                root.bookName = temp.bookName;
                root.right = delete(root.right, temp.orderID);    // Delete the sucessor
            }
        }

        // If the tree has only one node, return null
        if (root == null){
            return root;
        }

        // Update the height of the current node
        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        int balance = getBalance(root); // Get the balance factor of the tree

        //Perform necessary rotations for an unbalanced subtree

        // Left Left unbalance
        if (balance > 1 && getBalance(root.left) >= 0){
            return rotateRight(root);
        }

        // Left right unbalance
        if (balance > 1 && getBalance(root.left) < 0){
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }


        // Right Right unbalance
        if (balance < -1 && getBalance(root.right) <= 0){
            return rotateLeft(root);
        }

        // Right Left unbalance
        if (balance < -1 && getBalance(root.right) > 0){
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;    // Return updated root after deletion and balancing AVL tree
    }

    /**
    * Perform In-Order Traversal
    */
    public void inOrderTraversal(){
        inOrderTraversal(root);
    }

    /**
    * Helper Function for inOrderTraversal. Traverses AVL Tree as long as the input node exists.
    */
    private void inOrderTraversal(AVLTreeNode node){
        if (node != null){
            inOrderTraversal(node.left); // Traverse to left child
            System.out.println("Order ID: " + node.orderID + " Book Name: " + node.bookName); // Print the Order ID and BookName of the node
            inOrderTraversal(node.right); // Traverse to right child
        }
    }

    /**
    * Searches through AVL tree recursively to find the BookName of the target orderID. If the orderID is not in the tree, return NULL. 
    */
    public String search(int orderID){
        AVLTreeNode node = search(root, orderID);
        return node != null ? node.bookName : null;
    }

    /**
    * Helper function for search. 
    */
    private AVLTreeNode search(AVLTreeNode node, int orderID){
        if (node == null || node.orderID == orderID){   // Return the node if the current orderID matches target orderID
            return node;
        }

        if (orderID < node.orderID){                // If the target orderID is less than the current orderID, search left subtree
            return search(node.left, orderID);
        }

        return search(node.right, orderID);        // If the target orderID is greater than the current orderID, search right subtree
    }

    /**
    * Find the minimum value node and return book name
    */
    public String findMin(){
        AVLTreeNode node = findMin(root);
        return node != null ? node.bookName : null;
    }

    /**
    * Helper method to find minimum value node in AVL tree
    */
    private AVLTreeNode findMin(AVLTreeNode node){
        while (node.left != null){  // Traverse to leftmost node
            node = node.left;
        }

        return node;                // Return leftmost node as minimum node
    }

    /**
    * Find the maximum node in the AVL tree and return book name
    */
    public String findMax(){
        AVLTreeNode node = findMax(root);
        return node != null ? node.bookName : null;
    }

    /**
    * 
    */
    private AVLTreeNode findMax(AVLTreeNode node){
        while (node.right != null){    // Traverse to rightmost node 
            node = node.right;
        }

        return node;                    // Return rightmost node as maximum node
    }

    /**
    * Return the height of the specified node
    */
    private int getHeight(AVLTreeNode node){
        return node == null ? 0 : node.height;
    }

    /**
    * Calculate the balance factor of the specified node
    * Balance factor calculated as height of left subtree - height of right subtree
    * Empty node has a balance factor of 0
    */
    private int getBalance (AVLTreeNode node){
        return node == null ? 0: getHeight(node.left) - getHeight(node.right); 
    }

    /**
    * Perform a left rotation for the given node or subtree, labeled y
    */
    private AVLTreeNode rotateLeft(AVLTreeNode y){
        AVLTreeNode x= y.right;   // x is right child of y
        AVLTreeNode T2 = x.left;  // T2 is left child of x

        // Perform the left rotation
        x.left = y;
        y.right = T2;

        // Update the heights of x and y
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;    // Return the new root
    }

    /**
    * Perform a right rotation for the given node or subtree, labeled y
    */
    private AVLTreeNode rotateRight(AVLTreeNode y){
        AVLTreeNode x = y.left;     // x is left child of y 
        AVLTreeNode T2 = x.right;   // T2 is right child of x

        // Perform the right rotation
        x.right = y;
        y.left = T2;

        // Update the heights for x and y
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;    // Return the new root
    }

    /**
    * Print the number of node and height of the tree
    */
    public void printTreeDetails(){
        System.out.print("Number of Nodes: " + countNodes(root));
        System.out.println(" Height of Tree: " + getHeight(root));
    }

    /**
    * Count the nodes of the AVL tree
    */
    private int countNodes(AVLTreeNode node){
        if (node == null){
            return 0; // If the tree does not exist, then it has 0 nodes 
        }

        return 1 + countNodes(node.left) + countNodes(node.right);   // Recursively add nodes of left + right + current node
    }
}


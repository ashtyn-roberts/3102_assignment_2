import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



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

class BookStore{
    public static void main(String[] args){
        AVLTree tree = new AVLTree();
        loadInitialOrders(tree);

        Scanner scanner = new Scanner(System.in);
        while (true) { 
            printAsciiArt();
            System.out.println("1. Add a book order");
            System.out.println("2. Remove a book order");
            System.out.println("3. Print In-Order list of book names by order ID");
            System.out.println("4. Find the name of the book for a specific order number");
            System.out.println("5. Find the oldest book order");
            System.out.println("6. Find the latest book order");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice){
                case 1:
                    System.out.print("Enter order ID:");
                    int orderID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();
                    tree.insert(orderID, bookName);
                    break;
                case 2:
                    System.out.print("Enter order ID to remove: ");
                    orderID = scanner.nextInt();
                    tree.delete(orderID);
                    break;
                case 3:
                    tree.inOrderTraversal();
                    break;
                case 4:
                    System.out.print("Enter order ID to search: ");
                    orderID = scanner.nextInt();
                    String name = tree.search(orderID);
                    System.out.println(name != null ? "Book name: " + name : " Order not found");
                    break;
                case 5:
                    System.out.println("Oldest book order: " + tree.findMin());
                    break;
                case 6: 
                    System.out.println("Latest book order: " + tree.findMax());
                    break;
                case 7:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        
        }
    }

    private static void loadInitialOrders(AVLTree tree){
        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))){
            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                int orderID = Integer.parseInt(values[0]);
                String bookName = values[1];
                tree.insert(orderID, bookName);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void printAsciiArt() {
        System.out.println("  _____  _____      ____                        _ _ ");
        System.out.println(" |  __ \\|  __ \\    |  _ \\                      | ( )");
        System.out.println(" | |  | | |__) |   | |_) | __ _ _ __  ___  __ _| |/ ___");
        System.out.println(" | |  | |  _  /    |  _ < / _` | '_ \\/ __|/ _` | | / __|");
        System.out.println(" | |__| | | \\ \\ _  | |_) | (_| | | | \\__ \\ (_| | | \\__ \\");
        System.out.println(" |_____/|_|  \\_(_) |____/ \\__,_|_| |_|___/\\__,_|_| |___/");
        System.out.println("  ____              _       _____ _                  ");
        System.out.println(" |  _ \\            | |     / ____| |                ");
        System.out.println(" | |_) | ___   ___ | | __ | (___ | |_ ___  _ __ ___");
        System.out.println(" |  _ < / _ \\ / _ \\| |/ /  \\___ \\| __/ _ \\| '__/ _ \\ ");
        System.out.println(" | |_) | (_) | (_) |   <   ____) | || (_) | | |  __/");
        System.out.println(" |____/ \\___/ \\___/|_|\\_\\ |_____/ \\__\\___/|_|  \\___|");
        System.out.println("        .--.           .---.        .-.");
        System.out.println("    .---|--|   .-.     | A |  .---. |~|    .--.");
        System.out.println(" .--|===|  |---|_|--.__| S |--|:::| |~|-==-|==|---.");
        System.out.println(" |%%|   |  |===| |~~|%%| C |--|   |_|~|    |  |___|-.");
        System.out.println(" |  |   |  |===| |==|  | I |  |:::|=| |    |  |---|=|");
        System.out.println(" |  |   |  |   |_|__|  | I |__|   | | |    |  |___| |");
        System.out.println(" |~~|===|--|===|~|~~|%%|~~~|--|:::|=|~|----|==|---|=|");
        System.out.println(" ^--^---'--^---^-^--^--^---'--^---^-^-^-==-^--^---^-'");
    }
}
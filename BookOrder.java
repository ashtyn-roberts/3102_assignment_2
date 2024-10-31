// Import libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


// Application for managing book orders using an AVL tree as data structure
public class BookOrder{

    /**
    * The main interface for user interaction for the application
    **/
    public static void main(String[] args){
        AVLTree tree = new AVLTree(); // Create an instance of AVLTree to store book orders
        loadInitialOrders(tree);

        // Scanner object for reading user input from console
        Scanner scanner = new Scanner(System.in);

        // Loop runs until user exits
        while (true) { 
            // Print ASCII art to console
            printAsciiArt();

            // Display the user options
            System.out.println("1. Add a book order");
            System.out.println("2. Remove a book order");
            System.out.println("3. Print In-Order list of book names by order ID");
            System.out.println("4. Find the name of the book for a specific order number");
            System.out.println("5. Find the oldest book order");
            System.out.println("6. Find the latest book order");
            System.out.println("7. Exit");

            // Prompt user for input
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            // Perform action based on user input as choice
            switch (choice){
                case 1:
                    //Prompt for orderID and BookName to add to AVL tree
                    System.out.print("Enter order ID:");
                    int orderID = scanner.nextInt();          // Read the orderID
                    scanner.nextLine();
                    System.out.print("Enter book name: ");    // Read the bookName
                    String bookName = scanner.nextLine();
                    tree.insert(orderID, bookName);           // Insert the book order into AVL tree as a new node
                    break;
                    
                case 2:
                    // Prompt for orderID to remove from AVL tree
                    System.out.print("Enter order ID to remove: ");
                    orderID = scanner.nextInt();
                    tree.delete(orderID); // Perform the orderID from the AVL tree
                    break;
                    
                case 3:
                    // Display the book names sorted by OrderID using in-order traversal of AVL Tree
                    tree.inOrderTraversal();
                    break;
                    
                case 4:
                    // Prompt for orderID to search for
                    System.out.print("Enter order ID to search: ");
                    orderID = scanner.nextInt();
                    String name = tree.search(orderID); // Find the bookName associated with orderID
                    System.out.println(name != null ? "Book name: " + name : " Order not found"); // Print not found if orderID is not in AVL tree
                    break;
                    
                case 5:
                    // Find the oldest book order by searching for minimum orderID
                    System.out.println("Oldest book order: " + tree.findMin());
                    break;

                case 6: 
                    // Find the latest book order by searching for maximum orderID
                    System.out.println("Latest book order: " + tree.findMax());
                    break;
                    
                case 7:
                    // Close the scanner and exit the application 
                    scanner.close();
                    System.exit(0);
                default:
                    // Invalid choices from the user 
                    System.out.println("Invalid choice");
            }
        
        }
    }

    /**
    * Load the initial book orders from the CSV file into the AVL tree
    *
    **/
    private static void loadInitialOrders(AVLTree tree){
        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))){
            String line; // Holds each line read from the CSV file
            br.readLine(); // Read the header line as the first line 

            // Loop through each line in the CSV file
            while((line = br.readLine()) != null){
                String[] values = line.split(",");   // Split values by comma 
                int orderID = Integer.parseInt(values[0]); // Parse the first value as an integer for orderID
                String bookName = values[1]; // Second value is the bookName
                tree.insert(orderID, bookName); // Inser the orderID and bookName into the AVL tree
            }
        }catch (IOException e){
            e.printStackTrace(); // Print the stack trace if an IOException occurs 
        }
    }

    // Print the ASCII art before prompting user for options
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

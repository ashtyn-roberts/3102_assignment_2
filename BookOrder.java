import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class BookOrder{
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
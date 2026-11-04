import java.util.Scanner;
public class Main {
public static void main(String[]args){
    Scanner scanner = new Scanner(System.in);
    TicketManager manager = new TicketManager();
    int choice;
    do{
        System.out.println("\n===== IT HELP DESK SYSTEM ======");
        System.out.println("1. Create Support Ticket");
        System.out.println("2. View All Tickets");
        System.out.println("3. Update Ticket Status");
        System.out.println("4. Search Ticket");
        System.out.println("5. Filter Tickets by Status");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        
        while (!scanner.hasNextInt()){
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print("Enter your choice: ");
                }

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:
                        System.out.print("Employee Name: ");
                        String name= scanner.nextLine();

                        System.out.print("Category(Hardware/Software/Network/Account): ");
                        String category = scanner.nextLine();

                        System.out.print("Describe the problem: ");
                        String description = scanner.nextLine();

                        System.out.print("Priority(Low/Medium/High): ");
                        String priority = scanner.nextLine();

                        manager.createTicket(name,category,description,priority);

                        break;

                        case 2:
                            manager.displayTickets();
                            break;

                            case 3:
                                System.out.print("Enter Ticket ID: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("New Status (Open/In Progress/Resolved): ");
                            String status = scanner.nextLine();

                            manager.updateTicketStatus(id, status);
                            break;

                            case 4:
                                System.out.print("Enter Ticket ID to search: ");
                                int searchId = scanner.nextInt();
                                scanner.nextLine();

                                manager.searchTicket(searchId);
                                break;

                           
case 5:

    System.out.println("Select Status:");
    System.out.println("1. Open");
    System.out.println("2. In Progress");
    System.out.println("3. Resolved");

    System.out.print("Enter your choice: ");

    int statusChoice = scanner.nextInt();
    scanner.nextLine();

    String selectedStatus = "";

    switch (statusChoice) {

        case 1:
            selectedStatus = "Open";
            break;

        case 2:
            selectedStatus = "In Progress";
            break;

        case 3:
            selectedStatus = "Resolved";
            break;

        default:
            System.out.println("Invalid selection.");
            break;
    }

    if (!selectedStatus.isEmpty()) {
        manager.displayTicketsByStatus(selectedStatus);
    }

    break;

case 6:

    System.out.println("Thank you for using the IT Help Desk System!");
    break;

}      
}while (choice !=6);
                }
                }
            

import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class TicketManager {

    private ArrayList<Ticket> tickets;
    private int nextTicketId;

    private final String FILE_PATH = "data/tickets.csv";

    public TicketManager() {
        tickets = new ArrayList<>();
        nextTicketId = 1;

        loadTickets();
    }

    // Create a new support ticket
    public void createTicket(String employeeName,
                             String category,
                             String description,
                             String priority) {

        if (employeeName.trim().isEmpty()
                || description.trim().isEmpty()) {
            System.out.println("Name and description cannot be empty.");
            return;
        }

        if (!isValidCategory(category)) {
            System.out.println("Invalid category.");
            return;
        }

        if (!isValidPriority(priority)) {
            System.out.println("Invalid priority.");
            return;
        }

        Ticket newTicket = new Ticket(
            nextTicketId,
            employeeName.trim(),
            category.trim(),
            description.trim(),
            priority.trim()
        );

        tickets.add(newTicket);

        System.out.println("Ticket created successfully!");
        System.out.println("Ticket ID: " + nextTicketId);

        nextTicketId++;

        saveTickets();
    }

    // Validate categories
    private boolean isValidCategory(String category) {
        return category.equalsIgnoreCase("Hardware")
            || category.equalsIgnoreCase("Software")
            || category.equalsIgnoreCase("Network")
            || category.equalsIgnoreCase("Account");
    }

    // Validate priorities
    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("Low")
            || priority.equalsIgnoreCase("Medium")
            || priority.equalsIgnoreCase("High");
    }

    // Validate ticket statuses
    private boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Open")
            || status.equalsIgnoreCase("In Progress")
            || status.equalsIgnoreCase("Resolved");
    }

    // Display all tickets
    public void displayTickets() {

        if (tickets.isEmpty()) {
            System.out.println("No tickets available.");
            return;
        }

        for (Ticket ticket : tickets) {

            System.out.println("-------------------------");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Employee: " + ticket.getEmployeeName());
            System.out.println("Category: " + ticket.getCategory());
            System.out.println("Description: " + ticket.getDescription());
            System.out.println("Priority: " + ticket.getPriority());
            System.out.println("Status: " + ticket.getStatus());
        }
    }

    // Search for a ticket
    public void searchTicket(int id) {

        for (Ticket ticket : tickets) {

            if (ticket.getTicketId() == id) {

                System.out.println("\n===== TICKET FOUND =====");
                System.out.println("Ticket ID: " + ticket.getTicketId());
                System.out.println("Employee: " + ticket.getEmployeeName());
                System.out.println("Category: " + ticket.getCategory());
                System.out.println("Description: " + ticket.getDescription());
                System.out.println("Priority: " + ticket.getPriority());
                System.out.println("Status: " + ticket.getStatus());

                return;
            }
        }

        System.out.println("Ticket not found.");
    }

public void displayTicketsByStatus(String status) {

    if (!isValidStatus(status)) {
        System.out.println("Invalid status.");
        return;
    }

    boolean found = false;

    for (Ticket ticket : tickets) {

        if (ticket.getStatus().equalsIgnoreCase(status.trim())) {

            System.out.println("-------------------------");
            System.out.println("Ticket ID: " + ticket.getTicketId());
            System.out.println("Employee: " + ticket.getEmployeeName());
            System.out.println("Category: " + ticket.getCategory());
            System.out.println("Description: " + ticket.getDescription());
            System.out.println("Priority: " + ticket.getPriority());
            System.out.println("Status: " + ticket.getStatus());

            found = true;
        }
    }

    if (!found) {
        System.out.println("No tickets found with status: " + status);
    }
}

    // Update ticket status
    public void updateTicketStatus(int id, String newStatus) {

        if (!isValidStatus(newStatus)) {
            System.out.println("Invalid status.");
            return;
        }

        for (Ticket ticket : tickets) {

            if (ticket.getTicketId() == id) {

                ticket.setStatus(newStatus.trim());

                System.out.println("Ticket updated successfully!");

                saveTickets();
                return;
            }
        }

        System.out.println("Ticket not found.");
    }

    // Save all tickets to CSV
    public void saveTickets() {

        try {

            Path path = Paths.get(FILE_PATH);
            Files.createDirectories(path.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {

                writer.write("ID,Employee,Category,Description,Priority,Status");
                writer.newLine();

                for (Ticket ticket : tickets) {

                    String[] fields = {
                        String.valueOf(ticket.getTicketId()),
                        ticket.getEmployeeName(),
                        ticket.getCategory(),
                        ticket.getDescription(),
                        ticket.getPriority(),
                        ticket.getStatus()
                    };

                    for (int i = 0; i < fields.length; i++) {

                        String value = fields[i].replace("\"", "\"\"");

                        writer.write("\"" + value + "\"");

                        if (i < fields.length - 1) {
                            writer.write(",");
                        }
                    }

                    writer.newLine();
                }
            }

            System.out.println("Tickets saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving tickets: " + e.getMessage());
        }
    }

    // Load saved tickets from CSV
    public void loadTickets() {

        Path path = Paths.get(FILE_PATH);

        if (!Files.exists(path)) {
            System.out.println("No previous ticket records found.");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;

            // Skip CSV header
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                try {

                    ArrayList<String> fields = parseCsvLine(line);

                    if (fields.size() != 6) {
                        System.out.println("Skipping invalid ticket record.");
                        continue;
                    }

                    int id = Integer.parseInt(fields.get(0));

                    Ticket ticket = new Ticket(
                        id,
                        fields.get(1),
                        fields.get(2),
                        fields.get(3),
                        fields.get(4)
                    );

                    if (!isValidCategory(fields.get(2))
                            || !isValidPriority(fields.get(4))
                            || !isValidStatus(fields.get(5))) {
                        System.out.println("Skipping invalid ticket record.");
                        continue;
                    }

                    ticket.setStatus(fields.get(5));

                    tickets.add(ticket);

                    if (id >= nextTicketId) {
                        nextTicketId = id + 1;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping ticket with invalid ID.");
                }
            }

            System.out.println(tickets.size() + " ticket(s) loaded.");

        } catch (IOException e) {

            System.out.println("Error loading tickets: " + e.getMessage());
        }
    }

    // Read CSV values, including quoted commas
    private ArrayList<String> parseCsvLine(String line) {

        ArrayList<String> fields = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {

            char current = line.charAt(i);

            if (current == '"') {

                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {

                    value.append('"');
                    i++;

                } else {
                    insideQuotes = !insideQuotes;
                }

            } else if (current == ',' && !insideQuotes) {

                fields.add(value.toString());
                value.setLength(0);

            } else {
                value.append(current);
            }
        }

        fields.add(value.toString());

        return fields;
    }
}

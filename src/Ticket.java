public class Ticket{

    private int ticketId;
    private String employeeName;
    private String category;
    private String description;
    private String priority;
    private String status;

    public Ticket(int ticketId, String employeeName, String category, String description, String priority){
        this.ticketId = ticketId;
        this.employeeName = employeeName;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.status = "Open";
    }
    public int getTicketId(){
        return ticketId;
    }
    public String getEmployeeName(){
        return employeeName;
    }
    public String getCategory(){
        return category;
    }
    public String getDescription(){
        return description;
    }
    public String getPriority(){
        return priority;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public Ticket(int ticketId, String employeeName, String category, String description, String priority, String status ){
        this.ticketId=ticketId;
        this.employeeName = employeeName;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

}

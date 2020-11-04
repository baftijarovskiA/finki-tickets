package mk.ukim.finki.eimt.tickets.FinkiTickets.Model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user; // in function

    @ManyToOne
    private Ticket ticket; // get user from ticket

    private String ticketCode; // in function

    private int quantity; // input

    private int total; // in function

    private String dateTime; // in function

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Ticket getTicket() { return ticket; }

    public void setTicket(Ticket ticket) { this.ticket = ticket; }

    public String getTicketCode() { return ticketCode; }

    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getTotal() { return total; }

    public void setTotal(int total) { this.total = total; }

    public String getDateTime() { return dateTime; }

    public void setDateTime(String dateTime) { this.dateTime = dateTime; }
}

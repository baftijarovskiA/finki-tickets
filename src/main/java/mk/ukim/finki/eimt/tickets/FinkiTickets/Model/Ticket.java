package mk.ukim.finki.eimt.tickets.FinkiTickets.Model;


import javax.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    private int quantity;

    private int price;

    private boolean free;

    private boolean sold;

    private String time;

    private String ticketCode;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Event getEvent() { return event; }

    public void setEvent(Event event) { this.event = event; }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public boolean isFree() { return free; }

    public void setFree(boolean free) { this.free = free; }

    public boolean isSold() { return sold; }

    public void setSold(boolean sold) { this.sold = sold; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getTicketCode() { return ticketCode; }

    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }
}

package mk.ukim.finki.eimt.tickets.FinkiTickets.Service;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByEvent(String slug);

    List<Ticket> getTicketsByUser(Long userId);

    void addTicketToEvent(Ticket ticket, String userId);

    Ticket getTicketById(Long id);
}

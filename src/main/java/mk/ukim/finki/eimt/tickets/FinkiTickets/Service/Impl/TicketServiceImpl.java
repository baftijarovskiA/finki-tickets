package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Ticket;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl.TicketRepositoryImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepositoryImpl repository;

    public TicketServiceImpl(TicketRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Ticket> getTicketsByEvent(String slug) {
        return repository.getTicketsByEvent(slug);
    }

    @Override
    public void addTicketToEvent(Ticket ticket, String userId) {
        repository.addTicketToEvent(ticket,userId);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return repository.getTicketById(id);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        return repository.getTicketsByUser(userId);
    }
}

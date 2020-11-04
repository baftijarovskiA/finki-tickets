package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Ticket;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.TicketJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.TicketRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private TicketJpaRepository repository;
    private UserJpaRepository userJpaRepository;

    public TicketRepositoryImpl(TicketJpaRepository repository, UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.repository = repository;
    }


    @Override
    public List<Ticket> getTicketsByEvent(String slug) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket t : repository.findAll()) {
            if (t.getEvent().getSlug().equals(slug)){
                ticketList.add(t);
            }
        }
        return ticketList;
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        List<Ticket> ticketList = new ArrayList<>();
        for (Ticket t : repository.findAll()) {
            if (t.getUser().getId().equals(userId)){
                ticketList.add(t);
            }
        }
        return ticketList;
    }

    @Override
    public void addTicketToEvent(Ticket ticket, String userId) {
        ticket.setTicketCode(ticketCodeGenerator(10));
        ticket.setUser(currentUser(userId));
        ticket.setFree(false);
        if (ticket.getPrice() == 0){
            ticket.setFree(true);
        }
        repository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return repository.findById(id).get();
    }

    private User currentUser(String Id){
        User u = null;
        if (!Id.equals("nan")){
            u = userJpaRepository.findById(Long.parseLong(Id)).get();
        }
        return u;
    }

    private String ticketCodeGenerator(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
        StringBuilder sb = new StringBuilder(n);
        sb.append("TCK");
        for (int i = 0; i < n; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}

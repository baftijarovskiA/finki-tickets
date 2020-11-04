package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<Ticket, Long> {
}

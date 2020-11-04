package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.StripeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StripeTransactionJpaRepository extends JpaRepository<StripeTransaction, String> {
}

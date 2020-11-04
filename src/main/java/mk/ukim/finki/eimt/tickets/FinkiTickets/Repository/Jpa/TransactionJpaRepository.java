package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
}

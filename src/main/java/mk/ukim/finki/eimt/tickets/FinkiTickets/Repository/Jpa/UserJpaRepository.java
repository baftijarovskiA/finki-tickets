package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}

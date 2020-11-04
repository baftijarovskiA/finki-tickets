package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}

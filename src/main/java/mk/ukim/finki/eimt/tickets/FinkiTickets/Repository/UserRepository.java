package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;

public interface UserRepository {
    boolean checkCredentials(String email, String password);

    User findUserByEmail(String email);
}

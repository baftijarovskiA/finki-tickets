package mk.ukim.finki.eimt.tickets.FinkiTickets.Service;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void addNewUser(User user);

    void editUserById(Long id, User user);

    boolean checkCredentials(String email, String password);

    User findUserByEmail(String email);
}

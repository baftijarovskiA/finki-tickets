package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl.UserRepositoryImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepositoryImpl repository;

    public UserServiceImpl(UserRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return repository.getUserById(id);
    }

    @Override
    public void addNewUser(User user) {
        repository.addNewUser(user);
    }

    @Override
    public void editUserById(Long id, User user) {
        repository.editUserById(id, user);
    }

    @Override
    public boolean checkCredentials(String email, String password) {
        return repository.checkCredentials(email,password);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }
}

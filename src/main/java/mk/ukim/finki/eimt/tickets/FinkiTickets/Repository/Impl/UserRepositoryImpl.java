package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Controller.EmailController;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private EmailController emailController;
    private UserJpaRepository repository;

    public UserRepositoryImpl(EmailController emailController, UserJpaRepository repository) {
        this.emailController = emailController;
        this.repository = repository;
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public User getUserById(Long id){
        return repository.findById(id).get();
    }

    public void addNewUser(User user) {
        List<User> userList = repository.findAll();
        for (User u : userList) {
            if (u.getEmail().equals(user.getEmail())) {
                return;
            }
        }
        user.setRole("USER");
        repository.save(user);
        try {
            emailController.sendMail(user.getEmail(),user.getName(),0);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void editUserById(Long id, User user) {
        User u = repository.findById(id).get();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        repository.save(u);
        try {
            emailController.sendMail(user.getEmail(),user.getName(),1);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkCredentials(String email, String password) {
        for (User u : repository.findAll()) {
            if (u.getEmail().equals(email)){
                if (u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public User findUserByEmail(String email) {
        for (User u : repository.findAll()) {
            if (u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }
}

package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Controller.EmailController;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Ticket;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.TicketJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.TransactionJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private TransactionJpaRepository repository;
    private UserJpaRepository userJpaRepository;
    private TicketJpaRepository ticketJpaRepository;
    private EmailController emailController;

    public TransactionRepositoryImpl(TransactionJpaRepository repository, UserJpaRepository userJpaRepository, TicketJpaRepository ticketJpaRepository, EmailController emailController) {
        this.repository = repository;
        this.userJpaRepository = userJpaRepository;
        this.ticketJpaRepository = ticketJpaRepository;
        this.emailController = emailController;
    }

    @Override
    public void buyTicket(Transaction transaction, String userId) {
        // Get the ticket and info about that ticket
        Ticket ticket = transaction.getTicket();
        User seller = ticket.getUser();
        String ticketCode = ticket.getTicketCode()+""+transaction.getQuantity()+"F";
        int ticketPrice = ticket.getPrice();

        // set in-value for transaction model
        transaction.setUser(currentUser(userId));
        transaction.setDateTime(getDateTime());
        transaction.setTicketCode(ticketCode);
        transaction.setTotal(0);

        // Change and save the ticket quantity
        int currQuantity = ticket.getQuantity();
        int buyQuantity = transaction.getQuantity();
        ticket.setQuantity(currQuantity-buyQuantity);
        ticketJpaRepository.save(ticket);

        // Check if the ticket quantity is 0
        if (ticket.getQuantity() == 0){
            ticket.setSold(true);
            ticketJpaRepository.save(ticket);
        }

        // Set the total price if ticket is not for free
        if (!ticket.isFree()){
            transaction.setTotal(ticketPrice*buyQuantity);
        }

        repository.save(transaction);
        try {
            sendMailToBuyer(currentUser(userId), transaction);
            sendMailToSeller(seller, transaction);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getTotalProfitPerUser(Long userId) {
        int total = 0;
        for (Transaction t: repository.findAll()) {
            if (t.getTicket().getUser().getId().equals(userId)){
               total += t.getTotal();
            }
        }
        return total;
    }

    private void sendMailToSeller(User seller, Transaction transaction) throws MessagingException {
        emailController.ticketTransaction(seller,transaction,true);
    }

    private void sendMailToBuyer(User currentUser, Transaction transaction) throws MessagingException {
        emailController.ticketTransaction(currentUser,transaction,false);
    }

    private User currentUser(String Id){
        User u = null;
        if (!Id.equals("nan")){
            u = userJpaRepository.findById(Long.parseLong(Id)).get();
        }
        return u;
    }

    public String getDateTime(){
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        return localDate+" "+localTime;
    }
}

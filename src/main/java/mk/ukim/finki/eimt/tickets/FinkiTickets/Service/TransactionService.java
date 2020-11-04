package mk.ukim.finki.eimt.tickets.FinkiTickets.Service;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;

public interface TransactionService {

    void buyTicket(Transaction transaction, String userId);

    int getTotalProfitPerUser(Long userId);
}

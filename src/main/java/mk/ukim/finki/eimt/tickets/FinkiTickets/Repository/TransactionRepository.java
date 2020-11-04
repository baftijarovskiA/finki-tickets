package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;

public interface TransactionRepository {

    void buyTicket(Transaction transaction, String userId);
    int getTotalProfitPerUser(Long userId);
}

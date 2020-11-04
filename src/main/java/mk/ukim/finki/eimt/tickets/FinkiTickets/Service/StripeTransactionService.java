package mk.ukim.finki.eimt.tickets.FinkiTickets.Service;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.StripeTransaction;

import java.util.List;

public interface StripeTransactionService {
    List<StripeTransaction> findAll();
    StripeTransaction save(StripeTransaction transaction);
    void deleteAll();
}

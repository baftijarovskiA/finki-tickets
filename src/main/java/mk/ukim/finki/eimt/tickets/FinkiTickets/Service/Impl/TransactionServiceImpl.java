package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl.TransactionRepositoryImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepositoryImpl repository;

    public TransactionServiceImpl(TransactionRepositoryImpl repository) {
        this.repository = repository;
    }


    @Override
    public void buyTicket(Transaction transaction, String userId) {
        repository.buyTicket(transaction,userId);
    }

    @Override
    public int getTotalProfitPerUser(Long userId) {
        return repository.getTotalProfitPerUser(userId);
    }

}

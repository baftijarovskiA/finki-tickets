package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.StripeTransaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.StripeTransactionJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.StripeTransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeTransactionServiceImpl implements StripeTransactionService {

    private final StripeTransactionJpaRepository transactionRepository;

    public StripeTransactionServiceImpl(StripeTransactionJpaRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<StripeTransaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public StripeTransaction save(StripeTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteAll() {
        transactionRepository.deleteAll();
    }
}

package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.ChargeRequest;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.StripeTransaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.CategoryServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.TicketServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.TransactionServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.StripeService;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.StripeTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChargeController {

    private final StripeTransactionService stripeTransactionService;
    private TicketServiceImpl ticketService;
    private TransactionServiceImpl transactionService;
    private CategoryServiceImpl categoryService;

    @Autowired
    private StripeService paymentsService;

    public ChargeController(StripeTransactionService stripeTransactionService, TicketServiceImpl ticketService, TransactionServiceImpl transactionService, CategoryServiceImpl categoryService) {
        this.stripeTransactionService = stripeTransactionService;
        this.ticketService = ticketService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model,
                         @CookieValue(value = "_total", defaultValue = "nan") String _total ,
                         @CookieValue(value = "_ticket", defaultValue = "nan") String _ticket,
                         @CookieValue(value = "_q", defaultValue = "nan") String _q,
                         @CookieValue(value = "_session", defaultValue = "nan") String _user)
            throws StripeException {
        chargeRequest.setDescription("Ticket charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.MKD);
        int total = 0;
        if (!_total.equals("nan")){
            total = Integer.parseInt(_total);
        }
        chargeRequest.setAmount(total);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("availableCategories", categoryService.getAvailableCategories());
        stripeTransactionService.save(new StripeTransaction(charge.getId().toString(), charge.getStatus().toString(), charge.getId().toString(), charge.getBalanceTransaction().toString() ));
        Transaction transaction = new Transaction();

        transaction.setTotal(total);
        transaction.setQuantity(Integer.parseInt(_q));
        transaction.setTicket(ticketService.getTicketById(Long.parseLong(_ticket)));

        transactionService.buyTicket(transaction,_user);

        return "checkout";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "checkout";
    }
}

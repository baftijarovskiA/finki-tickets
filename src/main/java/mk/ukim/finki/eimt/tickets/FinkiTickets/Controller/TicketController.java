package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.*;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.UserJpaRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketServiceImpl ticketService;
    private CategoryServiceImpl categoryService;
    private EventServiceImpl eventService;
    private TransactionServiceImpl transactionService;
    private UserJpaRepository userJpaRepository;

    public TicketController(TicketServiceImpl ticketService, CategoryServiceImpl categoryService, EventServiceImpl eventService, TransactionServiceImpl transactionService,UserJpaRepository userJpaRepository) {
        this.ticketService = ticketService;
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.transactionService = transactionService;
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/sell")
    public String sellTicketView(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (Id.equals("nan")){ return "redirect:/account/sign-in"; }

        model.addAttribute("availableCategories", categoryService.getAvailableCategories());
        model.addAttribute("events", eventService.getUnexpiredEvents());
        model.addAttribute("ticket", new Ticket());
        return "ticket/sell";
    }

    @PostMapping("/sell")
    public String sellTicket(@ModelAttribute Ticket ticket, Model model, HttpServletRequest request, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (Id.equals("nan")){ return "redirect:/"; }

        Event event = eventService.getEventById(ticket.getEvent().getId());
        String time = request.getParameter("time");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int price = Integer.parseInt(request.getParameter("price"));

        ticket.setQuantity(quantity);
        ticket.setTime(time);
        ticket.setPrice(price);
        ticket.setEvent(event);
        ticketService.addTicketToEvent(ticket,Id);
        return "redirect:/"+event.getSlug();
    }

    @Value("pk_test_wShVl3FoJQCK3oSjBQQ752AI00jUdQEWgf")
    private String stripePublicKey;

    @GetMapping("/buy/{id}/{q}")
    public String buyTicketView(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id, @PathVariable("id") Long id, @PathVariable("q") int quantity, HttpServletResponse response){
        if (Id.equals("nan")){ return "redirect:/account/sign-in"; }

        User user = userJpaRepository.findById(Long.parseLong(Id)).get();
        Ticket ticket = ticketService.getTicketById(id);

        model.addAttribute("availableCategories", categoryService.getAvailableCategories());
        model.addAttribute("ticket", ticket);
        model.addAttribute("q",quantity);
        model.addAttribute("email", user.getEmail());

        int total = 100*quantity*ticket.getPrice();
        String _total = ""+total;

        Cookie cookie = new Cookie("_total",_total);
        cookie.setMaxAge(600); // setting cookie for 10 min
        cookie.setPath("/");
        response.addCookie(cookie);

        String _ticket = ""+ticket.getId();
        cookie = new Cookie("_ticket",_ticket);
        cookie.setMaxAge(600); // setting cookie for 10 min
        cookie.setPath("/");
        response.addCookie(cookie);

        String _q = ""+quantity;
        cookie = new Cookie("_q",_q);
        cookie.setMaxAge(600); // setting cookie for 10 min
        cookie.setPath("/");
        response.addCookie(cookie);

        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("amount", total); // in cents
        model.addAttribute("currency", ChargeRequest.Currency.MKD);

        return "ticket/buy";
    }
}

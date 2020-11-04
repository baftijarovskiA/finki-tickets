package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Transaction;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.CategoryService;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.EventServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.TicketServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.TransactionServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.UserServiceImpl;
import org.hibernate.Session;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/account")
public class UserController {

    private UserServiceImpl userService;
    private CategoryService categoryService;
    private TicketServiceImpl ticketService;
    private EventServiceImpl eventService;
    private TransactionServiceImpl transactionService;

    public UserController(UserServiceImpl userService, CategoryService categoryService, TicketServiceImpl ticketService, EventServiceImpl eventService,TransactionServiceImpl transactionService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String index(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            User user = userService.getUserById((long) Integer.parseInt(Id));
            model.addAttribute("user",user);
            model.addAttribute("availableCategories",categoryService.getAvailableCategories());
            model.addAttribute("tickets", ticketService.getTicketsByUser(Long.parseLong(Id)));
            model.addAttribute("allCategories", categoryService.getAllCategories());
            model.addAttribute("allUsers",userService.getAllUsers());
            model.addAttribute("allEvents", eventService.getUnexpiredEvents());
            model.addAttribute("profit", transactionService.getTotalProfitPerUser(Long.parseLong(Id)));
            return "/user/index";
        }
        return "redirect:/account/sign-in";
    }

    @GetMapping("/sign-up")
    public String addNewUserView(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        return "/user/sign-up";
    }

    @PostMapping("/sign-up")
    public String addNewUser(@ModelAttribute User user, Model model, HttpServletRequest request){

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.addNewUser(user);

        return "redirect:/account/sign-in";
    }


    @GetMapping("/sign-in")
    public String logInUserView(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        return "/user/sign-in";
    }

    @PostMapping("/sign-in")
    public String logInUser(@ModelAttribute User user, Model model, HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (userService.checkCredentials(email,password)){
            User logged = userService.findUserByEmail(email);
            Cookie cookie = new Cookie("_session",logged.getId().toString());
            cookie.setMaxAge(3600); // setting cookie for one hour
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/account/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response,  @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            Cookie cookie = new Cookie("_session",null);
            cookie.setMaxAge(0); // deleting a cookie
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @GetMapping("/settings")
    public String editUserView(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        User u = null;
        if (!Id.equals("nan")){
            u = userService.getUserById((long) Integer.parseInt(Id));
        }
        model.addAttribute("user", u);
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        return "/user/settings";
    }

    @PostMapping("/settings")
    public String editUser(@ModelAttribute User user,Model model, HttpServletRequest request, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String oldPassword = userService.getUserById(Long.parseLong(Id)).getPassword();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(oldPassword);
        if (!password.equals("")){
            user.setPassword(password);
        }

        Long uId = 0L;
        if (!Id.equals("nan")){
            uId = Long.parseLong(Id);
        }
        userService.editUserById(uId,user);
        return "redirect:/account/";
    }
}

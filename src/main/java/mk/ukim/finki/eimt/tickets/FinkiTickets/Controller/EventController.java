package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Event;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.CategoryServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.EventServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.TicketServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.UserServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EventController {

    private EventServiceImpl eventService;
    private UserServiceImpl userService;
    private CategoryServiceImpl categoryService;
    private TicketServiceImpl ticketService;

    public EventController(EventServiceImpl eventService,UserServiceImpl userService, CategoryServiceImpl categoryService, TicketServiceImpl ticketService) {
        this.userService = userService;
        this.eventService = eventService;
        this.categoryService = categoryService;
        this.ticketService = ticketService;
    }

    @GetMapping("/event/all")
    public String index(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("allEvents", eventService.getAllEvents());
        return "event/index";
    }

    @GetMapping("/{slug}")
    public String publicEvent(Model model, @PathVariable("slug") String slug){
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        Event e = eventService.getEventBySlug(slug);
        if (e != null){
            model.addAttribute("event", eventService.getEventBySlug(slug));
            model.addAttribute("tickets", ticketService.getTicketsByEvent(slug));
        } else {
            model.addAttribute("error", true);
        }
        return "event/preview";
    }

    @GetMapping("/event/create")
    public String createView(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (Id.equals("nan")){ return "redirect:/"; }

        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("event", new Event());
        return "event/create";
    }

    @PostMapping("/event/create")
    public String create(@ModelAttribute Event event, Model model, HttpServletRequest request, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (Id.equals("nan")){ return "redirect:/"; }

        Long catId = event.getCategory().getId();
        Category c = categoryService.getCategoryById(event.getCategory().getId());

        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String cover = request.getParameter("cover");
        String date = request.getParameter("date");

        event.setName(name);
        event.setLocation(location);
        event.setCover(cover);
        if (cover.equals("")){
            event.setCover("https://x.kinja-static.com/assets/images/logos/placeholders/default.png");
        }
        event.setCategory(c);
        event.setDate(date);
        eventService.addNewEvent(event, Id);

        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        return "thanks";
    }

    @GetMapping("/event/delete/{id}")
    public String delete(@CookieValue(value = "_session", defaultValue = "nan") String Id, @PathVariable("id") Long id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";
        eventService.deleteEventById(id);
        return "redirect:/event/all";
    }

    @GetMapping("/event/approve/{id}")
    public String toggle(@CookieValue(value = "_session", defaultValue = "nan") String Id, @PathVariable("id") Long id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        eventService.toggleApproved(id);
        return "redirect:/event/all";
    }

    @GetMapping("/c/{id}")
    public String eventsByCategory(Model model, @PathVariable("id") Long id){

        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("events", eventService.getEventsByCategory(id));
        return "event/event-cat";
    }
}

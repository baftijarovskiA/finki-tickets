package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.CategoryServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.EventServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private CategoryServiceImpl categoryService;
    private EventServiceImpl eventService;

    public HomeController(CategoryServiceImpl categoryService,EventServiceImpl eventService) {
        this.eventService = eventService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("events",eventService.getUnexpiredEvents());
        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());

        return "checkout";
    }

}

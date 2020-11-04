package mk.ukim.finki.eimt.tickets.FinkiTickets.Controller;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.User;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.CategoryServiceImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private CategoryServiceImpl categoryService;
    private UserServiceImpl userService;

    public CategoryController(CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("")
    public String index(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        return "category/index";
    }

    @GetMapping("/create")
    public String createView(Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Category category, HttpServletRequest request, Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        String name = request.getParameter("name");
        category.setName(name);
        categoryService.addNewCategory(category);
        return "redirect:/category/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        categoryService.deleteCategoryById(id);
        return "redirect:/category/";
    }

    @GetMapping("/toggle/{status}/{id}")
    public String toggle(@PathVariable Long status, @PathVariable Long id, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        boolean available = false;
        if (status == 1){
            available = true;
        }
        categoryService.toggleCategoryStatus(id,available);
        return "redirect:/category/";
    }

    @GetMapping("/edit/{id}")
    public String editView(@PathVariable Long id, Model model, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        Category c = categoryService.getCategoryById(id);
        model.addAttribute("availableCategories",categoryService.getAvailableCategories());
        model.addAttribute("category",c);
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Category category,@PathVariable Long id, HttpServletRequest request, @CookieValue(value = "_session", defaultValue = "nan") String Id){
        if (!Id.equals("nan")){
            if (!userService.getUserById(Long.parseLong(Id)).getRole().equals("ADMIN")){
                return "redirect:/";
            }
        } else return "redirect:/";

        String name = request.getParameter("name");
        category.setName(name);
        categoryService.editCategoryById(id,category);
        return "redirect:/category/";
    }
}

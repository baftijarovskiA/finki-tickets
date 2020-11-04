package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> getAllCategories();

    List<Category> getAvailableCategories();

    Category getCategoryById(Long id);

    void addNewCategory(Category category);

    void editCategoryById(Long id, Category category);

    void deleteCategoryById(Long id);

    void toggleCategoryStatus(Long id, boolean status);
}

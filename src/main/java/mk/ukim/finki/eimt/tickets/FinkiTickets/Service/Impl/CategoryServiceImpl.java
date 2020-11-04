package mk.ukim.finki.eimt.tickets.FinkiTickets.Service.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl.CategoryRepositoryImpl;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepositoryImpl repository;

    public CategoryServiceImpl(CategoryRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.getAllCategories();
    }

    @Override
    public List<Category> getAvailableCategories() {
        return repository.getAvailableCategories();
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.getCategoryById(id);
    }

    @Override
    public void addNewCategory(Category category) {
        repository.addNewCategory(category);
    }

    @Override
    public void editCategoryById(Long id, Category category) {
        repository.editCategoryById(id,category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        repository.deleteCategoryById(id);
    }

    @Override
    public void toggleCategoryStatus(Long id, boolean status) {
        repository.toggleCategoryStatus(id,status);
    }
}

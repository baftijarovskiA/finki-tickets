package mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Impl;

import mk.ukim.finki.eimt.tickets.FinkiTickets.Model.Category;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.CategoryRepository;
import mk.ukim.finki.eimt.tickets.FinkiTickets.Repository.Jpa.CategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private CategoryJpaRepository repository;

    public CategoryRepositoryImpl(CategoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public List<Category> getAvailableCategories() {
        List<Category> categoryList = new ArrayList<>();
        for (Category c : repository.findAll()) {
            if (c.isAvailable()){
                categoryList.add(c);
            }
        }
        return categoryList;
    }

    @Override
    public Category getCategoryById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void addNewCategory(Category category) {
        category.setAvailable(true);
        repository.save(category);
    }

    @Override
    public void editCategoryById(Long id, Category category) {
        Category c = repository.findById(id).get();
        c.setName(category.getName());
        repository.save(c);
    }

    @Override
    public void deleteCategoryById(Long id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public void toggleCategoryStatus(Long id, boolean status) {
        Category c = repository.findById(id).get();
        c.setAvailable(status);
        repository.save(c);
    }
}

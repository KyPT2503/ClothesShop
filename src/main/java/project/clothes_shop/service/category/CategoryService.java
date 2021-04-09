package project.clothes_shop.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Category;
import project.clothes_shop.repo.CategoryRepo;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category add(Category category) {
        return null;
    }

    @Override
    public boolean remove(Category category) {
        return false;
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepo.getOne(id);
    }
}

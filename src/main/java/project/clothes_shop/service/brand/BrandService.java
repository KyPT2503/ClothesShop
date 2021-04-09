package project.clothes_shop.service.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Brand;
import project.clothes_shop.repo.BrandRepo;

import java.util.List;

@Service
public class BrandService implements IBrandService {
    @Autowired
    private BrandRepo brandRepo;

    @Override
    public List<Brand> findAll() {
        return brandRepo.findAll();
    }

    @Override
    public Brand add(Brand brand) {
        return null;
    }

    @Override
    public boolean remove(Brand brand) {
        return false;
    }

    @Override
    public Brand update(Brand brand) {
        return null;
    }

    @Override
    public Brand findById(Long id) {
        return brandRepo.getOne(id);
    }
}

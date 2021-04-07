package project.clothes_shop.service.size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Size;
import project.clothes_shop.repo.SizeRepo;

import java.util.List;

@Service
public class SizeService implements ISizeService {
    @Autowired
    private SizeRepo sizeRepo;

    @Override
    public List<Size> findAll() {
        return sizeRepo.findAll();
    }

    @Override
    public Size add(Size size) {
        return null;
    }

    @Override
    public boolean remove(Size size) {
        return false;
    }

    @Override
    public Size update(Size size) {
        return null;
    }

    @Override
    public Size findById(Long id) {
        return sizeRepo.getOne(id);
    }
}

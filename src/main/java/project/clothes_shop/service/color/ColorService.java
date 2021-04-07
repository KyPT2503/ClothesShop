package project.clothes_shop.service.color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Color;
import project.clothes_shop.repo.ColorRepo;

import java.util.List;

@Service
public class ColorService implements IColorService {
    @Autowired
    private ColorRepo colorRepo;

    @Override
    public List<Color> findAll() {
        return colorRepo.findAll();
    }

    @Override
    public Color add(Color color) {
        return null;
    }

    @Override
    public boolean remove(Color color) {
        return false;
    }

    @Override
    public Color update(Color color) {
        return null;
    }

    @Override
    public Color findById(Long id) {
        return colorRepo.getOne(id);
    }
}

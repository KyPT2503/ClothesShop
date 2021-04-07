package project.clothes_shop.service.clothes_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
import project.clothes_shop.repo.ClothesDetailRepo;

import java.util.List;

@Service
public class ClothesDetailService implements IClothesDetailService {
    @Autowired
    private ClothesDetailRepo clothesDetailRepo;

    @Override
    public List<ClothesDetail> findAll() {
        return null;
    }

    @Override
    public ClothesDetail add(ClothesDetail clothesDetail) {
        return clothesDetailRepo.save(clothesDetail);
    }

    @Override
    public boolean remove(ClothesDetail clothesDetail) {
        return false;
    }

    @Override
    public ClothesDetail update(ClothesDetail clothesDetail) {
        return null;
    }

    @Override
    public ClothesDetail findById(Long id) {
        return null;
    }

    @Override
    public boolean isExist(ClothesDetail clothesDetail) {
        return clothesDetailRepo.existsById(clothesDetail.getId());
    }
}

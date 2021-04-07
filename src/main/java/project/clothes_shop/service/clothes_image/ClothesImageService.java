package project.clothes_shop.service.clothes_image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
import project.clothes_shop.repo.ClothesImageRepo;

import java.util.List;

@Service
public class ClothesImageService implements IClothesImageService {
    @Autowired
    private ClothesImageRepo clothesImageRepo;

    @Override
    public List<ClothesImage> findAll() {
        return null;
    }

    @Override
    public ClothesImage add(ClothesImage clothesImage) {
        return clothesImageRepo.save(clothesImage);
    }

    @Override
    public boolean remove(ClothesImage clothesImage) {
        if (!clothesImageRepo.existsById(clothesImage.getId())) {
            return false;
        }
        clothesImageRepo.delete(clothesImage);
        return true;
    }

    @Override
    public ClothesImage update(ClothesImage clothesImage) {
        return clothesImageRepo.save(clothesImage);
    }

    @Override
    public ClothesImage findById(Long id) {
        return clothesImageRepo.getOne(id);
    }

    @Override
    public List<ClothesImage> findByClothesDetail(ClothesDetail clothesDetail) {
        return clothesImageRepo.findByClothesDetail(clothesDetail);
    }

    @Override
    public void removeByClothesDetail(ClothesDetail clothesDetail) {
        List<ClothesImage> clothesImages = this.findByClothesDetail(clothesDetail);
        for (ClothesImage clothesImage : clothesImages) {
            this.remove(clothesImage);
        }
    }
}

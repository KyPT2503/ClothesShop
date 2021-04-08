package project.clothes_shop.service.clothes_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
import project.clothes_shop.repo.ClothesDetailRepo;
import project.clothes_shop.service.clothes_image.ClothesImageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClothesDetailService implements IClothesDetailService {
    @Autowired
    private ClothesDetailRepo clothesDetailRepo;
    @Autowired
    private ClothesImageService clothesImageService;

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
        ClothesDetail clothesDetail = clothesDetailRepo.getOne(id);
        List<ClothesImage> clothesImages = clothesImageService.findByClothesDetail(clothesDetail);
        List<String> sources = new ArrayList<>();
        for (ClothesImage clothesImage : clothesImages) {
            sources.add(clothesImage.getSource());
        }
        clothesDetail.setSources(sources);
        return clothesDetail;
    }

    @Override
    public boolean isExist(ClothesDetail clothesDetail) {
        return clothesDetailRepo.existsById(clothesDetail.getId());
    }
}

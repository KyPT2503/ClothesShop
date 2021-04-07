package project.clothes_shop.service.clothes_image;

import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IClothesImageService extends IGeneralService<ClothesImage> {
    List<ClothesImage> findByClothesDetail(ClothesDetail clothesDetail);

    void removeByClothesDetail(ClothesDetail clothesDetail);
}

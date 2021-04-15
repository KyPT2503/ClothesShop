package project.clothes_shop.service.clothes_detail;

import project.clothes_shop.dto.ClothesSearchDTO;
import project.clothes_shop.model.Brand;
import project.clothes_shop.model.Category;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IClothesDetailService extends IGeneralService<ClothesDetail> {
    boolean isExist(ClothesDetail clothesDetail);

    List<ClothesDetail> searchDTO(ClothesSearchDTO clothesSearchDTO);

    void upViewCount(ClothesDetail clothesDetail);

    void updateSoldAmountAndQuantity(ClothesDetail clothesDetail, int soldAmount, int quantity);

    List<ClothesDetail> findByName(String name);

    List<ClothesDetail> findByBrand(Brand brand);

    List<ClothesDetail> findByCategory(Category category);

    List<ClothesDetail> findTop5BySoldAmount(int soldAmount);

    List<ClothesDetail> findTop5ByViewCount(int viewCount);
}

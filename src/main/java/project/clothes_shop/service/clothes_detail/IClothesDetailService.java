package project.clothes_shop.service.clothes_detail;

import project.clothes_shop.dto.ClothesSearchDTO;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IClothesDetailService extends IGeneralService<ClothesDetail> {
    boolean isExist(ClothesDetail clothesDetail);

    List<ClothesDetail> searchDTO(ClothesSearchDTO clothesSearchDTO);
}

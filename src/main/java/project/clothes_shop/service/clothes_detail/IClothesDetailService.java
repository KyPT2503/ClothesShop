package project.clothes_shop.service.clothes_detail;

import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.service.IGeneralService;

public interface IClothesDetailService extends IGeneralService<ClothesDetail> {
    boolean isExist(ClothesDetail clothesDetail);
}

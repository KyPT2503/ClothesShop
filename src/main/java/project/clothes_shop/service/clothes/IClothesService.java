package project.clothes_shop.service.clothes;

import org.springframework.data.domain.Page;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.service.IGeneralService;

public interface IClothesService extends IGeneralService<Clothes> {
    boolean isExist(Clothes clothes);
}

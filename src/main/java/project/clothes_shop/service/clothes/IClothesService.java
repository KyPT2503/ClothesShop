package project.clothes_shop.service.clothes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IClothesService extends IGeneralService<Clothes> {
    boolean isExist(Clothes clothes);

    Page<Clothes> findPageable(Pageable pageable);

    void disable(Clothes clothes);

    List<Clothes> fromDetailToClothes(List<ClothesDetail> clothesDetails);
    void setAllSourceListClothes(List<Clothes> clothes);
}

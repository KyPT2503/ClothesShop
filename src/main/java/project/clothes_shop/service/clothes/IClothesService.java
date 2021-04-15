package project.clothes_shop.service.clothes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.clothes_shop.model.Brand;
import project.clothes_shop.model.Category;
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

    void setSourceForClothes(Clothes clothes);

    List<Clothes> findByName(String name);

    List<Clothes> findByCategory(Category category);

    List<Clothes> findByBrand(Brand brand);

    List<Clothes> findTop5ByViewCount(int viewCount);

    List<Clothes> findTop5BySoldAmount(int soldAmount);
}

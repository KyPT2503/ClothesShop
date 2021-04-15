package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Brand;
import project.clothes_shop.model.Category;
import project.clothes_shop.model.ClothesDetail;

import java.util.List;

@Repository
public interface ClothesDetailRepo extends JpaRepository<ClothesDetail, Long> {
    List<ClothesDetail> findAllByNameContaining(String name);

    List<ClothesDetail> findAllByCategory(Category category);

    List<ClothesDetail> findAllByBrand(Brand brand);

    List<ClothesDetail> findTop5ByOrderByViewCountDesc();

    List<ClothesDetail> findTop5ByOrderBySoldAmountDesc();
}

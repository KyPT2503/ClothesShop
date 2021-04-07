package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;

import java.util.List;

@Repository
public interface ClothesImageRepo extends JpaRepository<ClothesImage, Long> {
    List<ClothesImage> findByClothesDetail(ClothesDetail clothesDetail);
}

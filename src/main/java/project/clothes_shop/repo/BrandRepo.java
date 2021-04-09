package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Brand;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long> {
}

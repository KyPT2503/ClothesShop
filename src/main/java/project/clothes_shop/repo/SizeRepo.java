package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Size;

@Repository
public interface SizeRepo extends JpaRepository<Size, Long> {
}

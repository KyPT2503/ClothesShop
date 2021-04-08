package project.clothes_shop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Clothes;

@Repository
public interface ClothesRepo extends PagingAndSortingRepository<Clothes, Long> {
    Page<Clothes> findAllByStatus(Boolean status, Pageable pageable);
}

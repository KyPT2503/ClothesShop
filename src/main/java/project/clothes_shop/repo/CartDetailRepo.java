package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Cart;
import project.clothes_shop.model.CartDetail;
import project.clothes_shop.model.Clothes;

import java.util.List;

@Repository
public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {
    List<CartDetail> getAllByCart(Cart cart);

    CartDetail getFirstByClothesAndCart(Clothes clothes, Cart cart);
}

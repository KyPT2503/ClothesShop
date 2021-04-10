package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findFirstByAppUser(AppUser appUser);
}

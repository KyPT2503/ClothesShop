package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.OrderContact;

@Repository
public interface OrderContactRepo extends JpaRepository<OrderContact, Long> {
}

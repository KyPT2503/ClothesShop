package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
}

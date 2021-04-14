package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Order;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByAppUser(AppUser appUser);

    List<Order> findAllByDateBetween(Date start, Date end);

    List<Order> findAllByDate(Date date);
}

package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    Room getFirstByAppUser(AppUser appUser);
}

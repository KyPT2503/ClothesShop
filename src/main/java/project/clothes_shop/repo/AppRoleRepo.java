package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.AppRole;

@Repository
public interface AppRoleRepo extends JpaRepository<AppRole, Long> {
}

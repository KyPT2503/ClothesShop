package project.clothes_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.clothes_shop.model.MailAndCode;

@Repository
public interface MailAndCodeRepo extends JpaRepository<MailAndCode, Long> {
    MailAndCode findFirstByEmail(String email);
}

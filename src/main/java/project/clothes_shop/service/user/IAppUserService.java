package project.clothes_shop.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.service.IGeneralService;

public interface IAppUserService extends IGeneralService<AppUser>, UserDetailsService {
    AppUser getCurrentUser();

    Page<AppUser> findPageable(Pageable pageable);
}

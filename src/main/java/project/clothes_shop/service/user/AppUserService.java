package project.clothes_shop.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.repo.AppUserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements IAppUserService {
    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public List<AppUser> findAll() {
        return null;
    }

    @Override
    public AppUser add(AppUser appUser) {
        return appUserRepo.save(appUser);
    }

    @Override
    public boolean remove(AppUser appUser) {
        return false;
    }

    @Override
    public AppUser update(AppUser appUser) {
        return null;
    }

    @Override
    public AppUser findById(Long id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findByEmail(username);
        if (appUser == null) {
            return null;
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(appUser.getRole());
        return new User(username, appUser.getPassword(), roles);
    }

    @Override
    public AppUser getCurrentUser() {
        AppUser appUser;
        String email;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null) return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        appUser = appUserRepo.findByEmail(email);
        return appUser;
    }

    @Override
    public AppUser getByEmail(String email) {
        return appUserRepo.findByEmail(email);
    }

    @Override
    public Page<AppUser> findPageable(Pageable pageable) {
        return appUserRepo.findAll(pageable);
    }
}

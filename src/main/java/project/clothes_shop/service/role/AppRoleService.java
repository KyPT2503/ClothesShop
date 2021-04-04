package project.clothes_shop.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.AppRole;
import project.clothes_shop.repo.AppRoleRepo;

import java.util.List;

@Service
public class AppRoleService implements IAppRoleService {
    @Autowired
    private AppRoleRepo appRoleRepo;

    @Override
    public List<AppRole> findAll() {
        return null;
    }

    @Override
    public AppRole add(AppRole appRole) {
        return null;
    }

    @Override
    public boolean remove(AppRole appRole) {
        return false;
    }

    @Override
    public AppRole update(AppRole appRole) {
        return null;
    }

    @Override
    public AppRole findById(Long id) {
        return appRoleRepo.getOne(id);
    }
}

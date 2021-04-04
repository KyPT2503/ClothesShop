package project.clothes_shop.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.service.user.AppUserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/current-user")
    private AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }
}

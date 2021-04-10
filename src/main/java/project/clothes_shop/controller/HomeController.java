package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class HomeController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private ICartService cartService;
    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @GetMapping("")
    public ModelAndView showIndex() {
        return new ModelAndView("user/index");
    }
}

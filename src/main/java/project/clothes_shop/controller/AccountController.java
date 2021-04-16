package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.order.IOrderService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IOrderService orderService;

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @GetMapping("")
    public ModelAndView showAccountPage() {
        return new ModelAndView("user/account", "user_to_update", appUserService.getCurrentUser());
    }

    @PostMapping("/change")
    public ModelAndView save(@Validated @ModelAttribute("user_to_update") AppUser appUser, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("user/account", "user_to_update", appUser);
        }
        appUserService.add(appUser);
        return new ModelAndView("redirect:/account");
    }
}

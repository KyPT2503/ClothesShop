package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.login_google.IGoogleLoginService;
import project.clothes_shop.service.mail.MailAndCodeService;
import project.clothes_shop.service.role.AppRoleService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private MailAndCodeService mailAndCodeService;
    @Autowired
    private AppRoleService appRoleService;
    @Autowired
    private IGoogleLoginService googleLoginService;

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @GetMapping("/login")
    public ModelAndView showIndex() {
        return new ModelAndView("user/login");
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("user/register", "user_register", new AppUser());
    }

    @PostMapping("/user-register")
    public ModelAndView register(@Validated @ModelAttribute("user_register") AppUser appUser, BindingResult bindingResult, @RequestParam(value = "code", defaultValue = "noop") String code) {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("user/register", "user_register", appUser);
        }
        if (!mailAndCodeService.isValidEmail(appUser.getEmail(), code)) {
            ModelAndView modelAndView = new ModelAndView("user/register");
            modelAndView.addObject("message", "M?? x??c th???c kh??ng h???p l???, vui l??ng ????ng k?? l???i.");
            modelAndView.addObject("user_register", appUser);
            return modelAndView;
        }
        appUser.setRole(appRoleService.findById(1L));
        //add cart for new user
        Cart cart = new Cart();
        cart.setAppUser(appUserService.add(appUser));
        return new ModelAndView("redirect:/logout");
    }

    @RequestMapping("login-google")
    public ModelAndView loginWithGoogleAccount(@RequestParam("code") String code) throws IOException {
        String token = googleLoginService.getToken(code);
        String data = googleLoginService.getData(token);
        googleLoginService.loginWithData(data);
        return new ModelAndView("redirect:/");
    }
}

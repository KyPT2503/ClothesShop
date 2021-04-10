package project.clothes_shop.service.cart;

import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.service.IGeneralService;

import javax.servlet.http.HttpSession;

public interface ICartService extends IGeneralService<Cart> {
    Cart getCurrentCart(Long id);

    Cart getCurrentCart(AppUser appuser);

    Cart getCurrentCart(HttpSession session);
}

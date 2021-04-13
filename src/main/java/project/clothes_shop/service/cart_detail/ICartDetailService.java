package project.clothes_shop.service.cart_detail;

import project.clothes_shop.model.Cart;
import project.clothes_shop.model.CartDetail;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface ICartDetailService extends IGeneralService<CartDetail> {
    List<CartDetail> getCartDetailsByCart(Cart cart);

    boolean isExist(Clothes clothes, Cart cart);

    void removeByCartAndClothes(Cart cart, Clothes clothes);

    void removeAllByCart(Cart cart);
}

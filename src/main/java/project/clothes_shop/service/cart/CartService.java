package project.clothes_shop.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.*;
import project.clothes_shop.repo.CartRepo;
import project.clothes_shop.service.cart_detail.CartDetailService;
import project.clothes_shop.service.clothes.IClothesService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IClothesService clothesService;

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Cart add(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public boolean remove(Cart cart) {
        return false;
    }

    @Override
    public Cart update(Cart cart) {
        return null;
    }

    @Override
    public Cart findById(Long id) {
        Cart cart = cartRepo.getOne(id);
        this.setDetailForCart(cart);
        return cart;
    }

    private void setDetailForCart(Cart cart) {
        cart.setCartDetails(cartDetailService.getCartDetailsByCart(cart));
    }

    @Override
    public Cart getCurrentCart(Long id) {
        Cart cart = cartRepo.getOne(id);
        this.setDetailForCart(cart);
        return cart;
    }

    @Override
    public Cart getCurrentCart(AppUser appuser) {
        Cart cart = cartRepo.findFirstByAppUser(appuser);
        this.setDetailForCart(cart);
        return cart;
    }

    @Override
    public Cart getCurrentCart(HttpSession session) {
        Cart cart = null;
        AppUser appUser = appUserService.getCurrentUser();
        if (appUser == null) {
            String cartId = (String) session.getAttribute("cartId");
            if (cartId == null) {
                session.setAttribute("cartId", this.add(new Cart()).getId().toString());
                session.setMaxInactiveInterval(30 * 24 * 60 * 60);
                cartId = (String) session.getAttribute("cartId");
            }
            // get cart by cart id (save by session)
            cart = this.getCurrentCart(Long.valueOf(cartId));
        } else {
            // get cart by user
            cart = this.getCurrentCart(appUser);
        }
        // set source for clothes_detail/clothes/cart_detail/cart
        List<Clothes> clothes = new ArrayList<>();
        for (CartDetail cartDetail : cart.getCartDetails()) {
            clothes.add(cartDetail.getClothes());
        }
        clothesService.setAllSourceListClothes(clothes);
        return cart;
    }
}

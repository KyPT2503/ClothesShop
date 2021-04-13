package project.clothes_shop.service.cart_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Cart;
import project.clothes_shop.model.CartDetail;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.repo.CartDetailRepo;

import java.util.List;

@Service
public class CartDetailService implements ICartDetailService {
    @Autowired
    private CartDetailRepo cartDetailRepo;

    @Override
    public List<CartDetail> findAll() {
        return null;
    }

    @Override
    public CartDetail add(CartDetail cartDetail) {
        // remove exist cart detail
        CartDetail cartDetail1 = cartDetailRepo.getFirstByClothesAndCart(cartDetail.getClothes(), cartDetail.getCart());
        if (cartDetail1 != null) {
            cartDetailRepo.delete(cartDetail1);
        }
        // and add new
        return cartDetailRepo.save(cartDetail);
    }

    @Override
    public boolean remove(CartDetail cartDetail) {
        if (!cartDetailRepo.existsById(cartDetail.getId())) {
            return false;
        }
        cartDetailRepo.delete(cartDetail);
        return true;
    }

    @Override
    public CartDetail update(CartDetail cartDetail) {
        return null;
    }

    @Override
    public CartDetail findById(Long id) {
        return cartDetailRepo.getOne(id);
    }

    @Override
    public List<CartDetail> getCartDetailsByCart(Cart cart) {
        return cartDetailRepo.getAllByCart(cart);
    }

    @Override
    public boolean isExist(Clothes clothes, Cart cart) {
        return cartDetailRepo.getFirstByClothesAndCart(clothes, cart) != null;
    }

    @Override
    public void removeByCartAndClothes(Cart cart, Clothes clothes) {
        cartDetailRepo.delete(cartDetailRepo.getFirstByClothesAndCart(clothes, cart));
    }

    @Override
    public void removeAllByCart(Cart cart) {
        for (CartDetail cartDetail : cart.getCartDetails()) {
            cartDetailRepo.delete(cartDetail);
        }
    }
}

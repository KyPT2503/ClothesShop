package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.*;
import project.clothes_shop.service.brand.IBrandService;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.cart_detail.CartDetailService;
import project.clothes_shop.service.cart_detail.ICartDetailService;
import project.clothes_shop.service.category.ICategoryService;
import project.clothes_shop.service.clothes.IClothesService;
import project.clothes_shop.service.clothes_detail.IClothesDetailService;
import project.clothes_shop.service.color.IColorService;
import project.clothes_shop.service.size.ISizeService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IClothesService clothesService;
    @Autowired
    private IClothesDetailService clothesDetailService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICartDetailService cartDetailService;

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @ModelAttribute("brands")
    public List<Brand> getAllBrand() {
        return brandService.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @ModelAttribute("colors")
    public List<Color> getAllColor() {
        return colorService.findAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getAllSize() {
        return sizeService.findAll();
    }

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        Cart cart = cartService.getCurrentCart(session);
        return cartService.getCurrentCart(session);
    }

    @GetMapping("")
    public ModelAndView showCartPage(HttpSession session) {
        Cart cart = cartService.getCurrentCart(session);
        ModelAndView modelAndView = new ModelAndView("user/cart");
        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping("/remove-session") // for test
    public ModelAndView removeSession(HttpSession session) {
        session.removeAttribute("cartId");
        return new ModelAndView("redirect:/cart");
    }

    @PutMapping("/add/{clothesId}/{amount}/{isAdd}")
    public List<String> addToCart(HttpSession session, @PathVariable("clothesId") Clothes clothes, @PathVariable("amount") int amount, @PathVariable("isAdd") boolean isAdd) {
        //List<String> contains: code (0/1/2), message(added, het hang, vuot qua so luong)
        List<String> result = new ArrayList<>();
        boolean cartDetailIsExist = cartDetailService.isExist(clothes, cartService.getCurrentCart(session));
        int quantity = clothes.getClothesDetail().getQuantity();
        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cartService.getCurrentCart(session));
        cartDetail.setAmount(amount);
        cartDetail.setClothes(clothes);
        if (isAdd) {
            if (cartDetailIsExist) {
                result.addAll(Arrays.asList("4", "Sản phẩm đã tồn tại trong giỏ hàng."));
            } else if (quantity == 0) {
                result.addAll(Arrays.asList("0", "Sản phẩm này hiện tại đã hết hàng."));
            } else {
                // add new cart detail
                cartDetailService.add(cartDetail);
                result.addAll(Arrays.asList("3", "Thêm vào giỏ hàng thành công."));
            }
        } else if (quantity == 0) {
            result.addAll(Arrays.asList("0", "Sản phẩm này hiện tại đã hết hàng."));
        } else if (quantity < amount) {
            result.addAll(Arrays.asList("1", "Số lượng bạn chọn đã vượt qua số lượng sản phẩm trong kho của chúng tôi (" + quantity + ")."));
        } else {
            if (cartDetailIsExist) {
                result.addAll(Arrays.asList("2", "Đã thay đổi số lượng sản phẩm này trong giỏ hàng của bạn."));
            } else {
                result.addAll(Arrays.asList("3", "Thêm vào giỏ hàng thành công."));
            }
            cartDetailService.add(cartDetail);
        }
        return result;
    }

    @DeleteMapping("/remove/{clothesId}")
    public void removeFromCart(HttpSession session, @PathVariable("clothesId") Clothes clothes) {
        cartDetailService.removeByCartAndClothes(cartService.getCurrentCart(session), clothes);
    }
}

package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.*;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.cart_detail.ICartDetailService;
import project.clothes_shop.service.clothes_detail.IClothesDetailService;
import project.clothes_shop.service.order.IOrderService;
import project.clothes_shop.service.order_contact.IOrderContactService;
import project.clothes_shop.service.order_detail.IOrderDetailService;
import project.clothes_shop.service.order_state.IOrderStateService;
import project.clothes_shop.service.payment.IPaymentService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private IOrderContactService orderContactService;
    @Autowired
    private IOrderStateService orderStateService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private ICartDetailService cartDetailService;
    @Autowired
    private IClothesDetailService clothesDetailService;


    @ModelAttribute("payments")
    public List<Payment> getAllPayment() {
        return paymentService.findAll();
    }

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @GetMapping("")
    public ModelAndView showCheckoutPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("user/checkout");
        double total = 0;
        Cart cart = cartService.getCurrentCart(session);
        for (CartDetail cartDetail : cartService.getCurrentCart(session).getCartDetails()) {
            total += cartDetail.getAmount() * cartDetail.getClothes().getClothesDetail().getPrice();
        }
        modelAndView.addObject("total", total);
        Order order = new Order();
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @PostMapping("")
    public ModelAndView checkout(HttpSession session, @ModelAttribute("order") Order order) {
        order.setOrderContact(orderContactService.add(order.getOrderContact()));
        order.setDate(Date.valueOf(LocalDate.now().toString()));
        order.setOrderState(orderStateService.findById(1L));
        if (appUserService.getCurrentUser() != null) {
            order.setAppUser(appUserService.getCurrentUser());
        }
        order = orderService.add(order);
        // save orderId to session (session save order ids by a string)
        if (appUserService.getCurrentUser() == null) {
            String orderIds = (String) session.getAttribute("orderIds");
            if (orderIds == null) {
                String ids = String.valueOf(order.getId());
                session.setAttribute("orderIds", ids);
            } else {
                orderIds += "," + String.valueOf(order.getId());
                session.setAttribute("orderIds", orderIds);
            }
        }
        // set order detail form cart detail
        Cart cart = cartService.getCurrentCart(session);
        orderDetailService.setDetailForNewOrderFromCart(order, cart);

        // update soldAmount and quantity for clothes_detail
        for (CartDetail cartDetail : cart.getCartDetails()) {
            clothesDetailService.updateSoldAmountAndQuantity(cartDetail.getClothes().getClothesDetail(), cartDetail.getClothes().getClothesDetail().getSoldAmount() + cartDetail.getAmount(), cartDetail.getClothes().getClothesDetail().getQuantity() - cartDetail.getAmount());
        }

        // remove all in cart detail
        cartDetailService.removeAllByCart(cart);
        return new ModelAndView("redirect:/order");
    }
}

package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.model.Order;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.order.IOrderService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {
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
    public ModelAndView showOrders(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("user/order");
        if (appUserService.getCurrentUser() == null) {
            if (session.getAttribute("orderIds") == null) {
                modelAndView.addObject("orders", new ArrayList<Order>());
            } else {
                modelAndView.addObject("orders", orderService.getAllBySession((String) session.getAttribute("orderIds")));
            }
        } else {
            modelAndView.addObject("orders", orderService.getAllByAppUser(appUserService.getCurrentUser()));
        }
        return modelAndView;
    }

    @DeleteMapping("/remove/{orderId}")
    public String removeOrder(@PathVariable("orderId") Order order) {
        boolean isRemoved = orderService.remove(order);
        if (isRemoved) {
            return "Hủy đơn hàng thành công.";
        } else {
            return "Đơn hàng của bạn không thể hủy do đang trong quá trình vận chuyển hoặc đã giao hàng.";
        }
    }
}

package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.dto.DateSearchDTO;
import project.clothes_shop.model.Order;
import project.clothes_shop.model.OrderDetail;
import project.clothes_shop.model.OrderState;
import project.clothes_shop.service.order.IOrderService;
import project.clothes_shop.service.order_detail.IOrderDetailService;
import project.clothes_shop.service.user.IAppUserService;

import java.util.List;

@RestController
@RequestMapping("/admin/order")
public class OrderAdminController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IAppUserService appUserService;

    @GetMapping("")
    public ModelAndView showOrderPage(@PageableDefault(size = 3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("admin/order");
        Page<Order> orders = orderService.findPageable(pageable);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    private List<OrderDetail> getOrderDetailByOrder(@PathVariable("id") Order order) {
        // if is admin, continue
        // if user has this order, continue
        // if session save this order
        return orderDetailService.getAllByOrder(order);
    }

    @PutMapping("/change-state/{orderId}/{stateId}")
    private void changeOrderState(@PathVariable("orderId") Order order, @PathVariable("stateId") OrderState orderState) {
        order.setOrderState(orderState);
        orderService.add(order);
    }

    @PostMapping("/search")
    private List<Order> searchByDateRange(@RequestBody DateSearchDTO dateSearchDTO) {
        List<Order> orders = orderService.findByDateRange(dateSearchDTO.convertDateSql(dateSearchDTO.getStartDay()), dateSearchDTO.convertDateSql(dateSearchDTO.getEndDay()));
        return orders;
    }
}

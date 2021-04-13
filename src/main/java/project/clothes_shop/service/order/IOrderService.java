package project.clothes_shop.service.order;

import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Order;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IOrderService extends IGeneralService<Order> {
    List<Order> getAllBySession(String ordersString);

    List<Order> getAllByAppUser(AppUser appUser);

    void setOrderDetailForOrder(Order order);

    void setOrderDetailForListOrder(List<Order> orders);
}

package project.clothes_shop.service.order_detail;

import project.clothes_shop.model.Cart;
import project.clothes_shop.model.Order;
import project.clothes_shop.model.OrderDetail;
import project.clothes_shop.service.IGeneralService;

import java.util.List;

public interface IOrderDetailService extends IGeneralService<OrderDetail> {
    void setDetailForNewOrderFromCart(Order order, Cart cart);

    List<OrderDetail> getAllByOrder(Order order);
}

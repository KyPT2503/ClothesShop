package project.clothes_shop.service.order_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Cart;
import project.clothes_shop.model.CartDetail;
import project.clothes_shop.model.Order;
import project.clothes_shop.model.OrderDetail;
import project.clothes_shop.repo.OrderDetailRepo;
import project.clothes_shop.repo.OrderRepo;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public OrderDetail add(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public boolean remove(OrderDetail orderDetail) {
        return false;
    }

    @Override
    public OrderDetail update(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public OrderDetail findById(Long id) {
        return null;
    }

    @Override
    public void setDetailForNewOrderFromCart(Order order, Cart cart) {
        for (CartDetail cartDetail : cart.getCartDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setClothes(cartDetail.getClothes());
            orderDetail.setAmount(cartDetail.getAmount());
            orderDetailRepo.save(orderDetail);
        }
    }

    @Override
    public List<OrderDetail> getAllByOrder(Order order) {
        return orderDetailRepo.getAllByOrder(order);
    }
}

package project.clothes_shop.service.order_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.clothes_shop.model.*;
import project.clothes_shop.repo.OrderDetailRepo;
import project.clothes_shop.repo.OrderRepo;
import project.clothes_shop.service.clothes_detail.IClothesDetailService;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private IClothesDetailService clothesDetailService;

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public OrderDetail add(OrderDetail orderDetail) {
        return null;
    }

    @Override
    @Transactional
    public boolean remove(OrderDetail orderDetail) {
        // update soldAmount and quantity
        ClothesDetail clothesDetail = orderDetail.getClothes().getClothesDetail();
        clothesDetailService.updateSoldAmountAndQuantity(clothesDetail, clothesDetail.getSoldAmount() - orderDetail.getAmount(), clothesDetail.getQuantity() + orderDetail.getAmount());
        // remove order detail
        orderDetailRepo.delete(orderDetail);
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

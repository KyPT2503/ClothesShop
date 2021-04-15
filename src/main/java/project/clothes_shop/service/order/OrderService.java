package project.clothes_shop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Order;
import project.clothes_shop.model.OrderContact;
import project.clothes_shop.model.OrderDetail;
import project.clothes_shop.repo.OrderRepo;
import project.clothes_shop.service.order_contact.IOrderContactService;
import project.clothes_shop.service.order_detail.IOrderDetailService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IOrderContactService orderContactService;

    @Override
    public List<Order> findAll() {
        return findAll();
    }

    @Override
    public Order add(Order order) {
        return orderRepo.save(order);
    }

    @Override
    @Transactional
    public boolean remove(Order order) {
        if (order.getOrderState().getId() != 1L) {
            return false;
        }
        if (!orderRepo.existsById(order.getId())) {
            return false;
        }
        //remove order detail (update quantity and soldAmount)
        List<OrderDetail> orderDetails = orderDetailService.getAllByOrder(order);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetailService.remove(orderDetail);
        }
        OrderContact orderContact = order.getOrderContact();

        // remove order
        orderRepo.delete(order);

        //remove order contact
        orderContactService.remove(orderContact);
        return true;
    }

    @Override
    public Order update(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepo.getOne(id);
    }

    @Override
    public List<Order> getAllBySession(String ordersString) {
        List<String> idsString = Arrays.asList(ordersString.split(","));
        List<Order> result = new ArrayList<>();
        for (String id : idsString) {
            Long id_ = Long.valueOf(id);
            result.add(orderRepo.getOne(id_));
        }
        this.setOrderDetailForListOrder(result);
        return result;
    }

    @Override
    public List<Order> getAllByAppUser(AppUser appUser) {
        List<Order> result = orderRepo.findAllByAppUser(appUser);
        this.setOrderDetailForListOrder(result);
        return result;
    }

    @Override
    public void setOrderDetailForOrder(Order order) {
        order.setOrderDetails(orderDetailService.getAllByOrder(order));
    }

    @Override
    public void setOrderDetailForListOrder(List<Order> orders) {
        for (Order order : orders) {
            this.setOrderDetailForOrder(order);
        }
    }

    @Override
    public Page<Order> findPageable(Pageable pageable) {
        Page<Order> orders = orderRepo.findAll(pageable);
        this.setOrderDetailForListOrder(orders.toList());
        return orders;
    }

    @Override
    public List<Order> findByDateRange(Date start, Date end) {
        return orderRepo.findAllByDateBetween(start, end);
    }

    @Override
    public List<Order> findAllByDate(Date date) {
        List<Order> orders = new ArrayList<>();
        orders = orderRepo.findAllByDate(date);
        this.setOrderDetailForListOrder(orders);
        return orders;
    }
}

package project.clothes_shop.service.order_contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.OrderContact;
import project.clothes_shop.repo.OrderContactRepo;

import java.util.List;

@Service
public class OrderContactService implements IOrderContactService {
    @Autowired
    private OrderContactRepo orderContactRepo;

    @Override
    public List<OrderContact> findAll() {
        return orderContactRepo.findAll();
    }

    @Override
    public OrderContact add(OrderContact orderContact) {
        return orderContactRepo.save(orderContact);
    }

    @Override
    public boolean remove(OrderContact orderContact) {
        orderContactRepo.delete(orderContact);
        return false;
    }

    @Override
    public OrderContact update(OrderContact orderContact) {
        return null;
    }

    @Override
    public OrderContact findById(Long id) {
        return null;
    }
}

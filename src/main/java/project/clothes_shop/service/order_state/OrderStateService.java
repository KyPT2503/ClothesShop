package project.clothes_shop.service.order_state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.OrderState;
import project.clothes_shop.repo.OrderStateRepo;

import java.util.List;

@Service
public class OrderStateService implements IOrderStateService {
    @Autowired
    private OrderStateRepo orderStateRepo;

    @Override
    public List<OrderState> findAll() {
        return orderStateRepo.findAll();
    }

    @Override
    public OrderState add(OrderState orderState) {
        return null;
    }

    @Override
    public boolean remove(OrderState orderState) {
        return false;
    }

    @Override
    public OrderState update(OrderState orderState) {
        return null;
    }

    @Override
    public OrderState findById(Long id) {
        return orderStateRepo.getOne(id);
    }
}

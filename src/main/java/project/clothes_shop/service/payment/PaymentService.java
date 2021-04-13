package project.clothes_shop.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.Payment;
import project.clothes_shop.repo.PaymentRepo;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Override
    public List<Payment> findAll() {
        return paymentRepo.findAll();
    }

    @Override
    public Payment add(Payment payment) {
        return null;
    }

    @Override
    public boolean remove(Payment payment) {
        return false;
    }

    @Override
    public Payment update(Payment payment) {
        return null;
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepo.getOne(id);
    }
}

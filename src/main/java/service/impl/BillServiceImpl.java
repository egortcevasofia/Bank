package service.impl;

import entity.Bill;
import exception.BillNotFoundException;
import repository.impl.BillRepositoryImpl;
import repository.interfaces.BillRepository;
import service.interfaces.BillService;

import java.util.List;
import java.util.Optional;

public class BillServiceImpl implements BillService {
    private final BillRepository billRepository = new BillRepositoryImpl();
    private static final String BILL_NOT_FOUND_MESSAGE = "Bill with id %d not found";

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        Optional<Bill> optional = billRepository.findById(id);
        return optional.orElseThrow(() -> new BillNotFoundException(String.format(BILL_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public List<Bill> findAllByClientId(Long id) {
        return billRepository.findAllByClientId(id);
    }
}

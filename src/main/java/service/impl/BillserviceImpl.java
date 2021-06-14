package service.impl;

import entity.Bill;
import repository.interfaces.BillRepository;
import service.interfaces.BillService;

import java.util.List;

public class BillserviceImpl implements BillService {
    private BillRepository billRepository;

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public List<Bill> findAllByClientId(Long id) {
        return billRepository.findAllByClientId(id);
    }
}

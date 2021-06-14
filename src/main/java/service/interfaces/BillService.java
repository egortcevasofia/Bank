package service.interfaces;

import entity.Bill;

import java.util.List;

public interface BillService {
    Bill save(Bill bill);
    Bill findById(Long id);
    List<Bill> findAllByClientId(Long id);
}

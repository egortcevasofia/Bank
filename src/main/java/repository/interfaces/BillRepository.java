package repository.interfaces;

import entity.Bill;
import entity.Card;

import java.util.List;

public interface BillRepository {
    Bill save(Bill bill);
    Bill findById(Long id);
    List<Bill> findAllByClientId(Long id);
    void createTable();
    void dropTable();
}

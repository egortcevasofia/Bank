package repository.interfaces;

import entity.Bill;

import java.util.List;
import java.util.Optional;

public interface BillRepository {
    Bill save(Bill bill);
    Optional<Bill> findById(Long id);
    List<Bill> findAllByClientId(Long id);
    void createTable();
    void dropTable();
}

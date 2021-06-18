package repositoryTest;

import entity.Bill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.BillRepositoryImpl;
import repository.interfaces.BillRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillRepositoryTest {
    private final BillRepository billRepository = new BillRepositoryImpl();

    @BeforeEach
    public void setBeforeEach(){
        billRepository.createTable();
        billRepository.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 10000.99, 1L));
        billRepository.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 3L));
        billRepository.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 3L));
    }

    @DisplayName("Save bill in the BD and check it")
    @Test
    public void test_saveBill() {
        Bill bill = new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 4L);
        Bill savedBill = billRepository.save(bill);

        assertEquals(savedBill.getId(), 4L);
        assertEquals(savedBill.getLocalDateTime(), bill.getLocalDateTime());
        assertEquals(savedBill.getPayment(), bill.getPayment());
        assertEquals(savedBill.getClientId(), bill.getClientId());
    }

    @DisplayName("Get bill by id from the BD")
    @Test
    public void test_findBillById() {
        Bill bill = new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 4L);
        billRepository.save(bill);
        Bill savedBill = billRepository.findById(4L).get();
        assertEquals(savedBill.getId(), 4L);
        assertEquals(savedBill.getLocalDateTime(), bill.getLocalDateTime());
        assertEquals(savedBill.getPayment(), bill.getPayment());
        assertEquals(savedBill.getClientId(), bill.getClientId());
    }


    @DisplayName("Find all bills by clientId from BD")
    @Test
    public void test_findAllByClientId() {
        List<Bill> listOfBill = billRepository.findAllByClientId(3L);
        assertEquals(listOfBill.size(), 2);
        assertAll(() -> {
            assertEquals(listOfBill.get(0).getClientId(), 3);
            assertEquals(listOfBill.get(1).getClientId(), 3);
        });
    }


    //@AfterEach
    public void cleanAfterTest() {
        billRepository.dropTable();
    }
}

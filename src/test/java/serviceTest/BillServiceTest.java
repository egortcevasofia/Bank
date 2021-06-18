package serviceTest;

import entity.Bill;
import exception.AccountNotFoundException;
import exception.BillNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.BillRepositoryImpl;
import repository.interfaces.BillRepository;
import service.impl.BillServiceImpl;
import service.interfaces.BillService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BillServiceTest {
    private BillRepository billRepository = new BillRepositoryImpl();
    private BillService billService = new BillServiceImpl();
    @BeforeEach
    public void setBeforeEach(){
        billRepository.createTable();
        billService.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 1L));
        billService.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 3L));
        billService.save(new Bill(LocalDateTime.of(1999, 5, 5, 9, 9, 9), 99.99, 3L));
    }

    @DisplayName("Assert exception when bill not exist")
    @Test
    public void test_findBillById() {
        assertThrows(BillNotFoundException.class, () -> billService.findById(4L) );
    }

    @AfterEach
    public void cleanAfterTest() {
        billRepository.dropTable();
    }
}

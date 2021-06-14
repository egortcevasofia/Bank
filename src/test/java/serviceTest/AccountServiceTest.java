package serviceTest;

import entity.Account;
import entity.AccountStatus;
import exception.AccountAlreadyExists;
import exception.AccountNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;
import service.impl.AccountServiceImpl;
import service.interfaces.AccountService;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountService accountService = new AccountServiceImpl();
    private AccountRepository accountRepository = new AccountRepositoryImpl();


    @BeforeEach
    public void setBeforeEach() {
        accountRepository.createTable();
        accountService.save(new Account("Ivanov", "qwertI", AccountStatus.ACTIVE, 1L));
        accountService.save(new Account("Sidorov", "qwertyS", AccountStatus.ACTIVE, 2L));
        accountService.save(new Account("Petrov", "qwertyP", AccountStatus.ACTIVE, 3L));
    }

    @DisplayName("Assert exception when account not exist")
    @Test
    public void test_findAccountById() {
        assertThrows(AccountNotFoundException.class, () -> accountService.findById(4L) );
    }


    @DisplayName("Assert exception when account already exist")
    @Test
    public void test_saveAccount() {
        assertThrows(AccountAlreadyExists.class, () -> accountService.save(new Account("Ivan", "qwertI", AccountStatus.ACTIVE, 1L)) );
    }

    @DisplayName("Assert exception when account not exist")
    @Test
    public void test_updateById() {
        assertThrows(AccountNotFoundException.class, () -> accountService.update(4L, new Account("Ivan", "qwertI", AccountStatus.ACTIVE, 1L)) );
    }

    @DisplayName("Assert exception when account not exist")
    @Test
    public void test_deleteById() {
        assertThrows(AccountNotFoundException.class, () -> accountService.delete(4L));
    }

    @DisplayName("Assert exception when account not exist")
    @Test
    public void test_findByClientId() {
        assertThrows(AccountNotFoundException.class, () -> accountService.findByClientId(4L));

    }

    @AfterEach
    public void cleanAfterTest() {
        accountRepository.dropTable();
    }
}

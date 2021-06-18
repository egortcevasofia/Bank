package repositoryTest;

import java.util.List;
import java.util.Optional;

import entity.Account;
import entity.AccountStatus;
import org.junit.jupiter.api.*;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;
import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {
    private final AccountRepository accountRepository = new AccountRepositoryImpl();


    @BeforeEach
    public void setBeforeEach() {
        accountRepository.createTable();
        accountRepository.save(new Account("Ivanov", "qwertI", AccountStatus.ACTIVE, 1L));
        accountRepository.save(new Account("Sidorov", "qwertyS", AccountStatus.ACTIVE, 2L));
        accountRepository.save(new Account("Petrov", "qwertyP", AccountStatus.ACTIVE, 3L));
    }

    @DisplayName("Save account in the BD and check it")
    @Test
    public void test_saveAccount() {
        Account account = new Account("Vasyin", "qwertyV", AccountStatus.ACTIVE, 4L);
        Account savedAccount = accountRepository.save(account);
        assertEquals(savedAccount.getId(), 4);
        assertEquals(savedAccount.getLogin(), account.getLogin());
        assertEquals(savedAccount.getPassword(), account.getPassword());
        assertEquals(savedAccount.getAccountStatus(), account.getAccountStatus());
        assertEquals(savedAccount.getClientId(), account.getClientId());
    }

    @DisplayName("Get account by id from the BD")
    @Test
    public void test_findAccountById() {
        Account account = new Account("Vasyin", "qwertyV", AccountStatus.ACTIVE, 4L);
        accountRepository.save(account);
        Account savedAccount = accountRepository.findById(4L).get();
        assertAll(() -> {
            assertEquals(Optional.of(savedAccount.getId()).get(), 4L);
            assertEquals(savedAccount.getLogin(), account.getLogin());
            assertEquals(savedAccount.getPassword(), account.getPassword());
            assertEquals(savedAccount.getAccountStatus(), account.getAccountStatus());
            assertEquals(Optional.of(savedAccount.getClientId()).get(), 4L);
        });
    }

    @DisplayName("Get list of all accounts")
    @Test
    public void test_findAll() {
        List<Account> list = accountRepository.findAll();
        assertEquals(list.size(), 3);
    }

    @DisplayName("Get account from BD by id")
    @Test
    public void test_updateById() {
        Long id = 3L;
        Account account = new Account("Vasyin", "qwertyV", AccountStatus.ACTIVE, 4L);
        accountRepository.updateById(id, account);
        Account updatedAccount = accountRepository.findById(id).get();
        assertNotNull(updatedAccount);
        assertAll(() -> {
            assertEquals(Optional.of(updatedAccount.getId()).get(), 3L);
            assertEquals(updatedAccount.getLogin(), account.getLogin());
            assertEquals(updatedAccount.getPassword(), account.getPassword());
            assertEquals(updatedAccount.getAccountStatus(), account.getAccountStatus());
            assertEquals(Optional.of(updatedAccount.getClientId()).get(), 4L);
        });
    }

    @DisplayName("Delite account from BD by id")
    @Test
    public void test_deleteById() {
        Long id = 3L;
        accountRepository.deleteById(id);
        Account delitedAccount = accountRepository.findById(id).get();
        assertEquals(delitedAccount.getAccountStatus(), AccountStatus.DELETED);
    }

    @DisplayName("Find account  from BD by ClientId")
    @Test
    public void test_findByClientId() {
        Account account = new Account("Vasyin", "qwertyV", AccountStatus.ACTIVE, 4L);
        accountRepository.save(account);
        Long id = 4L;
        Account savedAccount = accountRepository.findByClientId(id).get();
        assertAll(() -> {
            assertEquals(Optional.of(savedAccount.getId()).get(), 4L);
            assertEquals(savedAccount.getLogin(), account.getLogin());
            assertEquals(savedAccount.getPassword(), account.getPassword());
            assertEquals(savedAccount.getAccountStatus(), account.getAccountStatus());
            assertEquals(Optional.of(savedAccount.getClientId()).get(), 4L);
        });
    }

    @AfterEach
    public void cleanAfterTest() {
        accountRepository.dropTable();
    }

}

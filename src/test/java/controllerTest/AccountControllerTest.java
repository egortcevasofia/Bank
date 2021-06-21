package controllerTest;

import controller.AccountController;
import entity.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.impl.AccountServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountControllerTest {
    AccountController accountController = new AccountController(new AccountServiceImpl());

    @DisplayName("Get list of all accounts")
    @Test
    public void test_findAll() {
        List<Account> list = accountController.findAll();
        assertEquals(list.size(), 4);
    }
}

package serviceTest;

import entity.*;
import exception.AccountExistsException;
import exception.CardExistsException;
import exception.ClientExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.impl.BankServiceImpl;
import service.interfaces.BankService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankServiceTest {
    private BankService bankService = new BankServiceImpl();

    @DisplayName("Save. Assert exception when account already exist")
    @Test
    public void test_createAccount() {
        assertThrows(AccountExistsException.class, () -> bankService.creatAccount(new Account("Ivan", "qwertI", AccountStatus.ACTIVE, 1L)) );
    }

    @DisplayName("Save. Assert exception when client already exist")
    @Test
    public void test_creatClient() {
        assertThrows(ClientExistsException.class, () -> bankService.createClient(new Client("Petr", "Petrov", 25, LocalDate.of(1993, 5, 6))));
    }

    @DisplayName("Save. Assert exception when card already exist")
    @Test
    public void test_creatCard() {
        assertThrows(CardExistsException.class, () -> bankService.createCard(new Card(100.90, TypeCard.STUDENT, 3L)));
    }
}

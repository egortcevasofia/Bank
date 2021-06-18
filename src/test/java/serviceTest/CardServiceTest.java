package serviceTest;

import entity.Account;
import entity.AccountStatus;
import entity.Card;
import entity.TypeCard;
import exception.AccountExistsException;
import exception.CardExistsException;
import exception.CardNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.CardRepositoryImpl;
import repository.interfaces.CardRepository;
import service.impl.CardServiceImpl;
import service.interfaces.CardService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CardServiceTest {
    private CardRepository cardRepository = new CardRepositoryImpl();
    private CardService cardService = new CardServiceImpl();

    @BeforeEach
    public void setBeforeEach(){
        cardRepository.createTable();
        cardService.save(new Card(100.90, TypeCard.STUDENT, 1L));
        cardService.save(new Card(100.90, TypeCard.STUDENT, 2L));
        cardService.save(new Card(100.90, TypeCard.STUDENT, 3L));
    }

    @DisplayName("Save card in the BD and check it")
    @Test
    public void test_saveCard() {
        assertThrows(CardExistsException.class, () -> cardService.save(new Card(100.90, TypeCard.STUDENT, 3L)));
    }

    @DisplayName("Get card by id from the BD")
    @Test
    public void test_findCardById() {
        assertThrows(CardNotFoundException.class, () -> cardService.findById(4L));
    }

    @DisplayName("Get card by clientId from the BD")
    @Test
    public void test_findCardByClientId() {
        assertThrows(CardNotFoundException.class, () -> cardService.getCardByClientId(4L));
    }

    @DisplayName("Change balance card by clientId from the BD")
    @Test
    public void test_changeBalanceByCardId() {
        assertThrows(CardNotFoundException.class, () -> cardService.changeBalance(4L, -70.00));
    }
    //todo возможно добавить сюда проверку баланса

    @AfterEach
    public void cleanAfterTest() {
        cardRepository.dropTable();
    }
}

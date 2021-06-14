package repositoryTest;

import entity.Card;

import entity.TypeCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.CardRepositoryImpl;

import repository.interfaces.CardRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardRepositoryTest {
    private final CardRepository cardRepository = new CardRepositoryImpl();

    @BeforeEach
    public void setBeforeEach(){
        cardRepository.createTable();
        cardRepository.save(new Card(100.90, TypeCard.STUDENT, 1L));
        cardRepository.save(new Card(100.90, TypeCard.STUDENT, 2L));
        cardRepository.save(new Card(100.90, TypeCard.STUDENT, 3L));
    }

    @DisplayName("Save card in the BD and check it")
    @Test
    public void test_saveCard() {
        Card card = new Card(100.90, TypeCard.STUDENT, 4L);
        Card savedCard = cardRepository.save(card);

        assertEquals(savedCard.getId(), 4L);
        assertEquals(savedCard.getBalance(), card.getBalance());
        assertEquals(savedCard.getTypeCard(), card.getTypeCard());
        assertEquals(savedCard.getClientId(), card.getClientId());
    }

    @DisplayName("Get card by id from the BD")
    @Test
    public void test_findCardById() {
        Card card = new Card(100.90, TypeCard.STUDENT, 4L);
        cardRepository.save(card);
        Card savedCard = cardRepository.findById(4L);
        assertEquals(savedCard.getId(), 4L);
        assertEquals(savedCard.getBalance(), card.getBalance());
        assertEquals(savedCard.getTypeCard(), card.getTypeCard());
        assertEquals(savedCard.getClientId(), card.getClientId());
    }


    @DisplayName("Change balance in BD")
    @Test
    public void test_changeBalancePos() {
        Long id = 3L;
        Double amountPositive = 50.50;

        cardRepository.changeBalance(id, amountPositive);
        Card updatedCard = cardRepository.findById(id);
        assertEquals(updatedCard.getBalance(), 151.40);
    }

    @DisplayName("Change balance in BD")
    @Test
    public void test_changeBalanceNeg() {
        Long id = 3L;
        Double amountNegative = -50.50;

        cardRepository.changeBalance(id, amountNegative);
        Card updatedCard = cardRepository.findById(id);
        assertEquals(updatedCard.getBalance(), 50.40);
    }

    @AfterEach
    public void cleanAfterTest() {
        cardRepository.dropTable();
    }
}

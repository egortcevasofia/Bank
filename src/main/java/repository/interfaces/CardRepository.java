package repository.interfaces;


import entity.Card;

import java.util.Optional;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(Long id);
    Optional<Card> changeBalance(Long cardId, Double amount);
    Optional<Card> getCardByClientId(Long id);
    void createTable();
    void dropTable();

}

package repository.interfaces;


import entity.Card;

public interface CardRepository {
    Card save(Card card);
    Card findById(Long id);
    Card changeBalance(Long cardId, Double amount);
    Long getCardIdBYClientId(Long id);
    void createTable();
    void dropTable();

}

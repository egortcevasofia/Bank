package service.interfaces;

import entity.Card;

public interface CardService {
    Card save(Card card);
    Card findById(Long id);
    Card changeBalance(Long cardId, Double amount);
    Card getCardByClientId(Long id);
}

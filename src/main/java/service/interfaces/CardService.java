package service.interfaces;

import entity.Card;

public interface CardService {
    Card save(Card card);
    Card findById(Long id);
    Card changeBalance(Long id, Double amount);
    Long getCardIdBYClientId(Long id);
}

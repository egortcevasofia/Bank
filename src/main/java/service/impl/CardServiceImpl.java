package service.impl;

import entity.Card;
import repository.interfaces.CardRepository;
import service.interfaces.CardService;

public class CardServiceImpl implements CardService {
private CardRepository cardRepository;

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Card changeBalance(Long id, Double amount) {
        return cardRepository.changeBalance(id, amount);
    }

    @Override
    public Long getCardIdBYClientId(Long id) {
        return cardRepository.getCardIdBYClientId(id);
    }
}

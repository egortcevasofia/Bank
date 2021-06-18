package service.impl;

import entity.Card;
import exception.CardExistsException;
import exception.CardNotFoundException;
import repository.impl.CardRepositoryImpl;
import repository.interfaces.CardRepository;
import service.interfaces.CardService;

import java.util.Optional;

public class CardServiceImpl implements CardService {
private final CardRepository cardRepository = new CardRepositoryImpl();
private static final String CARD_ALREADY_EXISTS_MESSAGE = "Card with clientId %d already exists";
private static final String CARD_NOT_FOUND_MESSAGE = "Card with id %d not found";

    @Override
    public Card save(Card card) {
        Long clientId = card.getClientId();
        Optional<Card> optional = cardRepository.getCardByClientId(clientId);
        if (optional.isEmpty()) {
            return cardRepository.save(card);
        } else {
            throw new CardExistsException(String.format(CARD_ALREADY_EXISTS_MESSAGE, clientId));
        }
    }

    @Override
    public Card findById(Long id) {
        Optional<Card> optional = cardRepository.findById(id);
        return optional.orElseThrow(() -> new CardNotFoundException(String.format(CARD_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public Card changeBalance(Long cardId, Double amount) {
        Optional<Card> optional = cardRepository.changeBalance(cardId, amount);
        return optional.orElseThrow(() -> new CardNotFoundException(String.format(CARD_NOT_FOUND_MESSAGE, cardId)));
    }

    @Override
    public Card getCardByClientId(Long id) {
        Optional<Card> optional = cardRepository.getCardByClientId(id);
        return optional.orElseThrow(() -> new CardNotFoundException(String.format(CARD_NOT_FOUND_MESSAGE, id)));
    }
}

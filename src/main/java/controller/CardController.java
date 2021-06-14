package controller;

import entity.Card;
import service.interfaces.CardService;

public class CardController {

    private final CardService cardService;

    public Card save(Card card){
        return cardService.save(card);
    }
    public Card findById(Long id){
        return cardService.findById(id);
    }
    public Card changeBalance(Long cardId, Double amount){
        return cardService.changeBalance(cardId, amount);
    }


    public CardController(CardService cardService) {
        this.cardService = cardService;
    }



}

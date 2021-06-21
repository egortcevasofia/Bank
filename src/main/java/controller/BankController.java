package controller;

import entity.Account;
import entity.Bill;
import entity.Card;
import entity.Client;
import service.interfaces.BankService;

import java.util.List;

public class BankController {
    private final BankService bankService;
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    public Client createClient(Client client){
        return bankService.createClient(client);
    }
    public Account creatAccount(Account account){
        return bankService.creatAccount(account);
    }
    public Card createCard(Card card){
        return bankService.createCard(card);
    }
    public List<Client> findAllClients(){
        return bankService.findAllClients();
    }
    public Bill createBill(Bill bill){
        return bankService.createBill(bill);
    }
    public Card debitByClientId(Long id, Double payment){
        return bankService.debitByClientId(id, payment);
    }



}

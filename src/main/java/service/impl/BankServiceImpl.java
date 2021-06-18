package service.impl;

import entity.*;
import repository.interfaces.BankRepository;
import repository.interfaces.BillRepository;
import service.interfaces.AccountService;
import service.interfaces.BankService;
import service.interfaces.CardService;
import service.interfaces.ClientService;

import java.util.List;

public class BankServiceImpl implements BankService {
    private ClientService clientService;
    private AccountService accountService;
    private CardService cardService;
    private BillRepository billRepository;
    private BankRepository bankRepository;

    @Override
    public Client createClient(Client client) {
        bankRepository.save(client.getId());//todo так верно?
         return clientService.save(client);
    }

    @Override
    public Account creatAccount(Account account) {
        return accountService.save(account);
    }

    @Override
    public Card createCard(Card card) {
        return cardService.save(card);
    }

    @Override
    public List<Client> findAllClients() {
        return clientService.findAll();
    }

    @Override
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Card debitByClientId(Long id, Double payment) {
        return cardService.changeBalance(cardService.getCardByClientId(id).getId(), payment);
    }
}

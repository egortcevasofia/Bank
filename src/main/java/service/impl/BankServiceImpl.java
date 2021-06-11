package service.impl;

import entity.*;
import repository.interfaces.BillRepository;
import service.interfaces.AccountService;
import service.interfaces.BankService;
import service.interfaces.CardService;
import service.interfaces.ClientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BankServiceImpl implements BankService {
    private ClientService clientService;
    private AccountService accountService;
    private CardService cardService;
    private BillRepository billRepository;

    @Override
    public Client createClient(String firstName, String lastName, Integer age, LocalDate dateOfBirth) {
        Client client = new Client(firstName, lastName, age, dateOfBirth);
        clientService.save(client);
        return client; //but whithoutId;
    }

    @Override
    public Account creatAccount(String firstName, String lastName, AccountStatus accountStatus, Long clientId) {
        Account account = new Account(firstName, lastName, accountStatus, clientId);
        accountService.save(account);
        return account;
    }

    @Override
    public Card createCard(Double balance, TypeCard typeCard, Long clientId) {
        Card card = new Card(balance, typeCard, clientId);
        cardService.save(card);
        return card;
    }

    @Override
    public List<Client> findAllClients() {
        return clientService.findAll();
    }

    @Override
    public Bill createBill(Double payment, Long clientId) {
        Bill bill = new Bill(LocalDateTime.now(), payment,  clientId);
        billRepository.save(bill);
        return bill;
    }

    @Override
    public Card debitByClientId(Long id, Double payment) {
        return cardService.changeBalance(cardService.getCardIdBYClientId(id), payment);
    }
}

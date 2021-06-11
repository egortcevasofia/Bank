package service.impl;

import entity.Account;
import entity.Bill;
import entity.Card;
import entity.Client;
import repository.interfaces.BillRepository;
import repository.interfaces.CardRepository;
import repository.interfaces.ClientRepository;
import service.interfaces.AccountService;
import service.interfaces.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private AccountService accountService;
    private BillRepository billRepository;
    private CardRepository cardRepository;


    @Override
    public Client findById(Long id) {
        Client client = clientRepository.findById(id);
        Account account = accountService.findByClientId(client.getId());
        List<Bill> listOfBill = billRepository.findAllByClientId(id);
        Card card = cardRepository.findById(id);
        client.setAccount(account);
        client.setListOfBill(listOfBill);
        client.setCard(card);
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> listOfClient = clientRepository.findAll();
        listOfClient.forEach(client -> {
            client.setAccount(accountService.findByClientId(client.getId()));
            client.setListOfBill(billRepository.findAllByClientId(client.getId()));
            client.setCard(cardRepository.findById(client.getId()));
        });
        return listOfClient;
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void update(Long id, Client client) {
        clientRepository.updateById(id, client);
    }

    @Override
    public void delite(Long id) {
        accountService.delite(id);
    }

    @Override
    public Boolean pay(Long clientId, Long billId) {
        return null;
    }

    @Override
    public Boolean topUp(Long clientId, int sum) {
        return null;
    }

    @Override
    public List<Bill> getListOfBill(Long clientId) {
        return billRepository.findAllByClientId(clientId);
    }
}

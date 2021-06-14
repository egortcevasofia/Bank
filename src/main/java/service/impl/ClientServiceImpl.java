package service.impl;

import entity.*;
import repository.interfaces.ClientRepository;
import service.interfaces.AccountService;
import service.interfaces.BillService;
import service.interfaces.CardService;
import service.interfaces.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private AccountService accountService;
    private CardService cardService;
    private BillService billService;


    @Override
    public Client findById(Long id) {
        Client client = clientRepository.findById(id);
        Account account = accountService.findByClientId(client.getId());
        List<Bill> listOfBill = billService.findAllByClientId(id);
        Card card = cardService.findById(id);
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
            client.setListOfBill(billService.findAllByClientId(client.getId()));
            client.setCard(cardService.findById(client.getId()));
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
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public Boolean pay(Long clientId, Long billId) {
        Client client = findById(clientId);
        Bill bill = billService.findById(billId);
        if (client.getCard().getBalance() >= bill.getPayment()){
            cardService.changeBalance(cardService.getCardIdBYClientId(clientId), bill.getPayment());
            return true;
        }
        return true;
       // throw new Exeption //не хвататет денег
    }

    @Override
    public Boolean topUp(Long clientId, Double sum) {
        if(accountService.findByClientId(clientId).getAccountStatus().equals(AccountStatus.ACTIVE)){
            cardService.changeBalance(cardService.getCardIdBYClientId(clientId), sum);
            return true;
        }
        return true;// убрать и потом эксепшен
    }

    @Override
    public List<Bill> getListOfBill(Long clientId) {
        return billService.findAllByClientId(clientId);
    }
}

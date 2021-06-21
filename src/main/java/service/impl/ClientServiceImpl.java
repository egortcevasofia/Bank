package service.impl;

import entity.*;
import exception.*;
import repository.impl.ClientRepositoryImpl;
import repository.interfaces.ClientRepository;
import service.interfaces.AccountService;
import service.interfaces.BillService;
import service.interfaces.CardService;
import service.interfaces.ClientService;

import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final CardService cardService = new CardServiceImpl();
    private final BillService billService = new BillServiceImpl();
    private static final String CLIENT_NOT_FOUND_MESSAGE = "Client with id %d not found";
    private static final String CLIENT_ALREADY_EXISTS_MESSAGE = "Client with first name %s and last name %s already exists";
    private static final String INSUFFICIENT_FUNDS_MESSAGE = "Client with id %d have doesn't enough money";
    private static final String ACCOUNT_NOT_ACTIVE_MESSAGE = "Client with id %d doesn't have active account";

    @Override
    public Client findById(Long id) {
        Optional<Client> client= clientRepository.findById(id);
        if (client.isEmpty()){
            throw new ClientNotFoundException(String.format(CLIENT_NOT_FOUND_MESSAGE, id));
        }else{
            Account account = accountService.findByClientId(client.get().getId());
            List<Bill> listOfBill = billService.findAllByClientId(id);
            Card card = cardService.findById(id);
            client.get().setAccount(account);
            client.get().setListOfBill(listOfBill);
            client.get().setCard(card);
            return client.get();
        }
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
        Optional<Client> optional = clientRepository.findByName(client.getFirstName(), client.getLastName(), client.getDateOfBirth());
        if (optional.isEmpty()) {
            return clientRepository.save(client);
        } else {
            throw new ClientExistsException(String.format(CLIENT_ALREADY_EXISTS_MESSAGE, client.getFirstName(), client.getLastName()));
        }
    }

    @Override
    public void update(Long id, Client client) {
        Optional<Client> optional = clientRepository.findById(id);
        optional.ifPresentOrElse(
                (value) -> clientRepository.updateById(value.getId(), client),
                () -> { throw new ClientNotFoundException(String.format(CLIENT_NOT_FOUND_MESSAGE, id));
                }
        );
    }

    @Override
    public void delete(Long id) {
        Optional<Client> optional = clientRepository.findById(id);
        optional.ifPresentOrElse(
                (value) -> accountService.delete(value.getId()),
                () -> { throw new ClientNotFoundException(String.format(CLIENT_NOT_FOUND_MESSAGE, id));
                }
        );
    }

    @Override
    public Boolean pay(Long clientId, Long billId) {
        Client client = findById(clientId);
        Bill bill = billService.findById(billId);
        if (client.getCard().getBalance() >= bill.getPayment()){
            cardService.changeBalance(cardService.getCardByClientId(clientId).getId(), -bill.getPayment());
            return true;
        }
        throw  new InsufficientFundsException(String.format(INSUFFICIENT_FUNDS_MESSAGE, clientId));
    }

    @Override
    public Boolean topUp(Long clientId, Double sum) {
        if(accountService.findByClientId(clientId).getAccountStatus().equals(AccountStatus.ACTIVE)){
            cardService.changeBalance(cardService.getCardByClientId(clientId).getId(), sum);
            return true;
        }
        throw new AccountNotActiveException(String.format(ACCOUNT_NOT_ACTIVE_MESSAGE, clientId));
    }

    @Override
    public List<Bill> getListOfBill(Long clientId) {
        return billService.findAllByClientId(clientId);
    }
}

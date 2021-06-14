package service.interfaces;

import entity.Bill;
import entity.Client;

import java.util.List;

public interface ClientService {

    Client findById(Long id);
    List<Client> findAll();
    Client save(Client client);
    void update(Long id, Client client);
    void delete(Long id);
    Boolean pay(Long clientId, Long billId);
    Boolean topUp(Long clientId, Double sum);
    List<Bill> getListOfBill(Long clientId);

}

package repository.interfaces;

import entity.Account;
import entity.Bill;
import entity.Card;
import entity.Client;

import java.util.List;

public interface ClientRepository {
    Client save(Client client);
    Client findById(Long id);
    List<Client> findAll();
    void updateById(Long id, Client client);
    void createTable();
    void dropTable();

}

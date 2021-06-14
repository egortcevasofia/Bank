package repository.interfaces;

import entity.Client;

public interface BankRepository {
    void save(Long clientId);
    void createTable();
    void dropTable();

}

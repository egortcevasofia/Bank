package repository.interfaces;

import entity.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
    Optional<Client> findByName(String firstName, String lastName, LocalDate dateOfBirth);
    void updateById(Long id, Client client);
    void createTable();
    void dropTable();

}

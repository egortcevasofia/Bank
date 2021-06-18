package serviceTest;


import entity.Client;
import exception.AccountNotActiveException;
import exception.ClientExistsException;
import exception.ClientNotFoundException;
import exception.InsufficientFundsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.ClientRepositoryImpl;
import repository.interfaces.ClientRepository;
import service.impl.ClientServiceImpl;
import service.interfaces.ClientService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientServiceTest {
    private ClientService clientService = new ClientServiceImpl();
    private ClientRepository clientRepository = new ClientRepositoryImpl();

   // @BeforeEach
    public void setBeforeEach(){
        clientRepository.createTable();
        clientService.save(new Client("Ivan", "Ivanov", 25, LocalDate.of(1993, 5, 6)));
        clientService.save(new Client("Sidor", "Sidorov", 25, LocalDate.of(1993, 5, 6)));
        clientService.save(new Client("Petr", "Petrov", 25, LocalDate.of(1993, 5, 6)));
    }

    @DisplayName("Find. Assert exception when client not exist")
    @Test
    public void test_findClientById() {
        assertThrows(ClientNotFoundException.class, () -> clientService.findById(4L) );
    }

    @DisplayName("Save. Assert exception when client already exist")
    @Test
    public void test_saveClient() {
        assertThrows(ClientExistsException.class, () -> clientService.save(new Client("Petr", "Petrov", 25, LocalDate.of(1993, 5, 6))));
    }

    @DisplayName("Update. Assert exception when client not exist")
    @Test
    public void test_updateClientById() {
        assertThrows(ClientNotFoundException.class, () -> clientService.update(4L, new Client("Petr", "Petrov", 25, LocalDate.of(1988, 5, 6))));
    }

    @DisplayName("Delete. Assert exception when client not exist")
    @Test
    public void test_deleteClientById() {
        assertThrows(ClientNotFoundException.class, () -> clientService.delete(3L));
    }

    @DisplayName("Pay. Assert exception when client doesn't have enough money")
    @Test
    public void test_Pay() {
        assertThrows(InsufficientFundsException.class, () -> clientService.pay(3L, 3L));
    }

    @DisplayName("TopUp. Assert exception when client doesn't have card or active status")
    @Test
    public void test_TopUp() {
        assertThrows(AccountNotActiveException.class, () -> clientService.topUp(3L, 50.00));
    }


   // @AfterEach
    public void cleanAfterTest() {
        clientRepository.dropTable();
    }
}

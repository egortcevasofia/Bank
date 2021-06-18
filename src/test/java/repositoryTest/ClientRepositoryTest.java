package repositoryTest;

import entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.impl.ClientRepositoryImpl;
import repository.interfaces.ClientRepository;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRepositoryTest {
    private final ClientRepository clientRepository = new ClientRepositoryImpl();

    @BeforeEach
    public void setBeforeEach(){
        clientRepository.createTable();
        clientRepository.save(new Client("Ivan", "Ivanov", 25, LocalDate.of(1993, 5, 6)));
        clientRepository.save(new Client("Sidor", "Sidorov", 25, LocalDate.of(1993, 5, 6)));
        clientRepository.save(new Client("Petr", "Petrov", 25, LocalDate.of(1993, 5, 6)));
    }

    @DisplayName("Save client in the BD and check it")
    @Test
    public void test_saveClient() {
        Client client = new Client("Vasili", "Vasin", 20, LocalDate.of(1992, 6, 7));
        Client savedClient = clientRepository.save(client);
        assertEquals(savedClient.getId(), 4L);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLastName(), client.getLastName());
        assertEquals(savedClient.getAge(), client.getAge());
        assertEquals(savedClient.getDateOfBirth(), client.getDateOfBirth());
    }

    @DisplayName("Get client by id from the BD")
    @Test
    public void test_getClientById() {
        Client client = new Client("Petr", "Petrov", 20, LocalDate.of(1982, 6, 7));
        clientRepository.save(client);
        Client savedClient = clientRepository.findById(4L).get();
        assertEquals(savedClient.getId(), 4L);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLastName(), client.getLastName());
        assertEquals(savedClient.getAge(), client.getAge());
        assertEquals(savedClient.getDateOfBirth(), client.getDateOfBirth());
    }

    @DisplayName("Get list of all clients")
    @Test
    public void test_findAll() {
        List<Client> list = clientRepository.findAll();
        assertEquals(list.size(), 3);
    }
    @DisplayName("Get account from BD by id")
    @Test
    public void test_updateById() {
        Long id = 3L;
        Client client = new Client("Vasili", "Vasin", 20, LocalDate.of(1962, 6, 7));
        clientRepository.updateById(id, client);
        Client updatedClient = clientRepository.findById(id).get();
        assertEquals(updatedClient.getId(), 3L);
        assertEquals(updatedClient.getFirstName(), client.getFirstName());
        assertEquals(updatedClient.getLastName(), client.getLastName());
        assertEquals(updatedClient.getAge(), client.getAge());
        assertEquals(updatedClient.getDateOfBirth(), client.getDateOfBirth());
    }

    @DisplayName("Get client by name from the BD")
    @Test
    public void test_getClientByName() {
        Client client = new Client("Petr", "Petrov", 20, LocalDate.of(1992, 6, 7));
        clientRepository.save(client);
        Client savedClient = clientRepository.findByName("Petr", "Petrov", LocalDate.of(1992, 6, 7)).get();
        assertEquals(savedClient.getId(), 4L);
        assertEquals(savedClient.getFirstName(), client.getFirstName());
        assertEquals(savedClient.getLastName(), client.getLastName());
        assertEquals(savedClient.getAge(), client.getAge());
        assertEquals(savedClient.getDateOfBirth(), client.getDateOfBirth());
    }



    @AfterEach
    public void cleanAfterTest() {
        clientRepository.dropTable();
    }
}

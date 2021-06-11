import entity.*;
import repository.impl.AccountRepositoryImpl;
import repository.impl.CardRepositoryImpl;
import repository.impl.ClientRepositoryImpl;
import repository.interfaces.AccountRepository;
import repository.interfaces.ClientRepository;

import java.sql.Driver;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {
//        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
//        Account account = new Account("firstName", "lastName", AccountStatus.ACTIVE);
//        accountRepository.save(account);
//        accountRepository.findAll();
//        accountRepository.findById(2L);
//        accountRepository.updateById(4L,account);
//        accountRepository.deleteById(3L);

//        CardRepositoryImpl cardRepository = new CardRepositoryImpl();
//        Card card = new Card(100.56, TypeCard.STUDENT);
//        cardRepository.save(card);
//        cardRepository.save(card);
//        cardRepository.save(card);
//        cardRepository.findById(2L);
        ClientRepository clientRepository = new ClientRepositoryImpl();
        clientRepository.save(new Client("firstName", "lastName", 12, LocalDate.of(1999, 11,12)));
        clientRepository.save(new Client("firstName", "lastName", 12, LocalDate.of(1999, 11,12)));
        clientRepository.save(new Client("firstName", "lastName", 12, LocalDate.of(1999, 11,12)));
        clientRepository.save(new Client("firstName", "lastName", 12, LocalDate.of(1999, 11,12)));

      //  AccountRepository accountRepository = new AccountRepositoryImpl();

//        accountRepository.save(new Account("Ivan", "Ivanov", AccountStatus.ACTIVE, 1L));
//        accountRepository.save(new Account("Sidor", "Sidorov", AccountStatus.ACTIVE, 2L));
//        accountRepository.save(new Account("Petr", "Petrov", AccountStatus.ACTIVE, 3L));



    }
}

import entity.Account;
import entity.Status;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        Account account = new Account("firstName", "lastName", Status.ACTIVE);
        accountRepository.save(account);
        accountRepository.findAll();
        accountRepository.findById(2L);
        accountRepository.updateById(4L,account);
        accountRepository.deleteById(3L);

    }
}

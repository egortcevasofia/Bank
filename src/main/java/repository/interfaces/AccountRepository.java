package repository.interfaces;

import entity.Account;


import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);
    Optional<Account> findById(Long id);
    List<Account> findAll();
    void updateById(Long id, Account account);
    void deleteById(Long id);
    Optional<Account> findByClientId(Long id);
    void createTable();
    void dropTable();

}

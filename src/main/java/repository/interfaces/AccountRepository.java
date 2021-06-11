package repository.interfaces;

import entity.Account;


import java.util.List;

public interface AccountRepository {

    Account save(Account account);
    Account findById(Long id);
    List<Account> findAll();
    void updateById(Long id, Account account);
    void deleteById(Long id);
    Account findByClientId(Long id);
    void createTable();
    void dropTable();

}

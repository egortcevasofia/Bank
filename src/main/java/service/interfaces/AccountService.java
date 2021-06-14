package service.interfaces;

import entity.Account;

import java.util.List;

public interface AccountService {
    Account findById(Long id);
    List<Account> findAll();
    Account save(Account account);
    void update(Long id, Account account);
    void delete(Long id);
    Account findByClientId(Long id);
}

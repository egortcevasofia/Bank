package service.impl;

import entity.Account;
import repository.interfaces.AccountRepository;
import service.interfaces.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;


    @Override
    public Account findById(Long id) {
        return accountRepository.findByClientId(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void update(Long id, Account account) {
        accountRepository.updateById(id, account);

    }

    @Override
    public void delite(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findByClientId(Long id) {
        return accountRepository.findByClientId(id);
    }
}

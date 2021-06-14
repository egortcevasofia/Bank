package service.impl;

import entity.Account;
import exception.AccountAlreadyExists;
import exception.AccountNotFoundException;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;
import service.interfaces.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository = new AccountRepositoryImpl();//TODO добавила инициализацию
    private static final String ACCOUNT_ALREADY_EXISTS = "Account with clientid %d already exists";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with id %d not found";


    @Override
    public Account findById(Long id) {
        Account account = accountRepository.findById(id);
        if (account == null) {
            throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
        } else {
            return account;
        }
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        Long clientId = account.getClientId();
        if (accountRepository.findByClientId(clientId) == null) {
            return accountRepository.save(account);
        } else {
            throw new AccountAlreadyExists(String.format(ACCOUNT_ALREADY_EXISTS, clientId));
        }
    }

    @Override
    public void update(Long id, Account account) {
        if (accountRepository.findById(id) == null) {
            throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
        } else {
            accountRepository.updateById(id, account);
        }
    }

    @Override
    public void delete(Long id) {
        if (accountRepository.findById(id) == null) {
            throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
        } else {
            accountRepository.deleteById(id);
        }
    }

    @Override
    public Account findByClientId(Long id) {
        if (accountRepository.findByClientId(id) == null) {
            throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
        } else {
            return accountRepository.findByClientId(id);
        }
    }
}

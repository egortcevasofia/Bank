package service.impl;

import entity.Account;
import exception.AccountExistsException;
import exception.AccountNotFoundException;
import repository.impl.AccountRepositoryImpl;
import repository.interfaces.AccountRepository;
import service.interfaces.AccountService;

import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository = new AccountRepositoryImpl();//TODO добавила инициализацию
    private static final String ACCOUNT_ALREADY_EXISTS_MESSAGE = "Account with clientid %d already exists";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with id %d not found";


    @Override
    public Account findById(Long id) {
        Optional<Account> optional = accountRepository.findById(id);
        return optional.orElseThrow(() -> new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account save(Account account) {
        Long clientId = account.getClientId();
        Optional<Account> optional = accountRepository.findByClientId(clientId);
        if (optional.isEmpty()) {
            return accountRepository.save(account);
        } else {
            throw new AccountExistsException(String.format(ACCOUNT_ALREADY_EXISTS_MESSAGE, clientId));
        }
    }

    @Override
    public void update(Long id, Account account) {
        Optional<Account> optional = accountRepository.findById(id);
        optional.ifPresentOrElse(
                (value) -> accountRepository.updateById(value.getId(), account),
                () -> { throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
                }
        );
    }

    @Override
    public void delete(Long id) {
        Optional<Account> optional = accountRepository.findById(id);
        optional.ifPresentOrElse(
                (value) -> accountRepository.deleteById(value.getId()),
                () -> { throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
                }
        );
    }

    @Override
    public Account findByClientId(Long id) {
        Optional<Account> optional = accountRepository.findByClientId(id);
        return optional.orElseThrow(() -> new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id)));
    }
}

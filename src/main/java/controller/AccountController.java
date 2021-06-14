package controller;

import entity.Account;
import service.interfaces.AccountService;

import java.util.List;

//("/accounts")
public class AccountController {
    private final AccountService accountService;

    //GET("/{id}")
    public Account findById(Long id){
        return accountService.findById(id);
    }

    //GET
    public List<Account> findAll(){
        return accountService.findAll();
    }

    //POST
    public Account save(Account account){
        return accountService.save(account);
    }

    //PUT
    public void update(Long id, Account account){
        accountService.update(id, account);
    }

    //DELETE
    public void delete(Long id){
        accountService.delete(id);
    }



    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
}

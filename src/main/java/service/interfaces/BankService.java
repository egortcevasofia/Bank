package service.interfaces;

import entity.*;

import java.time.LocalDate;
import java.util.List;

public interface BankService {
    Client createClient(Client client);
    Account creatAccount(Account account);
    Card createCard(Card card);
    List<Client> findAllClients();
    Bill createBill(Bill bill);
    Card debitByClientId(Long id, Double payment);//снятие с  баланса клиента суммы (по id клиента)


}

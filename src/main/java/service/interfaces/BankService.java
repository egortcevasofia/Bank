package service.interfaces;

import entity.*;

import java.time.LocalDate;
import java.util.List;

public interface BankService {
    Client createClient(String firstName, String lastName, Integer age, LocalDate dateOfBirth);
    Account creatAccount(String firstName, String lastName, AccountStatus accountStatus, Long clientId);
    Card createCard(Double balance, TypeCard typeCard, Long clientId);
    List<Client> findAllClients();
    Bill createBill(Double payment, Long clientId);
    Card debitByClientId(Long id, Double payment);//снятие с  баланса клиента суммы (по id клиента)


}

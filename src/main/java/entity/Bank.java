package entity;

import java.util.List;

public class Bank {
    private Long BankId;
    private String name;
    private List<Client> listOfClient;

    public Bank(Long bankId, String name, List<Client> listOfClient) {
        BankId = bankId;
        this.name = name;
        this.listOfClient = listOfClient;
    }

    public Bank(String name, List<Client> listOfClient) {
        this.name = name;
        this.listOfClient = listOfClient;
    }

    public Bank() {
    }

    public Long getBankId() {
        return BankId;
    }

    public void setBankId(Long bankId) {
        BankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getListOfClient() {
        return listOfClient;
    }

    public void setListOfClient(List<Client> listOfClient) {
        this.listOfClient = listOfClient;
    }
}

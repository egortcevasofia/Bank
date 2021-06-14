package entity;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private Long bankId;
    private String name;
    private List<Client> listOfClient;

    public Bank(Long bankId, String name) {
        this.bankId = bankId;
        this.name = name;
    }

    public Bank(String name) {
        this.name = name;
    }

    public Bank() {
    }

    public void addClient(Client client){
        if (listOfClient == null){
            listOfClient = new ArrayList<>();
        }

        listOfClient.add(client);
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
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

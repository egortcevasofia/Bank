package controller;

import entity.Bill;
import entity.Client;
import service.interfaces.ClientService;

import java.util.List;

public class ClientController {
    private final ClientService clientService;


    public Client findById(Long id){
        return clientService.findById(id);
    }
    public List<Client> findAll(){
        return clientService.findAll();
    }
    public Client save(Client client){
        return clientService.save(client);
    }
    public void update(Long id, Client client){
        clientService.update(id, client);
    }
    public void delete(Long id){
        clientService.delete(id);
    }
    public Boolean pay(Long clientId, Long billId){
        return clientService.pay(clientId, billId);
    }
    public Boolean topUp(Long clientId, Double sum){
       return clientService.topUp(clientId, sum);
    }
    public List<Bill> getListOfBill(Long clientId){
        return clientService.getListOfBill(clientId);
    }

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
}

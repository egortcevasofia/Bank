package entity;

public class Card {
    private Long id;
    private Double balance;
    private TypeCard typeCard;
    private Long clientId;

    public Card(Long id, Double balance, TypeCard typeCard, Long clientId) {
        this.id = id;
        this.balance = balance;
        this.typeCard = typeCard;
        this.clientId = clientId;
    }

    public Card(Double balance, TypeCard typeCard, Long clientId) {
        this.balance = balance;
        this.typeCard = typeCard;
        this.clientId = clientId;
    }

    public Card() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Double getBalance() { return balance; }

    public void setBalance(Double balance) { this.balance = balance; }

    public TypeCard getTypeCard() { return typeCard; }

    public void setTypeCard(TypeCard typeCard) { this.typeCard = typeCard; }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}

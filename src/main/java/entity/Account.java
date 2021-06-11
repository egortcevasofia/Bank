package entity;

public class Account {
    private Long id;
    private String firstName;
    private String lastName;
    private AccountStatus accountStatus;
    private Long clientId;

    public Account(Long id, String firstName, String lastName, AccountStatus accountStatus, Long clientId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountStatus = accountStatus;
        this.clientId = clientId;
    }

    public Account(String firstName, String lastName, AccountStatus accountStatus, Long clientId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountStatus = accountStatus;
        this.clientId = clientId;
    }

    public Account() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public AccountStatus getAccountStatus() { return accountStatus; }

    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public Long getClientId() { return clientId; }

    public void setClientId(Long clientId) { this.clientId = clientId; }
}

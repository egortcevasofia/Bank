package entity;

public class Account {
    private Long id;
    private String login;// поправить
    private String password;
    private AccountStatus accountStatus;
    private Long clientId;

    public Account(Long id, String login, String password, AccountStatus accountStatus, Long clientId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.accountStatus = accountStatus;
        this.clientId = clientId;
    }

    public Account(String login, String password, AccountStatus accountStatus, Long clientId) {
        this.login = login;
        this.password = password;
        this.accountStatus = accountStatus;
        this.clientId = clientId;
    }

    public Account() {
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public AccountStatus getAccountStatus() { return accountStatus; }

    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }

    public Long getClientId() { return clientId; }

    public void setClientId(Long clientId) { this.clientId = clientId; }
}

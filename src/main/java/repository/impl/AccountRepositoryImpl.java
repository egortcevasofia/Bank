package repository.impl;

import entity.AccountStatus;
import exception.AccountNotFoundException;
import repository.interfaces.AccountRepository;

import entity.Account;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepositoryImpl implements AccountRepository {
    //mavenCentral -> postgres driver -> pom.xml -> <dependencies>...</dependencies>
    private static final String INSERT_QUERY = "INSERT INTO ACCOUNT(first_name, last_name, account_status, client_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT a.id, a.first_name, a.last_name, a.account_status, a.client_id FROM ACCOUNT a WHERE a.id = ?";
    private static final String SELECTALL_QUERY = "SELECT * FROM ACCOUNT";
    private static final String UPDATE_QUERY = "UPDATE ACCOUNT SET first_name = ?, last_name = ?, account_status = ?, client_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "UPDATE ACCOUNT SET account_status = 'DELETED' WHERE id = ?";
    private static final String SELECT_BY_CLIENT_ID_QUERY = "SELECT a.id, a.first_name, a.last_name, a.account_status, a.client_id FROM ACCOUNT a WHERE a.client_id = ?";
    private static final String TRUNCATE_TABLE_QUERY = "TRUNCATE account";
    private static final String CREATE_TABLE_QUERY = "create table account(\n" +
            "id bigserial not null primary key,\n" +
            "first_name varchar(100) not null,\n" +
            "last_name varchar(100) not null,\n" +
            "account_status varchar(100) not null,\n" +
            "client_id bigint,\n" +
            "FOREIGN KEY (client_id) REFERENCES client(id)\n" +
            ")";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS account";

    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with id %d not found";

    private static final String URL = "jdbc:postgresql://localhost:5432/bank";
    private static final String DRIVER = "org.postgresql.Driver";
    private Connection connection;
    private PreparedStatement preparedStatement;


    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection(URL, "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Account save(Account account) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getAccountStatus().name());
            preparedStatement.setLong(4, account.getClientId());
            preparedStatement.execute();

            //KeyHolder Bigserial
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return account;
    }

    @Override
    public Account findById(Long id) {
        Account account = new Account();
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long AccountId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                AccountStatus status = toStatus(resultSet.getString(4));
                Long clientId = resultSet.getLong(5);
                account = new Account(AccountId, firstName, lastName, status, clientId);
            } else {
                throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        getConnection();
        Account account = null;
        List<Account> list = new ArrayList<Account>();
        try {
            preparedStatement = connection.prepareStatement(SELECTALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                AccountStatus status = toStatus(resultSet.getString(4));
                Long clientId = resultSet.getLong(5);
                account = new Account(id, firstName, lastName, status, clientId);
                list.add(account);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return list;
    }

    @Override
    public void updateById(Long id, Account account) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, account.getFirstName());
            preparedStatement.setString(2, account.getLastName());
            preparedStatement.setString(3, account.getAccountStatus().name());
            preparedStatement.setLong(4, account.getClientId());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();// изменить на мессадж
        }finally {
            JdbcUtils.close(connection);
        }
    }


    @Override
    public void deleteById(Long id) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findByClientId(Long id) {
        getConnection();
        Account account = null;
        try {
            preparedStatement = connection.prepareStatement(SELECT_BY_CLIENT_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long AccountId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                AccountStatus status = toStatus(resultSet.getString(4));
                Long clientId = resultSet.getLong(5);
                account = new Account(AccountId, firstName, lastName, status, clientId);
            } else {
                throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return account;
    }


    @Override
    public void createTable(){
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dropTable(){
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(DROP_TABLE_QUERY);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AccountStatus toStatus(String status) {
        if (status.equals("ACTIVE")) {
            return AccountStatus.ACTIVE;
        } else if (status.equals("BLOCKED")) {
            return AccountStatus.BLOCKED;
        } else {
            return AccountStatus.DELETED;
        }
    }
}

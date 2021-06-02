package repository.impl;

import entity.Status;
import exception.AccountNotFoundException;
import repository.interfaces.AccountRepository;

import entity.Account;
import utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepositoryImpl implements AccountRepository {
    //mavenCentral -> postgres driver -> pom.xml -> <dependencies>...</dependencies>
    private static final String INSERT_QUERY = "INSERT INTO ACCOUNT(firstName, lastName, status) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT a.id, a.firstName, a.lastName, a.status FROM ACCOUNT a WHERE a.id = ?";
    private static final String SELECTALL_QUERY = "SELECT * FROM ACCOUNT";
    private static final String UPDATE_QUERY = "UPDATE ACCOUNT SET firstname = ?, lastname = ?, status = ? WHERE id = ?";
    private static final String DELETE_QUERY = "UPDATE ACCOUNT SET status = 'DELETED' WHERE id = ?";

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
            preparedStatement.setString(3, account.getStatus().name());
            preparedStatement.execute();

            //KeyHolder
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
                Long accountId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Status status = toStatus(resultSet.getString(4));
                account = new Account(accountId, firstName, lastName, status);
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
                Long accountId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Status status = toStatus(resultSet.getString(4));
                account = new Account(accountId, firstName, lastName, status);
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
            preparedStatement.setString(3, account.getStatus().name());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Status toStatus(String status) {
        if (status.equals("ACTIVE")) {
            return Status.ACTIVE;
        } else if (status.equals("BLOCKED")) {
            return Status.BLOCKED;
        } else {
            return Status.DELETED;
        }
    }
}

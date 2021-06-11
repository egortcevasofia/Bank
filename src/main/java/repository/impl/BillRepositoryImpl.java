package repository.impl;

import entity.Account;
import entity.AccountStatus;
import entity.Bill;
import exception.AccountNotFoundException;
import repository.interfaces.BillRepository;
import utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillRepositoryImpl  implements BillRepository {
    private static final String INSERT_QUERY = "INSERT INTO BILL(date_of_creation, payment, client_id) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT b.id, b.date_of_creation, b.payment, b.client_id FROM BILL b WHERE b.id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM BILL where client_id = ?";
    private static final String CREATE_TABLE_QUERY = "create table bill(\n" +
            "id bigserial not null primary key,\n" +
            "date_of_creation timestamp not null,\n" +
            "payment numeric(19, 2) not null,\n" +
            "client_id bigint,\n" +
            "FOREIGN KEY (client_id) REFERENCES client(id)\n" +
            ")";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS bill";
    private static final String ACCOUNT_NOT_FOUND_MESSAGE = "Bill with id %d not found";


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
    public Bill save(Bill bill) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(bill.getLocalDateTime()));
            preparedStatement.setDouble(2, bill.getPayment());
            preparedStatement.setLong(3, bill.getClientId());
            preparedStatement.execute();

            //KeyHolder Bigserial
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return bill;
    }

    @Override
    public Bill findById(Long id) {
        Bill bill = new Bill();
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long billId = resultSet.getLong(1);
                LocalDateTime dateOfCreation = resultSet.getTimestamp(2).toLocalDateTime();
                Double payment = resultSet.getDouble(3);
                Long clientId = resultSet.getLong(4);
                bill = new Bill(billId, dateOfCreation, payment, clientId);
            } else {
                throw new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return bill;
    }


    @Override
    public List<Bill> findAllByClientId(Long id) {
        getConnection();
        Bill bill = null;
        List<Bill> list = new ArrayList<Bill>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long billId = resultSet.getLong(1);
                LocalDateTime dateOfCreation = resultSet.getTimestamp(2).toLocalDateTime();
                Double payment = resultSet.getDouble(3);
                Long clientId = resultSet.getLong(4);
                bill = new Bill(billId, dateOfCreation, payment, clientId);
                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return list;
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
}

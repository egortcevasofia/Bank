package repository.impl;

import repository.interfaces.BankRepository;
import utils.JdbcUtils;

import java.sql.*;

public class BankRepositoryImpl implements BankRepository {
    private static final String INSERT_QUERY = "INSERT INTO bank_client(bank_id, client_id) VALUES (?, ?)";
    private static final String CREATE_TABLE_QUERY = "create table bank_client(\n" +
            "bank_id bigint not null REFERENCES bank(id),\n" +
            "client_id bigint primary key REFERENCES client(id),\n" +
            ")";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS bank_client";

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
    public void save(Long clientId) {
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, 1L);
            preparedStatement.setLong(2, clientId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
    }

    @Override
    public void createTable() {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(CREATE_TABLE_QUERY);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropTable() {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(DROP_TABLE_QUERY);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

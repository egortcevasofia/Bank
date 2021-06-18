package repository.impl;

import entity.*;
import exception.AccountNotFoundException;
import repository.interfaces.ClientRepository;
import utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {
    private static final String INSERT_QUERY = "INSERT INTO CLIENT(first_name, last_name, age, date_of_birth) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT c.id, c.first_name, c.last_name, c.age, c.date_of_birth FROM CLIENT c WHERE c.id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM CLIENT";
    private static final String UPDATE_QUERY = "UPDATE CLIENT SET first_name = ?, last_name = ?, age = ?, date_of_birth = ? WHERE id = ?";
    private static final String SELECT_ALL_BY_NAME_QUERY = "SELECT c.id, c.first_name, c.last_name, c.age, c.date_of_birth FROM CLIENT c WHERE c.first_name = ? and c.last_name = ? and c.date_of_birth = ?";
    private static final String CREATE_TABLE_QUERY = "create table client(\n" +
            "id bigserial not null primary key,\n" +
            "first_name varchar(100) not null,\n" +
            "last_name varchar(100) not null,\n" +
            "age integer not null,\n" +
            "date_of_birth date not null\n" +
            ")";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS client";


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
    public Client save(Client client) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setDate(4, Date.valueOf(client.getDateOfBirth()));
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return client;
    }

    @Override
    public Optional<Client> findById(Long id) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long clientId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Integer age = resultSet.getInt(4);
                LocalDate dateOfBirth = resultSet.getDate(5).toLocalDate();
                return Optional.of(new Client(clientId, firstName, lastName, age, dateOfBirth));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        getConnection();
        List<Client> list = new ArrayList<Client>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long clientId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Integer age = resultSet.getInt(4);
                LocalDate dateOfBirth = resultSet.getDate(5).toLocalDate();

                Client client = new Client(clientId, firstName, lastName, age, dateOfBirth);
                list.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return list;
    }

    @Override
    public void updateById(Long id, Client client) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setInt(3, client.getAge());
            preparedStatement.setDate(4, Date.valueOf(client.getDateOfBirth()));
            preparedStatement.setLong(5, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
    }

    @Override
    public Optional<Client> findByName(String firstName, String lastName, LocalDate dateOfBirth) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_ALL_BY_NAME_QUERY);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, Date.valueOf(dateOfBirth));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long clientId = resultSet.getLong(1);
                Integer age = resultSet.getInt(4);
                return Optional.of(new Client(clientId, firstName, lastName, age, dateOfBirth));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection);
        }
        return Optional.empty();
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

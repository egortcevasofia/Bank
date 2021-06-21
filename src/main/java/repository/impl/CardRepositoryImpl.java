package repository.impl;

import entity.AccountStatus;
import entity.Card;
import entity.TypeCard;
import repository.interfaces.CardRepository;
import utils.JdbcUtils;


import java.sql.*;
import java.util.Optional;


public class CardRepositoryImpl implements CardRepository {
    private static final String INSERT_QUERY = "INSERT INTO CARD(balance, type_card, client_id) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT c.id, c.balance, c.type_card, c.client_id FROM CARD c WHERE c.id = ?";
    private static final String UPDATE_QUERY = "UPDATE CARD SET balance = ? WHERE id = ?";
    private static final String CREATE_TABLE_QUERY = "create table card(\n" +
            "id bigserial not null primary key,\n" +
            "balance numeric(19, 2) not null,\n" +
            "type_card varchar(100) not null,\n" +
            "client_id bigint,\n" +
            "FOREIGN KEY (client_id) REFERENCES client(id)\n" +
            ")";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS card";
    private static final String SELECT_CARD_BY_CLIENT_ID_QUERY = "SELECT c.id, c.balance, c.type_card, c.client_id FROM CARD c WHERE c.client_id = ?";


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
    public Card save(Card card) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, card.getBalance());
            preparedStatement.setString(2, card.getTypeCard().name());
            preparedStatement.setLong(3, card.getClientId());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                card.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return card;
    }

    @Override
    public Optional<Card> findById(Long id) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Long cardId = resultSet.getLong(1);
                Double balance = resultSet.getDouble(2);
                TypeCard typeCard = toTypeCard(resultSet.getString(3));
                Long clientId = resultSet.getLong(4);
                return Optional.of(new Card(cardId, balance, typeCard, clientId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Card> changeBalance(Long cardId, Double amount) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setLong(1, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                Double balance = resultSet.getDouble(2);
                TypeCard typeCard = toTypeCard(resultSet.getString(3));
                Long clientId = resultSet.getLong(4);
                double changedBalance = balance + (amount);

                preparedStatement = connection.prepareStatement(UPDATE_QUERY);
                preparedStatement.setDouble(1, changedBalance);
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
                return Optional.of(new Card(id, changedBalance, typeCard, clientId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(connection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Card> getCardByClientId(Long id) {
        getConnection();
        try {
            preparedStatement = connection.prepareStatement(SELECT_CARD_BY_CLIENT_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                Long cardId = resultSet.getLong(1);
                Double balance = resultSet.getDouble(2);
                TypeCard typeCard = toTypeCard(resultSet.getString(3));
                Long clientId = resultSet.getLong(4);
                return Optional.of(new Card(cardId, balance, typeCard, clientId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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

    private TypeCard toTypeCard(String status) {
        if (status.equals("STUDENT")) {
            return TypeCard.STUDENT;
        } else if (status.equals("SALARY")) {
            return TypeCard.SALARY;
        } else {
            return TypeCard.PREMIUM;
        }
    }
}

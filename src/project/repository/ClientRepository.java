package project.repository;


import project.classes.Client;
import project.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private RepositoryHelper repositoryHelper;

    public ClientRepository(){
        repositoryHelper = RepositoryHelper.getRepositoryHelper();
    }

    // CallableStatement
    public void insertClient(Client client) {
        String insertClientSql = "INSERT INTO clients(name, phoneNumber, email, age) VALUES(?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertClientSql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getPhoneNumber());
            preparedStatement.setString(3, client.getEmail());
            preparedStatement.setInt(4, client.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement - use when we have parameters
    public Client getClientById(int id) {
        String selectSql = "SELECT * FROM clients WHERE clientId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToClient(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Client> getClients(){
        String selectSql = "SELECT * FROM clients";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToClients(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // PreparedStatement
    public void updateClientName(String name, int id) {
        String updateNameSql = "UPDATE clients SET name=? WHERE clientId=?";
        repositoryHelper.updateString(name, id, updateNameSql);
    }

    public void updateClientPhoneNumber(String phoneNumber, int id) {
        String updateNameSql = "UPDATE clients SET phoneNumber=? WHERE clientId=?";
        repositoryHelper.updateString(phoneNumber, id, updateNameSql);
    }

    public void updateClientEmail(String email, int id) {
        String updateNameSql = "UPDATE clients SET email=? WHERE clientId=?";
        repositoryHelper.updateString(email, id, updateNameSql);
    }

    public void updateClientAge(int age, int id) {
        String updateNameSql = "UPDATE clients SET age=? WHERE clientId=?";
        repositoryHelper.updateInt(age, id, updateNameSql);
    }

    private Client mapToClient(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
        }
        return null;
    }

    private List<Client> mapToClients(ResultSet resultSet) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (resultSet.next()){
            clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
        }
        return clients;
    }

    public void deleteClient(int id){
        String deleteClientSql = "DELETE FROM clients WHERE clientId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteClientSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllClients(){
        String deleteClientSql = "DELETE FROM clients";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deleteClientSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

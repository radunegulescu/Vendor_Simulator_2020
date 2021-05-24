package project.repository;

import project.classes.Producer;
import project.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducerRepository {

    private RepositoryHelper repositoryHelper;

    public ProducerRepository(){
        repositoryHelper = RepositoryHelper.getRepositoryHelper();
    }

    // CallableStatement
    public void insertProducer(Producer producer) {
        String insertProducerSql = "INSERT INTO producers(name, contactPersonId, address) VALUES(?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertProducerSql);
            preparedStatement.setString(1, producer.getName());
            preparedStatement.setInt(2, producer.getContactPerson().getPersonId());
            preparedStatement.setString(3, producer.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement - use when we have parameters
    public Producer getProducerById(int id) {
        String selectSql = "SELECT * FROM producers WHERE producerId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToProducer(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Producer> getProducers(){
        String selectSql = "SELECT * FROM producers";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToProducers(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // PreparedStatement
    public void updateProducerName(String name, int id) {
        String updateNameSql = "UPDATE producers SET name=? WHERE producerId=?";
        repositoryHelper.updateString(name, id, updateNameSql);
    }

    public void updateProducerContactPerson(int contactPersonId, int id) {
        String updateNameSql = "UPDATE producers SET contactPersonId=? WHERE producerId=?";
        repositoryHelper.updateInt(contactPersonId, id, updateNameSql);
    }

    public void updateProducerAddress(String address, int id) {
        String updateNameSql = "UPDATE producers SET address=? WHERE producerId=?";
        repositoryHelper.updateString(address, id, updateNameSql);
    }

    private Producer mapToProducer(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            PersonRepository personRepository = new PersonRepository();
            return new Producer(resultSet.getInt(1), resultSet.getString(2), personRepository.getPersonById(resultSet.getInt(3)), resultSet.getString(4));
        }
        return null;
    }

    private List<Producer> mapToProducers(ResultSet resultSet) throws SQLException {
        List<Producer> producers = new ArrayList<>();
        while (resultSet.next()){
            PersonRepository personRepository = new PersonRepository();
            producers.add(new Producer(resultSet.getInt(1), resultSet.getString(2), personRepository.getPersonById(resultSet.getInt(3)), resultSet.getString(4)));
        }
        return producers;
    }

    public void deleteProducer(int id){
        String deleteProducerSql = "DELETE FROM producers WHERE producerId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteProducerSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllProducers(){
        String deleteProducerSql = "DELETE FROM producers";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deleteProducerSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
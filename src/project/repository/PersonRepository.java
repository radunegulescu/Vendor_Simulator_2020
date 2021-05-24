package project.repository;

import project.classes.Person;
import project.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private RepositoryHelper repositoryHelper;

    public PersonRepository(){
        repositoryHelper = RepositoryHelper.getRepositoryHelper();
    }

    // CallableStatement
    public void insertPerson(Person person) {
        String insertPersonSql = "INSERT INTO people(name, phoneNumber, email) VALUES(?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertPersonSql);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getPhoneNumber());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement - use when we have parameters
    public Person getPersonById(int id) {
        String selectSql = "SELECT * FROM people WHERE PersonId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToPerson(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Person> getPeople(){
        String selectSql = "SELECT * FROM people";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToPeople(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // PreparedStatement
    public void updatePersonName(String name, int id) {
        String updateNameSql = "UPDATE people SET name=? WHERE personId=?";
        repositoryHelper.updateString(name, id, updateNameSql);
    }

    public void updatePersonPhoneNumber(String phoneNumber, int id) {
        String updateNameSql = "UPDATE people SET phoneNumber=? WHERE personId=?";
        repositoryHelper.updateString(phoneNumber, id, updateNameSql);
    }

    public void updatePersonEmail(String email, int id) {
        String updateNameSql = "UPDATE people SET email=? WHERE personId=?";
        repositoryHelper.updateString(email, id, updateNameSql);
    }

    private Person mapToPerson(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
        }
        return null;
    }

    private List<Person> mapToPeople(ResultSet resultSet) throws SQLException {
        List<Person> people = new ArrayList<>();
        while (resultSet.next()){
            people.add(new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
        }
        return people;
    }

    public void deletePerson(int id){
        String deletePersonSql = "DELETE FROM people WHERE personId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deletePersonSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllPeople(){
        String deletePersonSql = "DELETE FROM people";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deletePersonSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
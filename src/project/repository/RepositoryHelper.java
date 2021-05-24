package project.repository;

import project.config.DatabaseConfiguration;

import java.sql.*;
import java.time.LocalDateTime;

public class RepositoryHelper {

    private static RepositoryHelper repositoryHelper = new RepositoryHelper();

    private RepositoryHelper() {
    }

    public static RepositoryHelper getRepositoryHelper() {
        return repositoryHelper;
    }

    public void executeSql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        // execute() for updating (INSERT, UPDATE, DELETE) and SELECT instructions
        stmt.execute(sql);
        //ResultSet resultSet = stmt.getResultSet();
    }

    public void executeUpdateSql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        // executeUpdate() for updating the data (INSERT, UPDATE, DELETE) or the database structure
        int i = stmt.executeUpdate(sql); // no of altered lines
    }

    public ResultSet executeQuerySql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        // executeQuery() for SELECT instructions
        return stmt.executeQuery(sql);
    }

    public void updateString(String str, int id, String updateStmt) {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateStmt);
            preparedStatement.setString(1, str);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInt(Integer integer, int id, String updateStmt) {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateStmt);
            preparedStatement.setInt(1, integer);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDouble(Double doubl, int id, String updateStmt) {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateStmt);
            preparedStatement.setDouble(1, doubl);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDate(LocalDateTime dateTime, int id, String updateStmt) {
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateStmt);
            java.sql.Date newDate = java.sql.Date.valueOf(dateTime.toLocalDate());
            preparedStatement.setDate(1, newDate);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
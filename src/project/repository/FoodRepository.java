package project.repository;

import project.classes.Food;
import project.classes.Person;
import project.config.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodRepository {

    private RepositoryHelper repositoryHelper;

    public FoodRepository(){
        repositoryHelper = RepositoryHelper.getRepositoryHelper();
    }

    // CallableStatement
    public void insertFood(Food food) {
        String insertFoodSql = "INSERT INTO foods(name, pricePerUnit, producerId, weight, units, calories, ingredients, commercialExcess) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertFoodSql);
            preparedStatement.setString(1, food.getName());
            preparedStatement.setDouble(2, food.getPricePerUnit());
            preparedStatement.setInt(3, food.getProducer().getProducerId());
            preparedStatement.setDouble(4, food.getWeight());
            preparedStatement.setDouble(5, food.getUnits());
            preparedStatement.setInt(6, food.getCalories());
            String ingredients = "";
            for(String s : food.getIngredients()){
                ingredients = ingredients + s + " ";
            }
            preparedStatement.setString(7, ingredients);
            preparedStatement.setDouble(8, food.getCommercialExcess());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement - use when we have parameters
    public Food getFoodById(int id) {
        String selectSql = "SELECT * FROM foods WHERE foodId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToFood(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Food> getFoods(){
        String selectSql = "SELECT * FROM foods";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToFoods(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // PreparedStatement
    public void updateFoodName(String name, int id) {
        String updateNameSql = "UPDATE foods SET name=? WHERE foodId=?";
        repositoryHelper.updateString(name, id, updateNameSql);
    }

    public void updateFoodProducerId(int producerId, int id) {
        String updateProducerIdSql = "UPDATE foods SET producerId=? WHERE foodId=?";
        repositoryHelper.updateInt(producerId, id, updateProducerIdSql);
    }

    public void updateFoodPricePerUnit(Double pricePerUnit, int id) {
        String updateSql = "UPDATE foods SET pricePerUnit=? WHERE foodId=?";
        repositoryHelper.updateDouble(pricePerUnit, id, updateSql);
    }

    public void updateFoodWeight(Double weight, int id) {
        String updateSql = "UPDATE foods SET weight=? WHERE foodId=?";
        repositoryHelper.updateDouble(weight, id, updateSql);
    }

    public void updateFoodUnits(Double units, int id) {
        String updateSql = "UPDATE foods SET units=? WHERE foodId=?";
        repositoryHelper.updateDouble(units, id, updateSql);
    }

    public void updateFoodCalories(int calories, int id) {
        String updateSql = "UPDATE foods SET calories=? WHERE foodId=?";
        repositoryHelper.updateInt(calories, id, updateSql);
    }

    public void updateFoodCommercialExcess(Double commercialExcess, int id) {
        String updateSql = "UPDATE foods SET commercialExcess=? WHERE foodId=?";
        repositoryHelper.updateDouble(commercialExcess, id, updateSql);
    }

    public void updateFoodIngredients(ArrayList<String> ingredients, int id) {
        String ingredients2 = "";
        for(String s : ingredients){
            ingredients2 = ingredients2 + s + " ";
        }
        String updateNameSql = "UPDATE foods SET name=? WHERE foodId=?";
        repositoryHelper.updateString(ingredients2, id, updateNameSql);
    }

    private Food mapToFood(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            ProducerRepository producerRepository = new ProducerRepository();
            List<String> ingredients = new ArrayList<>(Arrays.asList(resultSet.getString(8).split(" ")));
            return new Food(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), producerRepository.getProducerById(resultSet.getInt(4)), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getInt(7), ingredients, resultSet.getDouble(9));
        }
        return null;
    }

    private List<Food> mapToFoods(ResultSet resultSet) throws SQLException {
        List<Food> foods = new ArrayList<>();
        while (resultSet.next()){
            ProducerRepository producerRepository = new ProducerRepository();
            List<String> ingredients = new ArrayList<>(Arrays.asList(resultSet.getString(8).split(" ")));
            foods.add(new Food(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), producerRepository.getProducerById(resultSet.getInt(4)), resultSet.getDouble(5), resultSet.getDouble(6), resultSet.getInt(7), ingredients, resultSet.getDouble(9)));
        }
        return foods;
    }

    public void deleteFood(int id){
        String deleteFoodSql = "DELETE FROM foods WHERE foodId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteFoodSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllFoods(){
        String deleteFoodSql = "DELETE FROM foods";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deleteFoodSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

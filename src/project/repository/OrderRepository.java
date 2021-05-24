package project.repository;

import project.classes.Order;
import project.classes.Person;
import project.classes.Product;
import project.config.DatabaseConfiguration;
import project.service.ServiceDB;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private RepositoryHelper repositoryHelper;

    public OrderRepository(){
        repositoryHelper = RepositoryHelper.getRepositoryHelper();
    }

    // CallableStatement
    public void insertOrder(Order order) {
        String insertOrderSql = "INSERT INTO orders(date, clientId) VALUES(?, ?)";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            FoodRepository foodRepository = new FoodRepository();
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertOrderSql);
            java.sql.Date newDate = java.sql.Date.valueOf(order.getDate().toLocalDate());
            preparedStatement.setDate(1, newDate);
            preparedStatement.setInt(2, order.getClient().getPersonId());
            preparedStatement.executeUpdate();
            for(Product p : order.getProducts()){
                double minusUnits = p.getUnits();
                double oldUnits = foodRepository.getFoodById(p.getProductId()).getUnits();
                foodRepository.updateFoodUnits(oldUnits - minusUnits, p.getProductId());
                String insertOrderProductsSql = "INSERT INTO order_products(foodId, orderId, units) VALUES(?, ?, ?)";
                PreparedStatement preparedStatement2 = databaseConnection.prepareStatement(insertOrderProductsSql);
                preparedStatement2.setInt(1, p.getProductId());
                ServiceDB serviceDB = new ServiceDB();
                preparedStatement2.setInt(2, serviceDB.get_max_id("orders"));
                preparedStatement2.setDouble(3, minusUnits);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // PreparedStatement - use when we have parameters
    public Order getOrderById(int id) {
        String selectSql = "SELECT * FROM orders WHERE orderId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToOrder(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//
    public List<Order> getOrders(){
        String selectSql = "SELECT * FROM orders";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToOrders(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // PreparedStatement
    public void updateOrderClient(int clientId, int id) {
        String updateClientIdSql = "UPDATE orders SET clientId=? WHERE orderId=?";
        repositoryHelper.updateInt(clientId, id, updateClientIdSql);
    }

    public void updateOrderDate(LocalDateTime dateTime, int id) {
        String updateDate = "UPDATE orders SET date=? WHERE orderId=?";
        repositoryHelper.updateDate(dateTime, id, updateDate);
    }

    private Order mapToOrder(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            ClientRepository clientRepository = new ClientRepository();
            List<Product> products = orderProducts(resultSet.getInt(1));

            LocalDate localDate2 = resultSet.getDate(2).toLocalDate();
            LocalDateTime localDateTime = localDate2.atStartOfDay();
            return new Order(resultSet.getInt(1), clientRepository.getClientById(resultSet.getInt(3)), products, localDateTime);
        }
        return null;
    }

    private List<Product> orderProducts(int orderId) throws SQLException{
        String selectSql = "SELECT foodId, units FROM order_products WHERE orderId=" + orderId;
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToOrderProducts(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Product> mapToOrderProducts(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()){
            FoodRepository foodRepository = new FoodRepository();
            Product p = foodRepository.getFoodById(resultSet.getInt(1));
            p.setUnits(resultSet.getDouble(2));
            products.add(p);
        }
        return products;
    }

    private List<Order> mapToOrders(ResultSet resultSet) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()){
            orders.add(getOrderById(resultSet.getInt(1)));
        }
        return orders;
    }

    public void deleteOrder(int id){
        String deleteOrderSql = "DELETE FROM orders WHERE orderId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteOrderSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double cancelOrder(int id){
        String deleteOrderSql = "DELETE FROM orders WHERE orderId=?";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        double addedMoney = 0;
        try {
            List<Product> products = orderProducts(id);
            FoodRepository foodRepository = new FoodRepository();
            if(products != null){
                for(Product p : products){
                    foodRepository.updateFoodUnits(foodRepository.getFoodById(p.getProductId()).getUnits() + p.getUnits(), p.getProductId());
                    addedMoney += foodRepository.getFoodById(p.getProductId()).getUnits() * p.getPricePerUnit();
                }
            }
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteOrderSql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedMoney;
    }

    public void deleteAllOrders(){
        String deleteOrderSql = "DELETE FROM orders";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deleteOrderSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAllOrderProducts(){
        String deleteOrderSql = "DELETE FROM order_products";
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            stmt.executeUpdate(deleteOrderSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

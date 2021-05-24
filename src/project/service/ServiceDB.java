package project.service;

import project.classes.*;
import project.config.DatabaseConfiguration;
import project.repository.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServiceDB {
    private double money;   // banii magazinului
    private AuditService auditService;
    private PersonRepository personRepository;
    private ProducerRepository producerRepository;
    private FoodRepository foodRepository;
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    public ServiceDB() throws IOException {
        money = 0;
        personRepository = new PersonRepository();
        producerRepository = new ProducerRepository();
        foodRepository = new FoodRepository();
        clientRepository = new ClientRepository();
        orderRepository = new OrderRepository();
        auditService = AuditService.getInstance();
    }
    public void editPerson(int id){
        boolean OK = true;
        while(OK) {
            System.out.println("EDIT");
            System.out.println("1. NAME");
            System.out.println("2. PHONE NUMBER");
            System.out.println("3. EMAIL");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            Scanner readString = new Scanner(System.in);
            switch (x) {
                case 1: {
                    System.out.println("New Name:");
                    String name = readString.nextLine();
                    personRepository.updatePersonName(name, id);
                }
                case 2: {
                    System.out.println("New Phone Number:");
                    String phoneNumber = readString.nextLine();
                    personRepository.updatePersonPhoneNumber(phoneNumber, id);
                }
                case 3: {
                    System.out.println("New Email:");
                    String email = readString.nextLine();
                    personRepository.updatePersonEmail(email, id);
                }
                default: {
                    OK = false;
                }
            }
        }
    }

    public void newProducer(){
        Scanner read = new Scanner(System.in);
        Scanner readString = new Scanner(System.in);
    }

    public void setup(){
        clear_tabels();
        personRepository.insertPerson(new Person("Silvia2", "0755400594", "silvia@info.ro"));
        personRepository.insertPerson(new Person("Silvia3", "0755400594", "silvia@info.ro"));
        producerRepository.insertProducer(new Producer("Covalact", personRepository.getPersonById(get_max_id("people") - 1), "Strada florilor"));
        producerRepository.insertProducer(new Producer("Sana", personRepository.getPersonById(get_max_id("people")), "Strada rozleor"));
        foodRepository.insertFood(new Food("Smantana", 20, producerRepository.getProducerById(get_max_id("producers")), 30, 40, 50, new ArrayList<String>(Arrays.asList("Lapte", "Apa", "Sare")), 0.3));
        clientRepository.insertClient(new Client("Ion", "0755345324", "ion@info.ro", 30));
        clientRepository.insertClient(new Client("Ionela", "0755341324", "ionela@info.ro", 15));
        List<Product> listaProduse = new ArrayList<>();
        Product p = foodRepository.getFoodById(get_max_id("foods"));
        p.setUnits(10);
        listaProduse.add(p);
        LocalDateTime now = LocalDateTime.now();
        orderRepository.insertOrder(new Order(clientRepository.getClientById(get_max_id("clients")), listaProduse, now));
        Order order = orderRepository.getOrderById(get_max_id("orders"));
        System.out.println(order);
        System.out.println(personRepository.getPeople());
        System.out.println(producerRepository.getProducers());
        System.out.println(foodRepository.getFoods());
        System.out.println(clientRepository.getClients());
    }

    private void clear_tabels(){
        personRepository.deleteAllPeople();
        producerRepository.deleteAllProducers();
        foodRepository.deleteAllFoods();
        clientRepository.deleteAllClients();
        orderRepository.deleteAllOrders();
        orderRepository.deleteAllOrderProducts();
    }

    public int get_max_id(String table){
        String row = "";
        if(table.equals("people")){
            row = "personId";
        }
        else {
            if(table.equals("producers")){
                row = "producerId";
            }
            else {
                if(table.equals("orders")){
                    row = "orderId";
                }
                else {
                    if(table.equals("clients")){
                        row = "clientId";
                    }
                    else {
                        if(table.equals("foods")){
                            row = "foodId";
                        }
                    }
                }
            }
        }
        String selectSql = "SELECT max(" + row + ") FROM " + table;
        Connection databaseConnection = DatabaseConfiguration.getDatabaseConnection();
        try {
            Statement stmt = databaseConnection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectSql);
            return mapToId(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int mapToId(ResultSet resultSet) throws SQLException{
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return -1;
    }

    public void start(boolean clear_tabels) throws IOException {
        if(clear_tabels){
            clear_tabels();
        }
        boolean OK = true;
        while(OK) {
            System.out.println("Menu");
            System.out.println("1. Add Money To The Store");
            System.out.println("2. Add Producer");
            System.out.println("3. Add Food");
            System.out.println("4. Add Client");
            System.out.println("5. Add Order");
            System.out.println("6. Show Food");
            System.out.println("7. Show Store Money");
            System.out.println("8. Show Orders History");
            System.out.println("9. Show Clients");
            System.out.println("10. Show Producers");
            System.out.println("11. Edit Producers");
            System.out.println("12. Edit Foods");
            System.out.println("13. Edit Clients");
            System.out.println("14. Delete Producers");
            System.out.println("15. Delete Clients");
            System.out.println("16. Delete Foods");
            System.out.println("17. Cancel Orders");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> addMoneyToTheStore();
                case 2 -> addProducer();
                case 3 -> addFood();
                case 4 -> addClient();
                case 5 -> addOrder();
                case 6 -> showFood();
                case 7 -> showStoreMoney();
                case 8 -> showOrders();
                case 9 -> showClients();
                case 10 -> showProducers();
                case 11 -> editProducer();
                case 12 -> editFoods();
                case 13 -> editClients();
                case 14 -> deleteProducers();
                case 15 -> deleteClients();
                case 16 -> deleteFoods();
                case 17 -> cancelOrders();
                default -> OK = false;
            }
        }
    }
    private void addMoneyToTheStore() throws IOException {
        System.out.println("The added sum of money is:");
        Scanner read = new Scanner(System.in);
        double s = read.nextDouble();
        money += s;
        System.out.println("The total sum of money is: " + this.money);
        auditService.write("addMoneyToTheStore");
    }
    private void addProducer() throws IOException {
        Scanner read = new Scanner(System.in);
        System.out.println("Name of the producer:");
        String name = read.nextLine();
        System.out.println("Address of the producer:");
        String address = read.nextLine();
        System.out.println("Name of the contact person:");
        String nameContactPerson = read.nextLine();
        System.out.println("Contact person's phone number:");
        String phoneNumber = read.nextLine();
        System.out.println("Contact person's email:");
        String email = read.nextLine();
        personRepository.insertPerson(new Person(nameContactPerson, phoneNumber, email));
        producerRepository.insertProducer(new Producer(name, personRepository.getPersonById(get_max_id("people")), address));
        auditService.write("addProducers");
    }

    private void showProducers() throws IOException {
        System.out.println("Producers:");
        List<Producer> producers = producerRepository.getProducers();
        for (Producer producer : producers){
            System.out.println("Id: " + producer.getProducerId());
            System.out.println("Name: " + producer.getName());
            System.out.println("Address: " + producer.getAddress());
            System.out.println("Contact person: " + producer.getContactPerson().getName() +
                    ", phone number: " + producer.getContactPerson().getPhoneNumber() +
                    ", email: " + producer.getContactPerson().getEmail());
        }
        auditService.write("showProducers");
    }

    private void addFood() throws IOException {
        System.out.println("ADD FOOD");
        System.out.println("Choose a producer:");
        showProducers();
        Scanner readString = new Scanner(System.in);
        Scanner read = new Scanner(System.in);
        int producerId = read.nextInt();
        System.out.println("Name:");
        String name = readString.nextLine();
        System.out.println("Weight:");
        double weight = read.nextDouble();
        System.out.println("Price Per Unit:");
        double pricePerUnit = read.nextDouble();
        System.out.println("Units:");
        double units = read.nextDouble();
        System.out.println("Calories:");
        int calories = read.nextInt();
        System.out.println("Number of ingredients:");
        int nrIngredients = read.nextInt();
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < nrIngredients; i++) {
            System.out.println("Ingredient " + (i + 1));
            String ingredient = readString.nextLine();
            ingredients.add(ingredient);
        }
        System.out.println("Commercial excess: ");
        double commercialExcess = read.nextDouble();
        if(money >= units * pricePerUnit) {
            foodRepository.insertFood(new Food(name, pricePerUnit, producerRepository.getProducerById(producerId), weight, units, calories, ingredients, commercialExcess));
            money -= units * pricePerUnit;
        }
        else {
            System.out.println("Not enough money in the store");
        }
        auditService.write("addFood");
    }

    private void addClient() throws IOException {
        System.out.println("New Client:");
        Scanner read = new Scanner(System.in);
        System.out.println("Name:");
        String name = read.nextLine();
        System.out.println("Phone Number:");
        String phoneNumber = read.nextLine();
        System.out.println("Email:");
        String email = read.nextLine();
        System.out.println("Age:");
        int age = read.nextInt();
        clientRepository.insertClient(new Client(name, phoneNumber, email, age));
        auditService.write("addClient");
    }

    private void showFood() throws IOException {
        for (Food product : foodRepository.getFoods()){
            System.out.println("Id: " + product.getProductId());
            System.out.println("Name: " + product.getName());
            System.out.println("PricePerUnit: " + (product.getPricePerUnit() * (1 + product.getCommercialExcess())));
            System.out.println("Units: " + product.getUnits());
            System.out.println("Weight: " + product.getWeight());
            System.out.println("Calories: " + product.getCalories());
            System.out.println("Ingredients: " + product.getIngredients());
            System.out.println("Producer: " + product.getProducer().getName());
            System.out.println();
        }
        auditService.write("showFood");
    }

    private void showClients() throws IOException {
        System.out.println("Clients:");
        List<Client> clients = clientRepository.getClients();
        for(Client client : clients){
            System.out.println("Id: " + client.getPersonId());
            System.out.println("Name: " + client.getName());
            System.out.println("Phone Number: " + client.getPhoneNumber());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Age: " + client.getAge());
        }
        auditService.write("showClient");
    }

    private void addOrder() throws IOException {
        System.out.println("New Order");
        System.out.println("1. Existing Client");
        System.out.println("2. New Client");
        Scanner read = new Scanner(System.in);
        int x = read.nextInt();
        Client client = null;
        switch (x) {
            case 1 -> {
                System.out.println("Choose a client:");
                showClients();
                int id = read.nextInt();
                client = clientRepository.getClientById(id);
            }
            case 2 -> {
                addClient();
                client = clientRepository.getClientById(get_max_id("clients"));
            }
        }
        double moneyAdded = 0;
        System.out.println("Number of products:");
        int nrProducts = read.nextInt();
        List<Product> foods = new ArrayList<>();
        for(int i = 0; i < nrProducts; i++){
            System.out.println("Choose a foodId");
            showFood();
            int id = read.nextInt();
            System.out.println("Choose how many units");
            int units = read.nextInt();
            Product food = foodRepository.getFoodById(id);
            food.setUnits(units);
            if(units < foodRepository.getFoodById(id).getUnits()){
                foods.add(food);
                moneyAdded += food.getPricePerUnit() * (1 + food.getCommercialExcess()) * units;
            }
            else{
                System.out.println("Not enough units");
            }

        }
        LocalDateTime now = LocalDateTime.now();
        orderRepository.insertOrder(new Order(client, foods, now));
        money += moneyAdded;
        auditService.write("addOrder");
    }
    private void showOrders() throws IOException {
        System.out.println("Orders History:");
        int i = 1;
        for(Order order : orderRepository.getOrders()){
            System.out.println("Order " + order.getOrderId() + ":");
            System.out.println("Client = " + order.getClient());
            System.out.println("Products = " + order.getProducts());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Date = " + order.getDate().format(formatter));
            i++;
        }
        auditService.write("showOrders");
    }
    private void showStoreMoney() throws IOException {
        System.out.println("Store money: " + money);
        auditService.write("ShowStoreMoney");
    }
    private void editProducer() throws IOException {
        System.out.println("EDIT PRODUCERS");
        System.out.println("CHOOSE A PRODUCER ID: ");
        showProducers();
        Scanner read = new Scanner(System.in);
        int producerId = read.nextInt();
        editProducerAux(producerId);
        auditService.write("EditProducers");
    }
    private void editProducerAux(int producerId){
        Scanner readString = new Scanner(System.in);
        Producer producer = producerRepository.getProducerById(producerId);
        boolean OK = true;
        while(OK) {
            System.out.println("EDIT PRODUCER: " + producerId);
            System.out.println(producer);
            System.out.println("1. EDIT NAME");
            System.out.println("2. EDIT ADDRESS");
            System.out.println("3. EDIT CONTACT PERSON");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> {
                    System.out.println("OLD NAME: " + producer.getName());
                    System.out.println("NEW NAME: ");
                    String name = readString.nextLine();
                    producerRepository.updateProducerName(name, producerId);
                }
                case 2 -> {
                    System.out.println("OLD ADDRESS: " + producer.getAddress());
                    System.out.println("NEW ADDRESS: ");
                    String address = readString.nextLine();
                    producerRepository.updateProducerAddress(address, producerId);
                }
                case 3 -> {
                    editContactPerson(producer.getContactPerson().getPersonId());
                }
                default -> OK = false;
            }
        }
    }
    private void editContactPerson(int id){
        Scanner readString = new Scanner(System.in);
        Person person = personRepository.getPersonById(id);
        boolean OK = true;
        while(OK) {
            System.out.println("EDIT PERSON: ");
            System.out.println(person);
            System.out.println("1. EDIT NAME");
            System.out.println("2. EDIT PHONE NUMBER");
            System.out.println("3. EDIT EMAIL");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> {
                    System.out.println("OLD NAME: " + person.getName());
                    System.out.println("NEW NAME: ");
                    String name = readString.nextLine();
                    personRepository.updatePersonName(name, id);
                }
                case 2 -> {
                    System.out.println("OLD PHONE NUMBER: " + person.getPhoneNumber());
                    System.out.println("NEW PHONE NUMBER: ");
                    String phoneNumber = readString.nextLine();
                    personRepository.updatePersonPhoneNumber(phoneNumber, id);
                }
                case 3 -> {
                    System.out.println("OLD EMAIL: " + person.getEmail());
                    System.out.println("NEW EMAIL: ");
                    String email = readString.nextLine();
                    personRepository.updatePersonEmail(email, id);
                }
                default -> OK = false;
            }
        }
    }
    private void editFoods() throws IOException {
        System.out.println("EDIT FOODS");
        System.out.println("CHOOSE A FOOD ID: ");
        showFood();
        Scanner read = new Scanner(System.in);
        int foodId = read.nextInt();
        editFoodAux(foodId);
        auditService.write("EditFoods");
    }
    private void editFoodAux(int foodId){
        Scanner readString = new Scanner(System.in);
        Food food = foodRepository.getFoodById(foodId);
        boolean OK = true;
        while(OK) {
            System.out.println("EDIT FOOD: " + foodId);
            System.out.println(food);
            System.out.println("1. EDIT NAME");
            System.out.println("2. EDIT PricePerUnit");
            System.out.println("3. EDIT WEIGHT");
            System.out.println("4. EDIT UNITS");
            System.out.println("5. EDIT INGREDIENTS");
            System.out.println("6. EDIT CALORIES");
            System.out.println("7. EDIT COMMISSION EXCESS");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> {
                    System.out.println("OLD NAME: " + food.getName());
                    System.out.println("NEW NAME: ");
                    String name = readString.nextLine();
                    foodRepository.updateFoodName(name, foodId);
                }
                case 2 -> {
                    System.out.println("OLD PricePerUnit: " + food.getPricePerUnit());
                    System.out.println("NEW PricePerUnit: ");
                    double pricePerUnit = read.nextDouble();
                    foodRepository.updateFoodPricePerUnit(pricePerUnit, foodId);
                }
                case 3 -> {
                    System.out.println("OLD WEIGHT: " + food.getWeight());
                    System.out.println("NEW WEIGHT: ");
                    double weight = read.nextDouble();
                    foodRepository.updateFoodWeight(weight, foodId);
                }
                case 4 -> {
                    System.out.println("OLD UNITS: " + food.getUnits());
                    System.out.println("ADDED UNITS: ");
                    double units = read.nextDouble();
                    if(units * food.getPricePerUnit() <= money){
                        money -= units * food.getPricePerUnit();
                        foodRepository.updateFoodUnits(food.getUnits() + units, foodId);
                    }
                    else {
                        System.out.println("Not enough money");
                    }
                }
                case 5 -> {
                    System.out.println("OLD INGREDIENTS: " + food.getIngredients());
                    System.out.println("NEW INGREDIENTS: ");
                    System.out.println("Number of ingredients:");
                    int nrIngredients = read.nextInt();
                    ArrayList<String> ingredients = new ArrayList<>();
                    for (int i = 0; i < nrIngredients; i++) {
                        System.out.println("Ingredient " + (i + 1));
                        String ingredient = readString.nextLine();
                        ingredients.add(ingredient);
                    }
                    foodRepository.updateFoodIngredients(ingredients, foodId);
                }
                case 6 -> {
                    System.out.println("OLD CALORIES: " + food.getCalories());
                    System.out.println("NEW CALORIES: ");
                    int calories = read.nextInt();
                    foodRepository.updateFoodCalories(calories, foodId);
                }
                case 7 -> {
                    System.out.println("OLD COMMERCIAL EXCESS: " + food.getCommercialExcess());
                    System.out.println("NEW COMMERCIAL EXCESS: ");
                    double commercialExcess = read.nextDouble();
                    foodRepository.updateFoodCommercialExcess(commercialExcess, foodId);
                }
                default -> OK = false;
            }
        }
    }
    private void editClients() throws IOException {
        System.out.println("EDIT CLIENTS");
        System.out.println("CHOOSE A CLIENT ID: ");
        showClients();
        Scanner read = new Scanner(System.in);
        int clientId = read.nextInt();
        editClientAux(clientId);
        auditService.write("editClients");
    }
    private void editClientAux(int id){
        Scanner readString = new Scanner(System.in);
        Client client = clientRepository.getClientById(id);
        boolean OK = true;
        while(OK) {
            System.out.println("EDIT CLIENT: ");
            System.out.println(client);
            System.out.println("1. EDIT NAME");
            System.out.println("2. EDIT PHONE NUMBER");
            System.out.println("3. EDIT EMAIL");
            System.out.println("4. EDIT AGE");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> {
                    System.out.println("OLD NAME: " + client.getName());
                    System.out.println("NEW NAME: ");
                    String name = readString.nextLine();
                    clientRepository.updateClientName(name, id);
                }
                case 2 -> {
                    System.out.println("OLD PHONE NUMBER: " + client.getPhoneNumber());
                    System.out.println("NEW PHONE NUMBER: ");
                    String phoneNumber = readString.nextLine();
                    clientRepository.updateClientPhoneNumber(phoneNumber, id);
                }
                case 3 -> {
                    System.out.println("OLD EMAIL: " + client.getEmail());
                    System.out.println("NEW EMAIL: ");
                    String email = readString.nextLine();
                    clientRepository.updateClientEmail(email, id);
                }
                case 4 -> {
                    System.out.println("OLD AGE: " + client.getAge());
                    System.out.println("NEW AGE: ");
                    int age = read.nextInt();
                    clientRepository.updateClientAge(age, id);
                }
                default -> OK = false;
            }
        }
    }
    private void deleteProducers() throws IOException {
        System.out.println("DELETE PRODUCERS");
        System.out.println("CHOOSE A PRODUCER ID: ");
        showProducers();
        Scanner read = new Scanner(System.in);
        int producerId = read.nextInt();
        producerRepository.deleteProducer(producerId);
        auditService.write("deleteProducers");
    }
    private void deleteFoods() throws IOException {
        System.out.println("DELETE FOODS");
        System.out.println("CHOOSE A FOOD ID: ");
        showProducers();
        Scanner read = new Scanner(System.in);
        int foodId = read.nextInt();
        Food food = foodRepository.getFoodById(foodId);
        money += food.getUnits() * food.getPricePerUnit();
        foodRepository.deleteFood(foodId);
        auditService.write("deleteFoods");
    }
    private void deleteClients() throws IOException {
        System.out.println("DELETE CLIENTS");
        System.out.println("CHOOSE A CLIENT ID: ");
        showProducers();
        Scanner read = new Scanner(System.in);
        int clientId = read.nextInt();
        clientRepository.deleteClient(clientId);
        auditService.write("deleteClients");
    }
    private void cancelOrders() throws IOException {
        System.out.println("CANCEL ORDERS");
        System.out.println("CHOOSE AN ORDER ID: ");
        showProducers();
        Scanner read = new Scanner(System.in);
        int orderId = read.nextInt();
        orderRepository.cancelOrder(orderId);
    }
}

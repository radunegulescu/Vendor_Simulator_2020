package project.service;

import project.classes.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Service {
    private double money;   // banii magazinului
    private HashSet<Producer> producers;   // lista cu producatorii
    private ArrayList<Product> stock;    // lista cu produsele din stoc ordonate dupa pretul pe unitate
    private ArrayList<Order> orders;     // lista cu comenzile plasate
    private HashMap<String, Double> productProducer;   // lista care mapeaza producatorii la sumele platite catre acestia
    private ArrayList<Client> clients;  // lista cu clientii

    private void setMoney(double s){
        this.money = s;
    }

    private void addMoney(double s){
        this.money += s;
    }

    private double getMoney(){
        return money;
    }

    private HashSet<Producer> getProducers() {
        return producers;
    }

    private void setProducers(HashSet<Producer> producers) {
        this.producers = producers;
    }

    private ArrayList<Product> getStock() {
        return stock;
    }

    private void setStock(ArrayList<Product> stock) {
        this.stock = stock;
    }

    private ArrayList<Order> getOrders() {
        return orders;
    }

    private void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    private HashMap<String, Double> getProductProducer() {
        return productProducer;
    }

    private void setProductProducer(HashMap<String, Double> productProducer) {
        this.productProducer = productProducer;
    }

    public Service() {
        this.producers = new HashSet<>();
        this.stock = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.productProducer = new HashMap<>();
        this.money = 0;
        this.clients = new ArrayList<>();
    }
    private void addMapProducer(int producerId, double units, double pricePerUnit){
        List<Producer> list = new ArrayList<>(producers);
        String producerName = list.get(producerId - 1).getName();
        if (productProducer.containsKey(producerName)) {
            productProducer.put(producerName, productProducer.get(producerName) + units * pricePerUnit);
        }
        else {
            productProducer.put(producerName, units * pricePerUnit);
        }
    }
    private void addProducer(String name, String address, String nameContactPerson, String phoneNumber, String email){
        Person contactPerson = new Person(nameContactPerson, phoneNumber, email);
        producers.add(new Producer(name, contactPerson, address));
    }
    private void addProduct1(double units, double pricePerUnit, Product p, int producerId){
        if(money >= units * pricePerUnit){
            addMoney(-units * pricePerUnit);
            System.out.println("Commercial excess: ");
            Scanner read = new Scanner(System.in);
            Double commercialExcess = read.nextDouble();
            addMapProducer(producerId, units, pricePerUnit);
            addProduct(p, commercialExcess);
        }
        else {
            System.out.println("Not enough money in the store");
        }
    }
    private void addProduct(Product p, Double commerciaExcess){
        if (!stock.contains(p)){
            p.setPricePerUnit(p.getPricePerUnit() * (1 + commerciaExcess));
            stock.add(p);
        }
        else{
            for (Product product : stock) {
                if (p.equals(product)) {
                    product.setPricePerUnit(product.getPricePerUnit() * (1 + commerciaExcess));
                    product.setUnits(product.getUnits() + p.getUnits());
                }
            }
        }
        SortTheStock();
    }
    private void addFood(int producerId, String name, double pricePerUnit, double weight, double units, int calories, List<String> ingredients){
        List<Producer> list = new ArrayList<>(producers);
        Product p = new Food(name, pricePerUnit, list.get(producerId - 1), weight, units, calories, ingredients);
        addProduct1(units, pricePerUnit, p, producerId);
    }
    private void addDrink(int producerId, String name, double pricePerUnit, double weight, double units, double alcohol){
        List<Producer> list = new ArrayList<>(producers);
        Product p = new Drink(name, pricePerUnit, list.get(producerId - 1), weight, units, alcohol);
        addProduct1(units, pricePerUnit, p, producerId);
    }
    private void addClothes(int producerId, String name, double pricePerUnit, double weight, double units, String size){
        List<Producer> list = new ArrayList<>(producers);
        Product p = new Clothes(name, pricePerUnit, list.get(producerId - 1), weight, units, size);
        addProduct1(units, pricePerUnit, p, producerId);
    }
    private void addOthers(int producerId, String name, double pricePerUnit, double weight, double units, String category){
        List<Producer> list = new ArrayList<>(producers);
        Product p = new Others(name, pricePerUnit, list.get(producerId - 1), weight, units, category);
        addProduct1(units, pricePerUnit, p, producerId);
    }

    private void addOrder(int[] indicesOfProducts, int[] units, Client client){
        List<Product> products = new ArrayList<>();
        System.out.println("Order:");
        List<Integer> indicesAux = new ArrayList<>();
        List<Integer> indicesAux2 = new ArrayList<>();
        int k = 0;
        for (Integer i:indicesOfProducts){
            Product product = null;
            if(stock.get(i - 1).getClass().getName().equals("project.classes.Food")){
                product = new Food(stock.get(i - 1));
            }
            if(stock.get(i - 1).getClass().getName().equals("project.classes.Clothes")){
                product = new Clothes(stock.get(i - 1));
            }
            if(stock.get(i - 1).getClass().getName().equals("project.classes.Drink")){
                product = new Drink(stock.get(i - 1));
            }
            if(stock.get(i - 1).getClass().getName().equals("project.classes.Others")){
                product = new Others(stock.get(i - 1));
            }
            if (product != null){
                product.setUnits(units[k]);
                System.out.println(i + ": " + product + "\n price = " + product.getPricePerUnit() * product.getUnits());
                if (units[k] <= stock.get(i - 1).getUnits()){
                    products.add(product);
                    indicesAux.add(i);
                    indicesAux2.add(k);
                }
                else {
                    System.out.println("Not enough units");
                }
            }
            k++;
        }
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(client, products, now);
        orders.add(order);
        showStock();
        int j = 1;
        k = 0;
        for (int i:indicesAux){
            addMoney(units[indicesAux2.get(k)] * stock.get(i - j).getPricePerUnit());
            stock.get(i - j).setUnits(stock.get(i - j).getUnits() - units[indicesAux2.get(k)]);
            if(stock.get(i - j).getUnits() == 0){
                stock.remove(i - j);
                j++;
            }
            k++;
        }
        SortTheStock();
    }

    private void SortTheStock(){
        Collections.sort(stock);
    }

    private void showStock(){
        int i = 1;
        for (Product product : stock){
            System.out.println(i + ": " + product);
            i++;
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "producers=" + producers + "\n" +
                ", stock=" + stock + "\n" +
                ", orders=" + orders + "\n" +
                ", productProducer=" + productProducer +
                '}';
    }

    public void start(){
        boolean OK = true;
        while(OK) {
            System.out.println("Menu");
            System.out.println("1. Add money to the store");
            System.out.println("2. Add a producer");
            System.out.println("3. Add a product");
            System.out.println("4. Add an order");
            System.out.println("5. Show stock");
            System.out.println("6. Show the producers and how much were they paid");
            System.out.println("7. Show store money");
            System.out.println("8. Show orders history");
            System.out.println("9. Add a client");
            System.out.println("10. Show clients");
            System.out.println("11. Show producers");
            System.out.println("0. Exit");
            Scanner read = new Scanner(System.in);
            int x = read.nextInt();
            switch (x) {
                case 1 -> addMoneyToTheStore();
                case 2 -> addProducerAux();
                case 3 -> addProductAux();
                case 4 -> addOrderAux();
                case 5 -> showStock();
                case 6 -> showMoneyToProducers();
                case 7 -> showStoreMoney();
                case 8 -> showOrders();
                case 9 -> addClient();
                case 10 -> showClients();
                case 11 -> showProducers();
                default -> OK = false;
            }
        }
    }

    private void addMoneyToTheStore(){
        System.out.println("The added sum of money is:");
        Scanner read = new Scanner(System.in);
        double s = read.nextDouble();
        addMoney(s);
        System.out.println("The total sum of money is: " + this.money);
    }

    private void addProducerAux(){
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
        addProducer(name, address, nameContactPerson, phoneNumber, email);
    }
    private void showProducers(){
        System.out.println("Producers:");
        int i = 1;
        for (Producer producer : producers){
            System.out.println(i + ":");
            System.out.println("Name: " + producer.getName());
            System.out.println("Address: " + producer.getAddress());
            System.out.println("Contact person: " + producer.getContactPerson().getName() +
                    ", " + producer.getContactPerson().getPhoneNumber() +
                    ", " + producer.getContactPerson().getEmail());
            i++;
        }
    }
    private void showStoreMoney(){
        System.out.println("Money in the store = " + money);
    }

    private void addProductAux(){
        System.out.println("Choose a producer:");
        showProducers();
        Scanner readString = new Scanner(System.in);
        Scanner read = new Scanner(System.in);
        int producer_id = read.nextInt();
        System.out.println("Choose a category:");
        System.out.println("1. Food");
        System.out.println("2. Drink");
        System.out.println("3. Clothes");
        System.out.println("4. Others");
        int x = read.nextInt();
        System.out.println("Name:");
        String name = readString.nextLine();
        System.out.println("Weight:");
        double weight = read.nextDouble();
        System.out.println("Price Per Unit:");
        double pricePerUnit = read.nextDouble();
        System.out.println("Units:");
        double units = read.nextDouble();
        switch (x) {
            case 1 -> {
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
                addFood(producer_id, name, pricePerUnit, weight, units, calories, ingredients);
            }
            case 2 -> {
                System.out.println("Alcohol:");
                double alcohol = read.nextDouble();
                addDrink(producer_id, name, pricePerUnit, weight, units, alcohol);
            }
            case 3 -> {
                System.out.println("Size:");
                String size = readString.nextLine();
                addClothes(producer_id, name, pricePerUnit, weight, units, size);
            }
            case 4 -> {
                System.out.println("Category:");
                String category = readString.nextLine();
                addOthers(producer_id, name, pricePerUnit, weight, units, category);
            }
            default -> System.out.println("Non-action");
        }
    }
    private void addClient(){
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
        clients.add(new Client(name, phoneNumber, email, age));
    }
    private void showClients(){
        System.out.println("Clients:");
        for(int i = 0; i < clients.size(); i++){
            System.out.println("Client" + (i + 1));
            Client client = clients.get(i);
            System.out.println("Name: " + client.getName());
            System.out.println("Phone Number: " + client.getPhoneNumber());
            System.out.println("Email: " + client.getEmail());
            System.out.println("Age: " + client.getAge());
        }
    }
    private void addOrderAux(){
        System.out.println("New Order");
        System.out.println("1. Existing Client");
        System.out.println("2. New Client");
        Scanner read = new Scanner(System.in);
        int x = read.nextInt();
        Client client = new Client();
        switch (x) {
            case 1 -> {
                System.out.println("Choose a client:");
                showClients();
                int i = read.nextInt();
                client = clients.get(i - 1);
            }
            case 2 -> {
                addClient();
                client = clients.get(clients.size() - 1);
            }
        }
        System.out.println("Number of products:");
        int nrProducts = read.nextInt();
        int[] indicesOfProducts = new int[nrProducts];
        int[] units = new int[nrProducts];
        for(int i = 0; i < nrProducts; i++){
            System.out.println("Choose a product");
            showStock();
            int nr = read.nextInt();
            indicesOfProducts[i] = nr;
            System.out.println("Choose how many units");
            int unit = read.nextInt();
            units[i] = unit;
        }
        addOrder(indicesOfProducts, units, client);
    }
    private void showOrders(){
        System.out.println("Orders History:");
        int i = 1;
        for(Order order : orders){
            System.out.println("Order " + i + ":");
            System.out.println("Client = " + order.getClient());
            System.out.println("Products = " + order.getProducts());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("Date = " + order.getDate().format(formatter));
            i++;
        }
    }
    private void showMoneyToProducers(){
        for(Map.Entry<String, Double> entry : productProducer.entrySet()){
            System.out.println("Producer: " + entry.getKey() + " money = " + entry.getValue());
        }
    }

    public void demo(){
        addMoney(1000);
        addProducer("p1", "a1", "c1", "0755400594", "e1");
        addProducer("p2", "a2", "c2", "0765400594", "e2");
        addProducer("p3", "a3", "c3", "0765400594", "e3");
        addFood(1, "Pui intreg", 20, 3, 1, 150, new ArrayList<String>(Arrays.asList("Pui", "Apa", "Sare")));
        addFood(1, "Pui intreg dezosat", 25, 3, 2, 150, new ArrayList<String>(Arrays.asList("Pui", "Apa", "Sare")));
        addFood(1, "Pui intreg dezosat", 30, 3, 2, 150, new ArrayList<String>(Arrays.asList("Pui", "Apa", "Sare")));
        addFood(1, "Pui intreg marinat", 25, 3, 2, 150, new ArrayList<String>(Arrays.asList("Pui", "Apa", "Sare")));
        addDrink(2, "cola", 5, 100, 20, 0);
        addDrink(2, "vin", 10, 500, 2, 0.15);
        addClothes(3, "blugi", 50, 1000, 2, "M");
        addOthers(3, "pix", 2, 10, 10, "pixuri");
        showStock();
        showProducers();
        Client client = new Client("ion", "0743298456", "email@gmail.com", 20);
        clients.add(client);
        client = new Client("sergiu", "0743298454", "email2@gmail.com", 17);
        clients.add(client);
        showClients();
        addOrder(new int[]{1, 2, 3}, new int[]{1, 1, 2}, clients.get(0));
        addOrder(new int[]{1, 3, 4}, new int[]{9, 8, 2}, clients.get(0));
        showStock();
        showOrders();
        showStoreMoney();
        showMoneyToProducers();
    }
}

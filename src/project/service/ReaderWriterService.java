package project.service;

import project.classes.*;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


public class ReaderWriterService {
    private static ReaderWriterService single_instance = null;
    private ReaderWriterService()
    {
    }
    public static ReaderWriterService getInstance()
    {
        if (single_instance == null)
            single_instance = new ReaderWriterService();

        return single_instance;
    }

    public void emptyFiles(){
        try {
            File csvFile = new File("src/project/csv_input/producers.csv");
            if (csvFile.isFile()) {
                FileWriter csvWriter = new FileWriter("src/project/csv_input/producers.csv", false);
                csvWriter.append("");
            }
            File csvFile2 = new File("src/project/csv_input/clients.csv");
            if (csvFile2.isFile()) {
                FileWriter csvWriter = new FileWriter("src/project/csv_input/clients.csv", false);
                csvWriter.append("");
            }
            File csvFile3 = new File("src/project/csv_input/foods.csv");
            if (csvFile3.isFile()) {
                FileWriter csvWriter = new FileWriter("src/project/csv_input/foods.csv", false);
                csvWriter.append("");
            }
            File csvFile4 = new File("src/project/csv_input/orders.csv");
            if (csvFile4.isFile()) {
                FileWriter csvWriter = new FileWriter("src/project/csv_input/orders.csv", false);
                csvWriter.append("");
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public <T> void writeToCSV(T ob) {
        if (ob instanceof Producer){
            try {
                File csvFile = new File("src/project/csv_input/producers.csv");
                if (csvFile.isFile()) {
                    FileWriter csvWriter = new FileWriter("src/project/csv_input/producers.csv", true);
                    if (csvFile.length() == 0){
                        csvWriter.append("Name");
                        csvWriter.append(",");
                        csvWriter.append("Contact Person Id");
                        csvWriter.append(",");
                        csvWriter.append("Address");
                        csvWriter.append("\n");
                    }
                    csvWriter.append(((Producer) ob).getName());
                    csvWriter.append(",");
                    csvWriter.append(String.valueOf(((Producer) ob).getContactPerson().getPersonId()));
                    csvWriter.append(",");
                    csvWriter.append(((Producer) ob).getAddress().replace(",", ""));
                    csvWriter.append("\n");
                    csvWriter.flush();
                    csvWriter.close();
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else{
            if (ob instanceof Order){
                try {
                    File csvFile = new File("src/project/csv_input/orders.csv");
                    if (csvFile.isFile()) {
                        FileWriter csvWriter = new FileWriter("src/project/csv_input/orders.csv", true);
                        if (csvFile.length() == 0){
                            csvWriter.append("Client");
                            csvWriter.append(",");
                            csvWriter.append("ProductsIds");
                            csvWriter.append(",");
                            csvWriter.append("Date");
                            csvWriter.append("\n");
                        }
                        csvWriter.append(String.valueOf(((Order) ob).getClient().getPersonId()));
                        csvWriter.append(",");
                        csvWriter.append(((Order) ob).getProductsIds().toString().replace(",", ""));
                        csvWriter.append(",");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        csvWriter.append(((Order) ob).getDate().format(formatter));
                        csvWriter.append("\n");
                        csvWriter.flush();
                        csvWriter.close();
                    }
                }
                catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
            else {
                if (ob instanceof Food){
                    try {
                        File csvFile = new File("src/project/csv_input/foods.csv");
                        if (csvFile.isFile()) {
                            FileWriter csvWriter = new FileWriter("src/project/csv_input/foods.csv", true);
                            if (csvFile.length() == 0){
                                csvWriter.append("Name");
                                csvWriter.append(",");
                                csvWriter.append("PricePerUnit");
                                csvWriter.append(",");
                                csvWriter.append("ProducerId");
                                csvWriter.append(",");
                                csvWriter.append("Weight");
                                csvWriter.append(",");
                                csvWriter.append("Units");
                                csvWriter.append(",");
                                csvWriter.append("Calories");
                                csvWriter.append(",");
                                csvWriter.append("Ingredients");
                                csvWriter.append("\n");
                            }
                            csvWriter.append(((Food) ob).getName().replace(",", ""));
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(((Food) ob).getPricePerUnit()));
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(((Food) ob).getProducer().getProducerId()));
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(((Food) ob).getWeight()));
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(((Food) ob).getUnits()));
                            csvWriter.append(",");
                            csvWriter.append(String.valueOf(((Food) ob).getCalories()));
                            csvWriter.append(",");
                            csvWriter.append(((Food) ob).getIngredients().toString().replace(",", ""));
                            csvWriter.append("\n");
                            csvWriter.flush();
                            csvWriter.close();
                        }
                    }
                    catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    if (ob instanceof Client){
                        try {
                            File csvFile = new File("src/project/csv_input/clients.csv");
                            if (csvFile.isFile()) {
                                FileWriter csvWriter = new FileWriter("src/project/csv_input/clients.csv", true);
                                if (csvFile.length() == 0){
                                    csvWriter.append("Name");
                                    csvWriter.append(",");
                                    csvWriter.append("Phone Number");
                                    csvWriter.append(",");
                                    csvWriter.append("Email");
                                    csvWriter.append(",");
                                    csvWriter.append("Age");
                                    csvWriter.append("\n");
                                }
                                csvWriter.append(((Client) ob).getName());
                                csvWriter.append(",");
                                csvWriter.append(((Client) ob).getPhoneNumber());
                                csvWriter.append(",");
                                csvWriter.append(((Client) ob).getEmail());
                                csvWriter.append(",");
                                csvWriter.append(String.valueOf(((Client) ob).getAge()));
                                csvWriter.append("\n");
                                csvWriter.flush();
                                csvWriter.close();
                            }
                        }
                        catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }
    public <T> ArrayList<T> readFromCSV(String type, Service s) throws IOException {
        String line = "";
        ArrayList<T> list2 = new ArrayList<>();
        if(type.equals("Producer")){
            BufferedReader br = new BufferedReader(new FileReader("src/project/csv_input/producers_read.csv"));
            line = br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] producerString = line.split(",");    // use comma as separator
                String producerName = producerString[0];
                int contactPersonId = Integer.parseInt(producerString[1]);
                Person person = s.getContactPeople().stream()
                        .filter(person1 -> contactPersonId == person1.getPersonId())
                        .findAny()
                        .orElse(null);
                String producerAddress = producerString[2];
                Producer producer = new Producer(producerName, person, producerAddress);
                list2.add((T) producer);
            }
        }
        else if(type.equals("Client")){
            BufferedReader br = new BufferedReader(new FileReader("src/project/csv_input/clients_read.csv"));
            line = br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] clientString = line.split(",");    // use comma as separator
                Client client = new Client(clientString[0],clientString[1],clientString[2],Integer.parseInt(clientString[3]));
                list2.add((T) client);
            }
        }
        else if(type.equals("Food")){
            BufferedReader br = new BufferedReader(new FileReader("src/project/csv_input/foods_read.csv"));
            line = br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] foodString = line.split(",");    // use comma as separator
                int producerId = Integer.parseInt(foodString[2]);
                Producer producer = s.getProducers().stream()
                        .filter(producer1 -> producerId == producer1.getProducerId())
                        .findAny()
                        .orElse(null);
                String foodName = foodString[0];
                double pricePerUnit = Double.parseDouble(foodString[1]);
                double weight = Double.parseDouble(foodString[3]);
                double units = Double.parseDouble(foodString[4]);
                int calories = Integer.parseInt(foodString[5]);

                String ingredientsString = foodString[6].replace("[", "").replace("]", "");
                ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientsString.split(" ")));
                Food food = new Food(foodName,pricePerUnit,producer,weight,units,calories,ingredients);
                list2.add((T) food);
            }
        }
        else if(type.equals("Order")){
            BufferedReader br = new BufferedReader(new FileReader("src/project/csv_input/orders_read.csv"));
            line = br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] orderString = line.split(",");    // use comma as separator
                int clientId = Integer.parseInt(orderString[0]);
                Client client = s.getClients().stream()
                        .filter(client1 -> clientId == client1.getPersonId())
                        .findAny()
                        .orElse(null);
                ArrayList<Product> products = new ArrayList<>();
                ArrayList<Integer> productsIds = new ArrayList<>();
                String productsString = orderString[1].replace("[", "").replace("]", "");
                ArrayList<String> stringIds = new ArrayList<String>(Arrays.asList(productsString.split(" ")));
                for(String s2 : stringIds) productsIds.add(Integer.valueOf(s2));
                for(int id: productsIds){
                    Product product = s.getStock().stream()
                            .filter(product1 -> id == product1.getProductId())
                            .findAny()
                            .orElse(null);
                    if (product != null){
                        products.add(product);
                    }
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime date = LocalDateTime.parse(orderString[2], formatter);
                Order order = new Order(client,products,date);
                list2.add((T) order);
            }
        }
        return list2;
    }
}

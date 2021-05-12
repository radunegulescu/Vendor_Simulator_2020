package project.main;

import project.classes.Client;
import project.classes.Food;
import project.classes.Person;
import project.classes.Producer;
import project.service.Service;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void f(ArrayList<String> list){
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("ion");
        list2.add("ana");
        list.clear();
    }

    public static void main(String[] args) throws IOException {
        Service s = new Service();
//        s.start();
        s.demo();
//        s.readCSVDemo();
//        Food f = new Food("Pui intreg", 20, null, 3, 1, 150, new ArrayList<String>(Arrays.asList("Pui", "Apa", "Sare")));
//        System.out.println(f);
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("maria");
//        list.add("sergiu");
//        f(list);
//        System.out.println(list);
//        String line = "p1,Person{name='c1' phoneNumber=0755400594 email='e1'},a1";
//        String[] producerString = line.split(",");    // use comma as separator
//        String person = producerString[1].split("\\{")[1];
//        String producerName = producerString[0];
//        System.out.println(Arrays.toString(person.split(" ")));
//        String personName = person.split(" ")[0].split("=")[1].split("'")[1];
//        System.out.println(personName);
//        String personPhoneNumber = person.split(" ")[1].split("=")[1];
//        String personEmail = person.split(" ")[2].split("=")[1].split("'")[1];
//        String producerAddress = producerString[2];
//        Person person1 = new Person(personName, personPhoneNumber, personEmail);
//        Producer producer = new Producer(producerName, person1, producerAddress);
//        System.out.println(producerName + " " + personName + " " + personPhoneNumber + " " + personEmail + " " + producerAddress);
//        String line = "ion,0743298456,email@gmail.com,20";
//        String[] clientString = line.split(",");    // use comma as separator
//        Client client = new Client(clientString[0],clientString[1],clientString[2],Integer.parseInt(clientString[3]));
//        System.out.println(client);
    }
}

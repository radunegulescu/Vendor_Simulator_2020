package project.main;

import project.classes.Client;
import project.classes.Food;
import project.classes.Person;
import project.classes.Producer;
import project.repository.FoodRepository;
import project.repository.PersonRepository;
import project.repository.ProducerRepository;
import project.service.Service;
import project.service.ServiceDB;

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
//        s.demo();
//        s.readCSVDemo();
        PersonRepository personRepository = new PersonRepository();
        ProducerRepository producerRepository = new ProducerRepository();
        FoodRepository foodRepository = new FoodRepository();
//        personRepository.deleteAllPeople();
//        personRepository.insertPerson(new Person("Silvia2", "0755400594", "silvia@info.ro"));
//        personRepository.insertPerson(new Person("Silvia3", "0755400594", "silvia@info.ro"));
//        Person person = personRepository.getPersonById(1);
//        System.out.println("Name = " + person.getName());
//        personRepository.updatePersonName("Ion", 1);
//        Person updatedPerson = personRepository.getPersonById(1);
//        System.out.println("Name = " + updatedPerson.getName());
//        System.out.println(personRepository.getPeople());
//        producerRepository.insertProducer(new Producer("Covalact", personRepository.getPersonById(6), "Strada florilor"));
//        producerRepository.insertProducer(new Producer("Sana", personRepository.getPersonById(7), "Strada rozleor"));
//        System.out.println(producerRepository.getProducerById(11));
//        producerRepository.deleteAllProducers();
//        producerRepository.updateProducerContactPerson(7, 11);
//        producerRepository.updateProducerName("Nestle", 2);
//        System.out.println(producerRepository.getProducers());
//        producerRepository.deleteProducer(11);
//        System.out.println(producerRepository.getProducers());
//        producerRepository.deleteAllProducers();
        ServiceDB serviceDB = new ServiceDB();
//        serviceDB.setup();
        serviceDB.start();
    }
}

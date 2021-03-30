package project.classes;

import java.util.Objects;

public class Person {
    protected String name;
    protected String phoneNumber;
    protected String email;

    //constructor with parameters
    public Person (String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //constructor without parameters
    public Person (){
        this.name = "";
        this.phoneNumber = "";
        this.email = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(name, person.name) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email);
    }
}

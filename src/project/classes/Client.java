package project.classes;

import java.util.List;
import java.util.Objects;

public class Client extends Person{
    private int age;

    // contructor with parameters
    public Client(String name, String phoneNumber, String email, int age) {
        super(name, phoneNumber, email);
        this.age = age;
    }

    // contructor without parameters
    public Client() {
        super();
        this.age = 0;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return age == client.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age);
    }

    @Override
    public String toString() {
        return "Client{" +
                "age=" + age +
                " name='" + name + '\'' +
                " personId=" + personId +
                " phoneNumber=" + phoneNumber +
                " email='" + email + '\'' +
                '}';
    }

    public boolean major(){
        return age >= 18;
    }

}

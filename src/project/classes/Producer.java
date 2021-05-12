package project.classes;

import java.util.Objects;

public class Producer {
    static int maxId;
    private int producerId;
    private String name;
    private Person contactPerson;
    private String address;

    public Producer(String name, Person contactPerson, String address) {
        this.producerId = Producer.maxId + 1;
        Producer.maxId += 1;
        this.name = name;
        this.contactPerson = contactPerson;
        this.address = address;
    }

    public Producer() {
        this.producerId = Producer.maxId + 1;
        Producer.maxId += 1;
        this.name = "";
        this.contactPerson = null;
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProducerId() {
        return producerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(name, producer.name) && Objects.equals(contactPerson, producer.contactPerson) && Objects.equals(address, producer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contactPerson, address);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                " producerId=" + producerId +
                " contactPerson=" + contactPerson +
                " address='" + address + '\'' +
                '}';
    }

}

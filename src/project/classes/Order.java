package project.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class Order {
    private Client client;
    private List<Product> products;
    private LocalDateTime date;

    public Order(Client client, List<Product> products, LocalDateTime date) {
        this.client = client;
        this.products = products;
        this.date = date;
    }

    public Order(){
        this.client = null;
        this.products = null;
        this.date = null;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(client, order.client) && Objects.equals(products, order.products) && Objects.equals(date, order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, products, date);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Order{" +
                "client=" + client +
                ", products=" + products +
                ", date=" + date.format(formatter) +
                '}';
    }
}

package project.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    static int maxId;
    private int orderId;
    private Client client;
    private List<Product> products;
    private LocalDateTime date;

    public Order(Client client, List<Product> products, LocalDateTime date) {
        this.orderId = Order.maxId + 1;
        Order.maxId += 1;
        this.client = client;
        this.products = products;
        this.date = date;
    }

    public Order(){
        this.orderId = Order.maxId + 1;
        Order.maxId += 1;
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

    public List<Integer> getProductsIds() {
        List<Integer> list = new ArrayList<>();
        for(Product p:products){
            list.add(p.getProductId());
        }
        return list;
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

    public int getOrderId() {
        return orderId;
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
                "orderId=" + orderId +
                " client=" + client +
                " products=" + products +
                " date=" + date.format(formatter) +
                '}';
    }
}

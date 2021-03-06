package project.classes;

import java.util.Objects;

public abstract class Product implements Comparable<Product> {
    static int maxId;
    protected int productId;
    protected String name;
    protected double pricePerUnit;
    protected Producer producer;
    protected double weight;
    protected double units;
    protected double commercialExcess;

    public Product(String name, double pricePerUnit, Producer producer, double weight, double units) {
        this.commercialExcess = 0;
        this.productId = Product.maxId + 1;
        Product.maxId += 1;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.producer = producer;
        this.weight = weight;
        this.units = units;
    }

    public Product(String name, double pricePerUnit, Producer producer, double weight, double units, double commercialExcess) {
        this.commercialExcess = commercialExcess;
        this.productId = Product.maxId + 1;
        Product.maxId += 1;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.producer = producer;
        this.weight = weight;
        this.units = units;
    }

    public Product() {
        this.commercialExcess = 0;
        this.productId = Product.maxId + 1;
        Product.maxId += 1;
        this.name = null;
        this.pricePerUnit = 0;
        this.producer = null;
        this.units = 0;
        this.weight = 0;
    }

    public Product(Product product){
        this.commercialExcess = product.commercialExcess;
        this.productId = product.productId;
        this.name = product.name;
        this.pricePerUnit = product.pricePerUnit;
        this.producer = product.producer;
        this.weight = product.weight;
        this.units = product.units;
    }

    public double getCommercialExcess() {
        return commercialExcess;
    }

    public void setCommercialExcess(double commercialExcess) {
        this.commercialExcess = commercialExcess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public int getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.weight, weight) == 0 && Objects.equals(name, product.name) && Objects.equals(producer, product.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pricePerUnit, producer, weight, units);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                " name='" + name + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", producer=" + producer +
                ", weight=" + weight +
                ", units=" + units +
                ", commercialExcess=" + commercialExcess +
                '}';
    }

    @Override
    public int compareTo(Product product) {
        /*
        * positive integer, if the current object is greater than the
        specified object.
        * negative integer, if the current object is less than the
        specified object.
        * zero, if the current object is equal to the specified object.
        */
        int p1 = (int) this.pricePerUnit;
        int p2 = (int) product.pricePerUnit;
        return p2 - p1;
    }
}

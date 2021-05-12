package project.classes;

import java.util.Objects;

public class Clothes extends Product{
    private String size;

    public Clothes(String name, double pricePerUnit, Producer producer, double weight, double units, String size) {
        super(name, pricePerUnit, producer, weight, units);
        this.size = size;
    }

    public Clothes(){
        super();
        this.size = null;
    }

    public Clothes(Product product){
        Clothes clothes = (Clothes) product;
        this.name = product.name;
        this.pricePerUnit = product.pricePerUnit;
        this.producer = product.producer;
        this.weight = product.weight;
        this.units = product.units;
        this.size = clothes.size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Clothes clothes = (Clothes) o;
        return Objects.equals(size, clothes.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "name='" + name + '\'' +
                " productId=" + productId +
                " pricePerUnit=" + pricePerUnit +
                " producer=" + producer +
                " weight=" + weight +
                " units=" + units +
                " size='" + size + '\'' +
                '}';
    }
}

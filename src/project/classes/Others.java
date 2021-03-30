package project.classes;

import java.util.Objects;

public class Others extends Product{
    private String category;

    public Others(String name, double pricePerUnit, Producer producer, double weight, double units, String category) {
        super(name, pricePerUnit, producer, weight, units);
        this.category = category;
    }

    public Others(){
        super();
        this.category = null;
    }

    public Others(Product product){
        Others others = (Others) product;
        this.name = product.name;
        this.pricePerUnit = product.pricePerUnit;
        this.producer = product.producer;
        this.weight = product.weight;
        this.units = product.units;
        this.category = others.category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Others others = (Others) o;
        return Objects.equals(category, others.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category);
    }

    @Override
    public String toString() {
        return "Others{" +
                "name='" + name + '\'' +
                ", pricePerUnit=" + pricePerUnit +
                ", producer=" + producer +
                ", weight=" + weight +
                ", units=" + units +
                ", category='" + category + '\'' +
                '}';
    }
}

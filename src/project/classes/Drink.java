package project.classes;

import java.util.Objects;

public class Drink extends Product{
    public double alcohol;

    public Drink(String name, double pricePerUnit, Producer producer, double weight, double units, double alcohol) {
        super(name, pricePerUnit, producer, weight, units);
        this.alcohol = alcohol;
    }

    public Drink() {
        super();
        this.alcohol = 0;
    }

    public Drink(Product product){
        Drink drink = (Drink) product;
        this.name = product.name;
        this.pricePerUnit = product.pricePerUnit;
        this.producer = product.producer;
        this.weight = product.weight;
        this.units = product.units;
        this.alcohol = drink.alcohol;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public boolean alcoholic(){
        return alcohol > 0;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                " productId=" + productId +
                " pricePerUnit=" + pricePerUnit +
                " producer=" + producer +
                " weight=" + weight +
                " units=" + units +
                " alcohol=" + alcohol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Drink drink = (Drink) o;
        return Double.compare(drink.alcohol, alcohol) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), alcohol);
    }
}

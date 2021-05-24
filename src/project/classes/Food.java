package project.classes;

import java.util.List;
import java.util.Objects;

public class Food extends Product{
    private int calories;
    private List<String> ingredients;

    public Food(String name, double pricePerUnit, Producer producer, double weight, double units, int calories, List<String> ingredients, double commercialExcess) {
        super(name, pricePerUnit, producer, weight, units, commercialExcess);
        this.calories = calories;
        this.ingredients = ingredients;
    }

    public Food(String name, double pricePerUnit, Producer producer, double weight, double units, int calories, List<String> ingredients) {
        super(name, pricePerUnit, producer, weight, units);
        this.calories = calories;
        this.ingredients = ingredients;
    }

    public Food(int id, String name, double pricePerUnit, Producer producer, double weight, double units, int calories, List<String> ingredients, double commercialExcess) {
        super(name, pricePerUnit, producer, weight, units, commercialExcess);
        this.productId = id;
        this.calories = calories;
        this.ingredients = ingredients;
    }

    public Food(Product food2){
        Food food = (Food) food2;
        this.name = food.name;
        this.pricePerUnit = food.pricePerUnit;
        this.producer = food.producer;
        this.weight = food.weight;
        this.units = food.units;
        this.calories = food.calories;
        this.ingredients = food.ingredients;
    }

    public Food() {
        super();
        this.calories = 0;
        ingredients = null;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public int nrCorrectIngredients(List<String> l){
        int nr = 0;
        for (String x:ingredients){
            if (ingredients.contains(x)){
                nr++;
            }
        }
        return nr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return calories == food.calories && Objects.equals(ingredients, food.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), calories, ingredients);
    }

    @Override
    public String toString() {
        return "Food{" +
                "productId=" + productId +
                " name='" + name + '\'' +
                " pricePerUnit=" + pricePerUnit +
                " producer=" + producer +
                " weight=" + weight +
                " units=" + units +
                " calories=" + calories +
                " Ingredients=" + ingredients.toString().replace(",", "") +
                '}';
    }
}

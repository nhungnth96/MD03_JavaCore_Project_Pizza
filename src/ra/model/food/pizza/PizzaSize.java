package ra.model.food.pizza;

public enum PizzaSize {
    SMALL(10),
    MEDIUM(15),
    LARGE(20);

    private double price;

    PizzaSize(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

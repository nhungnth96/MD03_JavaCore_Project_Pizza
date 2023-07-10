package ra.model.food;

public enum PizzaSize {
    SMALL(0),
    MEDIUM(88.5),
    LARGE(78.5);

    private double price;

    PizzaSize(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

//    SMALL,MEDIUM,LARGE
}

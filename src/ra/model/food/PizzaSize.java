package ra.model.food;

public enum PizzaSize {
    SMALL(),
    MEDIUM(88500),
    LARGE(167000);

    private double price;

    PizzaSize() {
    }

    PizzaSize(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

//    SMALL,MEDIUM,LARGE
}

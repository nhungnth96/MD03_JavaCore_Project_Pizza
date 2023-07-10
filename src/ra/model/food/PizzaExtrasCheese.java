package ra.model.food;

public enum PizzaExtrasCheese {
    // extra,double,triple + price => toal: pizza price + extras
    NONE(0),
    EXTRA_CHEESE(24.5),
    DOUBLE_CHEESE(44.5),
    TRIPLE_CHEESE(64);

    private double price;

    PizzaExtrasCheese(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
//    NONE,EXTRA_CHEESE, DOUBLE_CHEESE,TRIPLE_CHEESE
}

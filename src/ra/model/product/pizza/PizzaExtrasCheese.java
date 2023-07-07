package ra.model.product.pizza;

public enum PizzaExtrasCheese {
    // extra,double,triple + price => toal: pizza price + extras
    NONE(0),
    EXTRA_CHEESE(3),
    DOUBLE_CHEESE(5),
    TRIPLE_CHEESE(7);

    private double price;

    PizzaExtrasCheese(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

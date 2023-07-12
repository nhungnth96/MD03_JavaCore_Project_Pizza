package ra.model.order;

public enum ShippingMethod {
    DELIVERY(50000),
    TAKE_AWAY(0);
    private double price;


    ShippingMethod(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

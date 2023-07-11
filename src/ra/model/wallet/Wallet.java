package ra.model.wallet;

public class Wallet {
    private double money;
    public Wallet() {
    }
    public Wallet(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

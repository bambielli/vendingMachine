/**
 * Created by bambielli on 10/30/16.
 */
public enum Item {
    COKE("Coke", 25), PEPSI("Pepsi", 30), SPRITE("Sprite", 34);
    private String name;
    private int price;

    Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String toString() {
        return getName();
    }
}

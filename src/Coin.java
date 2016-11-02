/**
 * Created by bambielli on 10/30/16.
 */
public enum Coin {
    PENNY("Penny", 1), NICKLE("Nickle", 5), DIME("Dime", 10), QUARTER("Quarter", 25);

    private int value;
    private String name;

    Coin(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }
}

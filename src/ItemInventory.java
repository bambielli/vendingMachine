import java.util.HashMap;

/**
 * Created by bambielli on 10/30/16.
 */
public class ItemInventory extends Inventory<Item> {
    ItemInventory() {
        super("ItemInventory");
    }
    ItemInventory(HashMap<Item, Integer> map) {
        super("ItemInventory", map);
    }
}

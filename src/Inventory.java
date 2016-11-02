import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by bambielli on 10/30/16.
 *
 * Inventories keep track of counts for each item contained in the machine.
 * Inventories can return the count for a particular item.
 * Inventories can be restocked with a particular item.
 * Inventories can deduct an item and return it to the caller.
 *
 * If an item with 0 inventory tries to be deducted, it will throw an exception
 * If count for an item is asked for that does not exist, it will throw an exception
 *
 */
public abstract class Inventory<T> {

    private HashMap<T,Integer> inventoryMap;
    private String inventoryName;

    Inventory () {
        this.inventoryName = "Generic Inventory";
        this.inventoryMap = new HashMap<>();
    }

    Inventory (String inventoryName) {
        this.inventoryName = inventoryName;
        this.inventoryMap = new HashMap<>();
    }

    Inventory (String inventoryName, HashMap<T, Integer> mapT) {
        this.inventoryName = inventoryName;
        this.inventoryMap = mapT;
    }

    public HashMap<T, Integer> getInventoryMap() {
        return inventoryMap;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public Integer getCountForItem(T item) throws NoSuchElementException {
        Integer count = inventoryMap.get(item);

        if (count == null) {
            throw new NoSuchElementException("Inventory " + this.getInventoryName() + " does not contain " + item.toString());
        } else {
            return count;
        }
    }

    /*
    * Adds "count" number of items to the inventory for the particular item.
    * If the item didn't exist before, the inventory list will add the item
    * to the inventoryMap.
    * */
    public void addToInventory(T item, Integer count) {
       if (inventoryMap.containsKey(item)) {
           Integer oldVal = inventoryMap.get(item);
           inventoryMap.put(item, oldVal + count);
       } else {
           inventoryMap.put(item, count);
       }
    }

    public T deductItem(T item) throws NoSuchElementException{
        if (inventoryMap.containsKey(item)) {
            Integer oldVal = inventoryMap.get(item);
            if (oldVal > 0) {
                inventoryMap.put(item, oldVal - 1);
                return item;
            } else {
                throw new NoSuchElementException("Inventory " + this.getInventoryName() + " does not contain " + item.toString());
            }
        } else {
            throw new NoSuchElementException("Inventory " + this.getInventoryName() + " does not contain " + item.toString());

        }
    }

    public String toString() {
        String returnString = inventoryName;
        for(HashMap.Entry<T, Integer> entry : inventoryMap.entrySet()) {
            returnString += "(" + entry.getKey() + "," + entry.getValue() + ")";
        }
        return returnString;
    }

}

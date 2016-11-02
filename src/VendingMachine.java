import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by bambielli on 10/30/16.
 */
public class VendingMachine {
    private final ItemInventory itemInventory;
    private final CoinBox coinBox;

    VendingMachine() {
        this.itemInventory = new ItemInventory();
        this.coinBox = new CoinBox();
    }

    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine();
        for(Coin c : Coin.values()) {
            vm.coinBox.addToInventory(c, 5);
        }
        for(Item i : Item.values()) {
            vm.itemInventory.addToInventory(i, 1);
        }
        ArrayList<Coin> sufficientPaymentExactChange = new ArrayList<>();
        sufficientPaymentExactChange.add(Coin.DIME);
        sufficientPaymentExactChange.add(Coin.QUARTER);
        Pair<Item, ArrayList<Coin>> p = vm.purchaseItem(sufficientPaymentExactChange, Item.SPRITE);
        System.out.println("Should be sprite: " + p.fst.getName());
        System.out.println("Should be 1 change returned: " + p.snd.size());
        System.out.println("Should be 6 QUARTERS: " + vm.coinBox.getInventoryMap().get(Coin.QUARTER));
        System.out.println("Should be 6 DIMES: " + vm.coinBox.getInventoryMap().get(Coin.DIME));
        System.out.println("Should be 5 NICKLES: " + vm.coinBox.getInventoryMap().get(Coin.NICKLE));
        System.out.println("Should be 4 PENNYS: " + vm.coinBox.getInventoryMap().get(Coin.PENNY));


        ArrayList<Coin> insufficientPayment = new ArrayList<>();
        insufficientPayment.add(Coin.DIME);
        insufficientPayment.add(Coin.DIME);
        Pair<Item, ArrayList<Coin>> p2 = vm.purchaseItem(insufficientPayment, Item.COKE);
        System.out.println("Should be null: " + p2.fst);
        System.out.println("Should be 2 change returned: " + p2.snd.size());
        System.out.println("Should be 6 Quarters: " + vm.coinBox.getInventoryMap().get(Coin.QUARTER));
        System.out.println("Should be 6 DIMES: " + vm.coinBox.getInventoryMap().get(Coin.DIME));
        System.out.println("Should be 5 NICKLES: " + vm.coinBox.getInventoryMap().get(Coin.NICKLE));
        System.out.println("Should be 4 PENNYS: " + vm.coinBox.getInventoryMap().get(Coin.PENNY));

        ArrayList<Coin> sufficientPaymentInexactChange = new ArrayList<>();
        sufficientPaymentInexactChange.add(Coin.QUARTER);
        sufficientPaymentInexactChange.add(Coin.QUARTER);
        Pair<Item, ArrayList<Coin>> p3 = vm.purchaseItem(sufficientPaymentInexactChange, Item.PEPSI);
        System.out.println("Should be Pepsi: " + p3.fst.getName());
        System.out.println("Should be 2 change returned: " + p3.snd.size());
        System.out.println("Should be 8 QUARTERS: " + vm.coinBox.getInventoryMap().get(Coin.QUARTER));
        System.out.println("Should be 4 DIMES: " + vm.coinBox.getInventoryMap().get(Coin.DIME));
        System.out.println("Should be 5 NICKLES: " + vm.coinBox.getInventoryMap().get(Coin.NICKLE));
        System.out.println("Should be 4 PENNIES: " + vm.coinBox.getInventoryMap().get(Coin.PENNY));



    }

    public Pair<Item, ArrayList<Coin>> purchaseItem(ArrayList<Coin> coins, Item item) {
        try {
            ArrayList<Coin> change = this.coinBox.payAndGetChange(coins, item.getPrice());
            Item vendedItem = this.itemInventory.deductItem(item);
            return new Pair(vendedItem, change);
        } catch (NoSuchElementException e) {
            //these come from itemInventory, and mean the item inventory is out of stock for a particular item.
            System.out.println(e.getMessage());
            return new Pair(null, coins);
        } catch (RuntimeException e) {
            //these come from coinBox, and mean the user either didn't pay enough
            //or we dont' have correct change.
            System.out.println(e.getMessage());
            return new Pair(null, coins);
        }
    }
}

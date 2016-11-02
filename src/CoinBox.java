import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bambielli on 10/30/16.
 */
public class CoinBox extends Inventory<Coin> {
    CoinBox() {
        super("CoinInventory");
    }
    CoinBox(HashMap<Coin, Integer> map) {
        super("CoinInventory", map);
    }

    public int getIntegerFromCoinsList(ArrayList<Coin> payment) {
        return payment.stream()
                      .mapToInt(Coin::getValue)
                      .sum();
    }
    /*
    * This method returns coins to be included in the payment, largest first.
    * It may or may not return a set of coins that satisfies the full payment
    * */
    public Pair<ArrayList<Coin>, Integer> getChangeFromPayment(ArrayList<Coin> paymentArray, int changeToMake) {
        List<Coin> sortedPayment = paymentArray.stream()
                                               .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                                               .collect(Collectors.toList());

        ArrayList<Coin> returnList = new ArrayList<>();
        int changeRemaining = changeToMake;
        for (Coin c : sortedPayment) {
            if (c.getValue() <= changeRemaining) {
                returnList.add(c);
                changeRemaining -= c.getValue();
            }
            if (changeRemaining == 0) {
                break;
            }
        }
        //this should be an array of the change. We try to use coins from
        //the array of coins provided by the user first, then go to the change
        //box for the rest
        return new Pair(returnList, changeRemaining);
    }

    /*
    * Get change of a specific value out of inventory
    * */
    public Pair<ArrayList<Coin>, Integer> getCoinsFromInventory(int value) throws RuntimeException {
        //sort the hashMap
        HashMap<Coin, Integer> inventoryMap = this.getInventoryMap();
        ArrayList<Coin> coinArray = inventoryMap.keySet().stream()
                                                         .sorted((a,b) -> Integer.compare(b.getValue(), a.getValue()))
                                                         .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Coin> returnArray = new ArrayList<>();
        int valueToGo = value;

        //pull out change that will
        for (Coin c : coinArray) {

            while (c.getValue() <= valueToGo && this.getInventoryMap().get(c) > 0) {
                returnArray.add(this.deductItem(c));
                valueToGo -= c.getValue();
            }
            if (valueToGo == 0) {
                break;
            }
        }
            return new Pair(returnArray, valueToGo);
    }

    /*
    * returns an array of coins that equates to the change from the payment provided
    * */
    public ArrayList<Coin> payAndGetChange(ArrayList<Coin> paymentArray, int cost) throws RuntimeException {
        int paymentInt = getIntegerFromCoinsList(paymentArray);
        if (paymentInt < cost) {
            throw new RuntimeException("User Didn't Pay Enough For Item");
        } else if (paymentInt == cost) {
            //deposit coins, return no change.
            depositCoins(paymentArray);
            return new ArrayList<>();
        } else {
            //else there is some change that needs to be made.
            int changeToMake = paymentInt - cost;
            Pair<ArrayList<Coin>, Integer> change = getChangeFromPayment(paymentArray, changeToMake);
            if(change.snd == 0) {
                //this means we don't need to get any more coins from changeBox. Diff the
                //array with the input coins list, deposit those, and return the change.fst
                paymentArray.removeAll(change.fst);
                depositCoins(paymentArray);
                return change.fst;
            } else {
                //diff the array with the input list (paymentarray) and hold
                //try to merge coins from inventory with the change.fst.
                //if that works, deposit the diff and return change.fst
                Pair<ArrayList<Coin>, Integer> fromInventory = getCoinsFromInventory(change.snd);
                if (fromInventory.snd == 0) {
                    //we had the correct change in inventory. Merge the lists and return the list.
                    paymentArray.removeAll(change.fst);
                    depositCoins(paymentArray);
                    change.fst.addAll(fromInventory.fst);
                    return change.fst;
                } else {
                    throw new RuntimeException("Insufficient Change in Changebox. Please Reload.");
                }
            }
        }
    }

    /*
    * Deposits the coins in a coin list to the CoinBox
    * */
    private void depositCoins(ArrayList<Coin> coinsToDeposit) {
        for(Coin c : coinsToDeposit) {
            this.addToInventory(c, 1);
        }
    }
}

package controller;

import java.util.ArrayList;
import java.util.Random;
import model.PokerCard;
import model.Suits;
import view.BlackjackTable;

/**
 * @author Tony Peng
 * @version 7/6/18
 */
public class PokerController {

    private BlackjackTable table;
    /**
     * constructor for pokerController
     * @param table the blackjackTable to change
     */
    public PokerController(BlackjackTable table) {
        this.table = table;
    }
    /**
     * count the total of the card in the arraylist
     * @param arr the list to be counted
     * @return return the total
     */
    public int countTotal(ArrayList<PokerCard> arr) {
        int aCount = 0, total = 0;
        for (int i = 0; i < arr.size(); i++) {
            int temp = arr.get(i).getNum();
            if (temp == 1) {
                aCount += 1;
                total += 11;
            } else if (temp == 11) {
                total += 10;
            } else if (temp == 12) {
                total += 10;
            } else if (temp == 13) {
                total += 10;
            } else {
                total += temp;
            }
        }
        if (!isBusted(total)) {
            return total;
        }
        while (aCount != 0) {
            total -= 10;
            aCount--;
            if (!isBusted(total)) {
                return total;
            }
        }
        return total;

    }
    /**
     * reset everything in the blackjackTable
     */
    public void reset() {
        table.getPlayerCard().clear();
        table.getHostCard().clear();
        table.getCardDealt().clear();
        table.getPlayerBox().getChildren().clear();
        table.getHostBox().getChildren().clear();
        table.getStatusText().setText("");
    }
    /**
     * returns the card and add it to arraylist
     * @param player the specific play's hand
     * @param cardDealt the arraylist to keep track of the cards dealt
     * @return a random card that is not dealt
     */
    public PokerCard dealCard(ArrayList<PokerCard> player,
                              ArrayList<PokerCard> cardDealt) {
        Random rd = new Random();
        Suits[] arr = Suits.values();
        PokerCard card = new PokerCard(rd.nextInt(13) + 1, arr[rd.nextInt(4)]);
        while (cardDealt.contains(card)) {
            card = new PokerCard(rd.nextInt(13) + 1, arr[rd.nextInt(4)]);
        }
        player.add(card);
        cardDealt.add(card);
        return card;
    }
    /**
     * check if number is greater than 21
     * @param n the number to be checked upon
     * @return if the number is greater than 21
     */
    public boolean isBusted(int n) {
        return n > 21;
    }
}

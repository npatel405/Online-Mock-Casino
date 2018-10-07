package model;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.util.Pair;
/**
 * @author Tony Peng
 * @version 7/6/18
 */
public class PokerDeck {

    private static HashMap<Pair<Integer, Suits>, Image> deck = new HashMap<>();
    private PokerDeck() {
    }
    /**
     * initialize the deck
     */
    public static void initialize() {
        Suits[] arr = Suits.values();
        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                deck.put(new Pair<>(i, arr[j]),
                        new Image(("file:images/card/" + i
                                + arr[j]).toLowerCase() + ".png" ,
                                90, 140, false , false));
            }
        }
    }
    /**
     * return the image based on the pair
     * @param num the number of the card
     * @param suit the suit of the card
     * @return the image based on the num and suit
     */
    public static Image getImageSource(int num, Suits suit) {
        return deck.get(new Pair<>(num, suit));
    }
    /**
     * get the cover of the card if needed
     * @return the cover of the card
     */
    public static Image getCoverCard() {
        return new Image("file:images/card/back.png", 90, 140, false, false);
    }
}

package model;

import javafx.scene.image.Image;

/**
 * @author Tony Peng
 * @version 7/6/18
 */
public class PokerCard {
    private Suits suit;
    private int num;
    /**
     * constructor for the Card
     * @param num the number of the card
     * @param suit the suit of the card
     */
    public PokerCard(int num, Suits suit) {
        this.num = num;
        this.suit = suit;
    }
    /**
     * getter for num
     * @return the number of the card
     */
    public int getNum() {
        return num;
    }
    /**
     * getter for num
     * @return the number of the card
     */
    public Suits getSuit() {
        return suit;
    }
    /**
     * getter for num
     * @return the number of the card
     */
    public Image getImage() {
        return PokerDeck.getImageSource(num, suit);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof PokerCard && ((PokerCard) other).num
                == num && ((PokerCard) other).suit == suit;
    }
    @Override
    public String toString() {
        return num + " of " + suit;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

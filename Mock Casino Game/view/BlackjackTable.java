package view;
import java.util.ArrayList;
import controller.PokerController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.PokerCard;
import model.PokerDeck;
import runner.CasinoGamesRunner;

/**
 * @author Npatel405 (Niket Patel)
 * @version 1.0
 */
public class BlackjackTable extends FlowPane {

    private ArrayList<PokerCard> playerCard = new ArrayList<>();
    private ArrayList<PokerCard> hostCard = new ArrayList<>();
    private HBox playerBox = new HBox(30);
    private HBox hostBox = new HBox(30);
    private HBox count = new HBox(30);
    private Text status = new Text();
    private ArrayList<PokerCard> cardDealt = new ArrayList<>();
    private PokerController controller = new PokerController(this);
    private int playerMoney = 1000;
    private Label thecount = new Label("Card Count: ");
    private Button backBtn;
    private Button betBtn;
    private Button hitBtn;
    private Button stayBtn;
    private Button resetBtn;
    private TextField textField2 = new TextField();
    private int betTracker;
    private Alert alert = new Alert(AlertType.INFORMATION);
    private Label yourcard = new Label("Your card ");
    private Label othercard = new Label("Dealer card");
    private Label label1 = new Label("Bet Amount:");
//    private Rectangle stage = new Rectangle(950, 550);
    private VBox master = new VBox(30);
    private ImageView cardBack = new ImageView(PokerDeck.getCoverCard());
    private ImageView tempholder = new ImageView();

    /**
     * Extra credit BlackjackTable constructor
     * Complete the constructor for BlackjackTable.
     * Should initialize screen.
     */
    public BlackjackTable() {
        PokerDeck.initialize();

        // the stage
        this.setStyle("-fx-background-image: url(\"file:images/table.jpg\"); "
            + "-fx-background-position: center center; "
            + "-fx-background-repeat: stretch;");
      //  this.getChildren().addAll(stage);

        HBox bottomPanel = new HBox(30);
        backBtn = new Button("Back");
        backBtn.setOnAction(n -> {
                Scene scene = new Scene(new ChooseMenu(),
                    CasinoGamesRunner.WIDTH, CasinoGamesRunner.HEIGHT);
                CasinoGamesRunner.getStage().setScene(scene);
            });

        // Add buttons to the bottom panel and display
        bottomPanel.getChildren().addAll(backBtn);
        bottomPanel.setPrefWidth(900);
        this.getChildren().addAll(bottomPanel);

        // Money Display
        Label moneyDisplay = new Label("Money:");
        moneyDisplay.setTextFill(Color.WHITE);
        bottomPanel.getChildren().addAll(moneyDisplay, textField2);
        textField2.setDisable(true);
        textField2.setText((Integer.toString(getPlayerMoney())));
        textField2.setEditable(false);

        // Betting Box
        betBtn = new Button("Bet");
        TextField textField = new TextField();
        betBtn.setOnAction(n -> {
                try {
                    if (textField.getText() != null
                        && Integer.parseInt(textField.getText())
                        <= getPlayerMoney()
                        && Integer.parseInt(textField.getText()) > 0) {
                        betTracker = Integer.parseInt(textField.getText());
                        textField.clear();
                        betBtn.setDisable(true);
                        hitBtn.setDisable(false);
                        stayBtn.setDisable(false);
                        startGame();
                    } else {
                        textField.setText("Not enough Money");
                    }
                } catch (Exception e) {
                    textField.setText("Not a valid input");
                }
            });
        bottomPanel.getChildren().addAll(label1, textField);
        bottomPanel.getChildren().addAll(betBtn);
        bottomPanel.setSpacing(20);
        playerBox.setPrefWidth(900);
        hostBox.setPrefWidth(900);
        master.getChildren().addAll(playerBox, count, hostBox);
        playerBox.setAlignment(Pos.TOP_CENTER);
        hostBox.setAlignment(Pos.CENTER);
       // count.setAlignment(Pos.CENTER);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(master);
        this.getChildren().addAll(stack);

        //reset button
        resetBtn = new Button("Reset");
        resetBtn.setDisable(true);
        resetBtn.setOnAction(n -> {
                playerBox.getChildren().clear();
                hostBox.getChildren().clear();
                betBtn.setDisable(false);
                resetBtn.setDisable(true);
                hitBtn.setDisable(true);
                stayBtn.setDisable(true);
                controller.reset();
                cardBack.setImage(PokerDeck.getCoverCard());
                count.getChildren().clear();
            });

        // hit button
        hitBtn = new Button("Hit");
        hitBtn.setDisable(true);
        hitBtn.setOnAction(n -> {
                playerBox.getChildren().add(deal(0));
                status.setText(getCount(playerCard));
                if (controller.countTotal(playerCard) == 21) {
                    hitBtn.setDisable(true);
                    stayBtn.setDisable(true);
                    resetBtn.setDisable(false);
                    cardBack.setImage(tempholder.getImage());
                    concludeGame();
                }
                if (controller.isBusted(controller.countTotal(playerCard))) {
                    hitBtn.setDisable(true);
                    stayBtn.setDisable(true);
                    resetBtn.setDisable(false);
                    cardBack.setImage(tempholder.getImage());
                    concludeGame();
                }
            });

        //stay button
        stayBtn = new Button("Stay");
        stayBtn.setDisable(true);
        stayBtn.setOnAction(n -> {
                hitBtn.setDisable(true);
                resetBtn.setDisable(false);
                stayBtn.setDisable(true);
                cardBack.setImage(tempholder.getImage());
                concludeGame();
            });
        bottomPanel.getChildren().addAll(resetBtn, hitBtn, stayBtn);

        //pop up info
        alert.setHeaderText("The results are in!");
        alert.setResizable(true);
        alert.setTitle("Results");
        status.setFill(Color.WHITE);
        thecount.setTextFill(Color.WHITE);
        yourcard.setTextFill(Color.WHITE);
        othercard.setTextFill(Color.WHITE);
        label1.setTextFill(Color.WHITE);
        playerBox.setAlignment(Pos.CENTER);
    }

    /**
     *  Starts a game of Blackjack, extra credit
     *  Implement logic to begin a game of Blackjack
     */
    public void startGame() {
        playerBox.getChildren().add(yourcard);
        playerBox.getChildren().addAll(
                new ImageView(controller.dealCard(playerCard,
                cardDealt).getImage()),
                new ImageView(controller.dealCard(playerCard,
                cardDealt).getImage())
        );
        status.setText(getCount(playerCard));
        count.getChildren().addAll(thecount, status);
        hostBox.getChildren().addAll(othercard, cardBack);
        tempholder.setImage(controller.dealCard(hostCard,
            cardDealt).getImage());
        hostBox.getChildren().add(deal(1));
        if (controller.countTotal(playerCard) == 21) {
            resetBtn.setDisable(false);
            hitBtn.setDisable(true);
            stayBtn.setDisable(true);
            concludeGame();
        }

    }
    /**
     * deals the card to the person based on the input.
     * @param target deals to player if target is 0
     * deals to host if target is 1, extra credit
     * Implement logic to deal to a particular target
     * @return the image of the card
     */
    public ImageView deal(int target) {
        if (target == 0) {
            return new ImageView(controller.dealCard(playerCard,
            cardDealt).getImage());
        } else {
            return new ImageView(controller.dealCard(hostCard,
                cardDealt).getImage());
        }
    }


    /**
     * When Dealer is finished with drawing card,
     * determining the result of the game, extra credit
     * Implement logic to end a game of Blackjack
     */
    public void concludeGame() {
        if (!(controller.isBusted(controller.countTotal(playerCard)))) {
            while (controller.countTotal(hostCard) < 16) {
                hostBox.getChildren().add(deal(1));
            }
        }
        cardBack.setImage(tempholder.getImage());
        if ((!(controller.isBusted(controller.countTotal(playerCard))))
            && (controller.isBusted(controller.countTotal(hostCard)))) {
            //win
            setPlayerMoney(getPlayerMoney() + (betTracker));
            textField2.setText((Integer.toString(getPlayerMoney())));
            alert.setContentText("You Win!");
            alert.showAndWait();
        } else if ((!(controller.isBusted(controller.countTotal(hostCard))))
            && (controller.isBusted(controller.countTotal(playerCard)))) {
            //lose
            setPlayerMoney(getPlayerMoney() - betTracker);
            textField2.setText((Integer.toString(getPlayerMoney())));
            if (getPlayerMoney() == 0) {
                resetBtn.setDisable(true);
            }
            alert.setContentText("Ouch! You Lose!");
            alert.showAndWait();
        } else if (((controller.isBusted(controller.countTotal(playerCard))))
            && (controller.isBusted(controller.countTotal(hostCard)))) {
            //both bust
            setPlayerMoney(getPlayerMoney());
            alert.setContentText("Draw!");
            alert.showAndWait();
        } else {
            if (controller.countTotal(playerCard)
                > controller.countTotal(hostCard)) {
                //win
                setPlayerMoney(getPlayerMoney() + (betTracker));
                textField2.setText((Integer.toString(getPlayerMoney())));
                alert.setContentText("You Win!");
                alert.showAndWait();
            } else if (controller.countTotal(playerCard)
                == controller.countTotal(hostCard)) {
                //draw
                setPlayerMoney(getPlayerMoney());
                alert.setContentText("Draw!");
                alert.showAndWait();
            } else {
                //lose
                setPlayerMoney(getPlayerMoney() - betTracker);
                textField2.setText((Integer.toString(getPlayerMoney())));
                if (getPlayerMoney() == 0) {
                    resetBtn.setDisable(true);
                }
                alert.setContentText("Ouch! You Lose!");
                alert.showAndWait();
            }
        }
    }

    /**
     * getter for playerCard
     * @return the ArrayList that keeps track of player's hand
     */
    public ArrayList<PokerCard> getPlayerCard() {
        return playerCard;
    }
    /**
     * getter for hostCard
     * @return the ArrayList that keeps track of dealer's hand
     */
    public ArrayList<PokerCard> getHostCard() {
        return hostCard;
    }
    /**
     * getter for carDealt
     * @return the ArrayList that keeps track of card that is dealt
     */
    public ArrayList<PokerCard> getCardDealt() {
        return cardDealt;
    }
    /**
     * getter for playerBox
     * @return the HBox that holds info for player
     */
    public HBox getPlayerBox() {
        return playerBox;
    }
    /**
     * getter for hostBox
     * @return the HBox that holds info for host
     */
    public HBox getHostBox() {
        return hostBox;
    }
    /**
     * getter for status
     * @return the Text that is showing the status of the game
     */
    public Text getStatusText() {
        return status;
    }
    /**
     * @return the playerMoney
     */
    public int getPlayerMoney() {
        return playerMoney;
    }

    /**
     * Gets the count and converts it
     * into a String
     * @param arr
     * array of the cards in hand
     * @return the count as a string
     */

    public String getCount(ArrayList<PokerCard> arr) {
        return Integer.toString(controller.countTotal(arr));
    }
    /**
     * @param playerMoney the playerMoney to set
     */
    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

}


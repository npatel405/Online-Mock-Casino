package view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.PokerCard;
import model.PokerDeck;
import runner.CasinoGamesRunner;
import java.util.Random;
import model.Suits;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * @author npatel405 (Niket Patel)
 * @version 1.0
 */
public class WarTable extends FlowPane {
    private PokerCard playerCard;
    private PokerCard dealerCard;
    private int playerMoney = 1000;
    private int betTracker;
    private Button backBtn;
    private Button betBtn;
    private Button resetBtn;
    private  HBox bottomPanel = new HBox(30);
    private  HBox playerPanel = new HBox(30);
    private  HBox computerPanel = new HBox(30);
    private TextField textField2 = new TextField();
    private ImageView playerholder = new ImageView();
    private ImageView otherholder = new ImageView();
    private Alert alert = new Alert(AlertType.INFORMATION);
    private Label yourcard = new Label("Your card ");
    private Label othercard = new Label("Dealer card");
    private Rectangle stage = new Rectangle(950, 550);

    /**
     * Complete the constructor for WarTable. Should initialize screen.
     */
    public WarTable() {
        // Initialize the poker deck, takes a moment.
        PokerDeck.initialize();

        // Complimentary HBox for holding the back button
        backBtn = new Button("Back");
        // This is a lambda, you'll need to use them for buttons.
        // Having provided you with examples for enter and back buttons,
        // you should be able to follow the pattern fairly well.
        // btn.setOnAction(n -> { METHOD CALL OR CODE HERE});
        backBtn.setOnAction(n -> {
                Scene scene = new Scene(new ChooseMenu(),
                    CasinoGamesRunner.WIDTH, CasinoGamesRunner.HEIGHT);
                CasinoGamesRunner.getStage().setScene(scene);
            });

        // Add buttons to the bottom panel and display
        bottomPanel.getChildren().addAll(backBtn);
        bottomPanel.setPrefWidth(900);
        this.getChildren().addAll(bottomPanel);

       //stage
        stage.setFill(Color.GREEN);
        this.getChildren().addAll(stage);

      //money display
        Label moneyDisplay = new Label("Money:");
        bottomPanel.getChildren().addAll(moneyDisplay, textField2);
        textField2.setDisable(true);
        textField2.setText((Integer.toString(getPlayerMoney())));
        textField2.setEditable(false);

        // betting box
        Label label1 = new Label("Bet Amount:");
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
                        resetBtn.setDisable(false);
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
        bottomPanel.setSpacing(10);
        // dealing
        ImageView cardBack = new ImageView(PokerDeck.getCoverCard());
        ImageView cardBack2 = new ImageView(PokerDeck.getCoverCard());
        playerPanel.setPrefWidth(900);
        computerPanel.setPrefWidth(900);
        playerPanel.getChildren().addAll(yourcard, cardBack);
        computerPanel.getChildren().addAll(othercard, cardBack2);
//  this.getChildren().addAll(computerPanel);
//  this.getChildren().addAll(playerPanel);

        playerPanel.setAlignment(Pos.TOP_CENTER);
        computerPanel.setAlignment(Pos.CENTER);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(stage, playerPanel, computerPanel);
        this.getChildren().addAll(stack);
        // reset button
        resetBtn = new Button("Reset");
        resetBtn.setDisable(true);
        resetBtn.setOnAction(n -> {
                playerPanel.getChildren().clear();
                computerPanel.getChildren().clear();
                playerPanel.getChildren().addAll(yourcard, cardBack);
                computerPanel.getChildren().addAll(othercard, cardBack2);
                betBtn.setDisable(false);
                resetBtn.setDisable(true);
            });
        bottomPanel.getChildren().addAll(resetBtn);

        //pop up info

        alert.setHeaderText("The results are in!");
        alert.setResizable(true);
        alert.setTitle("Results");
        //stage

        yourcard.setTextFill(Color.WHITE);
        othercard.setTextFill(Color.WHITE);
    }
    /**
     * Implement logic to begin War card game
     */
    public void startGame() {
        deal();
    }
    /**
     * Implement logic to deal one card each to dealer and player
     * HINT: look at the PokerController class in controller package!
     */
    public void deal() {
        Random rd = new Random();
        Suits[] arr = Suits.values();
        playerCard = new PokerCard(rd.nextInt(13) + 1, arr[rd.nextInt(4)]);
        dealerCard = new PokerCard(rd.nextInt(13) + 1, arr[rd.nextInt(4)]);
        if (!(playerCard.equals(dealerCard))) {
            computerPanel.getChildren().clear();
            playerPanel.getChildren().clear();
            playerPanel.getChildren().addAll(yourcard);
            computerPanel.getChildren().addAll(othercard);
            playerholder.setImage(playerCard.getImage());
            otherholder.setImage(dealerCard.getImage());
            playerPanel.getChildren().add(playerholder);
            computerPanel.getChildren().add(otherholder);
            concludeGame();
        } else {
            deal();
        }
    }
    /**
     * Implement logic to conclude a game of War and announce the winner!
     */
    public void concludeGame() {
        if (playerCard.getNum() > dealerCard.getNum()) {
            setPlayerMoney(getPlayerMoney() + (betTracker));
            textField2.setText((Integer.toString(getPlayerMoney())));
            alert.setContentText("You Win!");
            alert.showAndWait();
        } else if (playerCard.getNum() == dealerCard.getNum()) {
            setPlayerMoney(getPlayerMoney());
            alert.setContentText("Draw!");
            alert.showAndWait();
        } else {
            setPlayerMoney(getPlayerMoney() - betTracker);
            textField2.setText((Integer.toString(getPlayerMoney())));
            if (getPlayerMoney() == 0) {
                resetBtn.setDisable(true);
            }
            alert.setContentText("Ouch! You Lose!");
            alert.showAndWait();
        }
    }

    /**
     * @return the playerCard
     */
    public PokerCard getPlayerCard() {
        return playerCard;
    }

    /**
     * @param playerCard the playerCard to set
     */
    public void setPlayerCard(PokerCard playerCard) {
        this.playerCard = playerCard;
    }

    /**
     * @return the dealerCard
     */
    public PokerCard getDealerCard() {
        return dealerCard;
    }

    /**
     * @param dealerCard the dealerCard to set
     */
    public void setDealerCard(PokerCard dealerCard) {
        this.dealerCard = dealerCard;
    }

    /**
     * @return the playerMoney
     */
    public int getPlayerMoney() {
        return playerMoney;
    }

    /**
     * @param playerMoney the playerMoney to set
     */
    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

}

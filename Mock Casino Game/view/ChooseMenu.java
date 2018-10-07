package view;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import runner.CasinoGamesRunner;

/**
 *@author Npatel405 (Niket Patel)
 *@version 1.0
 */
public class ChooseMenu extends BorderPane {

    private VBox blackjack = new VBox();
    private VBox war = new VBox();

    /**
     * choose menu constructor
     */
    public ChooseMenu() {
        this.setLeft(war);
        this.setRight(blackjack);
        setBlackJack();
        setWar();
        this.setStyle("-fx-background-image: url(\"file:images/tablefelt.jpg\"); "
            + "-fx-background-position: center center; "
            + "-fx-background-repeat: stretch;");

    }
    /**
     * Sets up the BlackJack portion of the menu
     */
    private void setBlackJack() {
        blackjack.setPrefSize(200, 400);
        Image img = new Image("file:images/cards.jpg",
                300, 300, false, false);
        ImageView imgView = new ImageView(img);
        Text text = new Text("Blackjack");
        text.setFill(Color.WHITE);
        Button btn = new Button("Enter");
        btn.setAlignment(Pos.CENTER);
        btn.setOnAction(n -> {
                Scene scene = new Scene(new BlackjackTable(),
                    CasinoGamesRunner.WIDTH, CasinoGamesRunner.HEIGHT);
                CasinoGamesRunner.getStage().setScene(scene);
            });
        blackjack.getChildren().addAll(imgView, text, btn);
    }

    /**
     * Sets up the War portion of the menu
     */
    private void setWar() {
        war.setPrefSize(200, 400);
        Image img = new Image("file:images/casino.jpg",
                300, 300, false, false);
        ImageView imgView = new ImageView(img);
        Text text = new Text("War");
        text.setFill(Color.WHITE);
        Button btn = new Button("Enter");
        btn.setOnAction(n -> {
                Scene scene = new Scene(new WarTable(),
                    CasinoGamesRunner.WIDTH, CasinoGamesRunner.HEIGHT);
                CasinoGamesRunner.getStage().setScene(scene);
            });
        war.getChildren().addAll(imgView, text, btn);
    }

}

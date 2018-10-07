package runner;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.ChooseMenu;

/**
 * @author Tony Peng
 * @version 7/6/18
 */
public class CasinoGamesRunner extends Application {

    public static final int WIDTH = 950;
    public static final int HEIGHT = 550;
    private static Stage stage;
    /**
     * ughh main method?
     * @param args i need this because java said so
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Dr. McDaniel's Casino");
        primaryStage.getIcons().add(new Image("file:casino.jpg"));
        ChooseMenu chooseMenu = new ChooseMenu();
        Scene scene = new Scene(chooseMenu, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * getter for the entire stage
     * @return the stage of the application
     */
    public static Stage getStage() {
        return stage;
    }
}

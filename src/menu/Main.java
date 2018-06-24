package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import media.Music;

/**
 * Główna klasa aplikacji
 */
public class Main extends Application {
    /**
     * Metoda start, wczytuje plik fxml i tworzy nową scenę.
     * Dodatkowo zaczyna odtwarzać muzykę w tle.
     * @param primaryStage Główne miejsce/okno na wczytywanie scen
     * @throws Exception Wyjątek rzucany gdy wystąpi błąd podczas tworzenia okna
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fXMLLoader = new FXMLLoader();
        fXMLLoader.setLocation(getClass().getResource("menu.fxml"));
        Parent root = fXMLLoader.load();
        primaryStage.setTitle("Floppy Bird");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
        MenuController menuController = fXMLLoader.getController();
        primaryStage.getScene().setUserData(menuController);
        menuController.setStage(primaryStage);
        new Music().start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package menu;

import game.Game;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import media.GameBackground;

/**
 * Kontroler dla menu
 */
public class MenuController {
    /**
     * Vertical Box dla przycisków start/exit
     */
    @FXML
    private VBox btns;
    /**
     * Główny panel menu
     */
    @FXML
    private AnchorPane root;
    /**
     * Główny stage
     */
    private Stage stage;

    /**
     * Podczas ładowania kontrolera dodaje tło
     */
    @FXML
    private void initialize() {
        addBackground();
    }

    /**
     * Tworzy lub pobiera istniejącą instancję klasy GameBackground i dodaje tło do głównego panelu
     */
    public void addBackground() {
        GameBackground bg = GameBackground.getInstance();
        root.getChildren().add(bg.getBackground1());
        root.getChildren().add(bg.getBackground2());
        btns.toFront();
    }

    /**
     * Po kliknięciu przycisku Exit kończy działania programu
     * @param mouseEvent Zdarzenie dla akcji wykonanej myszką
     */
    @FXML
    private void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }

    /**
     * Po kliknięciu przycisku start tworzy grę
     * @param mouseEvent Zdarzenie dla akcji wykonanej myszką
     */
    @FXML
    private void startGame(MouseEvent mouseEvent) {
        Game game = new Game(stage);
    }

    /**
     * Ustawia atrybut stage
     * @param stage
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}

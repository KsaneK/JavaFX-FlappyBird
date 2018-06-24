package game;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import media.GameBackground;
import media.Point;
import menu.MenuController;

import java.util.*;

/**
 * Główna klasa gry
 */
public class Game {
    /**
     * Miejsce w które ładujemy scenę
     */
    private final Stage stage;
    /**
     * Obiekt losujący pozycję dla rur
     */
    Random rand;
    /**
     * Obiekt gracza
     */
    Player player;
    /**
     * Lista przechowująca wszystkie rury
     */
    List<Pipe> pipes = new ArrayList<>();
    /**
     * Główny panel, na który nakłdamy inne obiekty
     */
    Pane root;
    /**
     * Okienko z tekstem i przyciskami do restartu/powrotu do menu
     */
    Pane restartPane;
    /**
     * Tekst wyświetlający się w górnym lewym rogu ekranu
     */
    Text txtScore;
    /**
     * Główny timer gry
     */
    AnimationTimer timer;
    /**
     * Timeline tworzący nowe rury co określony czas
     */
    Timeline pipe_creator;
    /**
     * Scena menu (wykorzystywana w przypadku powrotu do menu)
     */
    Scene menuScene;
    /**
     * Obiekt tła
     */
    GameBackground bg;

    /**
     * Konstruktor tworzy obiekt gracza, dodaje do głównego panelu tło, tekst z punktami oraz gracza
     * Dodaje obsługę klawiatury oraz automatycznie zaczyna grę
     * @param stage główny stage
     */
    public Game(Stage stage) {
        Point.loadMedia();
        rand = new Random();
        player = new Player();
        root = new Pane();

        this.menuScene = stage.getScene();
        this.stage = stage;

        txtScore = new Text(15, 30, "Points: 0");
        txtScore.setStyle("-fx-font-size: 24");

        bg = GameBackground.getInstance();

        stage.setScene(new Scene(root, 1000, 600));
        root.getChildren().add(bg.getBackground1());
        root.getChildren().add(bg.getBackground2());
        root.getChildren().add(player);
        root.getChildren().add(txtScore);
        stage.getScene().setOnKeyPressed(player::jump);

        start();
        addRestartDialog();
    }

    /**
     * Dodaje panel z tekstem oraz przyciskami służacymi do restartu gry lub powrotu do menu
     */
    private void addRestartDialog() {
        // Restart Pane
        restartPane = new Pane();
        restartPane.setPrefWidth(300);
        restartPane.setPrefHeight(150);
        restartPane.setTranslateX(350);
        restartPane.setTranslateY(200);
        restartPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        restartPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Button YES
        Button yesBtn = new Button("YES");
        yesBtn.setTranslateX(50);
        yesBtn.setTranslateY(100);
        yesBtn.setPrefWidth(50);
        yesBtn.setPrefHeight(30);
        yesBtn.setStyle(
                "-fx-background-color: LIGHTGREEN;" +
                        "-fx-font-size: 16;"
        );
        yesBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.restart();
                pipes.clear();
                bg.startMoving();
                timer.start();
                pipe_creator.play();
                root.getChildren().removeIf(obj -> obj instanceof Pipe);
                restartPane.setVisible(false);
                txtScore.setText("Points: 0");
            }
        });

        // Button back
        Button backBtn = new Button("Back to menu");
        backBtn.setTranslateX(102);
        backBtn.setTranslateY(100);
        backBtn.setPrefWidth(150);
        backBtn.setPrefHeight(30);
        backBtn.setStyle(
                "-fx-background-color: LIGHTGREEN;" +
                        "-fx-font-size: 16;"
        );
        backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bg.startMoving();
                stage.setScene(menuScene);
                ((MenuController)menuScene.getUserData()).addBackground();
            }
        });

        // Text
        Text text = new Text();
        text.setTranslateX(40);
        text.setTranslateY(60);
        text.setStyle(
                "-fx-font-size: 20;"
        );

        restartPane.getChildren().add(text);
        restartPane.getChildren().add(yesBtn);
        restartPane.getChildren().add(backBtn);

        restartPane.setVisible(false);
        root.getChildren().add(restartPane);
    }

    /**
     * Włącza timer, który aktualizuje pozycję gracza, porusza wszystkimi rurami i sprawdza czy wystąpiła kolizja
     * Dodatkowo co 1500ms wywołuje funkcję tworzenia nowych rur
     */
    public void start() {
        // Game timer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boolean stop = false;
                player.move();
                if(player.isOutsideScreen())
                    stop = true;
                for (Iterator<Pipe> iterator = pipes.iterator(); iterator.hasNext();) {
                    Pipe pipe = iterator.next();
                    pipe.move();
                    if(pipe.getX() < -100) {
                        iterator.remove();
                    }
                    if(player.collide(pipe))
                        stop = true;
                    if(!pipe.isPassed() && player.getX() > pipe.getX()) {
                        player.addScore();
                        pipe.setPassed();
                        txtScore.setText(String.format("Points: %d", player.getScore()));
                    }
                }
                if(stop) {
                    stop();
                    pipe_creator.stop();
                    showRestart();
                    bg.stopMoving();
                }
            }
        };
        timer.start();


        pipe_creator = new Timeline(new KeyFrame(
                Duration.millis(1500),
                p -> addPipes()
        ));
        pipe_creator.setCycleCount(Animation.INDEFINITE);
        pipe_creator.play();
    }

    /**
     * Ustawia tekst(podaje liczbę zdobytych punktów) oraz wyświetla panel,
     * który pyta czy zagrać ponownie czy wrócić do menu
     */
    private void showRestart() {
        Text text = (Text)restartPane.getChildren().get(0);
        text.setText(String.format("Your score: %d, play again?", player.getScore()));
        restartPane.toFront();
        restartPane.setVisible(true);
    }

    /**
     * Tworzy dwie nowe rury z wylosowaną pozycją Y i je dodaje do głównego panelu
     */
    private void addPipes() {
        int randomY = rand.nextInt(280) + 60;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Pipe pipe = new Pipe("top", randomY - 600);
                pipes.add(pipe);
                root.getChildren().add(pipe);
                pipe = new Pipe("bottom", randomY + 200);
                pipes.add(pipe);
                root.getChildren().add(pipe);
                txtScore.toFront();
            }
        });
    }
}

package game;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Klasa player dziedziczy po klasie Rectangle
 */
public class Player extends Rectangle implements IMovable {
    /**
     * Pozycja X gracza
     */
    private double posX = 40;
    /**
     * Pozycja Y gracza
     */
    private double posY = 268;
    /**
     * Szybkość gracza
     */
    private double speed;
    /**
     * Maksymalna szybkość gracza
     */
    private final int maxSpeed = 8;
    /**
     * Liczba punktów
     */
    private int score;

    /**
     * Konstruktor ustawia punkty oraz prędkość początkową gracza na 0
     * Ustawie jego rozmiar oraz pozycję
     * Wczytuje obrazek i wypełnia nim nasz obiekt
     */
    Player() {
        score = 0;
        speed = 0;
        setWidth(64);
        setHeight(64);
        setTranslateY(posY);
        setTranslateX(posX);
        Image img = new Image("floppy.png");
        setFill(new ImagePattern(img));
    }

    /**
     * Aktualizuje szybkość gracza(zwiększa szybkość spadania)
     * Przesuwa gracza na osi Y o wartość szybkości
     * Z pomocą szybkości gracza wylicza odpowiedni kąt o jaki należy obrócić obrazek
     * Na koniec sprawdza czy szybkość maksymalna nie została przekroczona
     */
    public void move() {
        // Grawitacja
        speed += 0.5;

        // Przemieszczenie dyskietki
        posY += speed;
        setTranslateY(posY);

        // Obrót dyskietki
        double rotate = 90 + speed / 10 * 45;
        setRotate(rotate);

        // Sprawdzenie czy szybkość nie przekroczyła szybkości maksymalnej
        if (speed > maxSpeed)
            speed = maxSpeed;
    }

    /**
     * Po wciśnięciu przez gracza spacji ustawia szybkość gracza na -9
     * @param keyEvent zdarzenie klawiatury
     */
    void jump(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.SPACE)
            this.speed = -9;
    }

    /**
     * Sprawdza czy gracz nie wyszedł poza krawędzie ekranu
     * @return true, jeżeli gracz wyszedł poza ekran
     */
    public boolean isOutsideScreen() {
        if (posY < 0 || posY + getHeight() > 600)
            return true;
        return false;
    }

    /**
     * Sprawdza czy nastąpiła kolizja między graczem, a rurą
     * @param pipe sprawdzana rura
     * @return informacja czy nastąpiła kolizja
     */
    public boolean collide(Pipe pipe) {
        return(pipe.contains(posX, posY) ||
                pipe.contains(posX+getWidth(), posY) ||
                pipe.contains(posX, posY + getHeight()) ||
                pipe.contains(posX + getWidth(), posY + getHeight()));
    }

    /**
     * Inkrementuje punkty gracza
     */
    public void addScore() {
        score++;
    }

    /**
     * Zwraca punkty gracza
     * @return liczba punktów
     */
    public int getScore() {
        return score;
    }

    /**
     * Restartuje atrybuty gracza
     */
    public void restart() {
        speed = 0;
        score = 0;
        posX = 40;
        posY = 268;
        setTranslateX(posX);
        setTranslateY(posY);
    }
}

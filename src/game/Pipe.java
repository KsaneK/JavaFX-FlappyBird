package game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import media.Point;

/**
 * Klasa Pipe dziedziczy po klasie Rectangle
 */
public class Pipe extends Rectangle implements IMovable {
    /**
     * Ustawienie rury (top/bottom)
     */
    private final String location;
    /**
     * Atrybut mówiący czy rura została przekroczona przez gracza
     */
    private boolean passed;

    /**
     * Kontruktor wczytuje odpowiedni obrazek(rura skierowana w dół lub górę),
     * ustawia wcześniej wylosowaną pozycję Y oraz pozycję X równą 1000(aby rura pojawiła się za ekranem)
     * @param location kierunek rury
     * @param posY pozycja Y
     */
    public Pipe(String location, int posY) {
        this.location = location;
        passed = false;
        if (location.equals("top"))
            setFill(new ImagePattern(new Image("PipeTop.png")));
        else
            setFill(new ImagePattern(new Image("PipeBottom.png")));

        setX(1000);
        setY(posY);
        setWidth(100);
        setHeight(600);
    }

    /**
     * Przesuwa rurę o 3 piksele w lewo
     */
    public void move() {
        setX(getX()-3);
    }

    /**
     * Sprawdza czy rura została przez gracza przekroczona
     * Ignoruje rurę dolną, aby nie naliczało dwóch punktów za przejście
     * @return atrybut passed(czy rura została przekroczona)
     */
    public boolean isPassed() {
        // Ignore bottom pipe, don't add 2 points
        if (location.equals("bottom"))
            return true;
        return passed;
    }

    /**
     * Ustawia atrybut passed na true
     * oraz tworzy nowy wątek, który włącza dźwięk zdobycia punktu(zapobiega to mikro zacięciom gry)
     */
    public void setPassed() {
        passed = true;
        new Point().start();
    }
}

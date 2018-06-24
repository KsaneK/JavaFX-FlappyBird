package media;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

/**
 * Klasa tła typu singleton
 */
public class GameBackground {
    /**
     * Pierwszy obrazek tła
     */
    private ImageView background1;
    /**
     * Drugi identyczny obrazek tła
     */
    private ImageView background2;
    /**
     * Timer ruszający obrazki
     */
    AnimationTimer timer;

    /**
     * Instancja klasy
     */
    private static GameBackground instance = null;

    /**
     * Prywatny konstruktor może zostać uruchomiony tylko przez metodę statyczną getInstance
     * Tworzy dwa obiekty typu ImageView przechowujące ten sam obrazek
     * Drugi obrazek jest przesunięty o jego szerokość(1600px)
     * Tworzy nowy timer, który porusza tłem
     */
    private GameBackground() {
        background1 = new ImageView(getClass().getResource("/scrolling-background.png").toExternalForm());
        background2 = new ImageView(getClass().getResource("/scrolling-background.png").toExternalForm());
        background2.relocate(1600, 0);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                move();
            }
        };
        timer.start();
    }

    /**
     * Metoda statyczna, która tworzy obiekt i go zwraca, jeżeli nie istnieje,
     * a jeżeli istnieje to go po prostu zwraca
     * Może istnieć tylko jeden obiekt typu GameBackground
     * Pozwala to zarówno w menu jak i w grze mieć dokładnie to samo tło przesunięte dokładnie o taką samą ilość pikseli
     * @return obiekt klasy GameBackground
     */
    public static GameBackground getInstance() {
        if(instance == null) {
            instance = new GameBackground();
            return instance;
        }
        else
            return instance;
    }

    /**
     * Porusza obydwa obrazki o 1 piksel w lewo,
     * jeżeli obrazek po lewej stronie przesunie się poza lewą krawędź ekranu to zostaje przemieszczony
     * za obrazkiem znajdującym się po prawej stronie.
     * Daje to efekt tła przesuwającego się bez końca
     */
    public void move() {
        background1.setLayoutX(background1.getLayoutX()-1);
        if(background1.getLayoutX() <= -1600)
            background1.setLayoutX(1600);
        background2.setLayoutX(background2.getLayoutX()-1);
        if(background2.getLayoutX() <= -1600)
            background2.setLayoutX(1600);
    }

    /**
     * Zwraca pierwszy obrazek
     * @return obiekt typu ImageView
     */
    public ImageView getBackground1() {
        return background1;
    }

    /**
     * Zwraca drugi obrazek
     * @return obiekt typu ImageView
     */
    public ImageView getBackground2() {
        return background2;
    }

    /**
     * Włącza timer, który porusza obrazkami
     */
    public void startMoving() {
        timer.start();
    }

    /**
     * Zatrzymuje timer, który porusza obrazkami
     */
    public void stopMoving() {
        timer.stop();
    }
}

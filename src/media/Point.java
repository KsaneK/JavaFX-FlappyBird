package media;

import game.Pipe;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Klasa tworząca wątek odtwarzający dźwięk przy zdobyciu punkta
 */
public class Point extends Thread {
    /**
     * Załadowany plik muzyczny
     */
    private static Media media;
    /**
     * Odtwarzacz dla pliku
     */
    private static MediaPlayer mediaPlayer;

    /**
     * Metoda statyczna ładuje plik muzyczny do statycznego atrybutu
     * Przypisuje załadowany plik do odtwarzacza
     */
    public static void loadMedia() {
        media = new Media(Pipe.class.getClassLoader().getResource("point.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
    }

    /**
     * Odtworzenie dźwięku i cofnięcie wskaźnika odtwarzacza na początek
     */
    public void run() {
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);
    }
}

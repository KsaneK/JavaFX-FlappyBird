package media;

import javafx.scene.media.AudioClip;

/**
 * Klasa dla muzyki w tle
 */
public class Music {
    /**
     * Klip zawierający dzwięk zdobycia punktu
     */
    private AudioClip clip;

    /**
     * Podczas tworzenia obiektu wczytywany jest plik muzyczny
     */
    public Music() {
        clip = new AudioClip(getClass().getClassLoader().getResource("muzyczka.mp3").toString());

    }

    /**
     * Ustawione jest zapętlenie muzyki i jej odtworzenie
     */
    public void start() {
        clip.setCycleCount(AudioClip.INDEFINITE);
        clip.play();
    }
}

package sokobanfx.business;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * MusicPlayer class is used for controlling music.
 */
public class MusicPlayer implements IMusicPlayer {
    /**
     * Initialize the MediaPlayer
     */
    MediaPlayer mediaPlayer;

    /**
     * Initialize the Media with music file
     */
    public MusicPlayer() {
        musicPlayer();
    }

    /**
     * Creates a musicPlayer and selects a music file.
     */
    @Override
    public void musicPlayer() {
        String musicFile = GameEngine.class.getResource("/music/bgm.mp3").toString();
        Media music = new Media(musicFile);
        mediaPlayer = new MediaPlayer(music);
    }

    /**
     * Check if the music is active or not.
     *
     * @return true if music is active, false otherwise.
     */
    @Override
    public boolean isMusicActive() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * Plays the music.
     */
    @Override
    public void playMusic() {
        mediaPlayer.play();
    }

    /**
     * Stops the music.
     */
    @Override
    public void stopMusic() {
        mediaPlayer.stop();
    }
}

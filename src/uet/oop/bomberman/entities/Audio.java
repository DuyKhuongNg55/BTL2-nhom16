package uet.oop.bomberman.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Audio {

    public AudioStream soundTrack() {

        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/soundtrack.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.soundTrack();
     ap.start(as); bắt đầu phát.
     ap.stop(as); dừng nhac, vì là nhạc nền nên cần phải dừng mỗi khi win lose tắt game
     mấy cái sound còn lại thì ngắn nên hết luôn nên không cần dừng
     */
    public AudioStream winningSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/win.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.winningSound();
     ap.start(as);
     */
    public AudioStream loseSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/lose.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.loseSound();
     ap.start(as);
     */

    public AudioStream pickItemSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/pickitem.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.pickItemSound();
     ap.start(as);
     */
    public AudioStream plantBombSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/plantbomb.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.plantBombSound();
     ap.start(as);
     */
    public AudioStream footStepSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/footstep.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.footStepSound();
     ap.start(as);
     */
    public AudioStream explosionSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/BTL/explosion.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.explosionSound();
     ap.start(as);
     */
    public AudioStream enemiesDeadSound() {
        AudioStream BGM = null;
        try {
            InputStream sound = new FileInputStream("res/amThanh/enemiesdead.wav");
            BGM = new AudioStream(sound);
        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        return BGM;
    }
    /**
     Audio m = new Audio();
     AudioStream as = null;
     AudioPlayer ap = AudioPlayer.player;
     as = m.enemiesDeadSound();
     ap.start(as);
     */
}
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip = AudioSystem.getClip();
    URL soundURL[] = new URL[30];

    public Sound() throws LineUnavailableException {
        soundURL[0] = getClass().getResource("lvl1.wav");
    }

    public void setFile(int i) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            //clip = AudioSystem.getClip();
        if(soundURL[i]!=null) clip.open(AudioSystem.getAudioInputStream(soundURL[i]));
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}

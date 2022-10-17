import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class Board {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        JFrame window = new JFrame();
        BlankLevel n = new BlankLevel();
        //Level1 a = new Level1();
        Level2 b = new Level2();
        //System.out.println(n.gameThread);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 1000, 750);
        window.setVisible(true);
        window.setResizable(false);
        //window.setForeground(Color.DARK_GRAY);
        window.setTitle("BEAT BLAST");
        window.add(b);
        window.pack();

    }
}

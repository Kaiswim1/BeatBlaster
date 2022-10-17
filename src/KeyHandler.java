import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean left, right, space;

    public int blastCount=0;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 37) {
            left = true;
        }
        if(e.getKeyCode() == 39){
            right = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 37) {
            left = false;
        }
        if(e.getKeyCode() == 39){
            right = false;
        }
    }
}

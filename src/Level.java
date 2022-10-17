import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public abstract class Level extends JPanel implements Runnable {
    final int tileSize = 32;
    final int width = (tileSize * 12)*2;

    int scoreTracker=0; //The same value use to track the score will be used to track time through the game
    final int height = (tileSize * 8)*2;

    int playerX = width/2;
    final int playerY = 435; //final because movement is f
    EditImage editImage = new EditImage();

    int maxEnemies = 15; //The maximum amount of enemies reduces processing space

    Integer[] blastX = new Integer[maxEnemies];
    int iterator = 0;
    Integer[] blastY = new Integer[maxEnemies];
    int playerSpeed = 2;

    Graphics2D[] blasts = new Graphics2D[15];
    BufferedImage playerIcon = null; //Requires Instantiation
    BufferedImage enemyIcon = null;
    Thread gameThread = new Thread();
    Graphics2D[] enemies = new Graphics2D[15];
    Random random = new Random();
    int spawnIterator=0;

    int spawnCount = 0;
    int spawnDelay = 250; // The greater, the slower the spawn rate;

    int enemySpeed = 4; //The lower the faster

    int maxIter = 15;
    int groundVal = 480;
    boolean[] isShot = new boolean[15];

    Integer[] enemyX = new Integer[maxIter];
    Integer[] enemyY = new Integer[maxIter];

    KeyHandler keyHandler = new KeyHandler();

    boolean spacePressed;






}



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BlankLevel extends Level {

    boolean spacePressed;
    int playerX;
    boolean crashGame = false;

    int tileSize = 32;


    int iterator = 0;

    int scoreTracker = 0;

    int playerSpeed = 2;


    public Thread gameThread;

    public BlankLevel() throws IOException {
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 100000000/60;
        double drawTime = System.nanoTime() + drawInterval;
        while(gameThread != null){

            if(keyHandler.space){
                spacePressed = true;
                //keyHandler.blastCount++;
            }
            //60 FPS
            if(keyHandler.space)System.out.println(true);
            //System.out.println(keyHandler.space);
            update();
            repaint();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(scoreTracker/10);
            //if(scoreTracker>=2500)this.setBackground(Color.blue);
            scoreTracker++;
            //if(i==5)keyHandler.space = false;
        }
    }
    //@Override
    public void update() {

        if(keyHandler.right){
            //System.out.println(playerX);
            playerX += playerSpeed;
        }
        if(keyHandler.left){
            //System.out.println(playerX);
            playerX -= playerSpeed;
        }
        if(keyHandler.space){
            blastY[keyHandler.blastCount] = playerY;
            blastX[keyHandler.blastCount] = playerX;
            keyHandler.blastCount++;
            keyHandler.space = false;
        }

    }

    BufferedImage playerIcon = ImageIO.read(new File("Level1/shooter 1.png"));
    BufferedImage enemyIcon = ImageIO.read(new File("Level1/enemy.png"));

    int bgPulseAnimation = 5;
    int BGparallax = -518;
    boolean pulse;

    @Override
    public void paintComponent(Graphics g){
        iterator++;
        requestFocus(true);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D ground = (Graphics2D) g;
        g2.setColor(Color.white);
        g.fillRect(0, 0, width,height);
        ground.setColor(Color.black);
        ground.drawLine(0, groundVal, width, groundVal);
        //g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.drawImage(playerIcon, playerX, playerY, null);
        for(int i=0; i<enemies.length; i++) {//Because enemies.length is the largest iteration
            if(enemyY[i] != null && enemyX[i]!=null && enemyY[i] >= groundVal - 32){
                gameThread = null;
                System.out.println("terminated");
                return;
            }
            // if(blastX[i]  )
            if(i<15) blasts[i] = (Graphics2D) g;
            //System.out.println(isShot[i]);
            enemies[i] = (Graphics2D) g;
            enemies[i] = (Graphics2D) g;

            if(iterator % spawnDelay == 0){
                if(i==0){
                    enemyX[spawnIterator] = random.nextInt((width-tileSize)-tileSize) + tileSize;
                    enemyY[spawnIterator] = 12;
                    spawnIterator++;
                }
            }
            else{
                //enemyY[i] = 12;
                //System.out.println("tt: " + spawnIterator);
                if (enemies[i] != null && isShot[i] == false) {
                    if(enemyX[i]!=null && enemyY[i]!=null) {
                        enemies[i].drawRect( enemyX[i], enemyY[i], tileSize, tileSize);
                    }
                }
                //enemies[0].fillRect(enemyX[0], 0, 25, 25);
                //System.out.println(enemyX[0]);
                //int t = new random.nextInt(4-2)+2;
                if(iterator%enemySpeed == 0 && enemyY[i]!=null){
                    enemyY[i] += 1;
                }
            }

            if(spacePressed){
                if(i<15) {
                    if (blasts[i] != null && blastX[i]!=null && blastY[i]!=null) {
                        blasts[i].fillRect(blastX[i] + 15, blastY[i] - 9, 4, 5);
                        if(blastY[i] >0)blastY[i] -= 1;
                    }
                }
            }
            for(int j=0; j<15; j++) {
                if(blastY[i] != null && blastX[i]!=null && enemyY[j]!=null && enemyX[j]!=null) {
                    if (blastY[i] >= 0 && blastY[i] - 9 <= enemyY[j] + tileSize && blastX[i] >= enemyX[j] - ((tileSize/2))  && blastX[i] <= enemyX[j] + (tileSize/2)) {
                        if (blastY[i] != 0) {
                            blasts[i] = null;
                            isShot[j] = true;
                            enemyX[j] = null;
                            enemyY[j] = null;
                            blastX[i] = null;
                            blastY[i] = null;
                        }
                    }
                }
                if(i==14){
                    isShot[j] = false;
                }
            }
            //System.out.println(blastY[i]);


            //System.out.println(blastY[i]);
            //blast
        }
        if(keyHandler.blastCount >= 14){
            keyHandler.blastCount = 0;
        }
        if(spawnIterator >= 15) spawnIterator = 0;


        g2.dispose();
    }
}


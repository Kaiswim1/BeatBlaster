import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class BlankLevel extends Level {

    BufferedImage playerIcon = ImageIO.read(new File("Level1/shooter 1.png"));
    //int tileSize = 12;


    public BlankLevel() throws IOException {
        this.setPreferredSize(new Dimension(width, height));
        //this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Every 4 milliseconds:
     * Repaints the graphics on the game,
     * Listens to controls and what they are doing,
     * and increments the score by 1.
     */
    @Override
    public void run() {
        while(gameThread != null){
            update();
            repaint();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(scoreTracker);
            scoreTracker++;
        }
    }
    /**
     * Listens to the controls:
     * Moves the player left and right
     * Space allows the player to shoot
     */
    public void update() {

        if(keyHandler.right){
            playerX += playerSpeed;
        }
        if(keyHandler.left){
            playerX -= playerSpeed;
        }
        if(keyHandler.space){
            spacePressed=true;
            blastY[keyHandler.blastCount] = playerY;
            blastX[keyHandler.blastCount] = playerX+15;
            keyHandler.blastCount++;
            keyHandler.space = false;
        }

    }


    @Override
    public void paintComponent(Graphics g){
        iterator++;

        requestFocus(true); //Prevents controls from randomly not working.
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //g2 is the background.
        Graphics2D ground = (Graphics2D) g; //ground is the ground below the player
        g2.setColor(Color.white);
        g.fillRect(0, 0, width,height);
        ground.setColor(Color.black);
        ground.drawLine(0, groundVal, width, groundVal);
        g2.drawImage(playerIcon, playerX, playerY, null);

        for(int i=0; i<maxIter; i++) {//Because enemies.length is the largest iteration
            if(enemyY[i] != null && enemyY[i] >= groundVal - 32){ //If an enemy reaches below the ground, stop the gameThread.
                gameThread = null;
                return;
            }
            /*Graphics instantiation*/
            blasts[i] = (Graphics2D) g;
            enemies[i] = (Graphics2D) g;
            enemies[i] = (Graphics2D) g;

            //Spawns enemy collision
            if(iterator % spawnDelay == 0){
                if(i==0){
                    enemyX[spawnIterator] = random.nextInt((width-tileSize) + tileSize);
                    enemyY[spawnIterator] = 12;
                    spawnIterator++;
                }
            }
            //Draws the enemies onto the game
            else{
                if(enemyX[i]!=null && enemyY[i]!=null) {
                    if (enemies[i] != null && isShot[i] == false) {
                        enemies[i].drawRect( enemyX[i], enemyY[i], tileSize, tileSize);
                    }
                    if(iterator%enemySpeed == 0) enemyY[i]+=1;
                }
            }
            //Spawns player shot and makes it scroll upward
            if(spacePressed){
                if(i<15) {
                    if (blasts[i] != null && blastX[i]!=null && blastY[i]!=null) {
                        blasts[i].fillRect(blastX[i], blastY[i] - 9, 4, 5);
                        if(blastY[i] >0)blastY[i] -= 1;
                    }
                }
            }
            //Search if blasts and enemies collide
            for(int j=0; j<15; j++) {
                if(blastY[i] != null && blastX[i]!=null && enemyY[j]!=null && enemyX[j]!=null) {
                    if (blastY[i] >= 0 && blastY[i] - 9 <= enemyY[j] + tileSize && blastX[i] >= enemyX[j] && blastX[i] <= enemyX[j] + tileSize) {
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
        }
        if(keyHandler.blastCount >= 14){
            keyHandler.blastCount = 0;
        }
        if(spawnIterator >= maxIter) spawnIterator = 0;
        g2.dispose();
    }
}


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**Each frame takes 4 milliseconds, to prevent lag there is a limit on how many graphical items of one type can be.
 *For example, at any given time there can be up to (maxIter) enemies, and blasts in the game at one time.
 * Every time the frame updates, it repaints everything.
 * Since there is almost always more than one blast and enemy on the screen at any given time, they are represented in arrays.
 * I.e. blasts[] and enemies[]
 *
 * How this game handles collision detection:
 * With graphics that require collision detection, they also contain arrays of every x and y coordinate of the corresponding graphics. I.e.
 * enemies have the corresponding enemyX[] and enemy[] to keep track of their position.
 * In order to detect that you shot an enemy, the game compares every blastX[] and blastY[] element with every enemyX[] and enemyY[] every frame
 * and checks if any blast has collided with any enemy.
 *
 * (Time complexity)
 * The blasts[], and enemies[] both have a length of (maxIter). Each blast compares its position to every single enemy each frame, thus providing an
 * O(maxIter^2) time complexity for every case. Since I use Arrays, I am forced to have a best case of O(maxIter^2). This is because we check the array
 * elements even when they are null. This is rather inefficient, but it doesn't affect the gameplay at this point. Although scalability and performance can be a concern.
 *
 * (Ways to improve efficiency)
 * Future updates of this game will include options to increase efficiency such as:
 * 1. Using linkedLists rather than arrays (This is because we won't have null elements that your pc wastes time looking for.
 * 2. Linear searching of enemies rather than Exponential searching. This means that the blast will only look for one enemy at a time starting at the enemy with the highest Y value.
 * after it goes past the previous enemy value it searches for the next one. This will keep a linear operation of O(maxIter) The drawback to this approach is it make it
 * impossible for multiple enemies to share x values.
 * */
public class BlankLevel extends Level {

    BufferedImage playerIcon = ImageIO.read(new File("Level1/shooter 1.png"));
    //int tileSize = 16;


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
            blastX[keyHandler.blastCount] = playerX+(tileSize/2);
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


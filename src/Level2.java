import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Level2 extends Level {

    boolean spacePressed;
    int playerX;
    boolean crashGame = false;

    int tileSize = 32;

    BufferedImage enemy = ImageIO.read(new File("Level2/enemy.png"));

    int iterator = 0;

    //Obstacle
    int red_oo = 255;
    int green_oo = 0;
    int blue_oo = 0;

    int red_bg = 51; //(255/15)
    int green_bg = 0;
    int blue_bg = 0;




    Color obstacleOutline = new Color(red_oo, green_oo, blue_oo);
    Color bgColor = new Color(red_bg, green_bg, blue_bg);
    Integer[] obstacleX = new Integer[maxIter];
    Integer[] obstacleY = new Integer[maxIter];
    Graphics2D[] obstacles = new Graphics2D[maxIter];
    int obstacleSpeed = enemySpeed/2;
    int scoreTracker = 0;

    int playerSpeed = 2;

    Integer[] obstacleX2 = new Integer[15];


    public Thread gameThread;

    public Level2() throws IOException {
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        gameThread = new Thread(this);
        gameThread.start();
        spawnDelay = 450;
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
    //BufferedImage enemyIcon = ImageIO.read(new File("Level1/enemy.png"));

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
        if(iterator % 10 == 0) {
            green_oo=green_oo+15;
            green_bg=green_bg+3;
            if(green_oo>255)green_oo=0;
            if(green_bg>51)green_bg=0;
            obstacleOutline = new Color(red_oo, green_oo, blue_oo);
            bgColor = new Color(red_bg,green_bg,blue_bg);

        }
        g2.setColor(bgColor);
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
            if(i<15) {
                blasts[i] = (Graphics2D) g;
                //System.out.println(isShot[i]);
                enemies[i] = (Graphics2D) g;
                obstacles[i] = (Graphics2D) g;
            }
            Integer r = spawnIterator;
            boolean t = false;
            if(iterator % 250 == 0){
                if(i==0){
                    obstacleX[spawnIterator] = random.nextInt((width-250)-64) + 64;
                    obstacleX2[spawnIterator] = random.nextInt((obstacleX[spawnIterator]+233)-(obstacleX[spawnIterator]+(tileSize*5)))+obstacleX[spawnIterator]+(tileSize*5);
                    obstacleY[spawnIterator] = 12;
                    spawnIterator++;
                }
            }

            if(iterator % spawnDelay == 0){
                if(i==0){
                    enemyX[r] = random.nextInt((width - tileSize) - tileSize) + tileSize;
                    enemyY[r] = 12;
                }
            }

                //enemyY[i] = 12;
                //System.out.println("tt: " + spawnIterator);

            if(obstacles[i]!=null && obstacleX[i] != null && obstacleY[i]!=null){
                obstacles[i].setColor(Color.gray);
                obstacles[i].fillRect(obstacleX[i], obstacleY[i], tileSize*4, tileSize);
                obstacles[i].fillRect(obstacleX2[i], obstacleY[i], tileSize*4, tileSize);
                obstacles[i].setColor(obstacleOutline);
                obstacles[i].drawRect(obstacleX[i], obstacleY[i], tileSize*4, tileSize);
                obstacles[i].drawRect(obstacleX2[i], obstacleY[i], tileSize*4, tileSize);
            }

                //enemies[0].fillRect(enemyX[0], 0, 25, 25);
                //System.out.println(enemyX[0]);
                //int t = new random.nextInt(4-2)+2;
                if(iterator%enemySpeed == 0 && enemyY[i]!=null)enemyY[i] += 1;
                if(iterator%obstacleSpeed == 0 && obstacleY[i]!=null) obstacleY[i]+=1;

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

            if(obstacleY[i]!=null && obstacleX[i]!=null && obstacleY[i] >= playerY - (tileSize-20) && obstacleY[i] <= playerY + tileSize){
                int y = playerX;
                for(int e=0; e<=1; e++) {
                    if ((y >= obstacleX[i] && y <= obstacleX[i] + (tileSize * 4)) || (y >= obstacleX2[i] && y <= obstacleX2[i] + (tileSize * 4))) {
                        gameThread = null;
                    }
                    y+=tileSize;
                }
            }
            //System.out.println(blastY[i]);


            //System.out.println(blastY[i]);
            //blast
        }
        for(int i=0; i<15; i++){
            if (enemies[i] != null && !isShot[i] && enemyX[i]!=null && enemyY[i]!=null) {
                enemies[i].setColor(Color.red);
                enemies[i].drawImage(enemy, enemyX[i], enemyY[i],null);
            }

        }
        if(keyHandler.blastCount >= 14){
            keyHandler.blastCount = 0;
        }
        if(spawnIterator >= 15) spawnIterator = 0;

        g2.dispose();

    }
}

import javax.imageio.ImageIO;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Level1 extends Level {

    boolean spacePressed;
    int playerX;
    boolean crashGame = false;



    BufferedImage backyardBG50 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), -100);
    BufferedImage backyardBG40 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), -80);
    BufferedImage backyardBG30 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), -60);
    BufferedImage backyardBG20 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), -40);
    BufferedImage backyardBG10 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), -20);
    BufferedImage backyardBG0 = editImage.AdjustBrightness(ImageIO.read(new File("Level1/Backyard bg.png")), 0);

    BufferedImage backyardBGsunset = editImage.applyColorFilter(ImageIO.read(new File("Level1/Backyard bg.png")), 130, 0, 130);
    BufferedImage backyardBGalienDusk = editImage.applyColorFilter(ImageIO.read(new File("Level1/Backyard bg.png")), 255, 255, 255);
    BufferedImage blueFlower = ImageIO.read(new File("Level1/blue flower.png"));
    BufferedImage lavenderFlower = ImageIO.read(new File("Level1/lavender flower.png"));
    BufferedImage sunflower = ImageIO.read(new File("Level1/sunflower.png"));


    int iterator = 0;

    int scoreTracker = 0;

    int playerSpeed = 2;


    public Thread gameThread;
    Sound sound = new Sound();

    public Level1() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        playMusic("Level1/lvl1.wav");
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
            //System.out.println(scoreTracker/10);
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
    static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

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
        if(scoreTracker<=22250){
            if(scoreTracker>7240 && scoreTracker < 14500){

                sunflower = blueFlower;
                g.drawImage(backyardBGsunset, 0, BGparallax, null);
                enemySpeed = 2;
            }else if(scoreTracker>14300){
                //spawnDelay = 175;
                playerSpeed = 3;
                enemySpeed=1;
                sunflower = lavenderFlower;
                g.drawImage(backyardBGalienDusk, 0, BGparallax, null);
            }
            else {
                int h = (scoreTracker) % 222;
                if (h == 0) {
                    pulse = true;
                    bgPulseAnimation = 5;
                }
                if (pulse) {
                    if (bgPulseAnimation == 5) {
                        g.drawImage(backyardBG50, 0, BGparallax, null);
                    } else if (bgPulseAnimation == 4) {
                        g.drawImage(backyardBG40, 0, BGparallax, null);
                    } else if (bgPulseAnimation == 3) {
                        g.drawImage(backyardBG30, 0, BGparallax, null);
                    } else if (bgPulseAnimation == 2) {
                        g.drawImage(backyardBG20, 0, BGparallax, null);
                    } else if (bgPulseAnimation == 1) {
                        g.drawImage(backyardBG10, 0, BGparallax, null);
                        pulse = false;
                    }
                } else g.drawImage(backyardBG0, 0, BGparallax, null);
            }
        }else{
            stopThread();
        }
        ground.setColor(Color.black);
        ground.fillRect(0, groundVal, width, 34);
        //g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.drawImage(playerIcon, playerX, playerY, null);
        for(int i=0; i<enemies.length; i++) {//Because enemies.length is the largest iteration
            if(enemyY[i] != null && enemyX[i]!=null && enemyY[i] >= groundVal - 32){
                stopThread();
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
                    enemyX[spawnIterator] = random.nextInt((width-32)-32) + 32;
                    enemyY[spawnIterator] = 12;
                    spawnIterator++;
                }
            }
            else{
                //enemyY[i] = 12;
                //System.out.println("tt: " + spawnIterator);
                if (enemies[i] != null && isShot[i] == false) {
                    if(enemyX[i]!=null && enemyY[i]!=null) {
                        enemies[i].drawImage(sunflower, enemyX[i], enemyY[i], null);
                    }
                }
                //enemies[0].fillRect(enemyX[0], 0, 25, 25);
                //System.out.println(enemyX[0]);
                //int t = new random.nextInt(4-2)+2;
                if(iterator%enemySpeed == 0 && enemyY[i]!=null){
                    enemyY[i] += 1;
                }
                if(iterator%(enemySpeed*8) == 0){
                    if(i==0 && BGparallax < 0)BGparallax +=1;
                }
                if(iterator%(enemySpeed*4) == 0){
                    if(i==0) bgPulseAnimation--;
                }
            }

            if(spacePressed){
                if(i<15) {
                    if (blasts[i] != null && blastX[i]!=null && blastY[i]!=null) {
                        blasts[i].setColor(Color.red);
                        blasts[i].fillRect(blastX[i] + 15, blastY[i] - 9, 4, 5);
                        if(blastY[i] >0)blastY[i] -= 1;
                    }
                }
            }
            for(int j=0; j<15; j++) {
                if(blastY[i] != null && blastX[i]!=null && enemyY[j]!=null && enemyX[j]!=null) {
                    if (blastY[i] >= 0 && blastY[i] - 9 <= enemyY[j] + 32 && blastX[i] >= enemyX[j] - 16 && blastX[i] <= enemyX[j] + 16) {
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

    public static void playMusic(String filename)
    {
        try
        {
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
    private void stopThread(){
        gameThread = null;
        clip.stop();
    }
}

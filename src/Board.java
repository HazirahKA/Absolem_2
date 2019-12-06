/**
 * @author Hazirah Khair Anuar
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 800; //width of board
    private final int B_HEIGHT = 800; //height of board
    private final int DOT_SIZE = 25; //size of each dot
    private final int ALL_DOTS = 900; //all dots
    private final int RAND_POS = 29; //position for snake
    private final int SPEED = 110; //the speed of snake

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int score = 0;
    private String highScore = "";

    private boolean leftArrow = false;
    private boolean rightArrow = true;
    private boolean upArrow = false;
    private boolean downArrow = false;
    private boolean inGame = true;

    private Timer timer;
    private Image body;
    private Image apple;
    private Image head;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightArrow)) {
                leftArrow = true;
                upArrow = false;
                downArrow = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftArrow)) {
                rightArrow = true;
                upArrow = false;
                downArrow = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downArrow)) {
                upArrow = true;
                rightArrow = false;
                leftArrow = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upArrow)) {
                downArrow = true;
                rightArrow = false;
                leftArrow = false;
            }
        }
    }

    private void loadImages() {
        ImageIcon imgB = new ImageIcon("src/Images/body.png");
        body = imgB.getImage();

        ImageIcon imgA = new ImageIcon("src/Images/apple.png");
        apple = imgA.getImage();

        ImageIcon imgH = new ImageIcon("src/Images/head.png");
        head = imgH.getImage();
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        timer = new Timer(SPEED, this);
        timer.start();
    }

    /**
     *
     * paintComponenet() = was helped by my brother
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     *
     * doDrawing() = was helped by my brother
     */
    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                }
                else {
                    g.drawImage(body, x[z], y[z], this);
                }

                /**
                 *
                 * this code I did by myself
                 */
                if (highScore.equals("")){
                    //intialize the highscore
                    highScore = this.getHighScore();
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameOver(g);
        }

        /**
         *
         * this code I did by myself
         */
        //Draw the scores
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 16));
        g.drawString("Scores: " + score, 0, 15);

        /**
         *
         * this code I did by myself
         */
        //Draw the length of snake
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 16));
        g.drawString("Length: " + dots, 0, 35);

        /**
         *
         * this code I did by myself
         */
        //Draw the highscore
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.PLAIN, 16));
        g.drawString("Highcore: " + highScore, 0, 55);
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Arial", Font.BOLD, 60);
        FontMetrics metric = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metric.stringWidth(msg)) / 2, B_HEIGHT / 2);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            score += 10;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftArrow) {
            x[0] -= DOT_SIZE;
        }

        if (rightArrow) {
            x[0] += DOT_SIZE;
        }

        if (upArrow) {
            y[0] -= DOT_SIZE;
        }

        if (downArrow) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
                checkScore();
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
            checkScore();
        }

        if (y[0] < 0) {
            inGame = false;
            checkScore();
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
            checkScore();
        }

        if (x[0] < 0) {
            inGame = false;
            checkScore();
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r;

        r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    /**
     *
     * getHighScore() = was helped by my brother
     */
    public static String getHighScore(){
        //format: Hazirah:100
        FileReader readFile = null;
        BufferedReader reader = null;

        try{
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e){
            return "Default:0";
        }
        finally{
            try{
                if(reader != null){
                    reader.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * checkScore() = was helped by my brother
     */
    public void checkScore(){
        if(highScore.equals("")){
            return;
        }

        if (score > Integer.parseInt((highScore.split(":")[1]))){
            //user has set a new record
            String name = JOptionPane.showInputDialog("You has set a new highscore! \nEnter your name: ");
            highScore = name + ":" + score;

            File scoreFile = new File("highscore.dat");

            if (!scoreFile.exists()){
                try{
                    scoreFile.createNewFile();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            FileWriter writeFile = null;
            BufferedWriter writer = null;

            try{
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                writer.write(this.highScore);
            }
            catch (Exception e){
                //errors
            }
            finally{
                try{
                    if (writer != null){
                        writer.close();
                    }
                }
                catch (Exception e) {}
            }
        }
    }
}
//Os imports abaixo servem  armazenar o segmento da Snake
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//esse vai servir para a comida aparecer em pontos aleatórios
import java.util.Random;

import javax.swing.*;



public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    //vamos definir o tamanho dos quadrados
    int tileSize = 25;

    //cobra
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //comida
    Tile food;
    Random random;

    //logica do jogo
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight){
        //o "this." serve para diferenciar da variavel na classe SnakeGame
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5  , 5);
        snakeBody = new ArrayList<>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();



    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        //comida
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);


        //cabeça da cobra
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y  * tileSize, tileSize, tileSize);

        //Corpo da cobra
        for (int i =0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

        //contador de pontos
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over. Sua pontuação: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Pontuação: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void placeFood(){
        food.x = random.nextInt(boardWidth/tileSize); //600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        //comer
        if (collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // corpo da cobra
        for (int i = snakeBody.size() -1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //condições do game over
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //colisão com a cabeça
            if (collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        //colisao com a parede
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
            }
        


    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }
    }


    //nao vamos precisar
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

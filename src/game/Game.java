package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import constants.Direction;
import constants.GameConstants;
import constants.GameStatus;

import static java.awt.event.KeyEvent.*;

public class Game extends JFrame {
    final int screenWidth = GameConstants.screenHeight;
    final int screenHeight = GameConstants.screenWidth;
    final int wallTopX = GameConstants.wallTopX;
    final int wallTopY = GameConstants.wallTopY;
    final int wallBottomX = GameConstants.wallBottomX;
    final int wallBottomY = GameConstants.wallBottomY;
    final int gamespeed = GameConstants.gameRefreshMills;
    public int score;

    GameStatus status;

    Snake snake;
    Point food;

    Random rand = new Random();

    private Draw canvas;

    Point generateFood() {
        Point temp = new Point();
        while (true) {
            temp.x = rand.nextInt(GameConstants.feildWidthPoints) * GameConstants.cellWidth + GameConstants.wallTopX
                    + GameConstants.cellWidth;
            temp.y = rand.nextInt(GameConstants.feildHeightPoints) * GameConstants.cellWidth + GameConstants.wallTopY
                    + GameConstants.cellWidth;

            ArrayList<Point> body = snake.getBody();
            boolean intersects = false;
            for (Point p : body) {
                if (temp.intersect(p)) {
                    intersects = true;
                    break;
                }
            }
            if (!intersects)
                break;
        }

        return temp;
    }

    public void restartGame() {
        score = 0;
        status = GameStatus.PLAYING;
        snake = new Snake();
        food = generateFood();
    }

    private void pauseOrResume() {
        status = (status == GameStatus.PLAYING ? GameStatus.PAUSED : GameStatus.PLAYING);
    }

    private void stop() {
        status = GameStatus.STOPPED;
    }

    public Game() {

        setBackground(GameConstants.gameBgColor);
        setSize(screenWidth, screenHeight);
        setVisible(true);
        setTitle("Snake Game!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contain = getContentPane();
        canvas = new Draw(this);
        canvas.setPreferredSize(new Dimension(screenWidth, screenHeight));
        contain.add(canvas);
        setContentPane(contain);
        pack();

        restartGame();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case VK_LEFT:
                        snake.changeDirection(Direction.left);
                        break;
                    case VK_RIGHT:
                        snake.changeDirection(Direction.right);
                        break;
                    case VK_UP:
                        snake.changeDirection(Direction.up);
                        break;
                    case VK_DOWN:
                        snake.changeDirection(Direction.down);
                        break;
                    case VK_P:
                        pauseOrResume();
                        break;
                    case VK_R:
                        restartGame();
                }
            }
        });

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        };

        new Timer(gamespeed, actionListener).start();
    }

    boolean collide() {
        return (snake.getHead().y < wallTopY + GameConstants.cellWidth
                || snake.getHead().y > wallBottomY - GameConstants.cellWidth
                || snake.getHead().x < wallTopX + GameConstants.cellWidth
                || snake.getHead().x > wallBottomX - GameConstants.cellWidth);
    }

    boolean selfEat() {
        ArrayList<Point> snakeBody = snake.getBody();
        for (int i = 2; i < snake.getLength(); i++) {
            if (snake.getHead().intersect(snakeBody.get(i))) {
                return true;
            }
        }
        return false;
    }

    boolean checkfood() {
        return (food.intersect(snake.getHead()));
    }

    void update() {
        if (status == GameStatus.PLAYING)
            if (selfEat() || collide()) {
                stop();
            } else if (checkfood()) {
                score += GameConstants.foodScore;
                snake.grow();
                food = generateFood();
            } else {
                snake.move();
            }
        else if (status == GameStatus.PAUSED) {
        }
    }

}

class Draw extends JPanel {

    Game game;

    Draw(Game game) {
        this.game = game;
    }

    void drawRect(Graphics g, Point x) {
        g.fillRect(x.x, x.y, GameConstants.cellWidth, GameConstants.cellWidth);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Game Status : " + game.status.toString(), 20, 20);
        g.drawString("Use UP, DOWN, LEFT, RIGHT keys to play.", 20, 35);
        g.drawString("\"P\" to Pause or Resume the game. \"R\" to Restart.", 20, 50);
        g.drawString("Player Score : " + game.score, 30, GameConstants.wallBottomY + 100);
        g.setColor(GameConstants.wallColor);
        g.drawRect(GameConstants.wallTopX, GameConstants.wallTopY, game.wallBottomX, game.wallBottomY);

        g.setColor(GameConstants.foodColor);
        drawRect(g, game.food);

        ArrayList<Point> snakeBody = game.snake.getBody();
        g.setColor(GameConstants.snakeBodyColor);
        for (int i = 1; i < game.snake.getLength(); i++) {
            drawRect(g, snakeBody.get(i));
        }

        g.setColor(GameConstants.snakeHeadColor);
        drawRect(g, snakeBody.get(0));
    }

}
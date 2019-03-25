import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import static java.awt.event.KeyEvent.*;

public class Game extends JFrame
{
    static final int scrsizex =300;
    static final int scrsizey = 300;
    static final int wallx1 = 10;
    static final int wally1 = 10;
    static final int wallx2 = scrsizex-20;
    static final int wally2 = scrsizey -20;
    static final int gamespeed = 100;
    static final int cellwidth = 10;
    static Snake snake1 = new Snake();
    static Point food1 = new Point();
    Random rand = new Random();
    private Draw canvas;

    void generateFood(Point x)
    {
        x.x=rand.nextInt(25)*cellwidth+10;
        x.y=rand.nextInt(25)*cellwidth+10;
    }

    public Game()
    {
        snake1.dir = Snake.Direction.left;
        Point initial = new Point();
        initial.x = 250;
        initial.y=250;
        snake1.body.add(initial);
        for(int i=1;i<5;i++)
        {
            Point x1 = new Point();
            x1.x= initial.x+i*cellwidth;
            x1.y= initial.y;
            snake1.body.add(x1);
        }
        setBackground(Color.WHITE);
        setSize(scrsizex,scrsizey);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container contain = getContentPane();
        canvas= new Draw();
        canvas.setPreferredSize(new Dimension(scrsizex, scrsizey));
        contain.add(canvas);
        setContentPane(contain);
        pack();
        generateFood(food1);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch(evt.getKeyCode()) {
                    case VK_LEFT:  snake1.dir = Snake.Direction.left; break;
                    case VK_RIGHT: snake1.dir=Snake.Direction.right; break;
                    case VK_UP:  snake1.dir = Snake.Direction.up; break;
                    case VK_DOWN: snake1.dir=Snake.Direction.down; break;
                }
            }
        });
        ActionListener x = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        };
        new Timer(gamespeed,x).start();
    }

    boolean meet(Point x, Point y)
    {
        return x.x==y.x && x.y==y.y;
    }

    boolean collide()
    {
        return(snake1.body.get(0).y<wally1+10 || snake1.body.get(0).y > wally2-10 ||
                snake1.body.get(0).x <wallx1+10 || snake1.body.get(0).x> wallx2-10);
    }

    boolean selfEat()
    {
        for(int i=3; i< snake1.length() ; i++) {
            if (meet(snake1.body.get(0), snake1.body.get(i))) {
                return true;
            }
        }
        return false;
    }

    boolean checkfood()
    {
        return(meet(food1, snake1.body.get(0)));
    }

    void update()
    {
        if(selfEat())
        {}
        else if(collide())
        {}

        else if(checkfood())
        {
            snake1.grow();
            generateFood(food1);
        }
        else
        {snake1.move();}
    }

    class Draw extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            g.setColor(Color.BLACK);
            for(int i=0;i<Game.snake1.length();i++){
                g.fillRect(Game.snake1.body.get(i).x,Game.snake1.body.get(i).y,10,10);
            }
            g.setColor(Color.BLUE);
            g.fillRect(food1.x,food1.y,cellwidth,cellwidth);
            g.setColor(Color.RED);
            g.drawRect(10,10, wallx2, wally2);
        }

    }
    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }
}


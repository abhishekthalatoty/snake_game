import java.util.ArrayList;

public class Snake
{
    ArrayList<Point> body = new ArrayList<Point>();
    Direction dir;
    public enum Direction{
        right(0), down(1), left(2), up(3);
        private int value;

        private Direction(int value) {
            this.value = value;
        }
    };


    int length()
    {
        return body.size();
    }

    void move()
    {
        this.grow();
        body.remove((length())-1);
    }

    void grow()
    {
        body.add(0, newHead(this));
    }

    Point newHead(Snake ske)
    {
        Point temp = new Point();
        if(dir==Direction.right)
        {
            temp.x=ske.body.get(0).x+Game.cellwidth;
            temp.y=ske.body.get(0).y;
        }
        else if(dir==Direction.down)
        {
            temp.x=ske.body.get(0).x;
            temp.y=ske.body.get(0).y+Game.cellwidth;

        }
        else if(dir==Direction.left)
        {
            temp.x=ske.body.get(0).x-Game.cellwidth;
            temp.y=ske.body.get(0).y;
        }
        else if(dir==Direction.up)
        {
            temp.x=ske.body.get(0).x;
            temp.y=ske.body.get(0).y-Game.cellwidth;
        }
        return(temp);
    }

}
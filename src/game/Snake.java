package game;

import java.util.ArrayList;
import constants.Direction;
import constants.GameConstants;

public class Snake {

    private ArrayList<Point> body = new ArrayList<Point>();
    private Direction currentDirection;

    Snake() {
        this.currentDirection = Direction.left;
        int initialX = GameConstants.screenHeight / 2;
        int initialY = GameConstants.screenWidth / 2;
        Point initial = new Point(initialX, initialY);

        this.body.add(initial);
        for (int i = 1; i < 5; i++) {
            Point x1 = new Point(initialX + i * GameConstants.cellWidth, initialY);
            this.body.add(x1);
        }
    }

    int getLength() {
        return body.size();
    }

    ArrayList<Point> getBody() {
        return this.body;
    }

    void changeDirection(Direction dir) {
        if ((currentDirection == Direction.left && dir == Direction.right)
                || currentDirection == Direction.right && dir == Direction.left
                || currentDirection == Direction.up && dir == Direction.down
                || currentDirection == Direction.down && dir == Direction.up)
            return;
        this.currentDirection = dir;
    }

    void move() {
        this.grow();
        body.remove((getLength()) - 1);
    }

    Point getHead() {
        return this.body.get(0);
    }

    void grow() {
        this.body.add(0, newHead());
    }

    Point newHead() {
        Point temp = new Point();
        if (currentDirection == Direction.right) {
            temp.x = this.getHead().x + GameConstants.cellWidth;
            temp.y = this.getHead().y;
        } else if (currentDirection == Direction.down) {
            temp.x = this.getHead().x;
            temp.y = this.getHead().y + GameConstants.cellWidth;

        } else if (currentDirection == Direction.left) {
            temp.x = this.getHead().x - GameConstants.cellWidth;
            temp.y = this.getHead().y;
        } else if (currentDirection == Direction.up) {
            temp.x = this.getHead().x;
            temp.y = this.getHead().y - GameConstants.cellWidth;
        }
        return (temp);
    }

}
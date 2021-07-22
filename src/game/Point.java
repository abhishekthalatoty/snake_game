package game;

class Point {
    int x;
    int y;

    Point() {
    }

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersect(Point p) {
        return this.x == p.x && this.y == p.y;
    }
}
import java.awt.*;
import java.util.*;

public class Piece {
    // TODO create array of all pixels
    Point[] border;
    // TODO create (linked list?) of chain of edge
    Queue<Point> points;

    public Piece(Queue<Point> queue) {
        this.points = queue;
    }

    public Queue<Point> getPoints() {
        return points;
    }

    public void setPoints(Queue<Point> points) {
        this.points = points;
    }
}
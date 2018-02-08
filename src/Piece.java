import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Sam on 05/02/2018.
 */
public class Piece {
    // TODO create array of all pixels
    Point[] border;
    // TODO create (linked list?) of chain of edge
    public Piece(){

    }


    public void setBorder(Point[] border){
        this.border = border;
    }
    public Point[] getBorder(){
        return this.border;
    }
}

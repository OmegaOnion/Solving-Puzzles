import java.awt.*;
import java.util.Queue;

public class Compare {

    public Compare(){

        // get 2 pieces
        // compare all points
        // dont compare straight edges
        // take samples both sides of point
        // if point lies on a straight of these give score 0
        // otherwise determine the normal to find rotation
        // store highest score and points that gave the score
        // do for all piece pairs
        // create table of best matches


    }

    /**
     * compares all positions along 2 contours
     * @param a
     * @param b
     */
    public void compareTwoPieces(Piece a, Piece b){
        int aPointer = 0;
        List aPoints = (List)a.getPoints();

        int bPointer = 0;
        List bPoints = (List)b.getPoints();

        for (int i = 0; i < a.getPoints().size(); i++){ // a loop
            for (int j = 0; j < b.getPoints().size(); j++){ // b loop
               //comparePoints(aPoints.g), bPoints(j)); // must chane from queue
                System.out.println(aPoints.getItem(i).getClass());
            } // end b loop
        } // end a loop

        // Generate score

    }

    public void comparePoints(Point a, Point b){

    }


}

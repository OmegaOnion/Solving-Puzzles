import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Compare {

    ArrayList<Piece> pieces;

    public Compare(ArrayList<Piece> p){ // should take arraylist

        setPieces(p);
        // for all pieces in arraylist

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
        int aLength = a.getPoints().size();
        Point[] aPoints = queueToArray(a.getPoints());

        int bPointer = 0;
        int bLength = b.getPoints().size();
        Point[] bPoints = queueToArray(b.getPoints());
        // for all points
        for (int i = 0; i < a.getPoints().size(); i++){ // a loop
            for (int j = 0; j < b.getPoints().size(); j++){ // b loop
                // compare at current points
                System.out.println(i + " " + j);
               comparePoints(i, j, aPoints, bPoints);
            } // end b loop
        } // end a loop

        // Generate score
    }

    public void comparePoints(int aPoint, int bPoint, Point[] a, Point[] b){
        int length = a.length;
        // Chosen Point a
        Point aMid = a[aPoint];


        // Find a - 1 and a - 2
        Point am1 = aMid;
        Point am2 = aMid;
        // if point is at position 0 in the array then previous points will be at the ebd
        if (aPoint < 1){
            am1 = a[length-1];
            am2 = a[length-2];
        // if point is at position 1 in the array then 1 previous point will be at the end
        } else if (aPoint < 2){
            am1 = a[aPoint - 1];
            am2 = a[length - 1];
        // standard condition
        } else{
            am1 = a[aPoint - 1];
            am2 = a[aPoint - 2];
        }

        // find a + 1 and a + 2

        Point ap1 = aMid;
        Point ap2 = aMid;
        // if point is at position at the end then next positions will be at the start
        if (aPoint == (length-1)){
            ap1 = a[0];
            ap2 = a[1];
        // if point is 1 from the end in the array then 1 of the next positions will be at the start
        } else if (aPoint == (length-2)){
            ap1 = a[aPoint + 1];
            ap2 = a[0];
        // standard condition
        } else{
            ap1 = a[aPoint + 1];
            ap2 = a[aPoint + 2];
        }
        //  DEBUG SHOW POSITIONS
        //System.out.println("pointer = " + aPoint + " length = " + length + " point a = " + aMid + " + 1 = " + ap1 + " + 2 = " + ap2 + " - 1 = " + am1 + " - 2 = " + am2);
        findGradient(aMid, ap1,ap2, am1, am2);

        // find gradient of these points
        // if all points lie along gradient then score = 0 (straight edge)
        int xDif; // = a.getX - point -2.get X
        //double fact xdif / gradient
        double gradFactor; // multiplication of gradient to get to point point
        //
        // if a.getY  =  point - 2.getY + gradient factor
        // find normal angle to the chosen point a
        // rotate and translate entire Piece
        int rotation;
        int xTranslation;
        int yTranslaton;

        // overlay 2 points
        // compare neighbouring pixel positions
        // give small tolerance range
        // score = number of points until nolonger in same position
        int score;

        //return score;
    }

    /**
     * converts a queue of points to an array for comparisons
     * @param q
     * @return
     */
    public Point[] queueToArray(Queue<Point> q){

        int length = q.size();
        // array of points the length of the queue
        Point[] p = new Point[length];

        Queue<Point> newQ = new LinkedList<>(q);

        for (int i = 0; i < length; i++){ // for size of queue
            p[i] = newQ.poll();
            //System.out.println( i + "  " + p[i]);
        } // end size of queue loop

        return p;
    }

    /**
     * TODO: class description
     * @param mid middle chosen point
     * @param mp1 mid+1
     * @param mp2 mid+2
     * @param mm1 mid-1
     * @param mm2 mid-2
     */
    public void findGradient(Point mid, Point mp1, Point mp2, Point mm1, Point mm2){
        // given 5 points mid +-2
        // find the gradient
        double xChange = mp2.getX() - mm2.getX();
        double yChange = mp2.getY() - mm2.getY();

        double grad = yChange/xChange;

        Point middle =  new Point();
        double newX;
        double newY;


        if (Double.isInfinite(grad)){
            // score 0
        } else if (grad == 0){
            // score 0
        }

        // Note: Gradient is opposite to in real world, y axis is inverted
        // grad is positive (looks like \)
        if (grad > 0){

        // grad is negative (looks like /)
        } else {

        }
        // X value is increasing
        if (mp1.getX() >  mm1.getX()){

        // X value decreasing
        } else {

        }

        newX = mp2.getX() + (xChange/2);

        newY = mp2.getY() + (yChange/2);

        middle.setLocation(newX, newY); // must be doubles
        //System.out.println(mid);
        System.out.println(mm2 + " " + mp2 + " " + middle + " " + grad);


    }

    public void setPieces(ArrayList<Piece> p){
        this.pieces = p;
    }


}

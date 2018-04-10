import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Compare {

    ArrayList<Piece> pieces;
    int score = 1;

    public Compare(ArrayList<Piece> p){ // should take arraylist

        setPieces(p);
        // for all pieces in arraylist
        // compareTwoPieces();


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
        int maxScore = 1;
        Point bestAPoint = new Point();
        Point bestBPoint = new Point();
        int bestA=0;
        int bestB=0;
        int aLength = a.getPoints().size();
        Point[] aPoints = queueToArray(a.getPoints());

        int bPointer = 0;
        int bLength = b.getPoints().size();
        Point[] bPoints = queueToArray(b.getPoints());
        // for all points
        for (int i = 0; i < a.getPoints().size(); i++){ // a loop
            for (int j = 0; j < b.getPoints().size(); j++){ // b loop
                // compare at current points
               // System.out.println(i + " " + j);
                this.score = 1;
                this.score = comparePoints(i, j, aPoints, bPoints);
                if (this.score > maxScore){
                    maxScore = this.score;
                    bestA = i;
                    bestB = j;
                }

            } // end b loop

        } // end a loop

        System.out.println("Best score: " + maxScore + " at point A: " + bestA + " at pointB: " + bestB);
        Point[] temp = queueToArray(a.getPoints());
        bestAPoint = temp[bestA];
        temp = queueToArray(b.getPoints());
        bestBPoint = temp[bestB];
        System.out.println("A Point = " + bestAPoint + " B Point = " + bestBPoint);

        // Generate score
    }

    /**
     * rotates and translates piece then compares with neighbours
     * @param aPoint
     * @param bPoint
     * @param a
     * @param b
     * @return score equal to number of continuous connections
     */
    public int comparePoints(int aPoint, int bPoint, Point[] a, Point[] b){


        double aNormal = computePoint(aPoint,a);
        if (this.score == 0){
           // System.out.println("removed");
            return 0;
        }
        double bNormal = computePoint(bPoint,b);
        if (this.score == 0){
            //System.out.println("removed");
            return 0;
        }

        int[] TOLERANCE = {2,2};


        // rotate and translate entire Piece

        double radians = findRotation(aNormal, bNormal);

        double xTranslation = b[bPoint].getX() - a[aPoint].getX();
        double yTranslation = b[bPoint].getY() - a[aPoint].getY();

        Point[] rotated = new Point[a.length];
        // overlays 2 points
        for (int i = 0; i < a.length; i++){
            rotated[i] = rotatePoint(a[i],a[aPoint],radians);
            rotated[i].setLocation(rotated[i].getX() + xTranslation,rotated[i].getY() +yTranslation);
           // System.out.println(i);
        }

       /** // DEBUG
        Boolean match = false;
        if (rotated[aPoint].getX() < (double)b[bPoint].getX() + TOLERANCE[0] &&
                rotated[aPoint].getX() > (double)b[bPoint].getX() - TOLERANCE[0] &&

                rotated[aPoint].getY() <( (double)b[bPoint].getY() + TOLERANCE[1]) &&
                rotated[aPoint].getY() > (double)b[bPoint].getY() - TOLERANCE[1]){

            match = true;
        }
        **/
        int score = 1;
        // compare neighbouring pixel positions
        // give small tolerance range

        //Point current = rotated[aPoint - 1];
        int count = 1;
        int aPointer = aPoint;
        int bPointer = bPoint;

        if ( aPoint == 0 ){
            aPointer = rotated.length - 1;
        } else{
            aPointer --;
        }
        if ( bPoint == 0 ){
            bPointer = b.length - 1;
        } else{
            bPointer --;
        }

        //while(rotated[aPointer].getX() == (double) b[bPointer].getX() &&
         //       rotated[aPointer].getY() == (double) b[bPointer].getY() ){

        // points before
       // while(rotated[aPointer].getX() < ((double)b[bPointer].getX() + TOLERANCE[0]) &&
        //        rotated[aPointer].getX() > ((double)b[bPointer].getX() - TOLERANCE[0]) &&

         //       rotated[aPointer].getY() < ((double)b[bPointer].getY() + TOLERANCE[1]) &&
         //       rotated[aPointer].getY() > ((double)b[bPointer].getY() - TOLERANCE[1])){

        while(rotated[aPointer].getX() == (double) b[bPointer].getX() &&
                     rotated[aPointer].getY() == (double) b[bPointer].getY() ){
            score++;
           // System.out.println("--");
            if (aPointer == 0){
                aPointer = rotated.length - 1;
            } else{
                aPointer --;
            }

            if (bPointer == 0){
                bPointer = b.length - 1;
            } else{
                bPointer --;
            }
            // INFINITE LOOP WHERE PIECES ARE IDENTICAL
        }
        aPointer = aPoint;
        bPointer = bPoint;

        if ( aPoint == rotated.length - 1 ){
            aPointer = 0;
        } else{
            aPointer ++;
        }
        if ( bPoint == b.length - 1 ){
            bPointer = 0;
        } else{
            bPointer ++;
        }

        // points after
       while(rotated[aPointer].getX() == (double) b[bPointer].getX() &&
                      rotated[aPointer].getY() == (double) b[bPointer].getY() ){
            //System.out.println("++");
            score++;
            if (aPointer == rotated.length -1){
                aPointer = 0;
            } else{
                aPointer ++;
            }

            if (bPointer == b.length - 1){
                bPointer = 0;
            } else{
                bPointer ++;
            }
        }
       // System.out.println(score);


        // score = number of points until nolonger in same position


        return score;
    }

    /**
     * rotates a point around a given center at a given angle in radians
     * @param point
     * @param center
     * @param angle
     * @return
     */
    public Point rotatePoint(Point point, Point center, double angle){
        double x1 = point.x - center.x;
        double y1 = point.y - center.y;

        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

        double newX = x2 + center.x;
        double newY = y2 + center.y;

        Point newPoint = new Point();
        newPoint.setLocation(newX,newY);

        return newPoint;
    }

    /**
     * finds the difference in angles to be rotated
     * @param aGrad
     * @param bGrad
     * @return
     */
    public double findRotation(double aGrad, double bGrad){

        double gradRate = bGrad/aGrad;

        //tan x = m
        // for negative
        // tan(180-x) = m

        // x = tan-1(m)
        double aAngle = 1;
        double bAngle = 1;

        aAngle = Math.toDegrees(Math.atan(aGrad));
        bAngle = Math.toDegrees(Math.atan(bGrad));

        if (aGrad < 0){
            aAngle = 180 - aAngle;
        }

        if (bGrad < 0){
            bAngle = 180 - aAngle;
        }


        //System.out.println("A Gradient = " + aGrad + "A Angle = " +  aAngle);
      //  System.out.println("B Gradient = " + bGrad + "B Angle = " +  bAngle);



        double degrees = bAngle - aAngle; //  to rotation angle
        degrees+=180;

        // convert to radians
        double radians = degrees * (Math.PI/180);

        return radians;
    }


    /**
     * prepares data to find normal
     * @param aPoint
     * @param a
     * @return
     */
    public double computePoint(int aPoint, Point[] a){



        int length = a.length;
        // Chosen Point a
        Point aMid = a[aPoint];


        // Find a - 1 and a - 2
        Point am1 = aMid;
        Point am2 = aMid;
        // if point is at position 0 in the array then previous points will be at the end
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

        double grad = findGradient(ap2,am2);


        if (Double.isInfinite(grad)){
            this.score = 0;
           //System.out.println(grad);
        } else if (grad == 0){
            this.score = 0;
           // System.out.println(grad);
        }

        // find normal from mid point to chosen point
        double normal = findNormal(aMid,ap2, am2);

        return normal;

    }

    /**
     * finds the normal from the midway between the overall line and the chosen point
     * @param mid
     * @param mp2
     * @param mm2
     * @return
     */
    public double findNormal(Point mid, Point mp2, Point mm2){

        double xChange = mp2.getX() - mm2.getX();
        double yChange = mp2.getY() - mm2.getY();

        Point middle =  new Point();
        double newX;
        double newY;

        newX = mp2.getX() + (xChange/2);
        newY = mp2.getY() + (yChange/2);

        middle.setLocation(newX, newY); // must be doubles
        // DEBUG
        //System.out.println(mid);
        //System.out.println(mm2 + " " + mp2 + " " + middle + " " + grad);


        // gradient of the normal
        double grad = findGradient(mid,middle);
        return grad;
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
     * finds gradient between points
     * @param mp2 mid+2
     * @param mm2 mid-2
     */
    public double findGradient(Point mp2, Point mm2){
        // given 5 points mid +-2
        // find the gradient
        double xChange = mp2.getX() - mm2.getX();
        double yChange = mp2.getY() - mm2.getY();

        double grad = yChange/xChange;

        return grad;


    }

    public void setPieces(ArrayList<Piece> p){
        this.pieces = p;
    }


}

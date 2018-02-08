import com.sun.javafx.image.BytePixelSetter;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFill {

    private int height;
    private int width;
    // background colour RGB
    private int target;
    // tolerance range for each pixels value for R,G,B
    private int[] tolerance = {60,60,60};
    // array of coloured pixels
    private boolean[][] marked;
    // array of not coloured pixels
    private boolean[][] piece;


    // sets some global variables
    public boolean[][] floodFill(BufferedImage image, Point node, Color targetColor, Color replacementColor) throws IOException {
        width = image.getWidth();
        height = image.getHeight();
       // target = targetColor.getRGB();
        targetColor = getColor(image, 0,0);

        marked = new boolean[width][height];
        piece = new boolean[width][height];

        // main functionality
        fillArea(image,(int)node.getX(),(int)node.getY(), targetColor, replacementColor);
        //fill piece colour
        Color pieceColor = Color.BLUE;
        // fills piece
        fillPiece(image, pieceColor);
        edgePiece(image, Color.PINK);

        boolean[][] edges = inverseIntersection(image);

        outEdge(edges);
        return edges;
    }

    // Queue implementation flood fill
    private void fillArea (BufferedImage image, int x, int y, Color original, Color fill){
        if (x != 0)
            x--;
        if (y!= 0)
            y--;
        // create queue
        Queue<Point> queue = new LinkedList<Point>();
        //if (picture[y][x] != original){
        if (image.getRGB(x,y) != original.getRGB()){
            return;
        }
        queue.add(new Point(x, y));
        //while some element is in the queue
        while (!queue.isEmpty()){
            Point p = queue.remove();
            // debug for out of bounds
            //System.out.println(p.x + " " + p.y);
            Color temp = new Color(image.getRGB(p.x,p.y));
            int red = temp.getRed();
            int green = temp.getGreen();
            int blue = temp.getBlue();

            //((red < original.getRed() + tolerance[0]) && (red > original.getRed() - tolerance[0]))


            // if colour is within tolerance range
            if ( ((red < original.getRed() + tolerance[0]) && (red > original.getRed() - tolerance[0])) &&
                    ((green < original.getGreen() + tolerance[1]) && (green > original.getGreen() - tolerance[1])) &&
                    ((blue < original.getBlue() + tolerance[2]) && (blue > original.getBlue() - tolerance[2]))){
            //if (image.getRGB(p.x,p.y) == original.getRGB()){

                image.setRGB(p.x,p.y, fill.getRGB());
                // background pixels = true
                marked[p.x][p.y] = true;



                // queue adjacent pixels for checking
                if (p.x > 0){
                    queue.add(new Point(p.x-1, p.y));
                }
                if (p.x < (width -1)){
                    queue.add(new Point(p.x+1, p.y));
                }
                if (p.y > 0){
                    queue.add(new Point(p.x, p.y-1));
                }
                if (p.y < (height -1)) {
                    queue.add(new Point(p.x, p.y + 1));
                }
            } else {
                piece[p.x][p.y] = true;
            }
        }
    } // end fill area

    private Color getColor(BufferedImage image, int x, int y){
        Color c = new Color(image.getRGB(x,y));
        return c;
    }

    private void fillPiece(BufferedImage image, Color fill){
        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                if (marked[i][j] == false){
                    image.setRGB(i,j,fill.getRGB());
                }
            }

        }
    }

    private void edgePiece(BufferedImage image, Color fill){
        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                if (piece[i][j] == false){
                    image.setRGB(i,j,fill.getRGB());
                }
            }

        }
    }

    public boolean[][] inverseIntersection(BufferedImage image) {

        boolean[][] edges = new boolean[image.getWidth()][image.getHeight()];

        for (int i = 0; i < width; i++) { // x loop
            for (int j = 0; j < height; j++) { // y loop
                //array where (piece[x][y] == true && marked[x][y] == false)
                if (piece[i][j] == true && marked[i][j] == false){
                    edges[i][j] = true;
                }

            }
        }
        return edges;
    }

    public void outEdge( boolean[][] edges) throws IOException {

        BufferedImage newImage = new BufferedImage(width,height,8);

        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                if (edges[i][j] == true){
                    newImage.setRGB(i,j,Color.RED.getRGB());
                } else {
                    newImage.setRGB(i,j,Color.WHITE.getRGB());
                }

            }

        }

        ImageIO.write(newImage, "png", new File("edge.png"));
    }






}
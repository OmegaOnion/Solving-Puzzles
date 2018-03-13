import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Sam on 13/03/2018.
 */
public class Contours {
    public int width;
    public int height;

    public Contours(boolean[][] boolEdge) throws IOException{

        width = boolEdge.length;
        height = boolEdge[0].length;

        int[][] edges = boolToInt(boolEdge);

        edges = findPieces(edges);

        outEdge(edges);

    }

    /**
     * converts boolean matrix into int matrix
     * @param bool
     * @return
     */
    public int[][] boolToInt(boolean[][] bool){
        int[][] edges = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bool[i][j] == true){
                    edges[i][j] = 1;
                } else {
                    edges[i][j] = 0;
                }
            }
        }

        return edges;
    }

    /**
     * Where int[][] edges has 0 for background 1 for foreground
     * changes 1 to incrementing labels for piece detection
     */
    public int[][] findPieces(int[][] edges) {
        // label starts above foreground value
        int label = 2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (edges[i][j] == 1) {
                    // floodfill from found point
                    floodFill(edges,i,j,label,0);
                    label++;
                }
            }
        }
        System.out.println(label);
        return edges;
    }

    /**
     * 8 way floodfill algorithm
     * @param edges
     * @param x
     * @param y
     * @param label
     */
    public void floodFill(int[][] edges,int x,int y,int  label, int count){

        // starting position
        //Queue<Point> queue = new LinkedList<>();
        //queue.add(new Point(x, y));
        edges[x][y] = label;
        for (int ni = -1; ni <= 1; ni++) {
            for (int nj = -1; nj <= 1; nj++) {
                // if is in bounds
                if (((x+ni) >= 0) && ((x+ni) <= width) && ((y+nj) >= 0) && ((y+nj) <= height)){
                    // if pixel is in foreground
                    if (edges[x+ni][y+nj] == 1) {
                        // recurse
                        floodFill(edges, (x+ni), (y+nj), label, count + 1);
                    }
                }

            }

        }
        // set foreground to new label


    }



    public void outEdge(int[][] edges) throws IOException {

        BufferedImage newImage = new BufferedImage(width,height,8);

        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                if (edges[i][j] == 1){
                    newImage.setRGB(i,j,Color.RED.getRGB());
                } else if (edges[i][j] == 2) {
                    newImage.setRGB(i, j, Color.BLUE.getRGB());
                } else if (edges[i][j] == 3) {
                    newImage.setRGB(i, j, Color.GREEN.getRGB());
                } else if (edges[i][j] == 4) {
                    newImage.setRGB(i, j, Color.PINK.getRGB());
                } else {
                    newImage.setRGB(i,j,Color.WHITE.getRGB());
                }

            }

        }

        ImageIO.write(newImage, "png", new File("label.png"));
    }

}

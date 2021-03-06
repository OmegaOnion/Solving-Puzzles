import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Contours {
    public int width;
    public int height;
    public int maxLabel;
    public ArrayList<Queue<Point>> puzzlesList;
    public ArrayList<Piece> pieceList;
    public int[][] edges;

    /**
     * Sets variables and executes code
     * @param boolEdge
     * @throws IOException
     */
    public Contours(boolean[][] boolEdge) throws IOException{
        // set image size
        width = boolEdge.length;
        height = boolEdge[0].length;
        // 2d array of all the foreground pixels set to 1
        int[][] edges = boolToInt(boolEdge);
        // updates foreground pixels to incremented label numbers for each piece in the image
        edges = findPieces(edges);
        this.edges = edges;
        // prints image label.png for visual representation of pieces
        outEdge(edges);

        // adds pieces to objects and queue
        createPieces();

    }

    /**
     * Creates Piece objects from the Queues of points
     */
    public void createPieces(){
        ArrayList<Piece> pList = new ArrayList<>();

        for (int i = 0; i < this.puzzlesList.size(); i++){
            Piece newPiece = new Piece(this.puzzlesList.get(i));
            pList.add(newPiece);
        }
        this. pieceList = pList;

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
        ArrayList<Queue<Point>> q = new ArrayList<>();

        Piece[] pieces;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // if current position is in foreground
                if (edges[i][j] == 1) {
                    // floodfill from found point
                    //Piece p = new Piece();
                    q.add(floodQueue(edges,i,j,label));
                    label++;
                }
            }
        }
        setPuzzlesList(q);
        maxLabel = label;
        return edges;
    }

    /**
     * create linked list of points
     * @param edges
     * @param x
     * @param y
     * @param label
     * @return
     */
    public Queue<Point> floodQueue(int[][] edges,int x,int y, int label){
        Queue<Point> queue = new LinkedList<>();
        floodFill2(edges,x,y,label,0, queue);
        return queue;
    }

    /**
     * 8 way floodfill algorithm
     * @param edges
     * @param x
     * @param y
     * @param label
     */
    public Queue<Point> floodFill(int[][] edges,int x,int y,int  label, int count, Queue<Point> q){

        // starting position
        edges[x][y] = label;


        for (int ni = -1; ni <= 1; ni++) {
            for (int nj = -1; nj <= 1; nj++) {
                // if is in bounds
                if (((x+ni) >= 0) && ((x+ni) <= width) && ((y+nj) >= 0) && ((y+nj) <= height)){
                    // if pixel is in foreground
                    if (edges[x+ni][y+nj] == 1) {
                        // recurse
                        floodFill(edges, (x+ni), (y+nj), label, count + 1, q);
                        q.add(new Point(x,y));
                    }
                }

            }

        }

        return q;
    }

    /**
     * 8 way flood fill and component labelling
     * Alternate method without looping neighbouring positions
     * eliminates jumping pixels by checking 4 cardinal directions first
     * @param edges
     * @param x
     * @param y
     * @param label
     * @param count
     * @param q
     * @return
     */
    public Queue<Point> floodFill2(int[][] edges, int x, int y, int label, int count, Queue<Point> q){

        // starting position
        edges[x][y] = label;
        // avoid edge data
        if (x>0 && y>0 && x<edges.length-1 ){
            if (y < edges[x].length-1){
                // first check 4 cardinal directions

                // North
                if (edges[x][y - 1] == 1){
                    // recursive call
                    floodFill2(edges, x, (y-1), label, count + 1, q);
                }
                // South
                if (edges[x][y+1] == 1){
                    // recursive call
                    floodFill2(edges, x, (y+1), label, count + 1, q);
                }
                // East
                if (edges[x+1][y] == 1){
                    // recursive call
                    floodFill2(edges, (x+1), y, label, count + 1, q);
                }
                // West
                if (edges[x-1][y] == 1){
                    // recursive call
                    floodFill2(edges, (x-1), y, label, count + 1, q);
                }
                // then check 4 diagonal directions

                // North East
                if (edges[x+1][y-1] == 1){
                    // recursive call
                    floodFill2(edges, (x+1), (y-1), label, count + 1, q);
                }
                // South East
                if (edges[x+1][y+1] == 1){
                    // recursive call
                    floodFill2(edges, (x+1), (y+1), label, count + 1, q);
                }
                // South West
                if (edges[x-1][y+1] == 1){
                    // recursive call
                    floodFill2(edges, (x-1), (y+1), label, count + 1, q);
                }
                // North West
                if (edges[x-1][y-1] == 1){
                    // recursive call
                    floodFill2(edges, (x-1), (y-1), label, count + 1, q);
                }
            }

        }

        // add to queue of edge pixels within a contour
        q.add(new Point(x,y));
        // DEBUG shows positions as they are added to view any jumps
       // System.out.println(count);
       // System.out.println(new Point(x,y));

        return q;
    }

    /**
     * Prints image of all pieces coloured red,blue,green,black
     * @param edges
     * @throws IOException
     */
    public void outEdge(int[][] edges) throws IOException {

        BufferedImage newImage = new BufferedImage(width,height,8);
        Color[] color = new Color[1000];
        color[0] = Color.WHITE;
        color[1] = Color.WHITE;
        int pointer = 0;

        for (int k = 2; k < maxLabel; k++){
            if (pointer == 0){
                color[k] = Color.RED;
                pointer ++;
            } else if (pointer == 1){
                color[k] = Color.BLUE;
                pointer ++;
            } else if (pointer == 2){
                color[k] = Color.GREEN;
                pointer ++;
            } else if (pointer == 3){
                color[k] = Color.BLACK;
                pointer = 0;
            }
        }
        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                Color tempColor;
                tempColor = color[edges[i][j]];
                newImage.setRGB(i,j,tempColor.getRGB());
            }
        }
        ImageIO.write(newImage, "png", new File("label.png"));
    }

    /**
     * set puzzle list
     * @param q
     */
    public void setPuzzlesList(ArrayList<Queue<Point>> q){
        this.puzzlesList = q;
    }

    /**
     * get puzzle list
     * @return
     */
    public ArrayList<Queue<Point>> getPuzzlesList(){
        return this.puzzlesList;
    }

    /**
     * get piece list
     * @return
     */
    public ArrayList<Piece> getPieceList(){
        return this.pieceList;
    }

}


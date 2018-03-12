import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Created by Sam on 08/02/2018.
 */
public class PieceDetection {
    public int width;
    public int height;
    public int[][] labeledMatrix;


    public PieceDetection(boolean[][] edges, BufferedImage image) throws IOException {

        width = image.getWidth();
        height = image.getHeight();


        //TODO 8way
        findPieces(edges);




        //TODO Store each piece

        outEdge();

    }


 //https://en.wikipedia.org/wiki/Connected-component_labeling
 // 2 pass CCL
    public void findPieces(boolean[][] edges){

        ArrayList<ArrayList<Integer>> linked = new ArrayList<>();
        int[][] labels = new int[width][height];
        int NextLabel = 0;

        //Queue<Point> queue = new LinkedList<Point>();
        //queue.add(new Point(x, y));
        // first pass
        for (int i = 0; i < width; i++) {
            for(int j = 0; j< height; j++) {
                if (edges[i][j] == true) {

                    // labels of neighbors
                    ArrayList<Integer> neighbors = new ArrayList<>();

                    for (int ni = -1; ni <= 1; ni++) {
                        for (int nj = -1; nj <= 1; nj++) {
                            if (i + ni < 0 || j + nj < 0 || i + ni > labels.length - 1 || j + nj > labels[0].length - 1) {
                                continue;
                            } else {
                                if (i + ni == 0 && i + nj == 0) continue;
                                if (ni == -1 && nj == 1){
                                    System.out.println(labels[i + ni][j + nj]);
                                }
                                if (labels[i + ni][j + nj] != 0){
                                    neighbors.add(labels[i + ni][j + nj]);
                                }
                            }
                        }
                    }


                    if (neighbors.isEmpty()) {
                        ArrayList<Integer> tempArrayList = new ArrayList<>();
                        tempArrayList.add(NextLabel);
                        linked.add(NextLabel, tempArrayList);
                        labels[i][j] = NextLabel;
                        NextLabel++;
                    } else {

                        labels[i][j] = height * width;
                        for (int neighbor : neighbors) {
                            if (neighbor < labels[i][j]) labels[i][j] = neighbor;
                        }

                        for (int neighbor : neighbors) {
                            linked.set(neighbor, union(linked.get(neighbor), neighbors));
                        }
                    }

                }
            }
        }


        //Second pass
        for(int i = 0; i< width; i++) {
            for(int j = 0; j < height; j++) {
                ArrayList<Integer> EquivalentLabels = linked.get(labels[i][j]);
                labels[i][j]= height * width;
                for(int label : EquivalentLabels) {
                    if(label < labels[i][j]) labels[i][j]=label;
                }
            }
        }

       labeledMatrix = labels;

    }




    public <T> ArrayList<T> union(ArrayList<T> list1, ArrayList<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }


    public void outEdge() throws IOException {

        BufferedImage newImage = new BufferedImage(width,height,8);

        for (int i = 0; i < width; i++){ // x loop
            for (int j = 0; j < height; j++){ // y loop
                if (labeledMatrix[i][j] == 1){
                    newImage.setRGB(i,j,Color.RED.getRGB());
                } else if (labeledMatrix[i][j] == 2) {
                    newImage.setRGB(i, j, Color.BLUE.getRGB());
                } else if (labeledMatrix[i][j] == 3) {
                    newImage.setRGB(i, j, Color.GREEN.getRGB());
                } else if (labeledMatrix[i][j] == 4) {
                    newImage.setRGB(i, j, Color.PINK.getRGB());
                } else {
                    newImage.setRGB(i,j,Color.WHITE.getRGB());
                }

            }

        }

        ImageIO.write(newImage, "png", new File("label.png"));
    }

}

import java.io.IOException;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Test {
    public Test() throws IOException {


        BufferedImage image = ImageIO.read(new File("shapier test.png"));
        boolean[][] points = new FloodFill().floodFill(image, new Point(1, 1), Color.WHITE, Color.RED);
        ImageIO.write(image, "png", new File("floodfill.png"));

        Contours c = new Contours(points);
        ArrayList<Piece> p = c.getPieceList();

        // remove small errors
        int total = 0;
        for (Piece t : p){
            total = total+= t.getPoints().size();
        }
        int length = p.size();
        double avg = total/length;
        System.out.println("Pieces with noise: " + length);
        for (int i = 0; i<length;i++){
            if (p.get(i).getPoints().size() < (avg/2)){
                p.remove(i);
                i--;
                length = p.size();
            }
        }
        System.out.println("Pieces without noise: " + length);

        //c.outEdge(c.edges);
        System.out.println("Comparing pieces, this may take a couple of minutes");
        Compare newCompare = new Compare(p);
        //newCompare.compareTwoPieces(p.get(0), p.get(1));


    }

    public static void main(String[] args) throws IOException {
       new Test();
       // BufferedImage image = ImageIO.read(new File("real piece.png"));
       // GUI a = new GUI(image);

    }
}
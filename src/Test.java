import java.io.IOException;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Test {
    public Test() throws IOException {


        BufferedImage image = ImageIO.read(new File("test pieces.jpg"));
        boolean[][] points = new FloodFill().floodFill(image, new Point(1, 1), Color.WHITE, Color.RED);
        ImageIO.write(image, "png", new File("output.png"));
       // PieceDetection p = new PieceDetection(points, image);
        Contours c = new Contours(points);
        ArrayList<Piece> p = c.getPieceList();

        Compare newCompare = new Compare();

        newCompare.compareTwoPieces(p.get(1), p.get(0));

    }

    public static void main(String[] args) throws IOException {
       new Test();
       // BufferedImage image = ImageIO.read(new File("real piece.png"));
       // GUI a = new GUI(image);

        //a.create();

    }
}
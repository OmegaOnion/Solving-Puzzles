import java.io.IOException;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Test {
    public Test() throws IOException {


        BufferedImage image = ImageIO.read(new File("circle.png"));
        boolean[][] points = new FloodFill().floodFill(image, new Point(1, 1), Color.WHITE, Color.RED);
        ImageIO.write(image, "png", new File("output.png"));
        PieceDetection p = new PieceDetection(points, image);
    }

    public static void main(String[] args) throws IOException {
       new Test();
       // BufferedImage image = ImageIO.read(new File("real piece.png"));
       // GUI a = new GUI(image);

        //a.create();

    }
}
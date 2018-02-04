import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFill {

    private int height;
    private int width;
    private int target;
    private int[] tolerance = {80,80,80};



    public void floodFill(BufferedImage image, Point node, Color targetColor, Color replacementColor) {
        width = image.getWidth();
        height = image.getHeight();
        target = targetColor.getRGB();


        fillArea(image,(int)node.getX(),(int)node.getY(), targetColor, replacementColor);

    }


    private void fillArea (BufferedImage image, int x, int y, Color original, Color fill){
        if (x != 0)
            x--;
        if (y!= 0)
            y--;
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



            if ( ((red < original.getRed() + tolerance[0]) && (red > original.getRed() - tolerance[0])) &&
                    ((green < original.getGreen() + tolerance[1]) && (green > original.getGreen() - tolerance[1])) &&
                    ((blue < original.getBlue() + tolerance[2]) && (blue > original.getBlue() - tolerance[2]))){
            //if (image.getRGB(p.x,p.y) == original.getRGB()){

                image.setRGB(p.x,p.y, fill.getRGB());

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
            }
        }

    }

}
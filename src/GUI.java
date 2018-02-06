import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class GUI {
    BufferedImage image;
    final JFileChooser fc = new JFileChooser();

    public GUI(BufferedImage image){
        this.image = image;

        create();

    }




    public void create(){
/**
        //Create a file chooser


        //In response to a button click:

        //Filewalker fw = new Filewalker();
       // HelloWorldDisplay displayPanel = new HelloWorldDisplay();
        //ImageFrame displayPanel = new ImageFrame();
        JButton okButton = new JButton("OK");
        ButtonHandler listener = new ButtonHandler();
        okButton.addActionListener(listener);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
       // content.add(displayPanel, BorderLayout.CENTER);
        content.add(okButton, BorderLayout.SOUTH);

        //ImageComponent component = new ImageComponent();


       // JFrame window = new JFrame("GUI Test");

        ImageFrame window = new ImageFrame();

        window.setContentPane(content);
        window.setSize(800,800);
        window.setLocation(100,100);
        window.setVisible(true);

**/
        ImageFrame frame = new ImageFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = image.getWidth() + 100;
        int height = image.getHeight() + 100;

        frame.setSize(width, height);
        frame.setVisible(true);

        JButton okButton = new JButton("OK");
        ButtonHandler listener = new ButtonHandler();
        okButton.addActionListener(listener);

        frame.add(okButton, BorderLayout.SOUTH);


    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }


    class ImageFrame extends JFrame{

        public ImageFrame(){
            setTitle("ImageTest");
            setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

            ImageComponent component = new ImageComponent();
            add(component);

        }

        public static final int DEFAULT_WIDTH = 300;
        public static final int DEFAULT_HEIGHT = 200;
    }


    private static class HelloWorldDisplay extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString( "Open a file", 20, 30 );
        }
    }

}

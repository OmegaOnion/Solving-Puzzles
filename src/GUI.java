import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private JFrame frame;
    private JPanel btnPanel, imgPanel;
    private JButton btnFlood, btnSource, btnLabel;
    private ImageIcon img;
    private JLabel label;


    public GUI(BufferedImage image){

        frame = new JFrame("Solving Puzzles");

        //Button
        btnSource = new JButton("Source");
        btnSource.setPreferredSize(new Dimension(100,50));
        btnSource.addActionListener(this);

        btnFlood = new JButton("Flood Fill");
        btnFlood.setPreferredSize(new Dimension(100,50));
        btnFlood.addActionListener(this);

        btnLabel = new JButton("Connected Components");
        btnLabel.setPreferredSize(new Dimension(100,50));
        btnLabel.addActionListener(this);

        //Pane
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(100,600));
        btnPanel.add(btnSource);
        btnPanel.add(btnFlood);
        btnPanel.add(btnLabel);
       // btnPanel.setLayout(new GridLayout(3,1));

        imgPanel = new JPanel(new FlowLayout());
        imgPanel.setPreferredSize(new Dimension(1000,600));
        label = new JLabel("");
        add (label);


        // Frame
        frame.setLayout(new GridLayout(1,2));
        frame.add(btnPanel);
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();
        if (arg == "Source"){
            img = new ImageIcon (getClass().getResource("img011.jpg"));
            //imgPanel.setImage();
        } else if (arg == "Flood Fill"){
            JOptionPane.showMessageDialog(null, "flood");
        } else if (arg == "Connected Components"){
            JOptionPane.showMessageDialog(null, "lbl");
        }

    }
}
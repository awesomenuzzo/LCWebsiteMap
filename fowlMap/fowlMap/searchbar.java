package fowlMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class searchbar implements ActionListener {
//  building search bar
    static JTextField t0;
//  building buttons
    JButton submitButton;JButton b1;JButton b2;JButton b3;JButton b4;JButton b5;
    searchbar() {
        JFrame frame = new JFrame("Enhanced Search Interface"); // Title of the window
        frame.setLayout(null);

        // Adjusting the size of the window
        frame.setSize(1366, 768); // Set to a typical tablet/laptop size

        // Title Label with adjusted bounds for the larger size
        JLabel titleLabel = new JLabel("L&C Fowler Student Center Map");
        titleLabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); // Slightly larger font
        titleLabel.setBounds(50, 20, 800, 40); // Adjusted bounds
        frame.add(titleLabel);

        // Description Label with adjusted bounds
        JLabel descriptionLabel = new JLabel("Enter your destination and select a suggestion.");
        descriptionLabel.setFont(new Font("Sans Serif", Font.PLAIN, 22)); // Slightly larger font
        descriptionLabel.setBounds(50, 70, 800, 30); // Adjusted bounds
        frame.add(descriptionLabel);

        // Search TextField with adjusted bounds
        t0 = new JTextField();
        t0.setBounds(50, 110, 600, 40); // Adjusted bounds for width and height
        frame.add(t0);

        // Initialize and adjust bounds for the buttons used for suggestions
        b1 = new JButton("Suggestion 1");
        b1.setBounds(50, 160, 600, 40);
        b1.addActionListener(this);
        b1.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.add(b1);

        b2 = new JButton("Suggestion 2");
        b2.setBounds(50, 210, 600, 40);
        b2.addActionListener(this);
        b2.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.add(b2);

        b3 = new JButton("Suggestion 3");
        b3.setBounds(50, 260, 600, 40);
        b3.addActionListener(this);
        b3.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.add(b3);

        b4 = new JButton("Suggestion 4");
        b4.setBounds(50, 310, 600, 40);
        b4.addActionListener(this);
        b4.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.add(b4);

        b5 = new JButton("Suggestion 5");
        b5.setBounds(50, 360, 600, 40);
        b5.addActionListener(this);
        b5.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.add(b5);

        // Search Button with adjusted bounds
        submitButton = new JButton("Search");
        submitButton.setBounds(660, 110, 150, 40); // Adjusted for aesthetic placement
        submitButton.addActionListener(this);
        frame.add(submitButton);

        // Frame adjustments for visibility
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates on closing window

//
/*
//        // Initialize and set bounds for the buttons used for suggestions
//        b1 = new JButton("Suggestion 1");
//        b1.setBounds(50, 100, 300, 30);
//        b1.addActionListener(this);
//        frame.add(b1);
//
//        b2 = new JButton("Suggestion 2");
//        b2.setBounds(50, 140, 300, 30);
//        b2.addActionListener(this);
//        frame.add(b2);
//
//        b3 = new JButton("Suggestion 3");
//        b3.setBounds(50, 180, 300, 30);
//        b3.addActionListener(this);
//        frame.add(b3);
//
//        b4 = new JButton("Suggestion 4");
//        b4.setBounds(50, 220, 300, 30);
//        b4.addActionListener(this);
//        frame.add(b4);
//
//        b5 = new JButton("Suggestion 5");
//        b5.setBounds(50, 260, 300, 30);
//        b5.addActionListener(this);
//        frame.add(b5);
//
//        // Search Button
//        submitButton = new JButton("Search");
//        submitButton.setBounds(360, 60, 100, 30);
//        submitButton.addActionListener(this);
//        frame.add(submitButton);
//
//        // Frame adjustments
//        frame.setSize(500, 400);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //terminates on closing window
*/

    }//setting buttons and textbar

    public static String SearchQuery(){
        String s1=t0.getText();
        return s1;
    }
    public void printPosition(MouseEvent e) {PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.print("x = " +x);
        System.out.print(" y  = " +y);
    }
    public static Point getPosition(MouseEvent e) {PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        return b;
    }

    public void drawIcon(Point2D a, BufferedImage j){
        int x = (int)a.getX();
        int y = (int) a.getY();
        int r = 10;

        Graphics g2 = j.createGraphics();
        g2.setColor(Color.red);
        g2.fillOval(x-(r/2), y-(r*2), r, r);
        g2.dispose();

    }

    public class MapPanel extends JPanel{

        public BufferedImage mapImage;
        double scaleFactor;
        public BufferedImage mapIn(){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth = (int) (screenSize.getWidth() * 0.8); // 80% of screen width
            int maxHeight = (int) (screenSize.getHeight() *1);
            int width, height, newWidth, newHeight;
            double widthRatio, heightRatio;
            BufferedImage originalImage;
            BufferedImage m;
            try {
                originalImage = ImageIO.read(new File("Fowler2_cropped.jpg"));
                width = originalImage.getWidth();
                height = originalImage.getHeight();
                widthRatio = (double) maxWidth / width;
                heightRatio = (double) maxHeight / height;
                scaleFactor = Math.min(widthRatio, heightRatio);
                newWidth = (int) (width * scaleFactor);
                newHeight = (int) (height * scaleFactor);
                m = org.imgscalr.Scalr.resize(originalImage, org.imgscalr.Scalr.Method.BALANCED, newWidth, newHeight);

            } catch (IOException a) {
                throw new RuntimeException(a);
            }
            return m;
        }

        public MapPanel() {
            JPanel mapPanel = new JPanel();

            mapPanel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                printPosition(e);

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            mapImage = mapIn();
            JLabel mapLabel = new JLabel(new ImageIcon(mapImage));
            mapPanel.add(mapLabel);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame mapFrame = new JFrame();
            mapFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            mapFrame.add(mapPanel);
            mapFrame.pack();
            mapFrame.setVisible(true);


        }//fowler map
    }

    public Point2D scalePoints(Point2D a, double scaleFactor){

        Point2D b = new Point2D.Double();
        b.setLocation(a.getX()*scaleFactor, a.getY()*scaleFactor);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s1 = t0.getText();
        //if we click the search button
        if (e.getSource() == submitButton) {
            // emptying old results
            b1.setText("");
            b2.setText("");
            b3.setText("");
            b4.setText("");
            b5.setText("");
            ArrayList<String> results = new ArrayList<String>();
            results = search.search(s1, "fowlerNames.tsv");
            System.out.print(results);
            // setting new results
            b1.setText(results.get(0));
            b2.setText(results.get(1));
            b3.setText(results.get(2));
            b4.setText(results.get(3));
            b5.setText(results.get(4));
        }


//        Container c = mapFrame.getContentPane(); //Gets the content layer
//        JLabel label = new JLabel(); //JLabel Creation
//        label.setIcon(new ImageIcon("fowler2.pdf")); //Sets the image to be displayed as an icon
//        Dimension size = label.getPreferredSize(); //Gets the size of the image
//        label.setBounds(50, 30, size.width, size.height); //Sets the location of the image
//        c.add(label); //Adds objects to the container

        //buttons 1 and 2 are not working need to fix. same result in
        //if (e.getSource() != b0){
        if (e.getSource() == b1){
            String locationSelected = b1.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "fowlerNames.tsv", "officialnodes.csv");
            Point2D a = scalePoints(n.position, m.scaleFactor);
            drawIcon(a, m.mapImage);
            System.out.println(a.getX() + " " + a.getY());

        }
        if (e.getSource() == b2) {
            String locationSelected = b2.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "fowlerNames.tsv", "officialnodes.csv");
            drawIcon(n.position, m.mapImage);

        }
        if (e.getSource() == b3) {
            String locationSelected = b3.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "fowlerNames.tsv", "officialnodes.csv");
            drawIcon(n.position, m.mapImage);
        }
        if (e.getSource() == b4) {
            String locationSelected = b4.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "fowlerNames.tsv", "officialnodes.csv");
            drawIcon(n.position, m.mapImage);
        }
        if (e.getSource() == b5) {
            String locationSelected = b5.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "fowlerNames.tsv", "officialnodes.csv");
            drawIcon(n.position, m.mapImage);
        }



    }


    public static void main(String[] args) {
        new searchbar();
    }



}
package fowlMap;

import edu.princeton.cs.algs4.DirectedEdge;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;


public class searchbar implements ActionListener {
    String NODES_PATH = "src/fowlMap/officialnodes.csv";
    String NAMES_PATH = "src/fowlMap/fowlerNames5.tsv";
    String[] IMAGE_PATHS = {"", "src/fowlMap/FowlerSecond.jpg", ""};
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        t0 = new JTextField();
//        t0.setBounds(50,50,300,20);
//        b1 = new JButton("");
//        //b1.setEditable(false);
//        b1.setBounds(50,70,300,20);
//        b1.addActionListener(this);
//        b2 = new JButton("");
//        //b2.setEditable(false);
//        b2.setBounds(50,90,300,20);
//        b2.addActionListener(this);
//        b3 = new JButton("");
//        //b3.setEditable(false);
//        b3.setBounds(50,110,300,20);
//        b3.addActionListener(this);
//        b4 = new JButton("");
//        //t4.setEditable(false);
//        b4.setBounds(50,130,300,20);
//        b4.addActionListener(this);
//        b5 = new JButton("");
//        b5.setBounds(50,150,300,20);
//        b5.addActionListener(this);
//       // t5.setEditable(false);
//
//
//        submitButton = new JButton("Search");
//        submitButton.setBounds(375,50,100,50);
//        submitButton.addActionListener(this);
////      adding buttons and text to frame
//        frame.add(t0);frame.add(b1);frame.add(b2);frame.add(b3);frame.add(b4);frame.add(b5);frame.add(submitButton);
//        frame.setSize(500, 500);
//        frame.setLayout(null);
//        frame.setVisible(true);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminates on closing window

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
        int x = (int)Math.round(a.getX());
        int y = (int)Math.round(a.getY());
        int r = 10;

        Graphics g2 = j.createGraphics();
        g2.setColor(Color.red);
        g2.fillOval(x-(r/2), y-r, r, r);
        g2.dispose();

    }

    public class MapPanel extends JPanel{

        public BufferedImage mapImage;
        double scaleFactor;
        double w0;
        double h0;
        HashMap<Integer, Node> nodes;
        public BufferedImage mapIn(int floorNumber){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth = (int) (screenSize.getWidth()); // 80% of screen width
            int maxHeight = (int) (screenSize.getHeight());
            int originalWidth, originalHeight, newWidth, newHeight;
            double widthRatio, heightRatio;
            BufferedImage originalImage;
            BufferedImage m;
            try {
                originalImage = ImageIO.read(new File(IMAGE_PATHS[floorNumber]));
                originalWidth = originalImage.getWidth();
                w0 = originalWidth;
                originalHeight = originalImage.getHeight();
                h0 = originalHeight;
                widthRatio = (double) maxWidth / originalWidth;
                heightRatio = (double) maxHeight / originalHeight;
                scaleFactor = Math.min(widthRatio, heightRatio);
                newWidth = (int) (originalWidth * scaleFactor);
                newHeight = (int) (originalHeight * scaleFactor);

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

            mapImage = mapIn(1);
            nodes = Node.generateNodes(NODES_PATH);
            JLabel mapLabel = new JLabel(new ImageIcon(mapImage));
            mapPanel.add(mapLabel);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame mapFrame = new JFrame();
            mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mapFrame.add(mapPanel);
            mapFrame.pack();
            mapFrame.setVisible(true);


        }//fowler map
    }

    public Point2D scalePoints(Point2D a, double scaleFactor, double w0, double h0, BufferedImage mapImage){

        Point2D b = new Point2D.Double();
        int w = mapImage.getTileWidth();
        int h = mapImage.getTileHeight();
//        System.out.println("X: " + a.getX() + "W: " + w);
//        System.out.println("Y: " + a.getY() + "H: " + h);
        b.setLocation((a.getX()*w0*scaleFactor), (a.getY()*h0*scaleFactor));
        return b;
    }

    public void drawPath(Iterable<DirectedEdge> path, MapPanel m){
        for (DirectedEdge e:path) {
            Graphics g2 = m.mapImage.createGraphics();
            g2.setColor(Color.red);
            Node n1 = m.nodes.get(e.from());
            Point2D p1 = scalePoints(n1.getPosition(), m.scaleFactor, m.w0, m.h0, m.mapImage);
            Node n2 = m.nodes.get(e.to());
            Point2D p2 = scalePoints(n2.getPosition(), m.scaleFactor, m.w0, m.h0, m.mapImage);
//            g2.setStroke(new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int)Math.round(p1.getX()),(int)Math.round(p1.getY()), (int)Math.round(p2.getX()), (int)Math.round(p2.getY()));

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s1 = t0.getText();
        ArrayList<String> results = new ArrayList<String>();
        results = search.search(s1, NAMES_PATH);
        //if we click the search button
        if (e.getSource() == submitButton) {
            // emptying old results
            b1.setText("");
            b2.setText("");
            b3.setText("");
            b4.setText("");
            b5.setText("");

            // setting new results
            b1.setText(results.get(0));
            b2.setText(results.get(1));
            b3.setText(results.get(2));
            b4.setText(results.get(3));
            b5.setText(results.get(4));
        }


//
        if (e.getSource() == b1){
            String locationSelected = b1.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , NAMES_PATH, NODES_PATH);
            Point2D a = scalePoints(n.position, m.scaleFactor, m.w0, m.h0, m.mapImage);
            drawIcon(a, m.mapImage);
            Iterable<DirectedEdge> path = Node.dijkstraSearch(m.nodes, 11, n.nodeID);
            for (DirectedEdge edge:path) {
                System.out.println(edge.toString());
            }
            drawPath(path, m);
        }
        if (e.getSource() == b2) {
            String locationSelected = b2.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , NAMES_PATH, NODES_PATH);
            Point2D a = scalePoints(n.position, m.scaleFactor, m.w0, m.h0, m.mapImage);
            drawIcon(a, m.mapImage);
            Iterable<DirectedEdge> path = Node.dijkstraSearch(m.nodes, 11, n.nodeID);
            for (DirectedEdge edge:path) {
                System.out.println(edge.toString());
            }
            drawPath(path, m);

        }
        if (e.getSource() == b3) {
            String locationSelected = b3.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , NAMES_PATH, NODES_PATH);
            Point2D a = scalePoints(n.position, m.scaleFactor, m.w0, m.h0, m.mapImage);
            drawIcon(a, m.mapImage);            Iterable<DirectedEdge> path = Node.dijkstraSearch(m.nodes, 11, n.nodeID);
            for (DirectedEdge edge:path) {
                System.out.println(edge.toString());
            }
            drawPath(path, m);
        }
        if (e.getSource() == b4) {
            String locationSelected = b4.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , NAMES_PATH, NODES_PATH);
            Point2D a = scalePoints(n.position, m.scaleFactor, m.w0, m.h0, m.mapImage);
            drawIcon(a, m.mapImage);            Iterable<DirectedEdge> path = Node.dijkstraSearch(m.nodes, 11, n.nodeID);
            for (DirectedEdge edge:path) {
                System.out.println(edge.toString());
            }
            drawPath(path, m);
        }
        if (e.getSource() == b5) {
            String locationSelected = b5.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , NAMES_PATH, NODES_PATH);
            Point2D a = scalePoints(n.position, m.scaleFactor, m.w0, m.h0, m.mapImage);
            drawIcon(a, m.mapImage);
            Iterable<DirectedEdge> path = Node.dijkstraSearch(m.nodes, 11, n.nodeID);
            for (DirectedEdge edge:path) {
                System.out.println(edge.toString());
            }
            drawPath(path, m);
        }



    }


    public static void main(String[] args) {
        new searchbar();
    }



}
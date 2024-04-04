package fowlMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


public class searchbar implements ActionListener {
//  building search bar
    static JTextField t0;
//  building buttons
    JButton submitButton;JButton b1;JButton b2;JButton b3;JButton b4;JButton b5;
    searchbar() {
        JFrame frame = new JFrame(); //creating

        t0 = new JTextField();
        t0.setBounds(50,50,300,20);
        b1 = new JButton("");
        //b1.setEditable(false);
        b1.setBounds(50,70,300,20);
        b1.addActionListener(this);
        b2 = new JButton("");
        //b2.setEditable(false);
        b2.setBounds(50,90,300,20);
        b2.addActionListener(this);
        b3 = new JButton("");
        //b3.setEditable(false);
        b3.setBounds(50,110,300,20);
        b3.addActionListener(this);
        b4 = new JButton("");
        //t4.setEditable(false);
        b4.setBounds(50,130,300,20);
        b4.addActionListener(this);
        b5 = new JButton("");
        b5.setBounds(50,150,300,20);
        b5.addActionListener(this);
       // t5.setEditable(false);


        submitButton = new JButton("Search");
        submitButton.setBounds(375,50,100,50);
        submitButton.addActionListener(this);
//      adding buttons and text to frame
        frame.add(t0);frame.add(b1);frame.add(b2);frame.add(b3);frame.add(b4);frame.add(b5);frame.add(submitButton);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//terminates on closing window

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

    public void drawIcon(Point a, BufferedImage j){
        int x = (int)a.getX();
        int y = (int) a.getY();
        int r = 20;

        Graphics g2 = j.createGraphics();
        g2.setColor(Color.red);
        g2.fillOval(x-(r/2), y-(r*2), r, r);
        System.out.println(x +" and "+ x);
        g2.dispose();

    }

    public class MapPanel extends JPanel{

        public BufferedImage mapImage;
        double scaleFactor;
        public BufferedImage mapIn(){
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int maxWidth = (int) (screenSize.getWidth() * 0.8); // 80% of screen width
            int maxHeight = (int) (screenSize.getHeight() *0.5);
            int width, height, newWidth, newHeight;
            double widthRatio, heightRatio;
            BufferedImage originalImage;
            BufferedImage m;
            try {
                originalImage = ImageIO.read(new File("src/fowlMap/FowlerSecond.jpg"));
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
            mapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mapFrame.add(mapPanel);
            mapFrame.pack();
            mapFrame.setVisible(true);


        }//fowler map
    }

    public Point scalePoints(Point a, double scaleFactor){
        int x = (int) (a.getX()*scaleFactor);
        int y = (int) (a.getY()*scaleFactor);
        Point b = new Point(x,y);
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
            results = search.search(s1, "src/fowlMap/fowlerNames.tsv");
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
            Node n = search.searchNode(locationSelected , "src/fowlMap/fowlerNames.tsv");
            Point a = scalePoints(n.position, m.scaleFactor);
            drawIcon(a, m.mapImage);
            System.out.println(a.getX() + " " + a.getY());

        }
        if (e.getSource() == b2) {
            String locationSelected = b2.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "src/fowlMap/fowlerNames.tsv");
            drawIcon(n.position, m.mapImage);

        }
        if (e.getSource() == b3) {
            String locationSelected = b3.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "src/fowlMap/fowlerNames.tsv");
            drawIcon(n.position, m.mapImage);
        }
        if (e.getSource() == b4) {
            String locationSelected = b4.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "src/fowlMap/fowlerNames.tsv");
            drawIcon(n.position, m.mapImage);
        }
        if (e.getSource() == b5) {
            String locationSelected = b5.getText();
            MapPanel m = new MapPanel();
            Node n = search.searchNode(locationSelected , "src/fowlMap/fowlerNames.tsv");
            drawIcon(n.position, m.mapImage);
        }



    }





    public static void main(String[] args) {
        new searchbar();
    }



}
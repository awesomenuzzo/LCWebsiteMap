package fowlMap;
import edu.princeton.cs.algs4.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class room {

    public static ArrayList<Location> readLocationsFromCSV(String fileName) {
        ArrayList<Location> locations = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);
//        String line = "";
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] row = line.split(splitBy); // use comma as separator
                Location l = createlocation(row);
                locations.add(l);
//                line = br.readline();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return locations;
    }

    private static Location createlocation(String[] dataSet) {
        String name = dataSet[1];
        String roomNumber = dataSet[0];
        int nodeID = Integer.parseInt(dataSet[2]);
        return new Location(name, roomNumber, nodeID);
    }

    public static class Location {
        private String name;
        private String roomNumber;
        private int nodeID ;

        public Location(String name, String roomNumber, int nodeID){
            this.name = name;
            this.roomNumber = roomNumber;
            this.nodeID = nodeID; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getRoomNumber() { return roomNumber; }
        public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
        public int getNodeID() { return nodeID; }
        public void setNodeID(int nodeID) { this.nodeID = nodeID; }
        @Override public String toString()
        { return "[Room Name =" + this.name + ", Room Number=" + this.roomNumber + ", Node ID=" + this.nodeID + "]"; } }


    public static void main(String[] args){
//
        ArrayList<Location> locations = readLocationsFromCSV("src/fowlMap/fowlerNames4.csv");
        for (Location s: locations) {
            System.out.print(s + "\n");
        }

    }

    //test with pre-existing string

}


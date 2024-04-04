package fowlMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Location {
    int nodeID;

//    public static ArrayList<Room> readRoomsFromCSV(String fileName) {
//        ArrayList<Room> rooms = new ArrayList<>();
//        Path pathToFile = Paths.get(fileName);
////        String line = "";
//        String splitBy = ",";
//        try {
//            //parsing a CSV file into BufferedReader class constructor
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            String line = br.readLine();
//            while ((line = br.readLine()) != null)   //returns a Boolean value
//            {
//                String[] row = line.split(splitBy); // use comma as separator
//                Room l = createlocation(row);
//                rooms.add(l);
////                line = br.readline();
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return rooms;
//    }

//    private static Room createlocation(String[] dataSet) {
//        String name = dataSet[1];
//        String roomNumber = dataSet[0];
//        int floorNumber = Integer.parseInt(dataSet[2]);
//        return new Room(name, roomNumber, floorNumber, nodeID);
//    }

    public static class Room extends Location {
        private String name;
        private String roomNumber;
        private int floorNumber ;
        private int nodeID;

        public Room(String name, String roomNumber, int floorNumber, int nodeID){
            this.name = name;
            this.roomNumber = roomNumber;
            this.floorNumber = floorNumber;
            this.nodeID = nodeID;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getRoomNumber() { return roomNumber; }
        public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
        public int getFloorNumber() { return floorNumber; }
        public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }
        public int getNodeID(){return this.nodeID;}
        @Override public String toString()
        { return "[Room Name =" + this.name + ", Room Number=" + this.roomNumber + ", Floor Number=" + this.floorNumber + "]"; }

    }



//    public static void main(String[] args){
////
//        ArrayList<Room> rooms = readRoomsFromCSV("src/fowlMap/fowlerNames4.csv");
//
//
//    }

    //test with pre-existing string

}


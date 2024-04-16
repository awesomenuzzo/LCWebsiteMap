package fowlMap;

import edu.princeton.cs.algs4.*;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class search {

    public static HashMap<String, Location.Room> fileIn(String fileName) {
        HashMap<String, Location.Room> displayToSearch = new HashMap<String, Location.Room>();
        ArrayList<String> poi = new ArrayList<String>();
        String line = "";
        String splitBy = "\t";
        int count = 0;
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {

                String[] row = line.split(splitBy);
                int rownum = 0;
//                for (String s:row) {
//                    System.out.println(rownum + " " +s);
//
//                }
//                rownum++;
                String[] list = row[4].split("\\|");

                Location.Room room = new Location.Room(row[1], row[0], Integer.parseInt(row[3]), Integer.parseInt(row[2]));
//                System.out.println(room.getRoomNumber());
                for (String s: list) {
                    s = s.toLowerCase(Locale.ROOT);
                    displayToSearch.put(s, room);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return displayToSearch;
    }


    public static TrieSET populateTrie(Set<String> poi){
        TrieSET data = new TrieSET();
        for (String s: poi) {
            s = s.toLowerCase();
            data.add(s);
//            System.out.println(s);
        }
        return data;
    }


    public static ArrayList<String> transformIterable(Iterable<String> strings){
        ArrayList<String> result = new ArrayList<String>();
        for (String s: strings) {
            result.add(s);
        }
        return result;
    }

    public static class RoomCache {
        private static HashMap<String, HashMap> files = new HashMap<>();
        public static HashMap fetchDict(String filePath) {
            if (files.containsKey(filePath)) {
                return files.get(filePath);
            } else {
                HashMap<String, Location.Room> dict = fileIn(filePath);
                files.put(filePath, dict);
                return dict;
            }

        }
    }

    public static class TrieFactory {
        private static HashMap<HashMap, TrieSET> files = new HashMap<>();
        public static TrieSET fetchTrie(HashMap dict) {
            if (files.containsKey(dict)) {
                return files.get(dict);
            } else {
                TrieSET data = load_data(dict);
                files.put(dict, data);
                return data;
            }

        }
    }

    public static ArrayList<String> search(String query, String filePath){
        HashMap<String, Location.Room> dict = fileIn(filePath);
        TrieSET data = load_data(dict);
        query = query.toLowerCase();
        Iterable<String> prefixes = data.keysWithPrefix(query);
        ArrayList<String> array_prefixes = transformIterable(prefixes);
        ArrayList<String> result = new ArrayList<>();
        Iterator<String> interior = data.iterator();
        for (Iterator<String> it = interior; it.hasNext(); ) {
            String s = it.next();
            Location.Room r = dict.get(s);
            String display_name = r.getName();
//            System.out.println(s);
            if((s.contains(query)) && !result.contains(display_name)){
                result.add(display_name);
            }else {
                if (s.contains(query)) {
                    if (!result.contains(display_name)) {
                        result.add(display_name);
                    }
                }
            }
        }
        return result;
    }



    public static TrieSET load_data(HashMap<String, Location.Room> dict){
        Set<String> input = dict.keySet();
        TrieSET tree = populateTrie(input);
        return tree;
    }

    public static class NodeCache {
        public static HashMap<Integer, Node> nodes;
        public static void makeNodes(String nodesFileName){ nodes = Node.generateNodes(nodesFileName);}
        public static Node findNode(int nodeID) {
            return nodes.get(nodeID);
        }

    }

    public static Node searchNode(String name, String fileName, String nodesFileName){
        HashMap<String, Location.Room> dict = fileIn(fileName);
        Location.Room l = dict.get(name.toLowerCase(Locale.ROOT));
        NodeCache.makeNodes(nodesFileName);
        return NodeCache.findNode(l.getNodeID());
    }

    public static Integer getFloorNumber(int query, String filePath){
        HashMap<String, Location.Room> dict = RoomCache.fetchDict(filePath);
        return dict.get(query).getFloorNumber();
    }


    public static Integer getFloorNumberByNodeId(int nodeId, String filePath) {
        HashMap<Integer, Integer> nodeToFloor = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int currentNodeId = Integer.parseInt(values[0]);  // Node ID is in the first column
                int floorNumber = Integer.parseInt(values[4]);   // Floor number is in the fifth column
                nodeToFloor.put(currentNodeId, floorNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle exceptions or invalid file path
        }
        return nodeToFloor.get(nodeId);
    }

    //test with pre-existing string
    public static void main(String[] args){

        ArrayList<String> result = search("bathroom" , "src/fowlMap/fowlerNames5.tsv");
        for (String s:result) {
//            Node r = searchNode(s, "src/fowlMap/fowlerNames1.tsv", "src/fowlMap/officialnodes.csv");
//            System.out.println(r.toStringAll());
            System.out.println(s);
        }
//


    }
}


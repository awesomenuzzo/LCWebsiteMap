package fowlMap;

import edu.princeton.cs.algs4.*;

import java.lang.reflect.Array;
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
                String[] list = row[3].split("\\|");

                Location.Room room = new Location.Room(row[1], row[0], Integer.parseInt(row[2]), Integer.parseInt(row[6]));
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
        HashMap dict = RoomCache.fetchDict(filePath);
        TrieSET data = TrieFactory.fetchTrie(dict);
        query = query.toLowerCase();
        Iterable<String> prefixes = data.keysWithPrefix(query);
        ArrayList<String> result = transformIterable(prefixes);
        Iterator<String> interior = data.iterator();
        for (Iterator<String> it = interior; it.hasNext(); ) {
            String s = it.next();
            if(s.contains(query)){
                if(!result.contains(s)) {
                    result.add(s);
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
        public static HashMap<Integer, Node> nodes = Node.generateNodes("src/fowlMap/officialnodes.csv");
        public static Node findNode(int nodeID) {
            return nodes.get(nodeID);
        }

    }

    public static Node searchNode(String name, String fileName, String nodesFileName){
        HashMap<String, Location.Room> dict = RoomCache.fetchDict(fileName);
        Location.Room l = dict.get(name);
        return NodeCache.findNode(l.getNodeID());
    }

    public static Integer getFloorNumber(String query, String filePath){
        HashMap<String, Location.Room> dict = RoomCache.fetchDict(filePath);
        return dict.get(query).getFloorNumber();
    }

    //test with pre-existing string
    public static void main(String[] args){

        ArrayList<String> result = search("troom", "src/fowlMap/fowlerNames.tsv");
        for (String s:result) {
            Node r = searchNode(s, "src/fowlMap/fowlerNames.tsv", "src/fowlMap/officialnodes.csv");
            System.out.println(r.toStringAll());
        }
//


    }
}


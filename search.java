package fowlMap;
import edu.princeton.cs.algs4.*;

import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class search {

    public static ArrayList<String> fileIn(String fileName) {
        ArrayList<String> poi = new ArrayList<String>();
        String line = "";
        String splitBy = ",";

        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] row = line.split(splitBy);    // use comma as separator
                poi.add(row[1]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return poi;
    }


    public static TrieSET populateTrie(ArrayList<String> poi){
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

    public static ArrayList<String> search(String query, TrieSET data){
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

    //test with pre-existing string
    public static void main(String[] args){
//
        ArrayList<String> input = fileIn("src/fowlMap/fowlerNames4.csv");
        TrieSET tree = populateTrie(input);
        ArrayList<String> result = search("bathroom", tree);
        for (String s: result) {
            System.out.print(s + "\n");
        }

    }
}


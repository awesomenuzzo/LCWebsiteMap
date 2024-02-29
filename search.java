package fowlMap;
import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class search {

    public static TrieSET populateTrie(String[] poi){
        TrieSET data = new TrieSET();
        for (String s: poi) {
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
        String text =  "We the People of the United States, in Order to form a more perfect " +
                "Union, establish Justice, insure domestic Tranquility, provide for the " +
                "common defence, promote the general Welfare, and secure the Blessings " +
                "of Liberty to ourselves and our Posterity, do ordain and establish " +
                "this Constitution for the United States of America.";
        String[] input = text.split(" ");

        TrieSET tree = populateTrie(input);
        ArrayList<String> result = search("t", tree);
        for (String s: result) {
            System.out.print(s + "\n");
        }

    }
}


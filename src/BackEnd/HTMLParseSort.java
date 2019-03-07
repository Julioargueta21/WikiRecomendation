package BackEnd;

import com.sun.source.tree.Tree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;


public class HTMLParseSort {

    public static String grabWebPage() throws IOException {
        Document webDoc = Jsoup.connect("https://en.wikipedia.org/wiki/Steve_Jobs").userAgent("Mozilla").data("name", "jsoup").get();
        Elements webElements = webDoc.select("div#mw-content-text");

        //This Makes the out.file (But we need to read from a control file)

        //PrintStream fileOut = new PrintStream(new File("control.txt"));
        //System.setOut(fileOut);
        String elements = null;
        for (Element el : webElements) {
            elements = el.text(); }
        return elements;
    }

    static String[] test = {"test", "test", "test",  "SingularWord"};

    // Counts Amount of words in array and puts words with keys on a hashmap
    public static HashMap countStrings(String[] wordList){
        HashMap map = new HashMap();
        Integer ONE = new Integer(1);

        for (int i=0, n=wordList.length; i<n; i++) {
            String key = wordList[i];
            Integer frequency = (Integer)map.get(key);
            if (frequency == null) {
                frequency = ONE;
            } else {
                int value = frequency.intValue();
                frequency = new Integer(value + 1);
            }
            map.put(key, frequency);

    }
        return map;
    }


    public static TreeMap sortDescending(HashMap unSortedWordMap){
        TreeMap sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(unSortedWordMap);
        return sortedMap;
    }


        public static void main(String[] args) throws IOException  {
        System.out.println(grabWebPage());
            System.out.println(sortDescending(countStrings(test)));

        }



    }


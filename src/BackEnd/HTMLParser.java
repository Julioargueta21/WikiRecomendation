package BackEnd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;

public class HTMLParser {
    static File ctrlFile = new File("control.txt");




    public static String grabWebPage() throws IOException {
        // Reads From Control File
        Scanner sc = new Scanner(ctrlFile);
        String link = "";
        Document webDoc;
        Elements webElements = null;

        for (int amountOfLinks = 0 ; amountOfLinks < 10 && sc.hasNextLine(); amountOfLinks++) {
            System.out.println(amountOfLinks);
            System.out.println(link);
            link = sc.nextLine();
            webDoc = Jsoup.connect(link).userAgent("Mozilla").data("name", "jsoup").get();
            webElements = webDoc.select("div#mw-content-text");


            // To make File titles
            String[] shortLink = link.split("^(.*[\\\\\\/])");

            StringBuilder builder = new StringBuilder();
            for (String value : shortLink) {
                builder.append(value);
            }
            String text = builder.toString();
            //This Makes the output  (Makes a output ctrlFile and cuts off System.out (GUI Depends on this being false))
            PrintStream fileOut = new PrintStream(new File(text + ".txt"));

            System.setOut(fileOut);

        }
        // Parse and returns raw text
        String elements = null;
        for (Element el : webElements) {
            elements = el.text();
        }

        return elements;

}


    public static void printListVertically(List<Entry<String, Long>> listEntry) {
        for (Entry<String, Long> entry : listEntry) {
            String key = entry.getKey();
            Long value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }

    public static List<Map.Entry<String, Long>> filterAndSort(String string, String regex) {

        //
        Map<String, Long> frequencyMap = Arrays.stream(string.split("\\s+"))
                .filter(word -> word.matches("\\b\\w{5,}\\b"))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        Comparator<Map.Entry<String, Long>> byValue = Comparator.comparing(Map.Entry::getValue);

        //
        List<Map.Entry<String, Long>> sortedByFrequency = frequencyMap.entrySet()
                .stream()
                .sorted(byValue.reversed())
                .collect(Collectors.toList());

        return sortedByFrequency;

    }

    public void run(String regex) throws IOException {
        printListVertically(filterAndSort(grabWebPage(), regex));
    }

    // Constructor
    public HTMLParser() throws IOException {
        
        run("[^a-zA-Z\\s]+|\\b\\w{0,4}\\b");
    }

}


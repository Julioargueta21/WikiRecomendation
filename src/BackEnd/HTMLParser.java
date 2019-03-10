package BackEnd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;

public class HTMLParser {

    public static String grabWebPage() throws IOException {
        Document webDoc = Jsoup.connect("https://en.wikipedia.org/wiki/Steve_Jobs").userAgent("Mozilla").data("name", "jsoup").get();
        Elements webElements = webDoc.select("div#mw-content-text");
        //This Makes the out.file (But we need to read from a control file)
        //PrintStream fileOut = new PrintStream(new File("control.txt"));
        //System.setOut(fileOut);
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
        Comparator<Map.Entry<String, Long>> byValue = Comparator.comparing( Map.Entry::getValue);

        //
        List<Map.Entry<String, Long>> sortedByFrequency = frequencyMap.entrySet()
                .stream()
                .sorted(byValue.reversed())
                .collect( Collectors.toList());

        return sortedByFrequency;

    }

    public static void run(String regex) throws IOException {
        printListVertically(filterAndSort(grabWebPage(), regex));
    }

    // Constructor
    public HTMLParser() throws IOException{

        run("[^a-zA-Z\\s]+|\\b\\w{0,4}\\b");
    }

}


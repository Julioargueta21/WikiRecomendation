package BackEnd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import GUI.UI;

import static java.lang.Thread.sleep;

public class HTMLParser {
    static File ctrlFile = new File("control.txt");
    static Scanner sc;

    static {
        try {
            sc = new Scanner(ctrlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static int amountOfLinks;


    public static void clearFiles() {
        File folder = new File("/");
        File fList[] = folder.listFiles();

        for (File file : fList) {
            if (file.getName().endsWith(".txt")) {
                file.delete();
            } else if (file.getName().startsWith("control.txt")) {
            }
//TODO: FIX THIS
        }
    }


    public static void grabWebPage(boolean useCustomURL) throws IOException {
        // Reads From Control File
        clearFiles();
        String link = "";
        Document webDoc;
        Elements webElements;
        String elements = "";


        if (useCustomURL) {
            /// Grab Text from gui
            link = UI.getURLTxtBox();

            webDoc = Jsoup.connect(link + "").userAgent("Mozilla").data("name", "jsoup").get();
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
           // System.out.println(text);

            System.setOut(fileOut);
            for (Element el : webElements) {
                elements = el.text();

            }

            //--DEBUG
            //System.out.println(amountOfLinks);
            // System.out.println(link);
            printListVertically(filterAndSort(elements));


        } else if (!useCustomURL) {
            for (amountOfLinks = 0; amountOfLinks < 10 && sc.hasNextLine(); amountOfLinks++) {
               // System.out.println(amountOfLinks);

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
                // System.out.println(text);

                System.setOut(fileOut);
                for (Element el : webElements) {
                    elements = el.text();

                }
                //--DEBUG
                //System.out.println(amountOfLinks);
                // System.out.println(link);
                printListVertically(filterAndSort(elements));
                // System.out.println(link);
            }
        }
    }

    // Parse and returns raw text
    public static List<Map.Entry<String, Long>> filterAndSort(String string) {
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

    public static void printListVertically(List<Entry<String, Long>> listEntry) {

        for (Entry<String, Long> entry : listEntry) {
            String key = entry.getKey();
            Long value = entry.getValue();
            System.out.println(key + " = " + value);
        }

    }

    // Constructor
    public HTMLParser() throws IOException {
        // UI supposed to set bool

    }
}


package BackEnd;

import GUI.TerminalUI;
import GUI.UI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new TerminalUI();
        new UI();
    }


}

// TODO: The program reads 10 (or more) web pages (10/10)

// TODO: The urls for these web pages can be maintained in a control ctrlFile that is read when the program starts. (YES)

// TODO: For each page, the program maintains frequencies of words. (10/10)

// TODO: The user can enter any other URL (0/1)

// TODO: The program reports which other known page is most closely related, using a similarity metric of your choosing. (0/1)
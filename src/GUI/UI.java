package GUI;

import javax.swing.*;
import java.awt.*;

public class UI {
    public UI(){
    JFrame frame = new JFrame("Shit");
        // Shitty Panel
        JPanel panel = new JPanel();
        //Frame Stuff
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);




    //GridBagLayout
    //frame.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();



    //url stuff
    JLabel urlLabel = new JLabel("Search using URL:  ");
    JTextField urlField= new JTextField(20);

    //gobutton Stuff
    JButton goButton = new JButton("Go");
    //filebutton Stuff
    JButton fileButton = new JButton("Search using txt file" );

    //empty label stuff
    JLabel emptyLabel = new JLabel("   ");


    //urlLabel locations
    gc.gridy = 0; // row 0
    gc.gridx = 0; // column 0
    gc.anchor = GridBagConstraints.LINE_END;
    panel.add(urlLabel,gc);

    //Field(textbox) locations
    gc.gridy = 0; // row 0
    gc.gridx = 1; // column 1
    gc.anchor = GridBagConstraints.LINE_START;
    panel.add(urlField,gc);

    //GoButton locations
    gc.gridy = 1;
    gc.gridx = 1;
    gc.anchor = GridBagConstraints.LINE_END;
    panel.add(goButton, gc);

    //empty label location
    gc.gridy = 3;
    gc.gridx = 1;
    gc.anchor = GridBagConstraints.LINE_START;
    panel.add(emptyLabel, gc);


    //Search using txt file
    gc.gridy = 4;
    gc.gridx = 1;
    gc.anchor = GridBagConstraints.LINE_START;
    panel.add(fileButton, gc);

}

}

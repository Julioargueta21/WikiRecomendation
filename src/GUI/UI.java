package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {

    static String urlTxtBox = "";
    static boolean isGoButtonClicked = false;

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





        //url stuff
        JLabel urlLabel = new JLabel("Search using URL:  ");
        JTextField urlField= new JTextField(20);



        //gobutton Stuff
        JButton goButton = new JButton("Go");

        goButton.addActionListener(ae -> {
            urlTxtBox = urlField.getText();
            isGoButtonClicked = true;
            // .... do some operation on value ...
        });


        //filebutton Stuff
        JButton fileButton = new JButton("Search using txt file" );

        //empty label stuff
        JLabel emptyLabel = new JLabel("   ");



    //GridBagLayout
    //frame.setLayout(new GridBagLayout());
    GridBagConstraints gc = new GridBagConstraints();

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



    public static String getURLTxtBox(){
        System.out.println(urlTxtBox);
        return urlTxtBox;
    }

    public static boolean getGoButtonState(){
        System.out.println(" is button clicked "+ isGoButtonClicked  );

        return  isGoButtonClicked;
    }
}

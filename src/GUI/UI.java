package GUI;

import BackEnd.HTMLParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class UI {

    public static void main(String[] args) throws IOException{
        new UI();
    }

    public UI() throws IOException{
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                JPanel panel = new JPanel();

                JTextField urlField;
                JLabel urlLabel;
                JLabel emptyLabel;

                JButton goButton;
                JButton fileButton;

                //Frame Stuff
                frame.setSize(800,800);
                frame.setLocationRelativeTo(null);

                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);


                // GridBagLayout
               // panel.setLayout(new GridBagLayout());
                //GridBagConstraints gc= new GridBagConstraints();

                //terminal stuff
                TerminalOutputPane terminalOutputPanel = new TerminalOutputPane();
                panel.add(terminalOutputPanel);



                //url stuff
                //urlLabel = new JLabel("Search using URL:  ");
               // urlField= new JTextField(20);

                //gobutton Stuff
               // goButton = new JButton("Go");
                //filebutton Stuff
                //fileButton = new JButton("Search using txt file" );

                //empty label stuff
               // emptyLabel = new JLabel("   ");


                // terminals location
                /*gc.gridy = 0 ; //row 0
                gc.gridx = 2; //column 2
                gc.anchor = GridBagConstraints.LINE_START;
                panel.add(terminalOutputPanel, gc);

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
                panel.add(fileButton, gc);*/


                PrintStream ps = System.out;
                System.setOut(new PrintStream(new StreamCapturer("STDOUT", terminalOutputPanel, ps)));

                HTMLParser runHTMLParse = null;
                try {
                    runHTMLParse = new HTMLParser();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public class TerminalOutputPane extends JPanel implements Consumer {
        private JTextArea txtArea;

        public TerminalOutputPane() {
            setLayout(new BorderLayout());
            txtArea = new JTextArea();
            add(new JScrollPane( txtArea ));
        }

        @Override
        public void appendText(final String text) {
            if (EventQueue.isDispatchThread()) {
                txtArea.append(text);
                txtArea.setCaretPosition( txtArea.getText().length());
            } else {

                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        appendText(text);
                    }
                });

            }
        }
    }

    public interface Consumer {
        public void appendText(String text);
    }

    public class StreamCapturer extends OutputStream {

        private StringBuilder buffer;
        private String prefix;
        private Consumer consumer;
        private PrintStream old;

        public StreamCapturer(String prefix, Consumer consumer, PrintStream old) {
            this.prefix = prefix;
            buffer = new StringBuilder(128);
            buffer.append("[").append(prefix).append("] ");
            this.old = old;
            this.consumer = consumer;
        }

        @Override
        public void write(int b) throws IOException {
            char c = (char) b;
            String value = Character.toString(c);
            buffer.append(value);
            if (value.equals("\n")) {
                consumer.appendText(buffer.toString());
                buffer.delete(0, buffer.length());
                buffer.append("[").append(prefix).append("] ");
            }
            old.print(c);
        }
    }
}
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

                TerminalOutputPane terminalOutputPane = new TerminalOutputPane();
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                terminalOutputPane.setSize( 300,300 );
                frame.add( terminalOutputPane );
                frame.setSize(800, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                PrintStream ps = System.out;
                System.setOut(new PrintStream(new StreamCapturer("STDOUT", terminalOutputPane, ps)));


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
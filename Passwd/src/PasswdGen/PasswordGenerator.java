package PasswdGen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PasswordGenerator extends JFrame implements ActionListener {
    JFrame frame, frame2, frame3;
    JButton button, copy, delete, button2;
    JTextField textField, textField2;
    JButton[] buttons = new JButton[100];
    JLabel label, label2, label3;
    int opened = 0;
    int offset = 0;
    PasswordGenerator() {
        frame2 = new JFrame();
        frame2.setLayout(null);
        frame2.setSize(800, 500);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(false);
        frame2.setResizable(false);
        try {
            frame2.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("passwd_background.jpg")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        label = new JLabel();
        label.setBounds(120, 20, 400, 50);
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Haettenschweiler", Font.PLAIN, 75));

        label2 = new JLabel();
        label2.setBounds(270, 110, 400, 50);
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Haettenschweiler", Font.PLAIN, 75));

        label3 = new JLabel();
        label3.setBounds(270, 200, 400, 50);
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Haettenschweiler", Font.PLAIN, 50));

        delete = new JButton();
        delete.setBounds(230, 310, 150, 50);
        delete.addActionListener(this);
        delete.setText("delete");

        copy = new JButton();
        copy.setBounds(30, 310, 150, 50);
        copy.addActionListener(this);
        copy.setText("copy");

        frame3 = new JFrame();
        frame3.setLayout(null);
        frame3.setSize(800, 500);
        frame3.setLocationRelativeTo(null);
        frame3.setVisible(false);
        frame3.setResizable(false);
        try {
            frame3.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("list_background.jpg")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame3.add(label);
        frame3.add(label2);
        frame3.add(label3);
        frame3.add(delete);
        frame3.add(copy);

        button = new JButton();
        button.setBounds(30, 310, 150, 50);
        button.addActionListener(this);
        button.setText("generate");

        button2 = new JButton();
        button2.setBounds(230, 310, 150, 50);
        button2.addActionListener(this);
        button2.setText("show passwords");

        textField = new JTextField();
        textField.setBounds(165, 150, 200, 50);

        textField2 = new JTextField();
        textField2.setBounds(165, 240, 200, 50);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("main_background.jpg")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(button);
        frame.add(button2);
        frame.add(textField);
        frame.add(textField2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            if (textField.getText().equals("") || textField2.getText().equals("")) {
                button.setText("chyba");
                button.setForeground(Color.RED);
            } else{
            Log log = new Log();
            Password password = new Password();
            log.Write(textField.getText(), textField2.getText(), password.generate());
                button.setText("generate");
                button.setForeground(Color.BLACK);
            }
        }
        if(e.getSource() == copy){
            Log log = new Log();
            int z = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("ListOfSites"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(opened == z){
                        StringSelection stringSelection = new StringSelection(log.ReadPassword(line));
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    }
                    z++;
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() == button2){
            frame2.setVisible(true);
            int x = 0;
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(
                        "ListOfSites"));
                String line = reader.readLine();
                while (line != null) {
                    if(buttons[x] == null){
                        buttons[x] = new JButton();
                        buttons[x].setBounds(0, 100*x + 100, 150, 50);
                        buttons[x].addActionListener(this);
                        buttons[x].setText(line);
                        frame2.add(buttons[x]);
                    }
                    x++;
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        if(e.getSource() == delete){
            String text = "";
            try (BufferedReader br = new BufferedReader(new FileReader("ListOfSites"))) {
                String line;
                int z = 0;
                while ((line = br.readLine()) != null) {
                    if(opened != z){
                        text += line + "\n";
                    }
                    z++;
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try(FileWriter file = new FileWriter("ListOfSites")){
                file.write(text);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            buttons[opened].setVisible(false);
        }
        for(int x = 0; x < buttons.length; x++){
            if(e.getSource() == buttons[x]){
                opened = x;
                frame3.setVisible(true);
                BufferedReader reader;
                Log log = new Log();
                int y = 0;
                try {
                    reader = new BufferedReader(new FileReader(
                            "ListOfSites"));
                    String line = reader.readLine();
                    while (line != null) {
                        if(y == x){
                            label.setText(line);
                            label2.setText(log.ReadUsername(line));
                            label3.setText(log.ReadPassword(line));
                        }
                        y++;
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }
}

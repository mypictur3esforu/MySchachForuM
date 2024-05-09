/**
 * MySchachForu(m)
 */
import javax.swing.*;
import java.awt.*;

public class MySchachForuM {
    static Programm programm;
    public static void main(String[] args) {
        CreateUI();
        programm = new Programm();
        //programm.start();
    }

    static void klickFeld(String column, int row){
        String moveHalfOne = column;
        moveHalfOne += row;
        System.out.println("moveHalfOne: " + moveHalfOne);
    }

    static void CreateUI(){
        JFrame frame = new JFrame("Schach");
        frame.setSize(1400, 1400);
        frame.setBounds(0,0,400,400);
        JPanel panel = new JPanel(new GridLayout(8,8));
        frame.getContentPane().add(panel);
        for (int i = 1; i <= 8; i++) {
            for (int z = 0; z < 8; z++) {
               final String column = Programm.NumberToLetterConverter(z);
                JButton b=new JButton("Hallo");
                panel.add(b);
                final int row = i;
                b.addActionListener((ev)-> {
                    klickFeld(column, row);
                });
            }
        }

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

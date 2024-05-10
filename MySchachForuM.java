/**
 * MySchachForu(m)
 */
import javax.swing.*;
import java.awt.*;

public class MySchachForuM {
    static Programm programm;
    static String colour = "white", move = "";
    static int moveOrder = 0;
    public static void main(String[] args) {
        CreateUI();
        programm = new Programm();
        //programm.start();
    }

    static void klickFeld(String column, int row){
        String moveHalfOne = "", moveHalfTwo = "";
        if (moveOrder == 0) {
            move = column;
            move += row;
            moveOrder = 1;
        } else if (moveOrder == 1) {
            move += column;
            move += row;
            programm.start();
            moveOrder = 0;
        }
        System.out.println("move: " + move);
    }

    static void CreateUI(){
        JFrame frame = new JFrame("Schach");
        frame.setBounds(0,0,400,400);
        JPanel panel = new JPanel(new GridLayout(8,8));

        for (int i = 8; i > 0; i--) {
            for (int z = 0; z < 8; z++) {
                final String column = Programm.NumberToLetterConverter(z);
                JButton b=new JButton("1");
                panel.add(b);
                if (colour.equals("white")) {
                    b.setBackground(Color.white);
                }else{
                    b.setBackground(Color.black);
                }
                SwitchColor();
                final int row = i;
                b.addActionListener((ev)-> {
                    klickFeld(column, row);
                });
            }
            SwitchColor();
        }
        frame.getContentPane().add("Center", panel);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    static void SwitchColor(){
        if (colour.equals("white")) {
            colour = "black";
        }else{
            colour = "white";
        }
    }
}

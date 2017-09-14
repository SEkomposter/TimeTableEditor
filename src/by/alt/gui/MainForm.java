package by.alt.gui;


import javax.swing.*;
import java.awt.event.WindowEvent;

public class MainForm extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel timeTables;

    public static void main(String[] args) {
        new MainForm();
    }
    public MainForm(){
        setBounds(0,0,250,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.setVisible(true);
        timeTables = new JPanel();
        timeTables.setBounds(15,15,200,100);
        timeTables.setVisible(true);
        tabbedPane1.add(timeTables);
        panel1 = new JPanel();
        panel1.setVisible(true);
        panel1.add(tabbedPane1);
        add(panel1);
    }
}

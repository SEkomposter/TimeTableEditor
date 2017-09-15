package by.alt.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class MainForm extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private TimeTableTab timeTables;
    private DepartmentsTab depTab;
    private JTable table1;
    private Font font;
    private MyMenuBar menuBar;

    public static void main(String[] args) {
        new MainForm();
    }
    public MainForm(){
        setBounds(0,0,1000,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        menuBar = new MyMenuBar();

        //panel1 = new JPanel(new FlowLayout());
        this.setJMenuBar(menuBar);
        tabbedPane1 = new myTabbedPane(this.getX(),this.getY(),this.getWidth(),getHeight());
        getContentPane().add(tabbedPane1);

    }
    class myTabbedPane extends JTabbedPane{
        myTabbedPane(int x, int y,int w, int h){
            setBounds(x, y+100, w, h);
            setVisible(true);
            timeTables = new TimeTableTab(x, y+100, w, h);
            this.addTab("Расписания",timeTables);
            depTab = new DepartmentsTab(x, y+100, w, h);
            this.addTab("Подразделения",new DepartmentsTab());

        }
    }

    class TimeTableTab extends JPanel{
        TimeTableTab(int x, int y,int w, int h){
            setBounds(0, 0, 800, 600);
            setVisible(true);
            //setLayout(lm);
        }
    }
    class DepartmentsTab extends JPanel{
        DepartmentsTab(){}
        DepartmentsTab(int x, int y,int w, int h){
            setBounds(0, 0, 800, 600);
            setVisible(false);
            //setLayout(lm);
        }
    }
    class MyMenuBar extends JMenuBar{
        MyMenuBar(){
            JMenu fileMenu = new JMenu("File");
            fileMenu.setFont(font);
            fileMenu.setVisible(true);
            JMenuItem newMenu = new JMenuItem("New");
            newMenu.setFont(font);
            fileMenu.add(newMenu);
            JMenuItem openItem = new JMenuItem("Open");
            openItem.setFont(font);
            fileMenu.add(openItem);
            newMenu.setVisible(true);
            add(fileMenu);
        }
    }
}

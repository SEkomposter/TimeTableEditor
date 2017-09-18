package by.alt.gui;


import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class MainForm extends JFrame{
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private TimeTableTab timeTables;
    private DepartmentsTab depTab;
    private Font font;
    private MyMenuBar menuBar;
    private TimeTable tt;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new MainForm();
            }
        });

    }
    public MainForm(){
        setBounds(0,0,1000,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        menuBar = new MyMenuBar();
        tabbedPane1 = new myTabbedPane(this.getX(),this.getY(),this.getWidth(),getHeight());
        getContentPane().add(tabbedPane1);
        this.setJMenuBar(menuBar);
        repaint();

    }
    class myTabbedPane extends JTabbedPane{
        myTabbedPane(int x, int y,int w, int h){
            setBounds(x, y, w, h);
            setVisible(true);
            timeTables = new TimeTableTab(x, y, w, h);
            this.addTab("Расписания",timeTables);
            depTab = new DepartmentsTab(x, y, w, h);
            this.addTab("Подразделения",depTab);

        }
    }

    class TimeTableTab extends JPanel{
        TimeTableTab(int x, int y,int w, int h){
            setBounds(x, y, w, h);
            //setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            addButton("Добавить",this);
            addButton("Редактировать",this);
            addButton("Удалить",this);
            ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
            for (int i = 0; i < 30; i++) {
                tableEntryList.add(new TableEntry("Имя " + i, "Режим " + i, "Время начала " + i, "Время окончания " + i));
            }
            MyTableModel tableModel = new MyTableModel(tableEntryList);
            JTable tt = new JTable(tableModel);
            add(tt);
            add(new JScrollPane(tt));
            setVisible(true);
            //setLayout(lm);
        }
    }
    private static void addButton (String caption, Container container){
        JButton button = new JButton(caption);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
       button.setAlignmentY(Component.TOP_ALIGNMENT);
        container.add(button);
    }
    class DepartmentsTab extends JPanel{
        DepartmentsTab(){}
        DepartmentsTab(int x, int y,int w, int h){
            setBounds(x, y, w, h);
            add(new JButton("Department"));
            setVisible(true);
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
    class TimeTable extends JTable{
        TimeTable(){
            super();
            setVisible(true);
        }
    }
}

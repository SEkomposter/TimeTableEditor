package by.alt.gui;


import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private TimeTableEditor timeTableEditor;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

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
            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            JPanel tTabSubPan1 = new JPanel();
            JPanel tTabSubPan2 = new JPanel();
            add(tTabSubPan1);
            add(tTabSubPan2);
            tTabSubPan2.setLayout(new BorderLayout());
            timeTableEditor = new TimeTableEditor();
            JButton addButton = new JButton("Добавить");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeTableEditor.setVisible(true);
                }
            });
            tTabSubPan1.add(addButton);
            addButton("Редактировать",tTabSubPan1);
            addButton("Удалить",tTabSubPan1);
            ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
            for (int i = 0; i < 30; i++) {
                tableEntryList.add(new TableEntry("Имя " + i, "Режим " + i, "Время начала " + i, "Время окончания " + i));
            }
            MyTableModel tableModel = new MyTableModel(tableEntryList);
            JTable tt = new JTable(tableModel);
            //tt.setBounds(tTabSubPan2.getX()+20,tTabSubPan2.getY(),tTabSubPan2.getWidth()-40,tTabSubPan2.getHeight()-20);
            tt.setRowSelectionAllowed(true);
            tt.setRowHeight(25);
            tTabSubPan2.add(tt,BorderLayout.NORTH);
            tTabSubPan2.add(new JScrollPane(tt));
            setVisible(true);
            new TimeTableEditor();
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
    class TimeTableEditor extends JDialog{
        TimeTableEditor(){
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setBounds((int)(MainForm.this.getWidth()/2)-250,(int)(MainForm.this.getHeight()/2)-100,500,300);
            JPanel row1 = new JPanel();
            row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
            JPanel row2 = new JPanel();
            row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
            JPanel row3 = new JPanel();
            row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
            JPanel row4 = new JPanel();
            row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row1);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row2);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row3);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row4);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));

            row1.add(Box.createRigidArea(new Dimension(30,1)));
            JLabel nameLabel = new JLabel("Имя");
            row1.add(nameLabel);
            row1.add(Box.createRigidArea(new Dimension(100,1)));
            JTextField nameField = new JTextField(20);
            row1.add(nameField);
            row1.add(Box.createRigidArea(new Dimension(30,1)));

            row2.add(Box.createRigidArea(new Dimension(30,1)));
            JLabel sheduleLabel = new JLabel("Режим");
            row2.add(sheduleLabel);
            row2.add(Box.createRigidArea(new Dimension(83,1)));
            JComboBox sheduleCombo = new JComboBox();
            row2.add(sheduleCombo);
            row2.add(Box.createRigidArea(new Dimension(30,1)));

            row3.add(Box.createRigidArea(new Dimension(30,1)));
            JLabel sheduleLabel = new JLabel("Режим");
            row3.add(sheduleLabel);
            row3.add(Box.createRigidArea(new Dimension(83,1)));
            JComboBox sheduleCombo = new JComboBox();
            row3.add(sheduleCombo);
            row3.add(Box.createRigidArea(new Dimension(30,1)));
          /*  setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
            setBounds((int)(MainForm.this.getWidth()/2)-250,(int)(MainForm.this.getHeight()/2)-100,500,300);
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            getContentPane().add(Box.createRigidArea(new Dimension(50,250)));
            getContentPane().add(leftPanel);
            getContentPane().add(Box.createRigidArea(new Dimension(50,10)));

            JLabel nameLabel = new JLabel("Имя");
            nameLabel.setSize(100,20);
            leftPanel.add(Box.createRigidArea(new Dimension(1,40)));
            leftPanel.add(nameLabel);
            leftPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JLabel sheduleLabel = new JLabel("Режим");
            leftPanel.add(sheduleLabel);
            leftPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JLabel fromLabel = new JLabel("Время начала");
            leftPanel.add(fromLabel);
            leftPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JLabel toLabel = new JLabel("Время окончания");
            leftPanel.add(toLabel);
            leftPanel.add(Box.createRigidArea(new Dimension(1,40)));

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            getContentPane().add(rightPanel);
            getContentPane().add(Box.createRigidArea(new Dimension(50,250)));
            JTextField nameField = new JTextField(20);
            nameField.setSize(50,20);
            rightPanel.add(Box.createRigidArea(new Dimension(1,40)));
            rightPanel.add(nameField);
            rightPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JComboBox sheduleCombo = new JComboBox();
            rightPanel.add(sheduleCombo);
            rightPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JTextField fromField = new JTextField(30);
            fromField.setSize(50,20);
            rightPanel.add(Box.createRigidArea(new Dimension(1,30)));
            JTextField toField = new JTextField(30);
            toField.setSize(50,20);
            rightPanel.add(Box.createRigidArea(new Dimension(1,40)));
            */

            JButton ok = new JButton("OK");
            JButton cancel = new JButton("Cancel");
            ok.setSize(100,30);
            cancel.setSize(100,30);
           // add(ok);
           // add(cancel);
            //pack();
            setVisible(false);
        }
    }
}

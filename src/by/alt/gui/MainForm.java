package by.alt.gui;


import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;
import lu.tudor.santec.jtimechooser.JTimeChooser;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
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
    private TimeTableEditor timeTableEditor;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // определяем список записей в таблице вкладки расписаний:
    private ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
    MyTableModel tableModel= new MyTableModel(tableEntryList);
    JTable tt;


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

            JButton addButton = new JButton("Добавить");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeTableEditor = new TimeTableEditor(MainForm.this,"Добавление расписания");
                    timeTableEditor.setVisible(true);

                }
            });
            tTabSubPan1.add(addButton);
            JButton editButton = new JButton("Редактировать");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeTableEditor = new TimeTableEditor(MainForm.this,"редактирование расписания");
                    timeTableEditor.setVisible(true);
                }
            });
            tTabSubPan1.add(editButton);
            addButton("Удалить",tTabSubPan1);
            //tableEntryList.addAll(new TableEntry().getTableEntryList());
      /*      for (int i = 0; i < 30; i++) {
                tableEntryList.add(new TableEntry("Имя " + i, "Режим " + i, "Время начала " + i, "Время окончания " + i));
            }*/

            tt = new JTable(tableModel);
            tt.setRowSelectionAllowed(true);
            tt.setRowHeight(25);
            tTabSubPan2.add(tt,BorderLayout.NORTH);
            tTabSubPan2.add(new JScrollPane(tt));
            setVisible(true);
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
            JMenu fileMenu = new JMenu("Файл");
            fileMenu.setFont(font);
            fileMenu.setVisible(true);
            JMenuItem newMenu = new JMenuItem("Новый");
            newMenu.setFont(font);
            fileMenu.add(newMenu);
            JMenuItem openItem = new JMenuItem("Открыть");
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableEntryList.addAll(new TableEntry().getTableEntryList());
                    tableModel.fireTableStructureChanged();
                    tt.updateUI();
                }
            });
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
        TimeTableEditor(Frame owner, String title){
            super(owner,title,ModalityType.DOCUMENT_MODAL);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            setBounds((int)(MainForm.this.getWidth()/2)-250,(int)(MainForm.this.getHeight()/2)-100,500,330);
            setPreferredSize(new Dimension(500,330));
            setMinimumSize(new Dimension(500,330));
            setMaximumSize(new Dimension(500,330));
            JPanel row1 = new JPanel();
            row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
            JPanel row2 = new JPanel();
            row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
            JPanel row3 = new JPanel();
            row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
            JPanel row4 = new JPanel();
            row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
            JPanel row5 = new JPanel();
            row5.setLayout(new BoxLayout(row5, BoxLayout.X_AXIS));
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row1);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row2);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row3);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row4);
            getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
            getContentPane().add(row5);
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
            row2.add(Box.createRigidArea(new Dimension(100,1)));

            row3.add(Box.createRigidArea(new Dimension(30,1)));
            JLabel fromField = new JLabel("Время начала");
            row3.add(fromField);
            row3.add(Box.createRigidArea(new Dimension(40,1)));
            JTimeChooser fromTime = new JTimeChooser();
            row3.add(fromTime);
            row3.add(Box.createRigidArea(new Dimension(250,1)));

            row4.add(Box.createRigidArea(new Dimension(30,1)));
            JLabel toField = new JLabel("Время окончания");
            row4.add(toField);
            row4.add(Box.createRigidArea(new Dimension(22,1)));
            JTimeChooser toTime = new JTimeChooser();
            row4.add(toTime);
            row4.add(Box.createRigidArea(new Dimension(250,1)));

            JButton addB = new JButton("Добавить" );
            JButton cancelB = new JButton("Отмена");
            cancelB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            row5.add(addB);
            row5.add(Box.createRigidArea(new Dimension(70,1)));
            row5.add(cancelB);

        }

    }
}

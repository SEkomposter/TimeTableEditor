package by.alt.gui;


import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;
import by.alt.Object.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class MainForm extends JFrame{
    private JTabbedPane tabbedPane1;
    private TimeTableTab timeTables;
    private DepartmentsTab depTab;
    private Font font;
    private MyMenuBar menuBar;
    public static TimeTableEditor timeTableEditor;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // определяем список записей в таблице вкладки расписаний:
    public static ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
    public static MyTableModel tableModel = new MyTableModel(tableEntryList);
    static JTable tt;

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
    public static void tableUpdate(){
        tableModel.fireTableStructureChanged();
        tt.updateUI();
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
                    timeTableEditor = new TimeTableEditor(MainForm.this,"Добавление расписания", new DifferentB("Добавить"));
                    timeTableEditor.setVisible(true);
                }
            });
            tTabSubPan1.add(addButton);
            JButton editButton = new JButton("Редактировать");
            editButton.setEnabled(false);
            tTabSubPan1.add(editButton);
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeTableEditor = new TimeTableEditor(MainForm.this,"Редактирование расписания", new DifferentB("Изменить"), tt.getSelectedRow());
                    timeTableEditor.setVisible(true);
                }
            });
            JButton delButton = new JButton("Удалить");
            delButton.setEnabled(false);
            delButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = JOptionPane.showConfirmDialog(
                            MainForm.this,
                            "Вы действительно хотите удалить расписание?",
                            "Удаление расписания",
                            JOptionPane.YES_NO_OPTION);
                    // если нажата кнопка "ДА", удаляем строку:
                    if (n == JOptionPane.YES_NO_OPTION) {
                        int[] selections = tt.getSelectedRows();
                        int rowDeleted = 0;
                        for(int i:selections) {
                            new PropReader().removeProperty(PropType.TIMETABLE.toString() + "." + tt.getValueAt(i-rowDeleted, 1) + "." + tt.getValueAt(i-rowDeleted, 0));
                            tableModel.removeRow(i);
                            ++rowDeleted;
                        }

                        tableUpdate();
                    }
                }
            });
            tTabSubPan1.add(delButton);

            tt = new JTable(tableModel);
            tt.setRowSelectionAllowed(true);
            tt.setRowHeight(25);
            tTabSubPan2.add(tt,BorderLayout.NORTH);
            tTabSubPan2.add(new JScrollPane(tt));
            tt.setAutoCreateRowSorter(true);
            tt.getTableHeader().setReorderingAllowed(false);
            tt.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event) {
                    // do some actions here, for example
                    // print first column value from selected row
                    editButton.setEnabled(true);
                    delButton.setEnabled(true);
                }
            });
            setVisible(true);
        }

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
            JMenuItem newItem = new JMenuItem("Новый");
            newItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableEntryList.clear();
                    tableUpdate();
                }
            });
            newItem.setFont(font);
            fileMenu.add(newItem);
            JMenuItem openItem = new JMenuItem("Открыть");
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableEntryList.clear();
                    tableEntryList.addAll(new TableEntry().getTableEntryList());
                    tableUpdate();
                }
            });
            openItem.setFont(font);
            fileMenu.add(openItem);
            JMenuItem saveAsItem = new JMenuItem("Сохранить как...");
            saveAsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PropReader reader = new PropReader();
                    try {
                        reader.writeRepProp(reader.preparePropsForWrite(tableEntryList));

                    }catch (IOException exc){
                        exc.printStackTrace();
                    }
                }
            });
            saveAsItem.setFont(font);
            fileMenu.add(saveAsItem);
            newItem.setVisible(true);
            add(fileMenu);
        }
    }

}


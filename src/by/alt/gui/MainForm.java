package by.alt.gui;

import by.alt.DAO.DaoClass;
import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;
import by.alt.Object.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import static by.alt.gui.DepartmentsTab.groupTimeCombo;
import static by.alt.gui.UsersTab.*;

public class MainForm extends JFrame {
    private JTabbedPane tabbedPane1;
    private TimeTableTab timeTables;
    private PropReader propReader;
    public static DaoClass daoObject = new DaoClass();
    private Font font;
    private MyMenuBar menuBar;
    Logger logger = new Logger();
    public static TimeTableEditor timeTableEditor;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // определяем список записей в таблице вкладки расписаний:
    public static ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
    public static TEntryList userTimeList = new TEntryList();
    public static TEntryList groupTimeList = new TEntryList();
    public static MyTableModel tableModel = new MyTableModel(tableEntryList);
    static JTable tt;
    public static UsersTab usersTab;
    private static DepartmentsTab depTab;
    public boolean fileOpened = false;

    public MainForm() {
        setBounds(0, 0, 1300, 720);
        setMinimumSize(new Dimension(1300, 720));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        menuBar = new MyMenuBar();
        tabbedPane1 = new myTabbedPane(this.getX(), this.getY(), this.getWidth(), getHeight());
        getContentPane().add(tabbedPane1);
        this.setJMenuBar(menuBar);
        readPropsFromConf();
        repaint();

    }
    public static void tableUpdate() {
        tableModel.fireTableStructureChanged();
        tt.updateUI();
    }
    public void readPropsFromConf() {
        try {
            propReader = new PropReader();
            propReader.readRepProp();
        } catch (IOException exc) {
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
    }
    class myTabbedPane extends JTabbedPane{
        myTabbedPane(int x, int y,int w, int h){
            setBounds(x, y, w, h);
            setVisible(true);
            timeTables = new TimeTableTab(x, y, w, h);
            this.addTab("Расписания",timeTables);
            depTab = new DepartmentsTab(x, y, w, h);
            this.addTab("Подразделения",depTab);
            usersTab = new UsersTab(x, y, w, h);
            this.addTab("Сотрудники",usersTab);
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
                            "Также будут удалены связанные расписания для сотрудников и подразделений.\nВы действительно хотите удалить расписание?",
                            "Удаление расписания",
                            JOptionPane.YES_NO_OPTION);
                    // если нажата кнопка "ДА", удаляем строку:
                    String shed="",nm="";
                    if (n == JOptionPane.YES_NO_OPTION) {
                        int[] selections = tt.getSelectedRows();
                        int rowDeleted = 0;
                        for(int i:selections) {
                            shed = (String) tt.getValueAt(i-rowDeleted, 1);
                            nm = (String) tt.getValueAt(i-rowDeleted, 0);
                            propReader.removeProperty(PropType.TIMETABLE.toString() + "." + shed + "." + nm);
                            tableModel.removeRow(i-rowDeleted);
                            deleteUserTime(nm, shed);
                            rowDeleted++;
                            updateComponents();
                        }
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

    class MyMenuBar extends JMenuBar{
        MyMenuBar(){
            JMenu fileMenu = new JMenu("Файл");
            fileMenu.setFont(font);
            fileMenu.setVisible(true);
            JMenuItem newItem = new JMenuItem("Новый");
            newItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fileOpened = true;
                    int n=0;
                    if (fileOpened){
                    n = JOptionPane.showConfirmDialog(MainForm.this,
                            "Текущий файл конфигурации будет закрыт, все не сохраненные данные будут потеряны\nВы действительно хотите продолжить?",
                            "Новый файл",
                            JOptionPane.YES_NO_OPTION);
                    }
                    else {
                        n = JOptionPane.YES_NO_OPTION;
                    }
                    if (n == JOptionPane.YES_NO_OPTION) {
                    initFilterFields();
                    propReader = new PropReader();
                    propReader.removeAllProperties();
                    tableEntryList.clear();
                    userTimeList.clear();
                    groupTimeList.clear();
                    userTimeCombo.removeAllItems();
                    groupTimeCombo.removeAllItems();
                    MainForm.getUsersTab().treeModel.delAllPersonal(MainForm.getUsersTab().treeModel.getRootAddedPersonal());
                    MainForm.getUsersTab().treeModel.getTreeModelAddedPersonal().reload();
                    MainForm.getUsersTab().treeModel.getTreeModelFreePersonal().reload();
                    MainForm.getDepTab().treeModel2.delAllPersonal(MainForm.getDepTab().treeModel2.getRootAddedPersonal());
                    MainForm.getDepTab().treeModel2.getTreeModelAddedPersonal().reload();
                    MainForm.getDepTab().treeModel2.getTreeModelFreePersonal().reload();
                    tableUpdate();
                }
                }
            });
            newItem.setFont(font);
            fileMenu.add(newItem);
            JMenuItem openItem = new JMenuItem("Открыть");
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = 0;
                    if (fileOpened) {
                        n = JOptionPane.showConfirmDialog(MainForm.this,
                                "Текущий файл конфигурации будет закрыт, все не сохраненные данные будут потеряны\nВы действительно хотите продолжить?",
                                "Открыть файл",
                                JOptionPane.YES_NO_OPTION);
                    } else {
                        n = JOptionPane.YES_NO_OPTION;
                    }
                    if (n == JOptionPane.YES_NO_OPTION) {
                        propReader = new PropReader();
                        try {
                            propReader.readRepProp();
                            propReader.backupRepProps();
                        } catch (IOException exc) {
                            new Logger().pushToScreenNlog(exc, exc.getClass());
                        }
                        initFilterFields();
                        tableEntryList.clear();
                        tableEntryList.addAll(propReader.getPropertiesList(PropType.TIMETABLE));
                        tableUpdate();
                        userTimeList.clear();
                        userTimeList.addAll(propReader.getPropertiesList(PropType.USERTIME));
                        groupTimeList.clear();
                        groupTimeList.addAll(propReader.getPropertiesList(PropType.GROUPTIME));
                        fillComboes();
                        updateComponents();
                        fileOpened = true;
                    }
                }
            });
            openItem.setFont(font);
            fileMenu.add(openItem);
            JMenuItem saveAsItem = new JMenuItem("Сохранить");
            saveAsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = 0;
                    if (fileOpened) {
                        n = JOptionPane.showConfirmDialog(MainForm.this,
                                "Вы действительно хотите сохранить текущие настройки?",
                                "Открыть файл",
                                JOptionPane.YES_NO_OPTION);
                    } else {
                        n = JOptionPane.YES_NO_OPTION;
                    }
                    if (n == JOptionPane.YES_NO_OPTION) {
                        try {
                            propReader.writeRepProp();
                            propReader.readRepProp();
                        } catch (IOException exc) {
                            logger.pushToScreenNlog(exc, exc.getClass(), "файл не найден");
                        }
                    }
                }
            });
            saveAsItem.setFont(font);
            fileMenu.add(saveAsItem);
            JMenuItem closeItem = new JMenuItem("Закрыть");
            closeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        int dialogButton = JOptionPane.showConfirmDialog(MainForm.this, "Все не сохраненные данные будут потеряны.\n Вы действительно хотите закрыть приложение?", "Закрыть приложение",                              JOptionPane.YES_NO_OPTION);
                        if (dialogButton == JOptionPane.YES_OPTION) {
                            System.exit(NORMAL);
                        }
                    }
                });
            fileMenu.add(closeItem);
            newItem.setVisible(true);
            add(fileMenu);
        }
    }
    public void updateComponents(){
        refreshPersonal(MainForm.getUsersTab().treeModel,userTimeCombo);
        refreshPersonal(MainForm.getDepTab().treeModel2,groupTimeCombo);
   }
    public void deleteUserTime(String nm, String shed){
        ArrayList<TableEntry> tempList = new ArrayList<TableEntry>();
        try {
            Iterator it = userTimeList.iterator();
            while(it.hasNext()) {
                UserTime ut = (UserTime) it.next();
                if (ut.getName().equalsIgnoreCase(nm) && ut.getShedule().equalsIgnoreCase(shed)) tempList.add(ut);
            }
            userTimeList.removeAll(tempList);
            tempList.clear();
            it = groupTimeList.iterator();
            while(it.hasNext()) {
                GroupTime gt = (GroupTime) it.next();
                if (gt.getName().equalsIgnoreCase(nm) && gt.getShedule().equalsIgnoreCase(shed)) tempList.add(gt);
            }
            groupTimeList.removeAll(tempList);
            fillComboes();
            }catch (Exception exc){
            new Logger().pushToScreenNlog(exc,exc.getClass());
            }
        }
    public static void refreshPersonal(PersonalTreeModel tm, JComboBox combo){
        tm.delAllPersonal(tm.getRootFreePersonal());
        tm.delAllPersonal(tm.getRootAddedPersonal());
        daoObject.getEndNodes().clear();
        daoObject.buildObjTree(daoObject.getRootNode());
        tm.fillTreeAddedPersonal(tm.getRootAddedPersonal(),(UserTime) combo.getSelectedItem());
        if(combo.equals(userTimeCombo)) tm.fillTreeFreePersonal(tm.getRootFreePersonal(),daoObject.getAllPersonal().toArray());
        else tm.fillTreeFreePersonal(tm.getRootFreePersonal(),daoObject.getEndNodes().toArray());
        tm.removeAddedFromFree(tm.getRootAddedPersonal(),tm.getRootFreePersonal());
        tm.getTreeModelAddedPersonal().reload();
        tm.getTreeModelFreePersonal().reload();
    }
    public void initFilterFields(){
        usersTab.getFilterField().setEnabled(true);
        depTab.getFilterField().setEnabled(true);
        usersTab.getFilterField().addFocusListener(new by.alt.Object.FilterFieldListener());
        depTab.getFilterField().addFocusListener(new by.alt.Object.FilterFieldListener());
    }
    public static DepartmentsTab getDepTab() {return depTab;}
    public static UsersTab getUsersTab(){
        return  usersTab;
    }
    public static void fillComboes(){
        userTimeCombo.removeAllItems();
        groupTimeCombo.removeAllItems();
        getUsersTab().fillCombo(userTimeCombo,userTimeList);
        getDepTab().fillCombo(groupTimeCombo,groupTimeList);
    }
}




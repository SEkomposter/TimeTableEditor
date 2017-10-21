package by.alt.gui;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Personal;
import by.alt.Main;
import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;
import by.alt.Object.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

public class MainForm extends JFrame {
    private JTabbedPane tabbedPane1;
    private TimeTableTab timeTables;
    private DepartmentsTab depTab;
    private static UsersTab usersTab;
    private PropReader propReader;
    public static DaoClass daoObject = new DaoClass();
    private Font font;
    private MyMenuBar menuBar;
    public static TimeTableEditor timeTableEditor;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // определяем список записей в таблице вкладки расписаний:
    public static ArrayList<TableEntry> tableEntryList = new ArrayList<TableEntry>();
    public static ArrayList<TableEntry> userTimeList = new ArrayList<TableEntry>();
    public static ArrayList<TableEntry> groupTimeList = new ArrayList<TableEntry>();
    public static MyTableModel tableModel = new MyTableModel(tableEntryList);
    static JComboBox timeTableCombo = new JComboBox();
    public static JTextField filterField;
    static JTable tt;
    public static PersonalTreeModel treeModel = new PersonalTreeModel();
    public static JTree addedUsers;
    public static JTree freeUsers;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                new MainForm();
            }
        });
    }

    public MainForm() {
        setBounds(0, 0, 1300, 720);
        setMinimumSize(new Dimension(1300, 720));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        menuBar = new MyMenuBar();
        tabbedPane1 = new myTabbedPane(this.getX(), this.getY(), this.getWidth(), getHeight());
        getContentPane().add(tabbedPane1);
        this.setJMenuBar(menuBar);
        repaint();
        propReader = new PropReader();
    }

    public static void tableUpdate() {
        tableModel.fireTableStructureChanged();
        tt.updateUI();
    }

    public void readPropsFromConf() {
        try {
            propReader.readRepProp();
        } catch (IOException exc) {
            exc.printStackTrace();
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
                    propReader.removeAllProperties();
                    tableEntryList.clear();
                    userTimeList.clear();
                    groupTimeList.clear();
                    timeTableCombo.removeAllItems();
                    treeModel.delAllPersonal(treeModel.getRootAddedPersonal());
                    treeModel.treeModelAddedPersonal.reload();
                    tableUpdate();
                }
            });
            newItem.setFont(font);
            fileMenu.add(newItem);
            JMenuItem openItem = new JMenuItem("Открыть");
            openItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    readPropsFromConf();
                    tableEntryList.clear();
                    tableEntryList.addAll(propReader.getPropertiesList(PropType.TIMETABLE));
                    tableUpdate();
                    userTimeList.clear();
                    userTimeList.addAll(propReader.getPropertiesList(PropType.USERTIME));
                    groupTimeList.clear();
                    groupTimeList.addAll(propReader.getPropertiesList(PropType.GROUPTIME));
                    timeTableCombo.removeAllItems();
                    fillCombo(timeTableCombo, userTimeList);
                    //updateComponents();
                }
            });
            openItem.setFont(font);
            fileMenu.add(openItem);
            JMenuItem saveAsItem = new JMenuItem("Сохранить как...");
            saveAsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        propReader.writeRepProp();
                        propReader.readRepProp();
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
    class DepartmentsTab extends JPanel{
        DepartmentsTab(){}
        DepartmentsTab(int x, int y,int w, int h){
            setBounds(x, y, w, h);
            setVisible(true);
        }
    }
    class UsersTab extends JPanel{
        JPanel basicLayer = new JPanel();
        JLabel timeTableLabel = new JLabel("Расписание:");
        JLabel treeLabel1 = new JLabel("Сотрудники, добавленные в расписание:");
        JLabel treeLabel2 = new JLabel("Сотрудники, отсутствующие в расписании:");
        //JLabel filterLabel = new JLabel("Фильтр:");


        JButton addButton = new JButton("<= Добавить");
        JButton removeButton = new JButton("Убрать       =>");



        UsersTab(){}
        UsersTab(int x, int y,int w, int h){
            this.setLayout(null);
            this.setBounds(x,y,w,h);
           // this.add(new JScrollPane(addedUsers));
            this.setPreferredSize(new Dimension(w,h));
            TreeSelectionModel selModel = new DefaultTreeSelectionModel();
            selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
            addedUsers = new JTree(treeModel.treeModelAddedPersonal);
            freeUsers = new JTree(treeModel.treeModelFreePersonal);
            freeUsers.setSelectionModel(selModel);
            freeUsers.addTreeSelectionListener(new PersonalSelectionListener());
            add(basicLayer);
            setVisible(true);
            addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    basicLayer.setBounds(getX(),getY(),getWidth(),getHeight());
                }
            });
            basicLayer.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            basicLayer.setBounds(x,y,w,h);
            filterField = new JTextField("Фильтр:");
            filterField.setForeground(Color.GRAY);
            filterField.addFocusListener(new by.alt.Object.FilterFieldListener());
            filterField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal(),daoObject.getAllPersonal().toArray());
                    treeModel.filterPersonal(filterField.getText());
                    treeModel.treeModelFreePersonal.reload();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal(),daoObject.getAllPersonal().toArray());
                    treeModel.filterPersonal(filterField.getText());
                    treeModel.treeModelFreePersonal.reload();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal(),daoObject.getAllPersonal().toArray());
                    treeModel.filterPersonal(filterField.getText());
                    treeModel.treeModelFreePersonal.reload();
                }
            });
            timeTableCombo.setBackground(Color.white);
            timeTableCombo.addItemListener(
                    new ItemListener() {
                        public void itemStateChanged(ItemEvent ev)
                        {
                            if(ev.getStateChange() == ItemEvent.SELECTED) {
                                refreshPersonal();
                            }
                        }
                    }
            );
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        TreePath[] tp = freeUsers.getSelectionPaths();
                        for (TreePath t : tp)
                            ((UserTime) timeTableCombo.getSelectedItem()).addPersonal(new Personal(t.getLastPathComponent().toString()));
                        treeModel.movePersonal(treeModel.getRootFreePersonal(), treeModel.getRootAddedPersonal(), freeUsers.getSelectionPaths());
                        treeModel.treeModelAddedPersonal.reload();
                        treeModel.treeModelFreePersonal.reload();
                    }catch (NullPointerException exc){
                        exc.printStackTrace();
                        System.out.println("ничего не выбрано");
                    }
                    }
            });
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TreePath[] tp = addedUsers.getSelectionPaths();
                    for (TreePath t:tp)
                        ((UserTime)timeTableCombo.getSelectedItem()).removePersonal(t.getLastPathComponent().toString());
                    treeModel.movePersonal(treeModel.getRootAddedPersonal(),treeModel.getRootFreePersonal(),addedUsers.getSelectionPaths());
                    treeModel.treeModelAddedPersonal.reload();
                    treeModel.treeModelFreePersonal.reload();
                }
            });

            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 0.0;
            c.weighty = 0.0;
            c.insets = new Insets(0, 200, 0, 0);
            c.anchor = GridBagConstraints.WEST;
            basicLayer.add(timeTableLabel, c);
            timeTableLabel.setBorder(new BasicBorders.MarginBorder());

            c.fill = GridBagConstraints.NONE;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.weightx = 0.0;
            c.weighty = 0.0;
            c.insets = new Insets(0, 0, 0, 0);
            c.anchor = GridBagConstraints.EAST;
            basicLayer.add(timeTableCombo, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            c.weightx = 0.5;
            c.weighty = 0.1;
            c.insets = new Insets(0, 20, 0, 0);
            basicLayer.add(treeLabel1, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 4;
            c.gridy = 2;
            c.gridwidth = 2;
            c.weightx = 0.5;
            c.weighty = 0.1;
            c.insets = new Insets(0, 0, 0, 0);
            basicLayer.add(treeLabel2, c);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 4;
            c.gridy = 1;
            c.gridwidth = 1;
            c.weightx = 0.1;
            c.weighty = 0.1;
            c.insets = new Insets(0, 350, 0, 20);
            basicLayer.add(filterField, c);

            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = 4;
            c.gridwidth = 2;
            c.gridheight = 3;
            c.weightx = 0.45;
            c.weighty = 0.8;
            c.ipadx = 0;
            c.insets = new Insets(0, 20, 30, 0);
            c.anchor = GridBagConstraints.NORTHWEST;
            basicLayer.add(new JScrollPane(addedUsers),c);

            c.fill = GridBagConstraints.BOTH;
            c.gridx = 4;
            c.gridy = 4;
            c.gridwidth = 1;
            c.gridheight = 3;
            c.weightx = 0.45;
            c.weighty = 0.8;
            c.insets = new Insets(0, 0, 30, 10);
            basicLayer.add(new JScrollPane(freeUsers),c);

            c.fill = GridBagConstraints.NONE;
            c.gridx = 2;
            c.gridy = 4;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 0.05;
            c.weighty = 0.0;
            c.insets = new Insets(50, 20, 0, 0);
            basicLayer.add(addButton, c);

            c.fill = GridBagConstraints.NONE;
            c.gridx = 2;
            c.gridy = 5;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 0.05;
            c.weighty = 0.0;
            c.insets = new Insets(50, 20, 0, 0);
            basicLayer.add(removeButton, c);
        }

    }
    public void updateComponents(){
        this.refreshPersonal();
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
            timeTableCombo.removeAllItems();
            fillCombo(timeTableCombo,userTimeList);

            }catch (Exception exc){
                exc.printStackTrace();
            }
        }

    public JComboBox fillCombo(JComboBox jComboBox,ArrayList list ){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            jComboBox.addItem(iterator.next());
        }
        return jComboBox;
    }
    public static void refreshPersonal(){
        treeModel.delAllPersonal(treeModel.getRootFreePersonal());
        treeModel.delAllPersonal(treeModel.getRootAddedPersonal());
        daoObject.buildObjTree(daoObject.getRootNode());
        treeModel.fillTreeAddedPersonal(treeModel.getRootAddedPersonal(),(UserTime) MainForm.timeTableCombo.getSelectedItem());
        treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal(),daoObject.getAllPersonal().toArray());
        treeModel.removeAddedFromFree(treeModel.getRootAddedPersonal(),treeModel.getRootFreePersonal());
        freeUsers.expandRow(0);
        addedUsers.expandRow(0);
        treeModel.treeModelAddedPersonal.reload();
        treeModel.treeModelFreePersonal.reload();
    }

}


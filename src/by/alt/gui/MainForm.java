package by.alt.gui;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Personal;
import by.alt.Main;
import by.alt.Object.MyTableModel;
import by.alt.Object.TableEntry;
import by.alt.Object.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.*;

public class MainForm extends JFrame {
    private JTabbedPane tabbedPane1;
    private TimeTableTab timeTables;
    private DepartmentsTab depTab;
    private static UsersTab usersTab;
    private PropReader propReader;
    private DaoClass daoObject = new DaoClass();
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
    static JTable tt;

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
                    if (n == JOptionPane.YES_NO_OPTION) {
                        int[] selections = tt.getSelectedRows();
                        int rowDeleted = 0;
                        for(int i:selections) {
                            propReader.removeProperty(PropType.TIMETABLE.toString() + "." + tt.getValueAt(i-rowDeleted, 1) + "." + tt.getValueAt(i-rowDeleted, 0));
                            tableModel.removeRow(i-rowDeleted);
                            deleteTableEntry((String) tt.getValueAt(i-rowDeleted, 0), (String) tt.getValueAt(i-rowDeleted, 1));
                            //timeTableCombo.removeAll();
                           // usersTab.fillCombo(timeTableCombo);
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
                    usersTab.fillCombo(timeTableCombo);
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
                        propReader.writeRepProp(tableEntryList);
                        propReader.writeRepProp(userTimeList);
                        propReader.writeRepProp(groupTimeList);
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
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JTree addedUsers;
        JTree freeUsers;
        JButton addButton = new JButton("<= Добавить");
        JButton removeButton = new JButton("Убрать       =>");
        PersonalTreeModel treeModel = new PersonalTreeModel();

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
                    TreePath[] tp = freeUsers.getSelectionPaths();
                    for (TreePath t:tp)
                        ((UserTime)timeTableCombo.getSelectedItem()).addPersonal(new Personal(t.getLastPathComponent().toString()));
                    treeModel.movePersonal(treeModel.getRootFreePersonal(),treeModel.getRootAddedPersonal(),freeUsers.getSelectionPaths());
                    treeModel.treeModelAddedPersonal.reload();
                    treeModel.treeModelFreePersonal.reload();
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
        public JComboBox fillCombo(JComboBox jComboBox){
            Iterator iterator = MainForm.userTimeList.iterator();
            //Iterator iterator = propReader.getTableEntryList(PropType.USERTIME).iterator();
            while (iterator.hasNext()) {
                jComboBox.addItem(iterator.next());
            }
            return jComboBox;
        }
        public void refreshPersonal(){
            treeModel.delAllPersonal(treeModel.getRootFreePersonal());
            treeModel.delAllPersonal(treeModel.getRootAddedPersonal());
            daoObject.buildObjTree(daoObject.getRootNode());
            treeModel.fillTreeAddedPersonal(treeModel.getRootAddedPersonal(),(UserTime) MainForm.timeTableCombo.getSelectedItem());
            treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal());
            treeModel.removeAddedFromFree(treeModel.getRootAddedPersonal(),treeModel.getRootFreePersonal());
            freeUsers.expandRow(0);
            addedUsers.expandRow(0);
            treeModel.treeModelAddedPersonal.reload();
            treeModel.treeModelFreePersonal.reload();
        }

    }
    public void updateComponents(){
        usersTab.refreshPersonal();
    }
    public void deleteTableEntry(String nm, String shed){
        Iterator it = userTimeList.iterator();
        while(it.hasNext()) {
            UserTime ut = (UserTime) it.next();
            if (ut.getName().equalsIgnoreCase(nm)&&ut.getShedule().equalsIgnoreCase(shed)) userTimeList.remove(ut);
        }
    }
    /*removeButton.setSize(addButton.getSize());
            //basicLayer.setLayout(new BoxLayout(basicLayer, BoxLayout.Y_AXIS));
            //basicLayer.setBounds((int)(this.getParent().getX()),(int)(this.getParent().getY()),(int)(this.getParent().getWidth()),(int)(this.getParent().getHeight()));

            JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row1.setBackground(Color.cyan);
            //row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
            JPanel row2 = new JPanel(new GridLayout(0,4));
            row2.setBackground(Color.green);
            row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
            JPanel row3 = new JPanel();
            //row3.setBackground(Color.MAGENTA);
            row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
            //row3.setBackground(Color.white);

            //basicLayer.add(Box.createRigidArea(new Dimension(1,10)));
            basicLayer.add(row1,BorderLayout.NORTH);
            //basicLayer.add(Box.createRigidArea(new Dimension(1,30)));
            basicLayer.add(row2,BorderLayout.CENTER);
            //basicLayer.add(Box.createRigidArea(new Dimension(1,10)));
            basicLayer.add(row3,BorderLayout.SOUTH);
            //basicLayer.add(Box.createRigidArea(new Dimension(1,30)));

            //row1.add(Box.createRigidArea(new Dimension(80,1)));
            row1.add(timeTableLabel);
            //row1.add(Box.createRigidArea(new Dimension(30,1)));
            row1.add(timeTableCombo);
            //row1.add(Box.createRigidArea(new Dimension(300,1)));

            //row2.add(Box.createRigidArea(new Dimension(50,1)));
            row2.add(treeLabel1);
            //row2.add(Box.createRigidArea(new Dimension(300,1)));
            row2.add(treeLabel2);
            //row2.add(Box.createRigidArea(new Dimension(80,1)));

            row3.add(Box.createRigidArea(new Dimension(20,1)));
            row3.add(panel1);
            row3.add(Box.createRigidArea(new Dimension(20,1)));
            row3.add(panel2);
            row3.add(Box.createRigidArea(new Dimension(20,1)));
            row3.add(panel3);
            row3.add(Box.createRigidArea(new Dimension(20,1)));

            panel1.setLayout(new FlowLayout());
            panel1.add(addedUsers);
            panel3.add(freeUsers);
            panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
            panel2.add(Box.createRigidArea(new Dimension(1,50)));
            panel2.add(addButton);
            panel2.add(Box.createRigidArea(new Dimension(1,40)));
            panel2.add(removeButton);
            panel2.add(Box.createRigidArea(new Dimension(1,250)));
            */
}


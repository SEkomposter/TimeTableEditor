package by.alt.gui;

import by.alt.DAO.Personal;
import by.alt.Object.GroupTime;
import by.alt.Object.PersonalTreeModel;
import by.alt.Object.UserTime;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static by.alt.gui.MainForm.treeModel;

public class DepartmentsTab extends UserGroupTab {
    /*JPanel basicLayer = new JPanel();
    JLabel timeTableLabel = new JLabel("Расписание:");
    JLabel treeLabel1 = new JLabel("Подразделения, добавленные в расписание:");
    JLabel treeLabel2 = new JLabel("Подразделения, отсутствующие в расписании:");
    JButton addButton = new JButton("<= Добавить");
    JButton removeButton = new JButton("Убрать       =>");
    public static JTree addedGroups;
    public static JTree freeGroups;
    public static PersonalTreeModel groupTreeModel = new PersonalTreeModel();
    */
    public static JComboBox groupTimeCombo = new JComboBox();
    static {addedUsers = new JTree(treeModel.getTreeModelAddedPersonal());
        freeUsers = new JTree(treeModel.getTreeModelFreePersonal());}
    //DepartmentsTab(){}
    DepartmentsTab(int x, int y,int w, int h){
        super(x,y,w,h);
        treeLabel1.setText("Подразделения, добавленные в расписание:");
        treeLabel2.setText("Подразделения, отсутствующие в расписании:");
        groupTimeCombo.setBackground(Color.white);
        groupTimeCombo.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                        if (ev.getStateChange() == ItemEvent.SELECTED) {
                            MainForm.refreshPersonal();
                        }
                    }
                }
        );

        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 0.0;
        c.insets = new Insets(0, 00, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        basicLayer.add(groupTimeCombo, c);
    }

  /*
    DepartmentsTab(int x, int y,int w, int h){
        this.setLayout(null);
        this.setBounds(x,y,w,h);
        // this.add(new JScrollPane(addedUsers));
        this.setPreferredSize(new Dimension(w,h));
        TreeSelectionModel selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
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
        groupTimeCombo.setBackground(Color.white);
        groupTimeCombo.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev)
                    {
                        if(ev.getStateChange() == ItemEvent.SELECTED) {
                            // refreshPersonal();
                        }
                    }
                }
        );
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TreePath[] tp = freeGroups.getSelectionPaths();
                    for (TreePath t : tp)
                        ((GroupTime) groupTimeCombo.getSelectedItem()).addPersonal(new Personal(t.getLastPathComponent().toString()));
                    groupTreeModel.movePersonal(groupTreeModel.getRootFreePersonal(), groupTreeModel.getRootAddedPersonal(), freeGroups.getSelectionPaths());
                    groupTreeModel.treeModelAddedPersonal.reload();
                    groupTreeModel.treeModelFreePersonal.reload();
                }catch (NullPointerException exc){
                    exc.printStackTrace();
                    System.out.println("ничего не выбрано");
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] tp = addedGroups.getSelectionPaths();
                for (TreePath t:tp)
                    ((UserTime)groupTimeCombo.getSelectedItem()).removePersonal(t.getLastPathComponent().toString());
                groupTreeModel.movePersonal(groupTreeModel.getRootAddedPersonal(),groupTreeModel.getRootFreePersonal(),addedGroups.getSelectionPaths());
                groupTreeModel.treeModelAddedPersonal.reload();
                groupTreeModel.treeModelFreePersonal.reload();
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
        basicLayer.add(groupTimeCombo, c);

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
        basicLayer.add(new JScrollPane(addedGroups),c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.8;
        c.insets = new Insets(0, 0, 30, 10);
        basicLayer.add(new JScrollPane(freeGroups),c);

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
    }*/
}

package by.alt.gui;

import by.alt.DAO.Personal;
import by.alt.Object.*;
import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DepartmentsTab extends UserGroupTab {
    public static JComboBox groupTimeCombo = new JComboBox();
    public DepTreeModel treeModel2;
    //DepartmentsTab(){}
    DepartmentsTab(int x, int y,int w, int h){
        super(x,y,w,h);
        treeModel2 = new DepTreeModel();
        addedUsers.setModel(treeModel2.getTreeModelAddedPersonal());
        freeUsers.setModel(treeModel2.getTreeModelFreePersonal());
        selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        treeLabel1.setText("Подразделения, добавленные в расписание:");
        treeLabel2.setText("Подразделения, отсутствующие в расписании:");
        groupTimeCombo.setBackground(Color.white);
        groupTimeCombo.createToolTip();
        groupTimeCombo.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                        if (ev.getStateChange() == ItemEvent.SELECTED) {
                            MainForm.refreshPersonal(treeModel2,groupTimeCombo);
                            groupTimeCombo.setToolTipText(groupTimeCombo.getSelectedItem().toString());
                            //fillAllTrees(treeModel2);
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
        jPanel1.add(groupTimeCombo, c);

        freeUsers.setSelectionModel(selModel);
        freeUsers.addTreeSelectionListener(new PersonalSelectionListener());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   TreePath[] tp = freeUsers.getSelectionPaths();
                    for (TreePath t : tp)
                        ((GroupTime) groupTimeCombo.getSelectedItem()).addPersonal(new Personal(t.getLastPathComponent().toString()));
                    treeModel2.movePersonal(treeModel2.getRootFreePersonal(), treeModel2.getRootAddedPersonal(), freeUsers.getSelectionPaths());
                    treeModel2.getTreeModelAddedPersonal().reload();
                    treeModel2.getTreeModelFreePersonal().reload();
                } catch (NullPointerException exc) {
                    exc.printStackTrace();
                    System.out.println("ничего не выбрано");
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] tp = addedUsers.getSelectionPaths();
                for (TreePath t : tp)
                    ((GroupTime) groupTimeCombo.getSelectedItem()).removePersonal(t.getLastPathComponent().toString());treeModel2.movePersonal(treeModel2.getRootAddedPersonal(), treeModel2.getRootFreePersonal(), addedUsers.getSelectionPaths());
                treeModel2.getTreeModelAddedPersonal().reload();
                treeModel2.getTreeModelFreePersonal().reload();
            }
        });
        addedUsers.updateUI();
    }

}

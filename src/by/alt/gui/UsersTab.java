package by.alt.gui;

import by.alt.DAO.Personal;
import by.alt.Object.Logger;
import by.alt.Object.PersonalSelectionListener;
import by.alt.Object.PersonalTreeModel;
import by.alt.Object.UserTime;
import javax.swing.*;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class UsersTab extends UserGroupTab {

    public static JComboBox userTimeCombo = new JComboBox();
    public  PersonalTreeModel treeModel;

    //UsersTab() {}
    UsersTab(int x, int y, int w, int h){
        super(x,y,w,h);
        treeModel = new PersonalTreeModel();
        selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        addedUsers.setModel(treeModel.getTreeModelAddedPersonal());
        freeUsers.setModel(treeModel.getTreeModelFreePersonal());
        treeLabel1.setText("Сотрудники, добавленные в расписание:");
        treeLabel2.setText("Сотрудники, отсутствующие в расписании:");
        jPanel1.add(userTimeCombo);

        userTimeCombo.setBackground(Color.white);
        userTimeCombo.createToolTip();
        userTimeCombo.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                        if (ev.getStateChange() == ItemEvent.SELECTED) {
                            MainForm.refreshPersonal(treeModel,userTimeCombo);
                            userTimeCombo.setToolTipText(userTimeCombo.getSelectedItem().toString());
                        }
                    }
                }
        );

        freeUsers.setSelectionModel(selModel);
        freeUsers.addTreeSelectionListener(new PersonalSelectionListener());
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TreePath[] tp = freeUsers.getSelectionPaths();
                    for (TreePath t : tp)
                        ((UserTime) userTimeCombo.getSelectedItem()).addPersonal(new Personal(t.getLastPathComponent().toString()));

                    treeModel.movePersonal(treeModel.getRootFreePersonal(), treeModel.getRootAddedPersonal(), freeUsers.getSelectionPaths());
                    treeModel.getTreeModelAddedPersonal().reload();
                    treeModel.getTreeModelFreePersonal().reload();
                } catch (NullPointerException exc) {
                    tk.beep();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TreePath[] tp = addedUsers.getSelectionPaths();
                    for (TreePath t : tp)
                        ((UserTime) userTimeCombo.getSelectedItem()).removePersonal(t.getLastPathComponent().toString());

                treeModel.movePersonal(treeModel.getRootAddedPersonal(), treeModel.getRootFreePersonal(), addedUsers.getSelectionPaths());
                treeModel.getTreeModelAddedPersonal().reload();
                treeModel.getTreeModelFreePersonal().reload();
                }catch (NullPointerException exc){
                    tk.beep();
                }
            }
        });
        basicLayer.updateUI();
        basicLayer.setVisible(true);
    }



}


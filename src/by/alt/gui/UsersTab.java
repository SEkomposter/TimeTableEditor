package by.alt.gui;

import by.alt.DAO.Personal;
import by.alt.Object.PersonalSelectionListener;
import by.alt.Object.PersonalTreeModel;
import by.alt.Object.UserTime;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;

import static by.alt.gui.MainForm.daoObject;
import static by.alt.gui.MainForm.treeModel;
import static by.alt.gui.MainForm.userTimeList;


public class UsersTab extends UserGroupTab {

    public static JTextField filterField;
    public static JComboBox userTimeCombo = new JComboBox();

    static {addedUsers = new JTree(treeModel.getTreeModelAddedPersonal());
            freeUsers = new JTree(treeModel.getTreeModelFreePersonal());}
    //UsersTab() {}
    UsersTab(int x, int y, int w, int h){
        super(x,y,w,h);
        treeLabel1.setText("Сотрудники, добавленные в расписание:");
        treeLabel2.setText("Сотрудники, отсутствующие в расписании:");
        filterField = new JTextField("Фильтр:");
        filterField.setForeground(Color.GRAY);
        filterField.addFocusListener(new by.alt.Object.FilterFieldListener());
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fillAllTrees();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillAllTrees();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fillAllTrees();
            }
        });

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 0.0;
        c.ipadx = 150;
        c.insets = new Insets(0, 0, 0, 15);
        c.anchor = GridBagConstraints.EAST;
        jPanel2.add(filterField,c);

        jPanel1.add(userTimeCombo);
        userTimeCombo.setBackground(Color.white);
        userTimeCombo.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                        if (ev.getStateChange() == ItemEvent.SELECTED) {
                            MainForm.refreshPersonal();
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
                    ((UserTime) userTimeCombo.getSelectedItem()).removePersonal(t.getLastPathComponent().toString());
                treeModel.movePersonal(treeModel.getRootAddedPersonal(), treeModel.getRootFreePersonal(), addedUsers.getSelectionPaths());
                treeModel.getTreeModelAddedPersonal().reload();
                treeModel.getTreeModelFreePersonal().reload();
            }
        });
        fillAllTrees();
        basicLayer.setVisible(true);
    }
    public JTextField getFilterField(){
        return filterField;
    }
    public void fillAllTrees(){
        treeModel.fillTreeFreePersonal(treeModel.getRootFreePersonal(), daoObject.getAllPersonal().toArray());
        treeModel.filterPersonal(filterField.getText());
        treeModel.getTreeModelFreePersonal().reload();
    }


}


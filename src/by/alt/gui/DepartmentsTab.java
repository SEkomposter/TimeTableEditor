package by.alt.gui;

import by.alt.DAO.Personal;
import by.alt.Object.GroupTime;
import by.alt.Object.PersonalTreeModel;
import by.alt.Object.UserTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static by.alt.gui.MainForm.treeModel2;

public class DepartmentsTab extends UserGroupTab {

    public static JComboBox groupTimeCombo = new JComboBox();
    static {addedUsers = new JTree(treeModel2.getTreeModelAddedPersonal());
        freeUsers = new JTree(treeModel2.getTreeModelFreePersonal());}
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
                            MainForm.refreshPersonal(MainForm.treeModel2,groupTimeCombo);
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
        fillAllTrees(treeModel2);
        //basicLayer.setVisible(true);
    }

}

package by.alt.gui;


import com.sun.prism.paint.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;

import static by.alt.gui.MainForm.treeModel;
import static by.alt.gui.MainForm.userTimeList;


public class UserGroupTab extends JPanel{
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel basicLayer = new JPanel();
    JLabel timeTableLabel = new JLabel("Расписание:");
    JLabel treeLabel1 = new JLabel();
    JLabel treeLabel2 = new JLabel();
    JButton addButton = new JButton("<= Добавить");
    JButton removeButton = new JButton("Убрать       =>");
    public static JTree addedUsers;
    public static JTree freeUsers;

    GridBagConstraints c = new GridBagConstraints();
    TreeSelectionModel selModel;

//UserGroupTab() {}
    UserGroupTab (int x, int y, int w, int h) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(w, h));

        selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        add(jPanel1);
        jPanel1.setBounds(x+10,y,w-30,30);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        jPanel3.add(treeLabel1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 0.3;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.EAST;
        jPanel3.add(treeLabel2, c);

        add(jPanel2);
        jPanel2.setBounds(x+10,y+30,w-30,20);
        jPanel2.setLayout(new GridBagLayout());
        jPanel3.setBounds(x+10,y+50,w-30,30);
        add(jPanel3);
        basicLayer.setBounds(x+10,y+80,w-30,h-180);
        add(basicLayer);
        jPanel1.add(timeTableLabel);
        setVisible(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setBounds(getX(), getY(), getWidth(), getHeight());
            }
        });
        basicLayer.setLayout(new GridBagLayout());
        //basicLayer.setBounds(x, y, w, h);
        jPanel3.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        jPanel3.add(treeLabel1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 0.3;
        c.weighty = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.EAST;
        jPanel3.add(treeLabel2, c);

    c.fill = GridBagConstraints.NONE;
    c.gridx = 2;
    c.gridy = 1;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 0.00;
    c.weighty = 0.0;
        c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(150, 30, 0, 30);
    basicLayer.add(addButton, c);

    c.fill = GridBagConstraints.NONE;
    c.gridx = 2;
    c.gridy = 2;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 0.00;
    c.weighty = 0.0;
        c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(0, 30, 250, 30);
    basicLayer.add(removeButton, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.7;
        c.ipadx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        basicLayer.add(new JScrollPane(addedUsers), c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.7;

        c.insets = new Insets(0, 0, 0, 0);
        basicLayer.add(new JScrollPane(freeUsers), c);
}
    public JComboBox fillCombo(JComboBox jComboBox,ArrayList list ){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            jComboBox.addItem(iterator.next());
        }
        return jComboBox;
    }

}
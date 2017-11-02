package by.alt.gui;

import by.alt.Object.PersonalTreeModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import static by.alt.gui.MainForm.*;

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
    JScrollPane jSPane, jSPane2;
    public  JTree addedUsers;
    public  JTree freeUsers;
    public JTextField filterField;

    GridBagConstraints c = new GridBagConstraints();
    TreeSelectionModel selModel;

//UserGroupTab() {}
    UserGroupTab (int x, int y, int w, int h) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(w, h));
        freeUsers = new JTree();
        addedUsers = new JTree();
        jSPane = new JScrollPane(addedUsers);
        jSPane2 = new JScrollPane(freeUsers);
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
        basicLayer.add(jSPane, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.7;
        c.insets = new Insets(0, 0, 0, 0);
        basicLayer.add(jSPane2, c);

        filterField = new JTextField("Фильтр:");
        filterField.setEnabled(false);
        filterField.setForeground(Color.GRAY);
        //filterField.addFocusListener(new by.alt.Object.FilterFieldListener());
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (filterField.getParent().getParent().equals(MainForm.getDepTab()))fillAllTrees(MainForm.getDepTab().treeModel2);
                    else fillAllTrees(MainForm.getUsersTab().treeModel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (filterField.getParent().getParent().equals(MainForm.getDepTab()))fillAllTrees(MainForm.getDepTab().treeModel2);
                else fillAllTrees(MainForm.getUsersTab().treeModel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (filterField.getParent().getParent().equals(MainForm.getDepTab()))fillAllTrees(MainForm.getDepTab().treeModel2);
                else fillAllTrees(MainForm.getUsersTab().treeModel);
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
}
    public JComboBox fillCombo(JComboBox jComboBox,ArrayList list ){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            jComboBox.addItem(iterator.next());
        }
        return jComboBox;
    }
    public JTextField getFilterField(){
        return filterField;
    }
    public void fillAllTrees(PersonalTreeModel tm){
        if (tm.equals(MainForm.getUsersTab().treeModel)) tm.fillTreeFreePersonal(tm.getRootFreePersonal(), daoObject.getAllPersonal().toArray());
        else tm.fillTreeFreePersonal(tm.getRootFreePersonal(), daoObject.getEndNodes().toArray());
        tm.filterPersonal(filterField.getText());
        tm.getTreeModelFreePersonal().reload();
    }

}
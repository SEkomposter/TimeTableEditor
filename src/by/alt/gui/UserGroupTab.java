package by.alt.gui;


import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static by.alt.gui.MainForm.treeModel;
import static by.alt.gui.MainForm.userTimeList;


public class UserGroupTab extends JPanel{
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
        this.setBounds(x, y, w, h);
        // this.add(new JScrollPane(addedUsers));
        this.setPreferredSize(new Dimension(w, h));

        selModel = new DefaultTreeSelectionModel();
        selModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        add(basicLayer);
        setVisible(true);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                basicLayer.setBounds(getX(), getY(), getWidth(), getHeight());
            }
        });
        basicLayer.setLayout(new GridBagLayout());
        basicLayer.setBounds(x, y, w, h);


    c.fill = GridBagConstraints.NONE;
    c.gridx = 1;
    c.gridy = 0;
    c.gridwidth = 1;
    c.weightx = 0.;
    c.weighty = 0.0;
    c.insets = new Insets(0, 170, 0, 0);
    c.anchor = GridBagConstraints.EAST;
    basicLayer.add(timeTableLabel, c);
    timeTableLabel.setBorder(new BasicBorders.MarginBorder());


    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.weightx = 0.3;
    c.weighty = 0.1;
    c.insets = new Insets(0, 0, 0, 0);
    basicLayer.add(treeLabel1, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 3;
    c.gridy = 2;
    c.gridwidth = 2;
    c.weightx = 0.3;
    c.weighty = 0.1;
    c.insets = new Insets(0, 0, 0, 0);
    basicLayer.add(treeLabel2, c);


    c.fill = GridBagConstraints.NONE;
    c.gridx = 2;
    c.gridy = 4;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 0.00;
    c.weighty = 0.0;
        c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(0, 0, 0, 0);
    basicLayer.add(addButton, c);

    c.fill = GridBagConstraints.NONE;
    c.gridx = 2;
    c.gridy = 5;
    c.gridwidth = 1;
    c.gridheight = 1;
    c.weightx = 0.00;
    c.weighty = 0.0;
        c.anchor = GridBagConstraints.CENTER;
    c.insets = new Insets(0, 0, 0, 0);
    basicLayer.add(removeButton, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.8;
        c.ipadx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        basicLayer.add(new JScrollPane(addedUsers), c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.weightx = 0.45;
        c.weighty = 0.8;

        c.insets = new Insets(0, 0, 0, 0);
        basicLayer.add(new JScrollPane(freeUsers), c);

}

}
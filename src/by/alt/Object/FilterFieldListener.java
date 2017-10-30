package by.alt.Object;

import by.alt.gui.DepartmentsTab;
import by.alt.gui.MainForm;
import by.alt.gui.UserGroupTab;
import by.alt.gui.UsersTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FilterFieldListener implements FocusListener {
    UserGroupTab container;
    JTextField ff;
    @Override
    public void focusGained(FocusEvent e) {
        container = (UserGroupTab)e.getComponent().getParent().getParent();
        ff = container.getFilterField();
        if(ff.getText().equals("Фильтр:")){
            ff.setText("");
            ff.setForeground(Color.black);
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        container = (UserGroupTab)e.getComponent().getParent().getParent();
        ff = container.getFilterField();
        if(ff.getText().equals("")) {
            ff.setText("Фильтр:");
            ff.setForeground(Color.gray);
            if (container instanceof UsersTab) MainForm.refreshPersonal(MainForm.treeModel, UsersTab.userTimeCombo);
            else MainForm.refreshPersonal(MainForm.treeModel2, DepartmentsTab.groupTimeCombo);
        }
    }
}

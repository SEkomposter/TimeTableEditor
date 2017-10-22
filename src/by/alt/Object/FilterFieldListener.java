package by.alt.Object;

import by.alt.gui.MainForm;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FilterFieldListener implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        if(MainForm.usersTab.filterField.getText().equals("Фильтр:")){
            MainForm.usersTab.filterField.setText("");
            MainForm.usersTab.filterField.setForeground(Color.black);}
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(MainForm.usersTab.filterField.equals("")) {
            MainForm.usersTab.filterField.setText("Фильтр:");
            MainForm.usersTab.filterField.setForeground(Color.gray);
            MainForm.refreshPersonal();
        }
    }
}

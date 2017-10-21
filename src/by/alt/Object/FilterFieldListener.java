package by.alt.Object;


import by.alt.gui.MainForm;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FilterFieldListener implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        if(MainForm.filterField.getText().equals("Фильтр:")){
            MainForm.filterField.setText("");
            MainForm.filterField.setForeground(Color.black);}
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(MainForm.filterField.getText().equals("")) {
            MainForm.filterField.setText("Фильтр:");
            MainForm.filterField.setForeground(Color.gray);
            MainForm.refreshPersonal();
        }
    }
}

package by.alt.Object;

import by.alt.gui.MainForm;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FilterFieldListener implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        if(MainForm.getUsersTab().getFilterField().getText().equals("Фильтр:")){
            MainForm.getUsersTab().getFilterField().setText("");
            MainForm.getUsersTab().getFilterField().setForeground(Color.black);}
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(MainForm.getUsersTab().getFilterField().equals("")) {
            MainForm.getUsersTab().getFilterField().setText("Фильтр:");
            MainForm.getUsersTab().getFilterField().setForeground(Color.gray);
            MainForm.refreshPersonal();
        }
    }
}

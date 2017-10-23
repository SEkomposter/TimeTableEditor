package by.alt.Object;

import by.alt.gui.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FilterFieldListener implements FocusListener {
    //JTextField jtf;
    //public FilterFieldListener(){
    //    jtf = MainForm.usersTab.getFilterField();
    //}
    @Override
    public void focusGained(FocusEvent e) {
        if(MainForm.usersTab.getFilterField().getText().equals("Фильтр:")){
            MainForm.usersTab.getFilterField().setText("");
            MainForm.usersTab.getFilterField().setForeground(Color.black);}
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(MainForm.usersTab.getFilterField().getText().equals("")) {
            MainForm.usersTab.getFilterField().setText("Фильтр:");
            MainForm.usersTab.getFilterField().setForeground(Color.gray);
            MainForm.refreshPersonal();
        }
    }
}

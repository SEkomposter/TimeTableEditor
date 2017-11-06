package by.alt.gui;

import by.alt.Object.GroupTime;
import by.alt.Object.TableEntry;
import by.alt.Object.UserTime;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static by.alt.gui.MainForm.*;
import static by.alt.gui.MainForm.tableUpdate;
import static by.alt.gui.MainForm.tt;

public class DifferentB extends JButton {
    public DifferentB(String text){
        setText(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableEntry addedTableEntry = new TableEntry();
                addedTableEntry = addedTableEntry.getTableEntryFromDialog();

                if(addedTableEntry.getTableEntryFromDialog().getName().equals("")){
                    JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                            "Имя расписания обязательно к заполнению!",
                            "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                }else
                {
                    switch(text){
                        case "Добавить":
                        {
                            //определяем есть ли такая запись в списке параметров и выводит предупреждение
                            if (addedTableEntry.isEntryPresentInList()) {
                                JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                                        "Расписание с таким именем и режимом уже существует!",
                                        "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                TableEntry te = addedTableEntry.getTableEntryFromDialog();
                                MainForm.tableEntryList.add(te);
                                UserTime ut = new UserTime(te.getName(),te.getShedule());
                                MainForm.userTimeList.add(ut);
                                getUsersTab().userTimeCombo.addItem(ut);
                                ut = new GroupTime(te.getName(),te.getShedule());
                                MainForm.groupTimeList.add(ut);
                                getDepTab().groupTimeCombo.addItem(ut);
                                tableUpdate();
                                MainForm.timeTableEditor.dispose();
                            }
                            break;
                        }
                        case "Изменить":
                        {
                            //проверяем, не было ли изменено имя расписания в форме по сравнению с выбранной строкой таблицы (false - не изменено):
                            boolean option1 = addedTableEntry.isChanged(tt,addedTableEntry);
                            //проверка, если в таблице строка с таким именем:
                            boolean option2 = addedTableEntry.isEntryPresentInList();
                            if (option1&option2) {
                                JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                                        "Расписание с таким именем и режимом уже существует!",
                                        "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                int sel = tt.getSelectedRow();
                                MainForm.userTimeList.findObjByName((String) tt.getValueAt(sel,0),(String) tt.getValueAt(sel,1),TimeTableEditor.getNameFromDialog(),TimeTableEditor.getSheduleFromDialog());
                                MainForm.groupTimeList.findObjByName((String) tt.getValueAt(sel,0),(String) tt.getValueAt(sel,1),TimeTableEditor.getNameFromDialog(),TimeTableEditor.getSheduleFromDialog());
                                tt.setValueAt(TimeTableEditor.getNameFromDialog(),sel,0);
                                tt.setValueAt(TimeTableEditor.getSheduleFromDialog(),sel,1);
                                tt.setValueAt(TimeTableEditor.getFromTimeFromDialog(),sel,2);
                                tt.setValueAt(TimeTableEditor.getToTimeFromDialog(),sel,3);
                                tableUpdate();
                                MainForm.timeTableEditor.dispose();
                            }
                        }
                    }}
            }
        });
    }
}

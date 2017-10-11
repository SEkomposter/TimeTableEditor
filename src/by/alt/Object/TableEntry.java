package by.alt.Object;

import by.alt.DAO.Personal;
import by.alt.gui.MainForm;
import by.alt.gui.TimeTableEditor;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

import static by.alt.gui.MainForm.tableEntryList;
import static by.alt.gui.MainForm.tableModel;

public class TableEntry {
    private String name, shedule, timeFrom, timeTo;


    public TableEntry(){}
    public TableEntry(String n,String s,String tf, String tt){
        this.setName(n);
        this.setShedule(s);
        this.setTimeFrom(tf);
        this.setTimeTo(tt);
    }

    //Читаем данные из формы добавления или редактирования расписания
    public TableEntry getTableEntryFromDialog(){
        return new TableEntry(TimeTableEditor.getNameFromDialog(),TimeTableEditor.getSheduleFromDialog(),TimeTableEditor.getFromTimeFromDialog(),TimeTableEditor.getToTimeFromDialog());
    }

    public boolean isEntryPresentInList(){
        //определяем есть ли такая запись в списке параметров
        boolean isPresents = false;
        Iterator it = MainForm.tableEntryList.iterator();
        while(it.hasNext()&!isPresents)
        {
            if (it.next().toString().startsWith(PropType.TIMETABLE.toString() + "." + this.getShedule() + "." + this.getName())) isPresents = true;
        }
        return isPresents;
    }
    public boolean isChanged(JTable table, TableEntry tableEntry) {
        //определяем не было ли изменено имя расписания в форме по сравнению с выбранной строкой таблицы (false - не изменено)
        String str1 = PropType.TIMETABLE.toString() + "." + tableEntry.getTableEntryFromDialog().getShedule() + "." + tableEntry.getTableEntryFromDialog().getName();
        String str2 = PropType.TIMETABLE.toString() + "." +  tableModel.getValueAt(table.getSelectedRow(),1)+ "." +  tableModel.getValueAt(table.getSelectedRow(),0);
        return !str1.equals(str2);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShedule() {
        return shedule;
    }

    public void setShedule(String shedule) {
        this.shedule = shedule;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    @Override
    public String toString() {
        return "timetable."+ shedule +"."+ name + "=" + timeFrom + "-" + timeTo;
    }

}


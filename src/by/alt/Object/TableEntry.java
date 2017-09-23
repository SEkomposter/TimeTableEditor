package by.alt.Object;


import by.alt.gui.MainForm;
import by.alt.gui.TimeTableEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableEntry {
    private String name, shedule,timeFrom,timeTo;
    Map<String,String> map = new LinkedHashMap<>();
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
    public void setTableEntry(String n,String s,String tf, String tt){
        setName(n);
        setShedule(s);
        setTimeTo(tt);
        setTimeFrom(tf);
    }
    public ArrayList<TableEntry> getTableEntryList (){
        PropReader propReader = new PropReader();
        ArrayList<TableEntry> tableEntryArrayList = new ArrayList<>();
        try {
            map.putAll(propReader.readRepProp(PropType.TIMETABLE));
            Iterator it = map.keySet().iterator();
            String temp = "";
            while (it.hasNext()){
                temp = (String) it.next();
                tableEntryArrayList.add(parseName(temp, map.get(temp)));
            }
            //System.out.println(tableEntryArrayList);
        }
        catch (IOException exc){
            exc.printStackTrace();
        }
        return tableEntryArrayList;
    }
    static TableEntry parseName(String key, String val){
        TableEntry tempTE = new TableEntry();
        String[] strings = new String[3];
        strings = key.split("\\.");
        String[] strings2 = new String[2];
        strings2 = val.split("-");
        tempTE.setName(strings[2]);
        tempTE.setShedule(strings[1]);
        tempTE.setTimeFrom(strings2[0]);
        tempTE.setTimeTo(strings2[1]);
        return tempTE;
    }

    public String getName(String str) {
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
        return "timetable."+ shedule +"."+ name + "=" + timeFrom + "-" + timeTo+ "\n";
    }
}


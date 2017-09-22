package by.alt.Object;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class TableEntry {
    private String name, shedule,timeFrom,timeTo;
    Map<String,String> map = new LinkedHashMap<>();
    TableEntry(){}
    public TableEntry(String n,String s,String tf, String tt){
        this.setName(n);
        this.setShedule(s);
        this.setTimeFrom(tf);
        this.setTimeTo(tt);
    }
    public TableEntry getTableEntry(){
        return new TableEntry(getName(),getShedule(),getTimeFrom(),getTimeTo());
    }
    public TableEntry setTableEntry(String n,String s,String tf, String tt){
        return new TableEntry(n,s,tf,tt);
    }
    public ArrayList<TableEntry> getTableEntryList (ArrayList<TableEntry> tel){
        PropReader propReader = new PropReader();
        try {
            map.putAll(propReader.readRepProp(PropType.TIMETABLE));
            Iterator it = map.keySet().iterator();
            String temp = "";
            while (it.hasNext()){
                TableEntry tempTE = new TableEntry();
                temp = (String) it.next();

            }
        }
        catch (IOException exc){
            exc.printStackTrace();
        }

        return new TableEntry();
    }
    static String parseName(String key){
        Matcher matcher =
        key.
        return new String();
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
        return "timetable."+ shedule + name + "=" + timeFrom + "-" + timeTo+ "\n";
    }
}


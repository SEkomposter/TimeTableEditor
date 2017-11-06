package by.alt.Object;

import by.alt.DAO.Personal;
import by.alt.gui.MainForm;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PropReader {
    String fileName = "report03.properties";
    String commProps = "common.properties";
    String backup = "";
    static String fileSeparator = System.getProperty("file.separator");
    static String path = "D:" + fileSeparator + "ExternalReports" + fileSeparator;
    static String pathBackup = "configBackup" + fileSeparator;
   // static String path = "";

    File filePath = new File(path + fileName);
    File filePathCommon = new File(path + commProps);
    File filePathBackup;// = new File(path + pathBackup + backup);
    static HashMap<String,String> properties = new LinkedHashMap<String,String>();
    static Properties property = new Properties();
    private static TreeSet<TableEntry> tableEntrySet = new TreeSet<>();
    private static TreeSet<TableEntry> userTimeSet = new TreeSet<>();
    private static TreeSet<TableEntry> groupTimeSet = new TreeSet<>();
    public PropReader(){

    }
    public void writeRepProp() throws IOException{
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        String[] entries = new String[2];
        ArrayList<TableEntry> tableEntries = new ArrayList<>();
        tableEntries.addAll(MainForm.tableEntryList);
        tableEntries.addAll(MainForm.groupTimeList);
        tableEntries.addAll(MainForm.userTimeList);
            Iterator iterator = tableEntries.iterator();
            while (iterator.hasNext()) {
                Object obj = iterator.next();
                if (obj instanceof UserTime) {
                    map.put(obj.toString(), ((UserTime) obj).getPersonalAdded().toString());
                } else {
                    entries = obj.toString().split("=");
                    map.put(entries[0], entries[1]);
                }
            }
        property.clear();
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePath);

            Iterator it = map.keySet().iterator();
            String pr;
            while (it.hasNext()) {
                pr=it.next().toString();
                property.setProperty(pr, map.get(pr));
            }
            property.store(fos,"");
            fos.close();

        } catch (IOException exc) {
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
    }
    public void backupRepProps (){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyyMMdd'-'hhmm'_'");
        backup = formatForDateNow.format(dateNow);
        File backupDir = new File(path + pathBackup + "\\");
            backupDir.mkdirs();
        try {
            filePathBackup = new File(path + pathBackup + backup + fileName);
            FileOutputStream fos;
            filePathBackup.createNewFile();
            fos = new FileOutputStream(filePathBackup);
            property.store(fos,"");
            fos.close();
        } catch (IOException exc) {
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
    }
    public String readCommonProps(String propName){
        Properties property = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream(filePathCommon);
            property.load(fis);
        } catch (IOException exc) {
            new Logger().pushToScreenNlog(exc,exc.getClass());
    }
        return property.get(propName).toString();
    }
    public void removeProperty(String prop){
            property.remove(prop);
    }
    public void removeAllProperties(){
        property.clear();
    }

    public TreeSet<? extends TableEntry> getPropertiesList (PropType en){
        switch (en){
        case TIMETABLE:
            return tableEntrySet;
            case USERTIME:
                return userTimeSet;
            case GROUPTIME:
                return groupTimeSet;
            default:
                return null;
        }
    }
    public Map<String,String> readRepProp() throws IOException{
        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);
            property.load(fis);
            Iterator it = property.keySet().iterator();
            String pr;
            properties.clear();
            tableEntrySet.clear();
            userTimeSet.clear();
            while (it.hasNext()) {
                pr=it.next().toString();
                properties.put(pr, property.getProperty(pr));
                if (pr.toLowerCase().startsWith(PropType.TIMETABLE.toString().toLowerCase())) tableEntrySet.add(parseTimeTable(pr, property.getProperty(pr)));
                else if (pr.toLowerCase().startsWith(PropType.USERTIME.toString().toLowerCase()))userTimeSet.add(parseUsersGroups(pr, property.getProperty(pr),new UserTime()));
                else groupTimeSet.add(parseUsersGroups(pr, property.getProperty(pr), new GroupTime()));
            }
            fis.close();
        } catch (IOException exc) {
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
        return properties;
    }

    static TableEntry parseTimeTable(String key, String val){
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
    static TableEntry parseUsersGroups(String key, String val, TableEntry tempTE) {
        //Object tempTE = cl.newInstance();
        String[] strings = new String[3];
        strings = key.split("\\.");
         //   strings2 = val.split(",");
        tempTE.setName(strings[2]);
        tempTE.setShedule(strings[1]);
        String[] strings2 = new String[val.split(",").length];
        int i =0;
        for (String s: val.split(","))  {
            strings2[i] = s;
            i++;
        }
        i=0;
        for (i = 0; i < strings2.length; i++){
            if (strings2[i].startsWith(" ")) strings2[i] = strings2[i].substring(1);
            tempTE.addPersonal(new Personal(strings2[i]));}
        return tempTE;
    }
}

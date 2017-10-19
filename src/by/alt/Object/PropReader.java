package by.alt.Object;

import by.alt.DAO.Personal;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.io.*;
import java.util.*;

public class PropReader {
    String fileName = "report03.properties";
    String commProps = "common.properties";
    File filePath = new File("D:\\TimeTableEditor\\");
    static HashMap<String,String> properties = new LinkedHashMap<String,String>();
    static Properties property = new Properties();

    private static TreeSet<TableEntry> tableEntrySet = new TreeSet<>();
    private static TreeSet<TableEntry> userTimeSet = new TreeSet<>();
    private static TreeSet<TableEntry> groupTimeSet = new TreeSet<>();
    public PropReader(){

    }

    public void writeRepProp(ArrayList<TableEntry> tableEntries) throws IOException{
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        String[] entries = new String[2];
        Iterator iterator = tableEntries.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();
            if (obj instanceof UserTime){
                map.put(obj.toString(),((UserTime)obj).getPersonalAdded().toString());
                }
            else {
                entries = obj.toString().split("=");
                map.put(entries[0],entries[1]);
            }
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("src/resources/"+fileName);

            Iterator it = map.keySet().iterator();
            String pr;
            while (it.hasNext()) {
                pr=it.next().toString();
                property.setProperty(pr, map.get(pr));
            }
            property.store(fos,"");
            fos.close();

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }

    public String readCommonProps(String propName){
        Properties property = new Properties();
        FileInputStream fis;
        try {
            fis = new FileInputStream("src/resources/"+ commProps);
            property.load(fis);
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
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
            fis = new FileInputStream("src/resources/"+fileName);
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
        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
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

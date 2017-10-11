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

    ArrayList<TableEntry> tableEntryArrayList = new ArrayList<>();
    public PropReader(){}

    Map<String,String> readRepProp(Enum en) throws IOException{
            FileInputStream fis;
            try {
                fis = new FileInputStream("src/resources/"+fileName);
                property.load(fis);
                Iterator it = property.keySet().iterator();
                String pr;
                properties.clear();
                while (it.hasNext()) {
                    pr=it.next().toString();
                    if (pr.startsWith(en.toString()))
                        properties.put(pr, property.getProperty(pr));
                }
               // System.out.print(properties);
                fis.close();
            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }

    return properties;
    }
    public void writeRepProp(Map<String,String> map) throws IOException{

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
            readRepProp(PropType.TIMETABLE);
            //System.out.println(propertiesIn);
            fos.close();

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
    public LinkedHashMap<String,String> preparePropsForWrite(ArrayList<TableEntry> tableEntries){
        LinkedHashMap<String, String> tempMap = new LinkedHashMap<String, String>();
        String[] entries = new String[2];
        Iterator iterator = tableEntries.iterator();
        while (iterator.hasNext()){
            entries = iterator.next().toString().split("=");
            tempMap.put(entries[0],entries[1]);
        }
        return tempMap;
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

    public ArrayList<TableEntry> getTableEntryList (PropType en){
        Map<String,String> map = new LinkedHashMap<>();
        try {
            map.putAll(readRepProp(en));
            Iterator it = map.keySet().iterator();
            String temp = "";
            while (it.hasNext()){
                temp = (String) it.next();
                if (en==PropType.TIMETABLE) tableEntryArrayList.add(parseTimeTable(temp, map.get(temp)));
                else tableEntryArrayList.add(parseUsersGroups(temp, map.get(temp)));
            }
        }
        catch (IOException exc){
            exc.printStackTrace();
        }
        return tableEntryArrayList;
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
    static TableEntry parseUsersGroups(String key, String val) {
        UserTime tempTE = new UserTime();
        String[] strings = new String[3];
        strings = key.split("\\.");
        String[] strings2 = val.split("\\,");
        //strings2 = val.split("-");
        tempTE.setName(strings[2]);
        tempTE.setShedule(strings[1]);
        for (int i = 0; i < strings2.length; i++)
            tempTE.addPersonal(new Personal(strings2[i]));
        return tempTE;
    }
}

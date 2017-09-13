package by.alt;

import java.io.*;
import java.util.*;

public class PropReader {
    String fileName = "report03.properties";
    String commProps = "common.properties";
    File filePath = new File("D:\\TimeTableEditor\\");
    static HashMap<String,String> properties = new LinkedHashMap<String,String>();

    Map ReadRepProp() throws IOException{
            FileInputStream fis;
            Properties property = new Properties();

            try {
                fis = new FileInputStream("src/resources/"+fileName);
                property.load(fis);
                Iterator it = property.keySet().iterator();
                String pr;
                while (it.hasNext()) {
                    pr=it.next().toString();
                    properties.put(pr, property.getProperty(pr));
                }
                System.out.print(properties);

            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }

    return properties;
    }
    void WriteRepProp(Map<String,String> map) throws IOException{

        FileOutputStream fos;
        Properties property = new Properties();
        try {
            fos = new FileOutputStream("src/resources/"+fileName);

            Iterator it = map.keySet().iterator();
            String pr;
            while (it.hasNext()) {
                pr=it.next().toString();
                property.put(pr, map.get(pr));
            }
            property.store(fos,"");
            System.out.println(properties);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
    }
    public String ReadCommonProps(String propName){
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
}

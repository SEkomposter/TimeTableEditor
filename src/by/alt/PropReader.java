package by.alt;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class PropReader {
    String fileName = "report03.properties";
    File filePath = new File("D:\\TimeTableEditor\\");
    HashMap<String,String> properties = new LinkedHashMap<String,String>();
    Map ReadProp() throws IOException{

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

}

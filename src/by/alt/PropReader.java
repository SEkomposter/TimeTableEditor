package by.alt;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class PropReader {
    String fileName = "report03.properties";
    File filePath = new File("D:\\ExternalReports\\");
    HashSet<String> paramStrings = new LinkedHashSet<>();
    HashMap properties = new LinkedHashMap();
    String[] mapEntry = new String[2];
    String s1;
    BufferedReader input;
    Map Read(String file) throws IOException{
    try
    {
        input = new BufferedReader(new InputStreamReader(new FileInputStream(file+fileName),"UTF-8"));
        while ((s1=input.readLine())!=null){
            if (!s1.startsWith("#")){
                paramStrings.add(s1);
            }
        }
        //System.out.println(paramStrings);
        Iterator it = paramStrings.iterator();
        while (it.hasNext()){
            mapEntry = it.next().toString().split("=");
               byte[] bytes = mapEntry[1].getBytes("UTF-8");

           properties.put(mapEntry[0],new String(bytes,"UTF-8"));
        }
        System.out.println(properties);
    }
    catch(IOException exc){

        System.out.println("FileNotFound");
    }
    finally {
        input.close();
    }
    return properties;
    }

}

package by.alt;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        PropReader propReader = new PropReader();
        try {
         //  propReader.Read("D:\\ExternalReports\\");
            propReader.ReadRepProp();
        }
        catch (IOException exc){
            exc.printStackTrace();
        }

    }
}

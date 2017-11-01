package by.alt.Object;

import java.util.ArrayList;
import java.util.Iterator;

public class TEntryList extends ArrayList{
    public TableEntry findObjByName(String oldN, String oldS, String newN, String newS){
        Iterator it = this.iterator();
        TableEntry obj = null;
        while (it.hasNext()){
            obj = (TableEntry) it.next();
            if (obj.getName().equals(oldN)&&obj.getShedule().equals(oldS)) break;
        }
        obj.setName(newN);
        obj.setShedule(newS);
        return obj;
    }
}
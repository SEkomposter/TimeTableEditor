package by.alt.Object;

import by.alt.DAO.Personal;
import by.alt.gui.MainForm;

import javax.swing.tree.TreePath;
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
    public String findAddedPersonal (TreePath[] paths){
        ArrayList<String> tempList = new ArrayList<>();
        TableEntry tempTE;
        StringBuilder strings = new StringBuilder();
        String name;
        for (TreePath tp:paths){
            name = tp.getLastPathComponent().toString();
            strings.append("\""+name+"\"" + "\nесть в расписаниях:\n");
            Iterator it = this.iterator();
            while (it.hasNext()){
                tempTE = (TableEntry) it.next();
                Iterator it2 = tempTE.getPersonalAdded().iterator();
                while (it2.hasNext()){
                    if (((Personal)it2.next()).toString().equals(name)) {strings.append(tempTE.toString() + "\n"); break;}
                }
        }
        strings.append("\n");
        }
        return strings.toString();
    }
}
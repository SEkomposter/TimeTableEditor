package by.alt.Object;

import java.util.ArrayList;
import java.util.Iterator;

public class AddedSurvObjectList {
    ArrayList<TableEntry> survObjMembership = new ArrayList<>();
    public void addMembership (TableEntry te){
        survObjMembership.add(te);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.toString() + ":\n\n");
        Iterator it = survObjMembership.iterator();
        while (it.hasNext()) {
            sb.append((TableEntry)it.next());
            sb.append("\n");
        }
        return sb.toString();
    }
}

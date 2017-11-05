package by.alt.DAO;
import by.alt.Object.TableEntry;
import by.alt.Object.UserTime;

import java.util.ArrayList;
import java.util.Iterator;

import static by.alt.DAO.ObjectType.EMP;

public class Personal implements SurvObject, Comparable{
    private String name;
    private static final String TYPE = EMP.toString();
    private int id, parent_id;
    private Node node;
    ArrayList<TableEntry> survObjMembership = new ArrayList<>();

    public Personal(){}
    public Personal(String name){
        this.name = name;
    }
    Personal(int id, String name, int parent_id){
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
    }
    public void addSurvObjMembership (TableEntry te){
        survObjMembership.add(te);
    }

    public ArrayList<TableEntry> getSurvObjMembership() {
        return survObjMembership;
    }
    public String memberList(){
        StringBuilder sb = new StringBuilder(this.toString() + ":\n\n");
        Iterator it = survObjMembership.iterator();
        while (it.hasNext()) {
            sb.append((TableEntry)it.next());
            sb.append("\n");
        }
        return sb.toString();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return TYPE;
    }

    public int hashCode(){
        return this.getName().hashCode();
    }

    public boolean equals(Object obj){
       return this.getName().equals(((Personal)obj).getName());
   }
    public String toString(){
        return getName();
    }
    @Override
    public int compareTo(Object o) {
        return  (((Personal) o).name.compareToIgnoreCase(this.getName())<0?1: ((Personal)o).name.compareToIgnoreCase(this.name)==0?0:-1);
    }

}

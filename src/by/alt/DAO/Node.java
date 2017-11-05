package by.alt.DAO;

import by.alt.Object.TableEntry;
import by.alt.Object.UserTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Node implements SurvObject,Comparable {
    private String name;
    private String type = ObjectType.DEP.toString();
    private int id, parent_id;
    private ArrayList<SurvObject> childObjList = new ArrayList<>();
    ArrayList<TableEntry> survObjMembership = new ArrayList<>();
    private boolean hasChildNode = false;
    Node(){}
    public Node(String name){
        this.name = name;
    }
    Node(int id, String name, int parent_id){
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
    @Override
    public String toString(){
        return getName();
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Node))
            return false;
        Node entry = (Node) obj;
        return name.equals(entry.name);
    }
    public int getId() {return id;}

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
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public ArrayList<SurvObject> getChildObjList() {
        return childObjList;
    }

    public void setChildObjList(Collection list) {
        this.childObjList.clear();
        this.childObjList.addAll(list);
    }
    public void removeChildObject(int id){
        SurvObject deleted;
        Iterator it = this.getChildObjList().iterator();
        while (it.hasNext()){
            deleted = (SurvObject) it.next();
            if (deleted.getId() == id){
                childObjList.remove(deleted);
            }
        }
    }
    public boolean isHasChildNode() {
        boolean option1 = false, option2 = false;
        if(childObjList.size()!=0) option1=true;
        Iterator it = childObjList.iterator();
        Object tempObj = null;
        while(it.hasNext()){
            tempObj = it.next();
            if (tempObj instanceof Node) option2=true;
        }
        return option1&option2;
    }
    public void setHasChildNode(boolean hasChildNode) {
        this.hasChildNode = hasChildNode;
    }
    public int compareTo(Object o) {
        return  (((Node) o).name.compareToIgnoreCase(this.getName())<0?1: ((Node)o).name.compareToIgnoreCase(this.name)==0?0:-1);
    }
}

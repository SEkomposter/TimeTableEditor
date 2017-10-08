package by.alt.DAO;


import java.util.ArrayList;
import java.util.Iterator;

import static by.alt.DAO.ObjectType.DEP;

public class RootNode extends Node{
    private static String name = "Объект";
    private static final String TYPE = DEP.toString();
    private static final int ID = 0;
    private boolean hasChildNode = false;
    private ArrayList<SurvObject> childObjList = new ArrayList<>();
   // private static RootNode rootNode = new RootNode("Объект");
    RootNode(){}
    RootNode(String name){
        setName(name);
    }

    public int getId() {
        return ID;
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

    public ArrayList<SurvObject> getChildObjList() {
        return childObjList;
    }

    public void setChildObjList(ArrayList<SurvObject> list) {
        this.childObjList = list;
    }

    public void removeChildObject(int id){
        SurvObject deleted = null;
        Iterator it = this.getChildObjList().iterator();
        while (it.hasNext()){
            deleted = (SurvObject) it.next();
            if (deleted.getId() == id){
                break;
            }
        }
        childObjList.remove(deleted);
    }

}

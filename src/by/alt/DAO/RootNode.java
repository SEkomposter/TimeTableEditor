package by.alt.DAO;


import java.util.ArrayList;
import java.util.Iterator;

import static by.alt.DAO.ObjectType.DEP;

public class RootNode implements SurvObject{
    private static String name = "";
    private static final String TYPE = DEP.toString();
    private static final int ID = 0;
    private boolean hasChildNode = false;
    private ArrayList<? extends SurvObject> childObjList = new ArrayList<>();
    private static RootNode rootNode = new RootNode("Объект");
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

    public ArrayList<? extends SurvObject> getChildObjList() {
        return childObjList;
    }

    public void setChildObjList(ArrayList<? extends SurvObject> list) {
        this.childObjList = list;
    }

    public boolean isHasChildNode() {
        return hasChildNode;
    }
    public void setHasChild(boolean hasChildNode) {
        this.hasChildNode = hasChildNode;
    }
    public static RootNode getRootNode(){return rootNode;}
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

package by.alt.DAO;


import java.util.ArrayList;

import static by.alt.DAO.ObjectType.DEP;

public class RootNode implements SurvObject{
    private static String name = "";
    private static final String TYPE = DEP.toString();
    private static final int ID = 0;
    private boolean hasChildNode = false;
    private ArrayList<SurvObject> childObjList = new ArrayList<>();
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

    public boolean isHasChildNode() {
        return hasChildNode;
    }
    public void setHasChild(boolean hasChildNode) {
        this.hasChildNode = hasChildNode;
    }
}

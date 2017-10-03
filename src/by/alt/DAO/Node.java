package by.alt.DAO;

import java.util.ArrayList;

public class Node implements SurvObject {
    private String name;
    private String type = ObjectType.DEP.toString();
    private int id, parent_id;
    private ArrayList<SurvObject> childObjList = new ArrayList<>();
    private boolean hasChildNode = false;
    Node(){}
    Node(int id,String name, int parent_id){
        this.id = id;
        this.name = name;
        this.parent_id = parent_id;
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

    public void setChildObjList(ArrayList<SurvObject> list) {
        this.childObjList = list;
    }

    public boolean isHasChildNode() {
        return hasChildNode;
    }
    public void setHasChildNode(boolean hasChildNode) {
        this.hasChildNode = hasChildNode;
    }
}

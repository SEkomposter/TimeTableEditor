package by.alt.DAO;

import java.util.ArrayList;

public class Node implements SurvObject {
    private String name, type;
    private int id, parent_id;
    private Node node;
    private ArrayList<SurvObject> list = new ArrayList<>();

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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ArrayList<SurvObject> getList() {
        return list;
    }

    public void setList(ArrayList<SurvObject> list) {
        this.list = list;
    }
}

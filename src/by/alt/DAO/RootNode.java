package by.alt.DAO;


import java.util.ArrayList;

import static by.alt.DAO.ObjectType.DEP;

public class RootNode implements SurvObject{
    private static final String NAME = "Объект";
    private static final String TYPE = DEP.toString();
    private static final int ID = 0;
    private ArrayList<SurvObject> list = new ArrayList<>();

    public int getId() {
        return ID;
    }

    public String getName() {
        return NAME;
    }

    public String getType() {
        return TYPE;
    }

    public ArrayList<SurvObject> getList() {
        return list;
    }

    public void setList(ArrayList<SurvObject> list) {
        this.list = list;
    }
}

package by.alt.Object;


import by.alt.DAO.Personal;

import java.util.Set;
import java.util.TreeSet;

public class GroupTime extends UserTime{
    private String name, shedule;
    public GroupTime(){}
    public GroupTime(String n,String s){
        this.name = n;
        this.shedule = s;
    }

    @Override
    public String toString() {
        return "groupTime."+ super.getShedule() +"."+ super.getName();
    }

}

package by.alt.Object;


import by.alt.DAO.Personal;

import java.util.Set;
import java.util.TreeSet;

public class GroupTime extends UserTime{

    @Override
    public String toString() {
        return "groupTime."+ super.getShedule() +"."+ super.getName();
    }

}

package by.alt.Object;


import by.alt.DAO.Personal;

import java.util.Set;
import java.util.TreeSet;

public class UserTime extends TableEntry {
    private String name, shedule, timeFrom, timeTo;
    public UserTime(){
        super();

    }

    private Set<Personal> personalAdded = new TreeSet<Personal>();

    public void setPersonalAdded(Set<Personal> personalAdded) {
        this.personalAdded = personalAdded;
    }

    public void addPersonal(Personal newPers) {
        personalAdded.add(newPers);
    }

    public void removePersonal(Personal delPers) {
        personalAdded.remove(delPers);
    }

    public void addAllPersonal(TreeSet<Personal> personalTreeSet) {
        personalAdded.addAll(personalTreeSet);
    }

    public void clearUserTime() {
        personalAdded.clear();
    }
    @Override
    public String toString() {
        return "userTime."+ shedule +"."+ name;
    }

}

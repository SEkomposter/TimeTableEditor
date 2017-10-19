package by.alt.Object;


import by.alt.DAO.Personal;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class UserTime extends TableEntry implements Comparable{
    public UserTime(){}
    public UserTime(String n,String s){
        this.setName(n);
        this.setShedule(s);
    }

    private Set<Personal> personalAdded = new PersonalTreeSet<Personal>();

    public void setPersonalAdded(Set<Personal> personalAdded) {
        this.personalAdded = personalAdded;
    }

    public void addPersonal(Personal newPers) {
        personalAdded.add(newPers);
    }
    public Set<Personal> getPersonalAdded(){
        return personalAdded;
    }

    public void removePersonal(String name) {
        Iterator iterator = personalAdded.iterator();
        Personal delPers = new Personal();
        while (iterator.hasNext()) {
            delPers = (Personal) iterator.next();
            if (delPers.toString().equalsIgnoreCase(name))
             break;
        }
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
        return "userTime."+ super.getShedule() +"."+ super.getName();
    }
    @Override
    public int compareTo(Object o) {
        return  (((UserTime)o).toString().compareToIgnoreCase(this.toString())<0?1: ((UserTime)o).toString().compareToIgnoreCase(this.toString())==0?0:-1);
    }

}

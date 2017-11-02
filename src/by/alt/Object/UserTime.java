package by.alt.Object;

public class UserTime extends TableEntry implements Comparable{
    public UserTime(){}
    public UserTime(String n,String s){
        this.setName(n);
        this.setShedule(s);
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

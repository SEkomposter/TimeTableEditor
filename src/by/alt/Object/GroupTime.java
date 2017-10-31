package by.alt.Object;

public class GroupTime extends UserTime{
    private String name, shedule;
    public GroupTime(){}
    public GroupTime(String n,String s){
        this.setName(n);
        this.setShedule(s);
    }

    @Override
    public String toString() {
        return "groupTime."+ super.getShedule() +"."+ super.getName();
    }

}

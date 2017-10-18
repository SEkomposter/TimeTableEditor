package by.alt.Object;


import java.util.TreeSet;

public class PersonalTreeSet<Personal> extends TreeSet{
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object[] obj = super.toArray();
        for(Object o: obj)
            sb.append(o.toString()+ ",");
        return sb.toString();
    }
}

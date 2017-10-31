package by.alt.Object;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class DepTreeModel extends PersonalTreeModel{
    public void filterPersonal (String mask){
        DefaultMutableTreeNode source = getRootFreePersonal();
        ArrayList list = new ArrayList();
        for (int i=0;i<source.getChildCount();i++) {
            Object obj = source.getChildAt(i);
            if (obj.toString().toLowerCase().contains(mask.toLowerCase()))
                list.add(obj);
        }
        source.removeAllChildren();
        fillTreeFreePersonal(source, list.toArray());
        removeAddedFromFree(getRootAddedPersonal(),source);
    }
}

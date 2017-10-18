package by.alt.Object;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Personal;

import javax.swing.tree.*;
import java.util.Enumeration;

public class PersonalTreeModel {
    private static DaoClass daoObject = new DaoClass();
    private DefaultMutableTreeNode rootAddedPersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    private DefaultMutableTreeNode rootFreePersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    public DefaultTreeModel treeModelAddedPersonal = new DefaultTreeModel(rootAddedPersonal, false);
    public DefaultTreeModel treeModelFreePersonal = new DefaultTreeModel(rootFreePersonal, false);

    public void fillTreeFreePersonal(DefaultMutableTreeNode treeNode){
        Object[] tempArray = daoObject.getAllPersonal().toArray();
        for (int i=0; i< tempArray.length; i++){
            treeNode.add(new DefaultMutableTreeNode(tempArray[i],false));
        }
    }
    public static void fillTreeAddedPersonal(DefaultMutableTreeNode treeNode, UserTime userTime){
        Object[] tempArray =  userTime.getPersonalAdded().toArray();
        for (int i=0; i< tempArray.length; i++){
            treeNode.add(new DefaultMutableTreeNode(tempArray[i],false));
        }
    }
    public void delAllPersonal (DefaultMutableTreeNode treeNode){
        treeNode.removeAllChildren();
    }
    public void removeAddedFromFree(DefaultMutableTreeNode added,DefaultMutableTreeNode free){
        for (int i=0;i<added.getChildCount();i++) {
            for (int j=0;j<free.getChildCount();j++)
                if(free.getChildAt(j).toString().equals(added.getChildAt(i).toString())) free.remove(j);

        }
    }
    public void movePersonal (DefaultMutableTreeNode source, DefaultMutableTreeNode target, TreePath[] treePaths){
        for (TreePath tp:treePaths){
            String str = tp.getLastPathComponent().toString();
            for (int i=0;i<source.getChildCount();i++) {
                if (source.getChildAt(i).toString().equals(str)) source.remove(i);
            }
            target.add(new DefaultMutableTreeNode(str,false));
        }
    }

    public DefaultMutableTreeNode getRootAddedPersonal(){
        return this.rootAddedPersonal;
    }
    public DefaultMutableTreeNode getRootFreePersonal(){
        return this.rootFreePersonal;
    }
}

package by.alt.Object;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Personal;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class PersonalTreeModel {
    private static DaoClass daoObject = new DaoClass();
    private DefaultMutableTreeNode rootAddedPersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    private DefaultMutableTreeNode rootFreePersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    public DefaultTreeModel treeModelAddedPersonal = new DefaultTreeModel(rootAddedPersonal, false);
    public DefaultTreeModel treeModelFreePersonal = new DefaultTreeModel(rootFreePersonal, false);
    //public PersonalTreeModel(){
     //   fillList(rootFreePersonal);
    //}
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
        //added.getFirstChild();
        for (int i=0;i<added.getChildCount();i++) free.remove(free.getIndex(added.getChildAt(i)));
    }
    public DefaultMutableTreeNode getRootAddedPersonal(){
        return this.rootAddedPersonal;
    }
    public DefaultMutableTreeNode getRootFreePersonal(){
        return this.rootFreePersonal;
    }
}

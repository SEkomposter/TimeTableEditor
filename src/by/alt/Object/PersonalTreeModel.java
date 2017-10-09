package by.alt.Object;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Personal;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class PersonalTreeModel {
    private static DaoClass daoObject = new DaoClass();
    DefaultMutableTreeNode rootAddedPersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    DefaultMutableTreeNode rootFreePersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    public DefaultTreeModel treeModelAddedPersonal = new DefaultTreeModel(rootAddedPersonal, true);
    public DefaultTreeModel treeModelFreePersonal = new DefaultTreeModel(rootFreePersonal, true);
    public PersonalTreeModel(){
        rootFreePersonal.
    }
    static void fillList(DefaultMutableTreeNode treeNode){
        Object[] tempArray =  daoObject.getAllPersonal().toArray();
        for (int i=0; i< tempArray.length; i++){
            treeNode.add(tempArray[i]);
        }
    }
}

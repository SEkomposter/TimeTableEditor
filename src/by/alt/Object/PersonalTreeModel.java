package by.alt.Object;


import by.alt.DAO.DaoClass;
import by.alt.DAO.Node;
import by.alt.DAO.Personal;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Enumeration;


public class PersonalTreeModel {
    private static DaoClass daoObject = new DaoClass();
    private static DefaultMutableTreeNode rootAddedPersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    private static DefaultMutableTreeNode rootFreePersonal = new DefaultMutableTreeNode(daoObject.getRootNode());
    public DefaultTreeModel treeModelAddedPersonal = new DefaultTreeModel(rootAddedPersonal, false);
    public DefaultTreeModel treeModelFreePersonal = new DefaultTreeModel(rootFreePersonal, false);
    public DefaultTreeModel treeModelAddedDeps = new DefaultTreeModel(rootAddedPersonal, true);
    public DefaultTreeModel treeModelFreeDeps = new DefaultTreeModel(rootFreePersonal, true);

    public void fillTreeFreePersonal(DefaultMutableTreeNode treeNode, Object[] tempArray){
       //Object[] tempArray = daoObject.getAllPersonal().toArray();
        delAllPersonal(treeNode);
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
 public void fillTreeAddedDeps(){
        Object[] temp = daoObject.getRootNode().getChildObjList().toArray();
        for (Object o: temp)
            getRootFreePersonal().add(new DefaultMutableTreeNode(o,((Node)o).isHasChildNode()));
 }
    public void filterPersonal (String mask){
        DefaultMutableTreeNode source = getRootFreePersonal();
        ArrayList list = new ArrayList();
            for (int i=0;i<source.getChildCount();i++) {
                Object obj = source.getChildAt(i);
                if (obj.toString().toLowerCase().startsWith(mask.toLowerCase()))
                    list.add(obj);
            }
            source.removeAllChildren();
            fillTreeFreePersonal(source, list.toArray());
            removeAddedFromFree(getRootAddedPersonal(),source);
       // if (!(source.getChildAt(i).toString().toLowerCase().startsWith(mask.toLowerCase()))) source.remove(i);
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
    public DefaultMutableTreeNode getRootFreePersonal(){return this.rootFreePersonal;
    }

    public  DefaultTreeModel getTreeModelAddedPersonal() {
        return treeModelAddedPersonal;
    }

    public  DefaultTreeModel getTreeModelFreePersonal() {
        return treeModelFreePersonal;
    }
    public  DefaultTreeModel getTreeModelAddedDeps() {
        return treeModelAddedDeps;
    }

    public  DefaultTreeModel getTreeModelFreeDeps() {
        return treeModelFreeDeps;
    }
}

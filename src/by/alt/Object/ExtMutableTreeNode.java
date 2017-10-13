package by.alt.Object;


import by.alt.DAO.RootNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExtMutableTreeNode extends DefaultMutableTreeNode{
    ExtMutableTreeNode(){
        super();
    }
    ExtMutableTreeNode(RootNode rootNode){
        super(rootNode);
    }

    @Override
    public boolean equals(Object obj) {
        Object tempObj = (Object)this;
        return ((TableEntry)tempObj).getName().equals(((TableEntry)obj).getName());
    }
}

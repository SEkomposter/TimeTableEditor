package by.alt;
import by.alt.DAO.DBReader;
import by.alt.DAO.DaoClass;
import by.alt.DAO.RootNode;
import by.alt.Object.PropReader;
import by.alt.gui.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        PropReader propReader = new PropReader();
        DaoClass dao = new DaoClass();
        RootNode rootNode = new RootNode();

        try{
            DBReader.ConnectToDB();
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        rootNode.setChildObjList(dao.getChildList(0));
        System.out.println(rootNode.getChildObjList());
        System.out.println(rootNode.isHasChildNode());
        //DaoClass.getObjectId();
        //RootNode.getRootNode().removeChildObject(10);
       // System.out.println(RootNode.getRootNode().getChildObjList());

        //MainForm.main(args);

    }
}

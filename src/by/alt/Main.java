package by.alt;
import by.alt.DAO.DBReader;
import by.alt.DAO.DaoClass;
import by.alt.DAO.RootNode;
import by.alt.Object.PropReader;
import by.alt.gui.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
     //   PropReader propReader = new PropReader();
     //   DaoClass dao = new DaoClass();
        try{
            DBReader.ConnectToDB();
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        //dao.buildObjTree(dao.getRootNode());
        //System.out.println(dao.getAllPersonal());
        //RootNode.getRootNode().removeChildObject(10);
       // System.out.println(RootNode.getRootNode().getChildObjList());

        MainForm.main(args);

    }
}

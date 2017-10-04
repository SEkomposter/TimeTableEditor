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
        System.out.println(dao.getChildNodeList(0));
        //DaoClass.getObjectId();

        //MainForm.main(args);

    }
}

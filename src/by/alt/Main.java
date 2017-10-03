package by.alt;
import by.alt.DAO.DBReader;
import by.alt.DAO.DaoClass;
import by.alt.Object.PropReader;
import by.alt.gui.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        PropReader propReader = new PropReader();
        DaoClass dao = new DaoClass();
        dao.getChildNodeList(dao.getRootNode().getId());


        //MainForm.main(args);

    }
}

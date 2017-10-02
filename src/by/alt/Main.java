package by.alt;
import by.alt.DAO.DBReader;
import by.alt.Object.PropReader;
import by.alt.gui.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        PropReader propReader = new PropReader();
       try {
            DBReader dbr = new DBReader();
            dbr.QueryToDB("1");

        }
        catch (SQLException exc){
            exc.printStackTrace();
        }
        //MainForm.main(args);

    }
}

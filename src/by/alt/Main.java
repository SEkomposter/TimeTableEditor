package by.alt;
import by.alt.DAO.DBReader;
import by.alt.DAO.DaoClass;
import by.alt.DAO.RootNode;
import by.alt.Object.Logger;
import by.alt.Object.PropReader;
import by.alt.gui.*;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try{

            (new DBReader()).ConnectToDB();
        }catch (SQLException exc){
            new Logger().pushToScreenNlog(exc,exc.getClass());
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                try{
                    new MainForm();}
                catch (NullPointerException exc){
                    new Logger().pushToScreenNlog(exc,exc.getClass());
                }
            }
        });

    }
}

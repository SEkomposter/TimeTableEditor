package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

public class DaoClass {
    private static RootNode rootNode;
    private Node node;
    private ArrayList<Node> childNodeList;
    private static DBReader dbReader = new DBReader();
    public RootNode getRootNode(){
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT name, type FROM tc-db-main.personal WHERE id = 0;");
            rootNode = new RootNode(resultSet.getString("NAME"));
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return rootNode;
    }
    public ArrayList<Node> getChildNodeList(int parent_id){
        //try {
           // ResultSet resultSet = dbReader.QueryToDB("SELECT `id`,`name`,`type`, `parent_id` WHERE parent_id = `" + parent_id + "` AND type = `DEP` FROM `tc-db-main.personal`;");
            //rootNode = new RootNode(resultSet.getString("NAME"));
       // }catch (SQLException exc){
         //   exc.printStackTrace();
        //}
        return childNodeList;
    }
}

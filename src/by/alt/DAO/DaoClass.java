package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;


public class DaoClass {
    static RootNode rootNode;
    private Node node;
    private ArrayList<Node> childNodeList;
    private static DBReader dbReader = new DBReader();
    public RootNode getRootNode(){
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `name`, `type` FROM `personal` WHERE `id` = 0;");
            rootNode = new RootNode(resultSet.getString("NAME"));
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return rootNode;
    }
    public static void getObjectId (){
        try {
            ResultSet resultSet = dbReader.QueryToDB("SELECT `name`, `id` FROM `personal`");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("ID") + "  " + resultSet.getString("NAME"));
                //rootNode = new RootNode(resultSet.getString("NAME"));
            }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
    }
    public ArrayList<Node> getChildNodeList(int parent_id){
        childNodeList = new ArrayList<Node>();
        try {
           ResultSet resultSet = dbReader.QueryToDB("SELECT `id`,`name`,`type`, `status`, `parent_id`  FROM `personal` WHERE parent_id = "+String.valueOf(parent_id)+" AND type=" + ObjectType.DEP.ordinal()+1 + " AND status = 1");
           while (resultSet.next()){
               childNodeList.add(new Node(resultSet.getInt("ID"),resultSet.getString("NAME"),resultSet.getInt("PARENT_ID")));

           }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        System.out.println(childNodeList);
        return childNodeList;
    }

}

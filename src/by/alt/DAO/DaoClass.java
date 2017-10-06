package by.alt.DAO;

import com.mysql.jdbc.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;


public class DaoClass {
    static RootNode rootNode;
    private Node node;
    private ArrayList<? extends SurvObject> childList;
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


    public ArrayList<? extends SurvObject> getChildList(int parent_id){
        childList = new ArrayList<>();
        try {
           ResultSet resultSet = dbReader.QueryToDB("SELECT `id`,`name`,`type`, `status`, `parent_id`  FROM `personal` WHERE parent_id = "+String.valueOf(parent_id)+" AND status = 1");
           while (resultSet.next()){
               if (resultSet.getInt("TYPE")==1){
                    childList.add(new Node(resultSet.getInt("ID"),resultSet.getString("NAME"),resultSet.getInt("PARENT_ID")));}
               else{
                   childList.add(new Personal(resultSet.getInt("ID"),resultSet.getString("NAME"),resultSet.getInt("PARENT_ID")));
               }

           }
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return childList;
    }

}

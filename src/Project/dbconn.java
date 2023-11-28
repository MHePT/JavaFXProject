package Project;

import java.sql.Connection;
import java.sql.DriverManager;



public class dbconn {
     public static Connection DBConnection() {
        
         Connection conn = null;

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");  
            
             //cmd :SQLPLUS / AS SYSDBA
             // ALTER USER HR ACCOUNT UNLOCK;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students?zeroDateTimeBehavior=CONVERT_TO_NULL","root","");

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
     
        return conn;
    
}
}

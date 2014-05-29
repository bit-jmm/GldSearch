package com.gld.lucene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class DBSource {
    private String driver;
    private String url;
    private String username;
    private String password;
    private static DBSource instance =null;
 
    public DBSource() throws IOException, ClassNotFoundException {
        driver = "org.postgresql.Driver";
        url = "jdbc:postgresql://127.0.0.1:5432/gldjc";
        username = "postgres";
        password = "jiangming";
        Class.forName(driver);
    }
 
    public static DBSource getInstance() throws IOException, ClassNotFoundException{
        if (instance==null) {
            synchronized (DBSource.class) {
                if(instance==null){
                    instance = new DBSource();  
                }
            }
        }
        return instance;
    }
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
    public void closeAll(ResultSet rs, Statement ps,Connection conn) throws SQLException{
        closeResultSet(rs);
        closeStatement(ps);
        closeConnection(conn);
    }
    public static void closeConnection(Connection con) {
        try {
          if (con != null) {
            con.close();
          }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
      }
 
    public static void closeStatement(Statement st) {
        try {
          if (st != null) {
            st.close();
          }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
      }
 
    public static void closeResultSet(ResultSet rs) {
        try {
          if (rs != null) {
            rs.close();
          }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
      }
}
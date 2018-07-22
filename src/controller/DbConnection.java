/*
 * Esta aplicación fue desarrollada por Brayan Novoa.
 * Si desea ponerse en contacto por alguna duda no
 * dude en escribir al siguiente correo.
 * bnovoa.linux@gmail.com
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author b41n
 */
public class DbConnection {
        
        private String PORT;
        private String DATABASE;
        private String CLASSNAME;
        private String JDBC_MYSQL_DRIVER;
        private String URL;
    
    private Connection cn = null;
    public DbConnection(String HOST, String USERNAME,String PASSWORD){
        try{
            PORT = "3306";
            DATABASE = "db_caracterizacion";
            CLASSNAME = "com.mysql.jdbc.Driver";
            JDBC_MYSQL_DRIVER="jdbc:mysql://";
            URL = JDBC_MYSQL_DRIVER+HOST+":"+PORT;
            Class.forName(CLASSNAME);
            cn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Conectado a la base de datos");
        }catch(ClassNotFoundException | SQLException e){
            System.err.println("ERROR : "+e);
        }
    }
    public Connection getConexion(){
        return cn;
    } 
    public Connection closeConexion(){
        if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) { 
                    System.out.println("EROOR: "+e);
                }
            }
        System.out.println("Conexión cerrada");
        return null;
    }
    
    public DbConnection(String HOST, String USERNAME,String PASSWORD, String DATABASE){
        try{
            PORT = "3306";
            CLASSNAME = "com.mysql.jdbc.Driver";
            JDBC_MYSQL_DRIVER="jdbc:mysql://";
            URL = JDBC_MYSQL_DRIVER+HOST+":"+PORT+"/"+DATABASE;
            Class.forName(CLASSNAME);
            cn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("Conectado a la base de datos");
        }catch(ClassNotFoundException | SQLException e){
            System.err.println("ERROR : "+e);
        }
    }
    
    public static void main(String[] args){
        DbConnection cn= new DbConnection("localhost","Programador","programador");
        cn.getConexion();
        cn.closeConexion();
    }
}

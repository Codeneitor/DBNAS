/*
 * Esta aplicación fue desarrollada por Brayan Novoa.
 * Si desea ponerse en contacto por alguna duda no
 * dude en escribir al siguiente correo.
 * bnovoa.linux@gmail.com
 */
package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author b41n
 */
public class DBNASController {
    DefaultTableModel model;
    public void showDataBases(String H, String U, String P){
        //System.out.println("Servidor: "+H+"\nUsuario: "+U+"\nPassword: "+P+"");
        DbConnection cn = new DbConnection(H,U,P);
        String [] campos = {"DataBases"};
        String [] registro = new String [1];
        String sql ="SHOW DATABASES";
        model = new DefaultTableModel(null,campos);
        Connection connection = cn.getConexion();
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]= rs.getString("Database");
                //System.out.println(registro[0]);
                model.addRow(registro);
            }
            dbnas.DBMS.tbDataBases.setModel(model);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(DBNASController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showDatabase(String database){
        //System.out.println("Base De Datos: "+database);
        DbConnection cn = new DbConnection("localhost","Programador","programador",database);
        String [] campos = {"Tablas"};
        String [] registro = new String [1];
        String sql ="SHOW TABLES";
        model = new DefaultTableModel(null,campos);
        Connection connection = cn.getConexion();
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]= rs.getString("Tables_in_"+database);
                //System.out.println(registro[0]);
                model.addRow(registro);
            }
            dbnas.DBMS.tbTables.setModel(model);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(DBNASController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectTable(String table, String database){
        
        //System.out.println("Base De Datos: "+database);
        DbConnection cn = new DbConnection("localhost","Programador","programador",database);
        String [] campos = {"facultad_id"};
        String[] registro = new String[campos.length];
        String sql ="SELECT facultad_id FROM "+table;
        model = new DefaultTableModel(null,campos);
        Connection connection = cn.getConexion();
        try {
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                registro[0]= rs.getString("facultad_id");
                model.addRow(registro);
            }
            dbnas.DBMS.tbContent.setModel(model);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(DBNASController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void descTable(String table, String database){
        DbConnection cn = new DbConnection("localhost","Programador","programador",database);
        String [] campos = {"Field","Type","Null","Key","Default","Extra"};
        String[] registro = new String[campos.length];
        String sql ="DESC "+table+";";
        Connection connection = cn.getConexion();
        model = new DefaultTableModel(null,campos);
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int r =0;
            while(rs.next()){
                r=r+1;
            }
            rs.beforeFirst();
            while(rs.next()){
                for(int i =1; i<=registro.length; i++){
                    registro[i-1]=rs.getString(campos[i-1]);
                    //System.out.println(registro[i-1]);
                }
                model.addRow(registro);
            }
            dbnas.DBMS.tbDescription.setModel(model);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(DBNASController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void showContent(String database, String table){
        DbConnection cn = new DbConnection("localhost","Programador","programador",database);
        String sql ="SELECT * FROM "+table+";";
        Connection connection = cn.getConexion();
        int x = 0;
        int c =0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            String campos[] = new String[numberOfColumns];
            String[] registro = new String[campos.length];
            String columnName = null;
            model = new DefaultTableModel(null,campos);
            
            for (int i = 1; i < numberOfColumns + 1; i++) {
                columnName = rsmd.getColumnName(i);
                campos[i-1]=columnName;
                System.out.println(campos[i-1]);
            }
            while(rs.next()){
                /*registro[0]=rs.getString(columnName);
                registro[1]=rs.getString(columnName);
                registro[2]=rs.getString(columnName);*/
                for(int f=1;f<=numberOfColumns;f++){
                    //registro[f-1]=rs.getString(columnName);
                    registro[f-1]=rs.getString(f);
                    //registro[f-1]=campos[f-1];
                }
                model.addRow(registro);
                //campos[0]=rs.getString(i);
                //campos[1]=rs.getString(2);
                //campos[2]=rs.getString(3);
                //System.out.println("Columna: "+campos[i-1]);
                //System.out.println("Columna: "+registro[i-1]);
                //i= i+1;
            }
            //int i =1;
            model.setColumnIdentifiers(campos);
            dbnas.DBMS.tbContent.setModel(model);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(DBNASController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        DBNASController dbc= new DBNASController();
        //dbc.showDataBases("localhost", "Programador", "programador");
        //dbc.descTable("db_caracterizacion","tb_facultades");
        //dbc.selectTable("tb_facultades","db_caracterizacion");
        dbc.showContent("db_caracterizacion","tb_facultades");
        
    }
    
}

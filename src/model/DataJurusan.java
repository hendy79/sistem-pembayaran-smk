package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataJurusan {
    private String query;
    private ResultSet rs;
    private Statement stmt;
    
    public String[] getNama(){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[] = null;
       try{
           stmt = connect.createStatement();
           int jmlBaris = 0;
           query = "SELECT COUNT(id) FROM jurusan;";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               jmlBaris = rs.getInt(1);
           }  
           query = "SELECT nama_jurusan FROM jurusan";
           rs = stmt.executeQuery(query);
           data = new String[jmlBaris];
           int i = 0;
           while(rs.next()){
               data[i] = rs.getString(1);
               i++;
           }
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       return data;
    }
    
    public String[] getSingleData(String id){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[] = null;
       try{
           stmt = connect.createStatement();
           query = "SELECT * FROM jurusan WHERE id = "+id+";";
           rs = stmt.executeQuery(query);
           ResultSetMetaData meta = rs.getMetaData();
           int jmlKolom = meta.getColumnCount();
           data = new String[jmlKolom];
           while(rs.next()){
               for(int c=0; c<jmlKolom; c++){
                   data[c] =rs.getString(c+1);
               }
           }
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       return data;
    }
}

package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPetugas {
    private String query;
    private ResultSet rs;
    private Statement stmt;
    
    private static String nip_static;
    
    public static void setIDUser(String nip){
        DataPetugas.nip_static = nip;
    }
    
    public static String getIDUser(){
        return DataPetugas.nip_static;
    }
   
    //cek login apakah valid
    public boolean isLoginValid(String nip, String password){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       int data = 0;
       try{
           stmt = connect.createStatement();
           query = "SELECT COUNT(nip) FROM petugas WHERE nip='"+nip+"'"
                   + " and password='"+password+"';";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               data = rs.getInt(1);
           }  
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       if(data == 0){
           return false;
       }else{
           return true;
       }
    }
    
    //cek apakah nip petugas valid
    public boolean isPetugasValid(String nip){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       int data = 0;
       try{
           stmt = connect.createStatement();
           query = "SELECT COUNT(nip) FROM petugas WHERE nip='"+nip+"';";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               data = rs.getInt(1);
           }  
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       if(data == 0){
           return false;
       }else{
           return true;
       }
    }
    
    //memperbarui password
    public boolean updatePassword(String nip, String password){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "UPDATE petugas SET password='"+password
                    +"' WHERE nip='"+nip+"';";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    //Mendapatkan semua data
    public String [][] getAllData(String nip){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[][] = null;
       try{
           stmt = connect.createStatement();
           int jmlBaris = 0;
           query = "SELECT COUNT(nip) FROM petugas;";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               jmlBaris = rs.getInt(1);
           }  
           query = "SELECT nama, nohp, alamat FROM petugas WHERE nip='"+nip+"';";
           rs = stmt.executeQuery(query);
           ResultSetMetaData meta = rs.getMetaData();
           int jmlKolom = meta.getColumnCount();
           data = new String[jmlBaris][jmlKolom];
           int r = 0;
           while(rs.next()){
               for(int c=0; c<jmlKolom; c++){
                   data[r][c] =rs.getString(c+1);
               }
               r++;
           }
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       return data;
    }
    
    //memperbarui data
    public boolean updateData(String nip, String[] data){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "UPDATE petugas SET nama='"+data[0]+"', nohp='"+data[1]
                    +"', alamat='"+data[2]+"' WHERE nip='"+nip+"';";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
}

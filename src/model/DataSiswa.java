package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSiswa {
    private String query;
    private ResultSet rs;
    private Statement stmt;
    
    public String[][] getDataforTbl(){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[][] = null;
       try{
           stmt = connect.createStatement();
           int jmlBaris = 0;
           query = "SELECT COUNT(nis) FROM siswa;";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               jmlBaris = rs.getInt(1);
           }  
           query = "SELECT nis, nama, " +
                "(SELECT nama_jurusan FROM jurusan WHERE id = sw.`id_jurusan`)"
                   + " AS nama_jurusan, " +
                "(SELECT nama_kelas FROM kelas WHERE id = sw.`id_kelas`)"
                   + " AS nama_kelas, " +
                "id_jurusan, id_kelas, alamat, jenis_kelamin " +
                "FROM siswa AS sw;";
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
    
    public String[][] getCari(String kunci){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[][] = null;
       try{
           stmt = connect.createStatement();
           int jmlBaris = 0;
           query = "SELECT COUNT(nis) "
                   + "FROM siswa WHERE nis LIKE '%"+kunci+"%'"
                   + " OR nama LIKE '%"+kunci+"%';";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               jmlBaris = rs.getInt(1);
           }  
           query = "SELECT nis, nama, " +
                "(SELECT nama_jurusan FROM jurusan WHERE id = sw.`id_jurusan`)"
                   + " AS nama_jurusan, " +
                "(SELECT nama_kelas FROM kelas WHERE id = sw.`id_kelas`)"
                   + " AS nama_kelas, " +
                "id_jurusan, id_kelas, alamat, jenis_kelamin "
                   + "FROM siswa AS sw WHERE nis LIKE '%"+kunci+"%'"
                   + " OR nama LIKE '%"+kunci+"%';";
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
    
    public String[] getSingleData(String nis){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[] = null;
       try{
           stmt = connect.createStatement();
           query = "SELECT * FROM siswa WHERE nis = '"+nis+"';";
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
    
    //cek apakah nis siswa valid
    public boolean isSiswaValid(String nis){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       int data = 0;
       try{
           stmt = connect.createStatement();
           query = "SELECT COUNT(nis) FROM siswa WHERE nis='"+nis+"';";
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
    
    public boolean insertAll(String[] data){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "INSERT INTO siswa VALUES ("
                    + "'"+data[0]+"', '"+data[1]+"', "
                    + "'"+data[2]+"', '"+data[3]+"', "
                    +data[4]+", "+data[5]+ ");";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean updateAllByNis(String[] data){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "UPDATE siswa SET nama='"+data[1]+"', alamat='"+data[2]+"', "
                    + "jenis_kelamin='"+data[3]+"', id_kelas="+data[4]+", "
                    + "id_jurusan="+data[5]+" WHERE nis='"+data[0]+"';";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean deleteByNis(String nis){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "DELETE FROM siswa WHERE nis='"+nis+"'";
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

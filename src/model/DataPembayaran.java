package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPembayaran {
    private String query;
    private ResultSet rs;
    private Statement stmt;
    
    public String[][] getDataforTbl(String bayar){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[][] = null;
       try{
           stmt = connect.createStatement();
           int jmlBaris = 0;
           query = "SELECT COUNT(id) FROM transaksi "
                   + "WHERE jenis_pembayaran LIKE '"+bayar+"%'";
           rs = stmt.executeQuery(query);
           while(rs.next()){
               jmlBaris = rs.getInt(1);
           }  
           query = "SELECT id, nis, nip, tanggal, biaya, jenis_pembayaran, "
                   + "(SELECT nama_jurusan FROM jurusan WHERE id="
                   + "(SELECT id_jurusan FROM siswa WHERE nis=trs.`nis`)), "
                   + "(SELECT nama_kelas FROM kelas WHERE id="
                   + "(SELECT id_kelas FROM siswa WHERE nis=trs.`nis`)) "
                   + "FROM transaksi AS trs WHERE jenis_pembayaran LIKE '"+bayar+"%';";
           rs = stmt.executeQuery(query);
           ResultSetMetaData meta = rs.getMetaData();
           int jmlKolom = meta.getColumnCount();
           data = new String[jmlBaris][jmlKolom];
           int r = 0;
           while(rs.next()){
               for(int c=0; c<jmlKolom; c++){
                   data[r][c] = rs.getString(c+1);
                   if(c == 5 && bayar.equals("SPP")){
                       data[r][c] = data[r][c].split(" ")[1];
                   }
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
    
    public String[] getSingleData(String id){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[] = null;
       try{
           stmt = connect.createStatement();
           query = "SELECT nis, (SELECT nama FROM siswa WHERE nis=trs.`nis`) "
                   + "AS nama_siswa, "
                   + "(SELECT nama_jurusan FROM jurusan WHERE id="
                   + "(SELECT id_jurusan FROM siswa WHERE nis=trs.`nis`)), "
                   + "(SELECT nama_kelas FROM kelas WHERE id="
                   + "(SELECT id_kelas FROM siswa WHERE nis=trs.`nis`)) , nip  "
                   + "FROM transaksi AS trs WHERE id = '"+id+"';";
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
    
    public String[] getSingleSPPData(String id){
       KoneksiDB kon = new KoneksiDB();
       Connection connect = kon.koneksiDatabase();
       String data[] = null;
       try{
           stmt = connect.createStatement();
           query = "SELECT nis, (SELECT nama FROM siswa WHERE nis=trs.`nis`) "
                   + "AS nama_siswa, jenis_pembayaran, nip  "
                   + "FROM transaksi AS trs WHERE id = '"+id+"';";
           rs = stmt.executeQuery(query);
           ResultSetMetaData meta = rs.getMetaData();
           int jmlKolom = meta.getColumnCount();
           data = new String[jmlKolom];
           while(rs.next()){
               for(int c=0; c<jmlKolom; c++){
                   data[c] =rs.getString(c+1);
                   if(c == 2){
                       String[] tmp = data[c].split(" ");
                       data[c] = tmp[1];
                   }
               }
           }
           stmt.close();
           connect.close();
       }catch(SQLException ex){
           System.out.println("Error : " + ex.getMessage());
       }
       return data;
    }
    
    public boolean insertAll(String[] data){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "INSERT INTO transaksi(nis,nip,tanggal,biaya,jenis_pembayaran)"
                    + " VALUES ('"+data[0]+"', '"+data[1]+"', "
                    + "(SELECT NOW()), "+data[2]+", '"+data[3]+"');";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean updateData(String id, String[] data){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "UPDATE transaksi SET nis='"+data[0]+"', nip='"+data[1]+"', "
                    + "tanggal=(SELECT NOW()), biaya="+data[2]+", "
                    + "jenis_pembayaran='"+data[3]+"' WHERE id="+id+";";
            stmt.executeUpdate(query);
            stmt.close();
            connect.close();
        }catch(SQLException ex){
            System.out.println("Error : " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean deleteById(String id){
        KoneksiDB kon = new KoneksiDB();
        Connection connect = kon.koneksiDatabase();
        try{
            stmt = connect.createStatement();
            query = "DELETE FROM transaksi WHERE id="+id+"";
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

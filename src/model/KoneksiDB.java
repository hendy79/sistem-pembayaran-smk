package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private Connection koneksi = null;
    public Connection koneksiDatabase() {
        try{
            //setting driver mysql
            Class.forName("com.mysql.jdbc.Driver");
            //buat connection 
            koneksi = DriverManager.getConnection("jdbc:mysql:///pembayaran_spp","root","");    
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Error : " + e.getMessage());
        }
        return koneksi;
    }
}

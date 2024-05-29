/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.helper;

import java.sql.*;
import java.util.*;
import project_akhir_pbo.models.AnggotaModel;
import project_akhir_pbo.models.TempData;

/**
 *
 * @author Hp
 */
public class DBHelper {
    
    private final String dbUrl = "jdbc:mysql://localhost/db_lomba";
    private final String user = "root";
    private final String pass = "";
    
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String query;
    
    public DBHelper(){
        try {
            conn = DriverManager.getConnection(dbUrl, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkPesertaLogin(String name, String pass){
        boolean value = false;
        
        query = "SELECT * FROM kelompok WHERE nama ='" + name + "' AND pass = '" + pass + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                value = true;
                TempData.kelompokID = rs.getString("kelompok_id");
                TempData.kelompokStatus = rs.getString("status");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    public boolean addNewPeserta(String name, String pass){
        boolean value = false;
        
        if(checkPesertaLogin(name, pass)){
            return false;
        }
        
        query = "INSERT INTO kelompok SET nama ='" + name + "', pass = '" + pass + "', tgl_dibuat = sysdate(), status = '0'";
        try {
            stmt = conn.createStatement();
            if(stmt.executeUpdate(query) > 0){
                value = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    public List<AnggotaModel> getAllAnggota(String kelompokId){
        List<AnggotaModel> data = new ArrayList<>();
        query = "SELECT * FROM member WHERE kelompok_id = " + kelompokId + "";
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                AnggotaModel anggota = new AnggotaModel();
                anggota.setId(rs.getString("member_id"));
                anggota.setNama(rs.getString("nama"));
                anggota.setUmur(rs.getString("umur"));
                anggota.setRole(rs.getString("role").equals("1") ? "Ketua" : "Anggota");
                data.add(anggota);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    private String generateIdAnggota(String kelompok){
        String value = "";
        
        query = "SELECT anggota_id from member WHERE kelompok_id = " + kelompok + " ORDER BY member_id DESC LIMIT 1";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                value = kelompok + "_" + (Integer.parseInt(rs.getString("anggota_id").split("_")[1]) + 1);
            }else{
                value = kelompok + "_1";
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println(value);
        return value;
    }
    
    public boolean addNewAnggota(String kelompok, String name, String umur, String role){
        //Cek apakah sudah ada ketua jika memasukkan role ketua
        if(isAnyKetua(kelompok) && role.equals("1")){
            return false;
        }
        boolean value = false;
        
        String id = generateIdAnggota(kelompok);
        query = "INSERT into member SET member_id = '" + id + "', kelompok_id = " + kelompok + ", nama = '" + name + "', umur = " + umur + ", role = '" + role + "'";
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(query) > 0) {
                value = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return value;
    }
    
    public boolean updateAnggota(String kelompok, String id, String nama, String umur, String role) {
        //Cek apakah sudah ada ketua jika memasukkan role ketua
        if(isAnyKetua(kelompok) && role.equals("1")){
            return false;
        }
        boolean value = false;
        
        query = "UPDATE member SET nama = \"" + nama + "\", umur = " + umur + ", role = '" + role + "' WHERE member_id =\"" + id + "\"";
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(query) > 0) {
                value = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public boolean deleteAnggota(String id) {
        boolean value = false;
        query = "DELETE FROM member WHERE member_id =\"" + id + "\"";
        try {
            stmt = conn.createStatement();
            if (stmt.executeUpdate(query) > 0) {
                value = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public boolean isAnyKetua(String id) {
        boolean value = false;
        query = "SELECT * FROM member WHERE kelompok_id = " + id + " AND role = '1'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                value = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    // admin
    public boolean checkAdminLogin(String user, String pass) {
        boolean value = false;
        query = "SELECT * FROM admin WHERE user ='" + user + "' AND pass ='" + pass + "'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                value = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
    
}

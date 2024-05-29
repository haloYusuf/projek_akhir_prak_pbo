/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.helper;

import java.sql.*;
import java.util.*;
import project_akhir_pbo.models.AnggotaModel;
import project_akhir_pbo.models.DetailModel;
import project_akhir_pbo.models.KelompokModel;
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
    
    public boolean addNewKelompok(String name, String pass){
        boolean value = false;
        
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
    
    public List<AnggotaModel> getAllMember(String kelompokId){
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
    
    private String generateIdMember(String kelompok){
        String value = "";
        
        query = "SELECT member_id from member WHERE kelompok_id = " + kelompok + " ORDER BY member_id DESC LIMIT 1";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                value = kelompok + "_" + (Integer.parseInt(rs.getString("member_id").split("_")[1]) + 1);
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
    
    public boolean addNewMember(String kelompok, String name, String umur, String role){
        boolean value = false;
        
        String id = generateIdMember(kelompok);
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
    
    public boolean updateMember(String kelompok, String id, String nama, String umur, String role) {
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

    public boolean removeMember(String id) {
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
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public boolean checkIdKetua(String id) {
        boolean value = false;
        query = "SELECT * FROM member WHERE member_id = '" + id + "' AND role = '1'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                value = true;
            }
            stmt.close();
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
    
    public List<KelompokModel> getAllKelompok() {
        List<KelompokModel> data = new ArrayList<>();
        query = "SELECT \n" +
                "    k.kelompok_id,\n" +
                "    k.nama AS kelompok_nama,\n" +
                "    k.tgl_dibuat,\n" +
                "    COUNT(m.member_id) AS jumlah_member,\n" +
                "    (SELECT m1.nama\n" +
                "     FROM member m1\n" +
                "     WHERE m1.kelompok_id = k.kelompok_id AND m1.role = '1') AS nama_ketua,\n" +
                "    k.status\n" +
                "FROM \n" +
                "    kelompok k\n" +
                "LEFT JOIN \n" +
                "    member m ON k.kelompok_id = m.kelompok_id\n" +
                "GROUP BY \n" +
                "    k.kelompok_id";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                KelompokModel kelompok = new KelompokModel();
                kelompok.setId(rs.getString("kelompok_id"));
                kelompok.setKelompokNama(rs.getString("kelompok_nama"));
                kelompok.setTglDibuat(rs.getString("tgl_dibuat"));
                kelompok.setJmlMember(rs.getString("jumlah_member"));
                kelompok.setNamaKetua(rs.getString("nama_ketua") == null ? "-" : rs.getString("nama_ketua"));
                kelompok.setStatus(rs.getInt("status"));
                data.add(kelompok);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public boolean accKelompok(String id){
        boolean value = false;
        query = "UPDATE kelompok SET status = '1' WHERE kelompok_id = " + id + "";
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
    
    public boolean removeKelompok(String id){
        boolean value = false;
        query = "DELETE FROM member WHERE kelompok_id =" + id + "";
        try {
            stmt = conn.createStatement();
            if(stmt.executeUpdate(query) <= 0){
                return false;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        query = "DELETE FROM kelompok WHERE kelompok_id =" + id + "";
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
    
    public boolean dissKelompok(String id){
        boolean value = false;
        query = "UPDATE kelompok SET status = '0' WHERE kelompok_id = " + id + "";
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
    
    //Detail
    public DetailModel getDetailKelompok(String id){
        DetailModel data = new DetailModel();
        List<String> anggota = new LinkedList<>();
        
        query = "SELECT * FROM kelompok WHERE kelompok_id = " + id + "";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                data.setNamaKelompok(rs.getString("nama"));
                data.setTglDibuat(rs.getString("tgl_dibuat"));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(isAnyKetua(id)){
            query = "SELECT * FROM member WHERE kelompok_id = " + id + " AND role = '1'";
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    data.setKetua(rs.getString("nama"));
                }
            } catch (SQLException e) {
            }
        }else{
            data.setKetua("-");
        }
        
        query = "SELECT * FROM member WHERE kelompok_id = " + id + " AND role = '0'";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                anggota.add(rs.getString("nama"));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.setAnggota(anggota);
        
        return data;
    }
    
}

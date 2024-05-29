/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import project_akhir_pbo.helper.DBHelper;
import project_akhir_pbo.models.AnggotaModel;
import project_akhir_pbo.models.DetailModel;
import project_akhir_pbo.models.TempData;
import project_akhir_pbo.views.KelompokMainView;

/**
 *
 * @author Hp
 */
public class KelompokMainController {
    
    KelompokMainView v;
    private final DefaultTableModel model;
    
    public KelompokMainController(){
        String[] header = {"Id", "Nama", "Umur", "Role"};
        model = new DefaultTableModel(header, 0);
        refreshTable();
        
        v = new KelompokMainView(this);
        v.getDataTable().setModel(model);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void tambahData(String nama, String umur, String role){
        DBHelper helper = new DBHelper();
        if(helper.isAnyKetua(TempData.kelompokID) && role.equals("1")){
            //Error sudah ada ketua pada kelompok, ketua hanya boleh 1 orang
            JOptionPane.showMessageDialog(v, "Sudah terdapat ketua pada kelompok", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            if (helper.addNewMember(TempData.kelompokID, nama, umur, role)) {
                JOptionPane.showMessageDialog(v, "Data berhasil dibuat", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }else{
                JOptionPane.showMessageDialog(v, "Gagal menambah data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void updateData(String id, String nama, String umur, String role) {
        DBHelper helper = new DBHelper();
        if(helper.isAnyKetua(TempData.kelompokID) && role.equals("1") && !helper.checkIdKetua(id)){
            //Error sudah ada ketua pada kelompok, ketua hanya boleh 1 orang
            JOptionPane.showMessageDialog(v, "Sudah terdapat ketua pada kelompok", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            if(helper.updateMember(TempData.kelompokID, id, nama, umur, role)){
                JOptionPane.showMessageDialog(v, "Data berhasil diupdate", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }else{
                JOptionPane.showMessageDialog(v, "Gagal mengupdate data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void deleteData(String id) {
        DBHelper helper = new DBHelper();
        if(helper.removeMember(id)){
            JOptionPane.showMessageDialog(v, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshTable();
        }else{
            JOptionPane.showMessageDialog(v, "Gagal menghapus data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void generateFile(){
        DBHelper helper = new DBHelper();
        DetailModel data = new DetailModel();
        Date date = new Date();  
        
        data = helper.getDetailKelompok(TempData.kelompokID);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        
        String filePath = "fileData\\" + TempData.kelompokID + "_" + data.getNamaKelompok() + ".txt";
        try {
            PrintWriter w = new PrintWriter(filePath);
            w.write("Data Peserta Perlombaan dengan Kelompok (" + TempData.kelompokID + ")\n");
            w.write("Nama Tim\t: " + data.getNamaKelompok() + "\n");
            w.write("Tanggal Dibuat\t: " + data.getTglDibuat()+ "\n\n");
            w.write("Nama Ketua\t: " + data.getKetua()+ "\n");
            w.write("Nama Kelompok\t: [" + String.join(",", data.getAnggota()) + "]\n\n\n");
            
            w.write("File dicetak pada tanggal : " + formatter.format(date) + "\n");
            w.close();
            JOptionPane.showMessageDialog(v, "Berhasil membuat file di folder fileData dengan nama file " + TempData.kelompokID + "_" + data.getNamaKelompok() + ".txt", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(v, "Gagal membuat file", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }
    
    public void logOut(){
        v.dispose();
        //Hapus temporary data
        TempData.kelompokID = "";
        TempData.kelompokStatus= "";
    }
    
    private void refreshTable(){
        model.setRowCount(0);
        DBHelper helper = new DBHelper();
        
        List<AnggotaModel> data = helper.getAllMember(TempData.kelompokID);
        data.forEach((m) -> {
            model.addRow(new Object[]{m.getId(), m.getNama(), m.getUmur(), m.getRole()});
        });
    }
    
}

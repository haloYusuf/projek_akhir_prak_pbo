/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import project_akhir_pbo.helper.DBHelper;
import project_akhir_pbo.models.AnggotaModel;
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
        String[] header = {"Id", "Nama", "Umur", "Role"};//Set header table
        model = new DefaultTableModel(header, 0);//Buat data model untuk table
        refreshTable();//Manggil fungsi refresh table untuk nampilin data ke table
        
        v = new KelompokMainView(this);
        v.getDataTable().setModel(model);//Masukkin model ke tabel UI
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void tambahData(String nama, String umur, String role){
        if(nama.isEmpty() || umur.isEmpty()){
            //Tambahin Error Handling
            JOptionPane.showMessageDialog(v, "Data harus ada isi", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Data harus ada isi");
        }else{
            DBHelper helper = new DBHelper();
            if(helper.addNewAnggota(TempData.kelompokID, nama, umur, role)){
                System.out.println("Berhasil add data!");
                refreshTable();
            }else{
                //Tambahin Error Handling
                JOptionPane.showMessageDialog(v, "Gagal add data!", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Gagal add data!");
            }
        }
    }
    
    public void updateData(int row, String nama, String umur, String role) {
        if (row != -1) {
            String id = model.getValueAt(row, 0).toString(); //Ambil data id sesuai data tabel yang di klick
            if (nama.isEmpty() || umur.isEmpty()){
                JOptionPane.showMessageDialog(v, "Data harus ada isi", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                DBHelper helper = new DBHelper();
                if (helper.updateAnggota(TempData.kelompokID, id, nama, umur, role)) {
                    JOptionPane.showMessageDialog(v, "berhasil diupdate", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(v, "gagal mengupdate data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(v, "tidak ada data yg dipilih", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteData(int row) {
        if (row != -1) {
            String id = model.getValueAt(row, 0).toString(); //Ambil data id sesuai data tabel yang di klick
            DBHelper helper = new DBHelper();
            if (helper.deleteAnggota(id)) {
                JOptionPane.showMessageDialog(v, "berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(v, "gagal menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(v, "Tidak ada data yg dipilih", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        List<AnggotaModel> data = helper.getAllAnggota(TempData.kelompokID);
        data.forEach((m) -> {
            model.addRow(new Object[]{m.getId(), m.getNama(), m.getUmur(), m.getRole()});
        });
    }
    
}

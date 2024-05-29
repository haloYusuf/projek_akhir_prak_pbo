/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import project_akhir_pbo.helper.DBHelper;
import project_akhir_pbo.models.KelompokModel;
import project_akhir_pbo.models.TempData;
import project_akhir_pbo.views.AdminMainView;

/**
 *
 * @author Hp
 */
public class AdminMainController {
    
    AdminMainView v;
    private final DefaultTableModel modelBelumAcc;
    private final DefaultTableModel modelSudahAcc;
    
    public AdminMainController(){
        String[] header = {"Id", "Kelompok", "Tanggal Dibuat", "Jumlah Member", "Nama Ketua"};
        modelBelumAcc = new DefaultTableModel(header, 0);
        modelSudahAcc = new DefaultTableModel(header, 0);
        refreshTable();
        
        v = new AdminMainView(this);
        v.getDataTableRequest().setModel(modelBelumAcc);
        v.getDataTableAcc().setModel(modelSudahAcc);
        
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void accKelompok(String id){
        DBHelper helper = new DBHelper();
        if(helper.isAnyKetua(id)){
            if(helper.accKelompok(id)){
                refreshTable();
            }else{
                //Tampilan error gagal meng acc kelompok
                JOptionPane.showMessageDialog(v, "Terdapat kesalahan dalam ACC kelompok", "Try Again", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            //Tampilan error kelompok belum memiliki ketua
            JOptionPane.showMessageDialog(v, "Kelompok wajib memiliki ketua, Pilih ketua terlebih dahulu", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void tolakKelompok(String id){
        DBHelper helper = new DBHelper();
        if(helper.removeKelompok(id)){
            refreshTable();
        }else{
            //Tampilan error gagal menghapus kelompok
            JOptionPane.showMessageDialog(v, "Kelompok gagal dihapus", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void dissKelompok(String id){
        DBHelper helper = new DBHelper();
        if(helper.dissKelompok(id)){
            refreshTable();
        }else{
            //Tampilan gagal mendiskualifikasi kelompok
            JOptionPane.showMessageDialog(v, "Terdapat kesalahan dalam diskualifikasi kelompok", "Try Again", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void getDetail(String id){
        TempData.kelompokID = id;
        new DataDetailController();
    }
    
    public void logOut(){
        v.dispose();
    }
    
    private void refreshTable(){
        modelBelumAcc.setRowCount(0);
        modelSudahAcc.setRowCount(0);
        DBHelper helper = new DBHelper();
        
        List<KelompokModel> data = helper.getAllKelompok();
        data.forEach((m) -> {
            if(m.getStatus() == 0){
                modelBelumAcc.addRow(new Object[]{m.getId(), m.getKelompokNama(), m.getTglDibuat(), m.getJmlMember(), m.getNamaKetua()});
            }else{
                modelSudahAcc.addRow(new Object[]{m.getId(), m.getKelompokNama(), m.getTglDibuat(), m.getJmlMember(), m.getNamaKetua()});
            }
        });
    }
    
}

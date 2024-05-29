/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import project_akhir_pbo.helper.DBHelper;
import project_akhir_pbo.models.DetailModel;
import project_akhir_pbo.models.TempData;
import project_akhir_pbo.views.DataDetailView;

/**
 *
 * @author Hp
 */
public class DataDetailController {
    
    DataDetailView v;
    public DataDetailController(){
        DBHelper helper = new DBHelper();
        DetailModel data = new DetailModel();
        data = helper.getDetailKelompok(TempData.kelompokID);
        
        v = new DataDetailView(this);
        v.getNamaTimLabel().setText(data.getNamaKelompok());
        v.getTglLabel().setText(data.getTglDibuat());
        v.getKetuaLabel().setText(data.getKetua());
        v.getAnggotaLabel().setText(String.join(", ", data.getAnggota()));
        
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void goBack(){
        TempData.kelompokID = "";
        v.dispose();
    }
    
}

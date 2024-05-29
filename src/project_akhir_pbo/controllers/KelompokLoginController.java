/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import javax.swing.JOptionPane;
import project_akhir_pbo.helper.DBHelper;
import project_akhir_pbo.views.KelompokLoginView;

/**
 *
 * @author Hp
 */
public class KelompokLoginController {
    
    KelompokLoginView v;
    public KelompokLoginController(){
        v = new KelompokLoginView(this);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void loginProcess(String name, String pass) {
        DBHelper helper = new DBHelper();
        if(helper.checkPesertaLogin(name, pass)){
            v.dispose();
            new KelompokMainController();
        }else{
            JOptionPane.showMessageDialog(v, "Login gagal, username atau password salah", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void registerProcess(String name, String pass) {
        DBHelper helper = new DBHelper();
        if(helper.addNewPeserta(name, pass)){
            JOptionPane.showMessageDialog(v, "Akun berhasil ditambahkan, Klik login untuk melanjutkan", "Success", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(v, "Whoopsie nama kelompok sudah terdaftar, Klik login untuk melanjutkan", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

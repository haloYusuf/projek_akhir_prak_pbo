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

        if (name.isEmpty() && pass.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Username dan password tidak boleh kosong", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Username tidak boleh kosong", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Password tidak boleh kosong", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

        else {
            if (helper.checkPesertaLogin(name, pass)) {
                v.dispose();
                new KelompokMainController();
            } else {
                JOptionPane.showMessageDialog(v, "Login gagal, username atau password salah", "Login Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void registerProcess(String name, String pass) {
        DBHelper helper = new DBHelper();
        if (name.isEmpty() && pass.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Username dan password tidak boleh kosong", "Registerasi Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Username tidak boleh kosong", "Registerasi Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(v, "Password tidak boleh kosong", "Registerasi Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            if (helper.addNewPeserta(name, pass)) {
                JOptionPane.showMessageDialog(v, "Akun berhasil ditambahkan, Klik login untuk melanjutkan", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(v, "Whoopsie akun sudah terdaftar, Klik login untuk melanjutkan", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
}

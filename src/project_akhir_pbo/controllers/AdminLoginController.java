/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import javax.swing.JOptionPane;
import project_akhir_pbo.helper.DBHelper;
import views.AdminLoginView;

/**
 *
 * @author Hp
 */
public class AdminLoginController {
    
    AdminLoginView v;
    public AdminLoginController(){
        v = new AdminLoginView(this);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void loginProccess(String user, String pass){
        if (user.isEmpty() && pass.isEmpty() ){
            JOptionPane.showMessageDialog(v, "Username dan password tidak boleh kosong", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else if(user.isEmpty()){
            JOptionPane.showMessageDialog(v, "Username tidak boleh kosong", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else if(pass.isEmpty()){
            JOptionPane.showMessageDialog(v, "Password tidak boleh kosong", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else{
            DBHelper helper = new DBHelper();
            if(helper.checkAdminLogin(user, pass)){
                new AdminMainController();
            } else {
                JOptionPane.showMessageDialog(v, "Login gagal, username atau password salah", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}

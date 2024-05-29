/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import project_akhir_pbo.views.AdminMainView;

/**
 *
 * @author Hp
 */
public class AdminMainController {
    
    AdminMainView v;
    public AdminMainController(){
        v = new AdminMainView(this);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void logOut(){
        v.dispose();
    }
    
}

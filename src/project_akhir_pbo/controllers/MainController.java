/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import project_akhir_pbo.views.MainView;

/**
 *
 * @author Hp
 */
public class MainController {
    
    MainView v;
    public MainController(){
        v = new MainView(this);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
    public void pesertaView(){
        new KelompokLoginController();
    }
    
    public void adminView(){
        new AdminLoginController();
    }
    
}

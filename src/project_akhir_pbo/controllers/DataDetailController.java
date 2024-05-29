/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.controllers;

import project_akhir_pbo.views.DataDetailView;

/**
 *
 * @author Hp
 */
public class DataDetailController {
    
    DataDetailView v;
    public DataDetailController(){
        v = new DataDetailView(this);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
    }
    
}

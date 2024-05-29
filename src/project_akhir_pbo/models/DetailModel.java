/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_akhir_pbo.models;

import java.util.List;

/**
 *
 * @author Hp
 */
public class DetailModel {
    private String namaKelompok;
    private String tglDibuat;
    private String ketua;
    private List<String> anggota;

    public String getNamaKelompok() {
        return namaKelompok;
    }

    public void setNamaKelompok(String namaKelompok) {
        this.namaKelompok = namaKelompok;
    }

    public String getTglDibuat() {
        return tglDibuat;
    }

    public void setTglDibuat(String tglDibuat) {
        this.tglDibuat = tglDibuat;
    }

    public String getKetua() {
        return ketua;
    }

    public void setKetua(String ketua) {
        this.ketua = ketua;
    }

    public List<String> getAnggota() {
        return anggota;
    }

    public void setAnggota(List<String> anggota) {
        this.anggota = anggota;
    }
    
    
    
}

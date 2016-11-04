package com.wikrama.masis.POJO;

/**
 * Created by I Can Do That on 11/4/2016.
 */

public class Mading {
    public Mading(){

    }
    public String judulMading;
    private String deskripsi;
    private String gambarMading;
    private String user;
    public String univ;
    private String email;
    private String role;

    public Mading(String judulMading,String univ){
        this.judulMading = judulMading;
        this.univ = univ;
    }

    public String getJudulMading() {
        return judulMading;
    }

    public void setJudulMading(String judulMading) {
        this.judulMading = judulMading;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambarMading() {
        return gambarMading;
    }

    public void setGambarMading(String gambarMading) {
        this.gambarMading = gambarMading;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
    }
}

package dz.tdm.AmrineMadji.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amine on 01/04/2017.
 */

public class User {

    private String nom;
    private String email;
    private String photoPath;
    private String passWord;
    private String desc;
    private Permis permis;
    private List<CarteGris> carteGris = new ArrayList<>();


    public User(String nom, String email, String photoPath, String passWord) {
        this.nom = nom;
        this.email = email;
        this.photoPath = photoPath;
        this.passWord = passWord;
    }

    public User(String nom, String email, String passWord) {
        this.nom = nom;
        this.email = email;
        this.passWord = passWord;
    }

    public User(String photoPath, String desc) {
        this.photoPath = photoPath;
        this.desc = desc;
    }

    public User() {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Permis getPermis() {
        return permis;
    }

    public void setPermis(Permis permis) {
        this.permis = permis;
    }

    public List<CarteGris> getCarteGris() {
        return carteGris;
    }

    public void setCarteGris(List<CarteGris> carteGris) {
        this.carteGris = carteGris;
    }
}

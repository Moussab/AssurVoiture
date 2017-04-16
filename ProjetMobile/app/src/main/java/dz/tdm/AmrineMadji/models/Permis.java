package dz.tdm.AmrineMadji.models;

import java.util.Date;

/**
 * Created by Amine on 14/04/2017.
 */

public class Permis {

    private String categorie;
    private String numPermis;

    public Permis(String categorie, String numPermis) {
        this.categorie = categorie;
        this.numPermis = numPermis;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNumPermis() {
        return numPermis;
    }

    public void setNumPermis(String numPermis) {
        this.numPermis = numPermis;
    }
}

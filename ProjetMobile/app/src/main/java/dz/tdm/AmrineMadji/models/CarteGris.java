package dz.tdm.AmrineMadji.models;

/**
 * Created by Amine on 14/04/2017.
 */

public class CarteGris {

    private String numCarte;
    private String numSacher;
    private String matriculeVoiture;

    public CarteGris(String numCarte, String numSacher, String matriculeVoiture) {
        this.numCarte = numCarte;
        this.numSacher = numSacher;
        this.matriculeVoiture = matriculeVoiture;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

    public String getNumSacher() {
        return numSacher;
    }

    public void setNumSacher(String numSacher) {
        this.numSacher = numSacher;
    }

    public String getMatriculeVoiture() {
        return matriculeVoiture;
    }

    public void setMatriculeVoiture(String matriculeVoiture) {
        this.matriculeVoiture = matriculeVoiture;
    }
}

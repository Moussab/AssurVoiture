package dz.tdm.AmrineMadji.models;

import java.util.List;

/**
 * Created by Amine on 01/04/2017.
 */

public class Sinistre {

    private String date;
    private SinistreType type;
    private List<User> userList;
    private Localisation localisation;
    private User sender;
    private String typeDeclaration;

    public Sinistre(){

    }

    public Sinistre(String date, SinistreType type, List<User> userList, Localisation localisation) {
        this.date = date;
        this.type = type;
        this.userList = userList;
        this.localisation = localisation;
    }

    public Sinistre(String date, SinistreType type, List<User> userList) {
        this.date = date;
        this.type = type;
        this.userList = userList;
    }

    public Sinistre(String date, SinistreType type, Localisation localisation) {
        this.date = date;
        this.type = type;
        this.localisation = localisation;
    }

    public Sinistre(String date, SinistreType type) {
        this.date = date;
        this.type = type;
    }

    public String getTypeDeclaration() {
        return typeDeclaration;
    }

    public void setTypeDeclaration(String typeDeclaration) {
        this.typeDeclaration = typeDeclaration;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Localisation getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SinistreType getType() {
        return type;
    }

    public void setType(SinistreType type) {
        this.type = type;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}

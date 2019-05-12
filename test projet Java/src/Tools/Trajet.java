package Tools;

import java.sql.Timestamp;
import Exception.*;

import java.sql.Timestamp;

public class Trajet {
    private int identifiant, nbKm, nbPassagers, matricule, codePostal, client_id, numberOfDigits;
    private Boolean aEuPanne, aEuEmbouteillage;
    private String nom;
    private Timestamp heureArrivee, heureDepart;

    public Trajet(int identifiant, int nbKm, int nbPassagers, int matricule, int codePostal, String nom, int client_id,
                  Boolean aEuPanne, boolean aEuEmbouteillage, Timestamp heureArrivee, Timestamp heureDepart) throws ValeurException, CodePostalException, IdException, TimeException {
        setIdentifiant(identifiant);
        setNbKm(nbKm);
        setNbPassagers(nbPassagers);
        setMatricule(matricule);
        setCodePostal(codePostal);
        setNom(nom);
        setClient_id(client_id);
        setaEuPanne(aEuPanne);
        setaEuEmbouteillage(aEuEmbouteillage);
        this.heureDepart = heureDepart;
        setHeureArrivee(heureArrivee);
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public int getNbKm() {
        return nbKm;
    }

    public int getNbPassagers() {
        return nbPassagers;
    }

    public int getMatricule() {
        return matricule;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public int getClient_id() {
        return client_id;
    }

    public Boolean getaEuPanne() {
        return aEuPanne;
    }

    public boolean getaEuEmbouteillage() {
        return aEuEmbouteillage;
    }

    public String getNom() {
        return nom;
    }

    public Timestamp getHeureArrivee() {
        return heureArrivee;
    }

    public Timestamp getHeureDepart() {
        return heureDepart;
    }


    public void setIdentifiant(Integer identifiant) throws ValeurException {
        if(identifiant <= 0){
            throw new ValeurException(identifiant);
        } else{
            this.identifiant = identifiant;
        }
    }

    public void setNbKm(Integer nbKm) throws ValeurException {
        if(nbKm <= 0){
            throw new ValeurException(nbKm);
        } else{
            this.nbKm = nbKm;
        }
    }

    public void setNbPassagers(Integer nbPassagers) throws ValeurException {
        if(nbPassagers <= 0){
            throw new ValeurException(nbPassagers);
        } else{
            this.nbPassagers = nbPassagers;
        }
    }

    public void setMatricule(Integer matricule) throws IdException {
        numberOfDigits = String.valueOf(matricule).length();
        if(numberOfDigits != 6){
            throw new IdException(matricule);
        } else{
            this.matricule = matricule;
        }
    }

    public void setCodePostal(Integer codePostal) throws CodePostalException {
        // On ne s'occupe que des codes postaux belges
        if(codePostal < 1000 || codePostal > 9992){
            throw new CodePostalException(codePostal);
        }
        this.codePostal = codePostal;
    }

    public void setClient_id(Integer client_id) throws ValeurException {
        if(client_id <= 0){
            throw new ValeurException(client_id);
        } else{
            this.client_id = client_id;
        }
    }

    public void setaEuPanne(Boolean aEuPanne) {
        this.aEuPanne = aEuPanne;
    }

    public void setaEuEmbouteillage(Boolean aEuEmbouteillage) {
        this.aEuEmbouteillage = aEuEmbouteillage;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setHeureArrivee(Timestamp heureArrivee) throws TimeException {
        if(heureArrivee.after(heureDepart) && heureArrivee.getTime()-heureDepart.getTime() <  86366673){//86 000 000 mili correspond a 24h
            this.heureArrivee = heureArrivee;
        } else {
            throw new TimeException();
        }
    }

    public void setHeureDepart(Timestamp heureDepart) throws TimeException{
        if(heureDepart.before(heureArrivee) && heureArrivee.getTime()-heureDepart.getTime() <  86366673){//86 000 000 mili correspond a 24h
            this.heureDepart = heureDepart;
        }else{
            throw new TimeException();
        }
    }

}

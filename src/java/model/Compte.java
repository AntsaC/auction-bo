/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import services.Connexion;
import java.util.*;

/**
 *
 * @author hhyy
 */
public class Compte {
    private int id_compte = 0;
    private Membre membre = null;
    private double solde = 0.0;
    private LocalDate dates = null;
    private int etat = 0;

    public Compte() {
    }
    
    public static List<Compte> getAllNonValidate() throws Exception {
        List<Compte> list = new ArrayList<Compte>();
        Statement st = null;
        ResultSet res = null;
        Connection con = null;
        Compte compte = null;
        Membre membre = null;
        try {
            String requete = "select * from v_NonValidation";
            con = Connexion.getConnection();
            st = con.createStatement();
            res = st.executeQuery(requete);
            while (res.next()) {
                membre = new Membre(); 
                membre.setId(Long.parseLong(res.getString("id_auth")));
                membre.setUsername(res.getString("username"));
                compte = new Compte(res.getInt("id_compte"), membre, res.getDouble("solde"),
                        LocalDate.parse(res.getString("dates")), res.getInt("etat"));
                list.add(compte);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public static void validationCompte(String id) throws Exception {
        Statement st = null;
        ResultSet res = null;
        Connection con = null;
        int i = 0;
                try {
                    String requete = "UPDATE compte set etat=1 where id_compte=" + id;
                    con = Connexion.getConnection();
                    st = con.createStatement();
                    st.executeUpdate(requete);
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    if (con != null) {
                        con.close();
                    }
                }
    }

    public void rechargerCompte() throws Exception {
        Statement st = null;
        ResultSet res = null;
        Connection con = null;
        Compte compte = null;
        Membre membre = null;
        try {
            String requete = "INSERT INTO Compte (id_membre,solde,dates,etat) VALUES (" +
                    this.getMembre().getId()+ "," + this.solde + ",'"
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    + "',0)";
            con = Connexion.getConnection();
            st = con.createStatement();
            st.executeUpdate(requete);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public int getId_compte() {
        return id_compte;
    }

    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public LocalDate getDates() {
        return dates;
    }

    public void setDates(LocalDate dates) {
        this.dates = dates;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
     public Compte(int id_compte, Membre membre, double solde, LocalDate dates, int etat) {
        this.id_compte = id_compte;
        this.membre = membre;
        this.solde = solde;
        this.dates = dates;
        this.etat = etat;
    }
    
}

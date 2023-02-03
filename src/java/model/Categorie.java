/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.Connexion;

/**
 *
 * @author lucky
 */
public class Categorie {
    private int id;
    private String nom;
    private double stat = 0.0;

    public double getStat() {
        return stat;
    }

    public void setStat(double stat) {
        this.stat = stat;
    }
    
    public static ArrayList<Categorie> topCategorie() throws Exception{
        Connection conn = null;
        Categorie [] donner = null;
        ArrayList <Categorie> al = new ArrayList<>();
        try {
            conn = Connexion.getConnection();
        String requete = "select * from v_categorie";
        Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet res = stat.executeQuery(requete);
        res.afterLast();
        
        donner = new Categorie[res.getRow()];  
        int i = 0;
        Categorie cate = null;
        res.beforeFirst();
        while(res.next()){            
            cate = new Categorie();
            cate.setId(res.getInt(res.findColumn("id")));
            cate.setNom(res.getString(res.findColumn("nom")));
            cate.setStat(res.getDouble("nombre"));
            al.add(cate);
        }
        } catch (Exception ex) {
            throw ex;
        }finally{
            if(conn != null){
                conn.close();
            }
        }
        
        return al;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void insert(Connection conn) throws SQLException{
        String requete = "insert into categorie (nom) values ('"+this.nom+"')";
        conn.createStatement().execute(requete);
    }
    
    public void update(Connection conn) throws Exception {        
        String requete = "update categorie set nom = '"+this.nom+"' where id = "+this.id;
        conn.createStatement().execute(requete);        
    }
    
    public void delete(Connection conn) throws Exception {
        String requete = "delete from categorie where id = "+this.id;
        conn.createStatement().execute(requete);
    }

    public static Categorie [] getAll(Connection conn) throws Exception {
        String requete = "select * from categorie";
        Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet res = stat.executeQuery(requete);
        res.afterLast();
        ArrayList <Categorie> al = new ArrayList<>();
        Categorie [] donner = new Categorie[res.getRow()];   
        res.beforeFirst();
        while(res.next()){            
            Categorie cate = new Categorie();
            cate.setId(res.getInt(res.findColumn("id")));
            cate.setNom(res.getString(res.findColumn("nom")));
            al.add(cate);
        }
        donner = new Categorie[al.size()];
        for(int i=0;i<donner.length;i++){
            donner[i] = (Categorie)al.get(i);
        }
        
        return donner;
    }


    
}
























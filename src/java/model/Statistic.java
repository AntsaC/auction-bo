/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author hhyy
 */
public class Statistic {
    private ArrayList<Categorie> categorie = null;

    public ArrayList<Categorie> getCategorie() {
        return categorie;
    }
    
    public void initStatistic() throws Exception{
        categorie = Categorie.topCategorie();
    }

    public void setCategorie(ArrayList<Categorie> categorie) {
        this.categorie = categorie;
    }
    
}

package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;


public class Duree_Enchere {
    private Duree min;
    private Duree max;

    public Duree getMin() {
            return min;
    }

    public void setMin(Duree min) {
            this.min = min;
    }

    public Duree getMax() {
            return max;
    }
    public void setMax(Duree max) {
            this.max = max;
    }	

    public boolean update(Connection conn) throws Exception {
            String requete = "update duree_enchere set min = "+this.min.tosecond()+" , max = "+this.max.tosecond() ;
            boolean s = conn.createStatement().execute(requete);
            return s;
    }

    public static Duree_Enchere getDuree (Connection conn) throws Exception {
            String requete = "select * from duree_enchere";
            Statement stat = conn.createStatement();

            ResultSet res = stat.executeQuery(requete);
            Duree_Enchere duree_Enchere = new Duree_Enchere();
            while (res.next()) {
                    Duree mi = new Duree(res.getLong(res.findColumn("min")));
                    Duree ma = new Duree(res.getLong(res.findColumn("max")));
                    duree_Enchere.setMin(mi);
                    duree_Enchere.setMax(ma);
            }
            stat.close();
            res.close();
            return duree_Enchere;

    }
    
}
































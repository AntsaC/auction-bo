/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lucky
 */
public class Duree {

	private int heure;
	private int minute;

	public Duree(){

	}

	public Duree(long sec){
		this.heure = (int)sec/3600;
		this.minute = (int)(sec%3600)/60;
	}

	public long tosecond(){
		long l = (long) heure*3600 + minute*60;
		return l;
	}

	@Override
	public String toString() {
		return "Duree [heure=" + heure + ", minute=" + minute + "]";
	}

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }



}

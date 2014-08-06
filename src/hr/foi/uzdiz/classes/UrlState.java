/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.classes;

/**
 *
 * @author Winner
 */
public class UrlState {
    
    private String urlName;
    private long vrijeme;
    private int brojRucnih;
    private int brojAutom;
    private int brojPromjena;

    public UrlState() {
        brojAutom = 0;
        brojRucnih = 0;
        brojPromjena = 0;
    }

    
    
    public long getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(long vrijeme) {
        this.vrijeme = vrijeme;
    }

    public int getBrojRucnih() {
        return brojRucnih;
    }

    public void setBrojRucnih(int brojRucnih) {
        this.brojRucnih = brojRucnih;
    }

    public int getBrojAutom() {
        return brojAutom;
    }

    public void setBrojAutom(int brojAutom) {
        this.brojAutom = brojAutom;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public int getBrojPromjena() {
        return brojPromjena;
    }

    public void setBrojPromjena(int brojPromjena) {
        this.brojPromjena = brojPromjena;
    }

    
    
}

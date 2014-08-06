/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.mvc;

import hr.foi.uzdiz.classes.UrlSingle;
import hr.foi.uzdiz.classes.UrlState;
import java.util.Observable;

/**
 *
 * @author Winner
 */
public class View implements java.util.Observer{
    
    private Model model;

    public View() {
        System.out.println("-B - ispis ukupnog broja poveznica");
        System.out.println("-I - ispis adresa poveznica s rednim brojem");
        System.out.println("-J n - prijelaz na poveznicu s rednim brojem n te "
                + "učitavanje web stranice koja time postaje vežeća");
        System.out.println("-R - obnovi važeću web stranicu (ponovno učitaj) te "
                + "informiraj ako je promijenjena u odnosu na prethodni sadržaj");
        System.out.println("-S - ispis statistike rada koja prikazuje svaku URL "
                + "web stranicu koja je korištena (bila važeća) uz prikaz vremena "
                + "zadržavanja na svakoj od njih, broj ručnih i automatiziranih "
                + "obnavljanja sadržaja te ukupan broj promjena sadržaja");
        System.out.println("-Q - prekid rada programa");
    }
    
    public void addModel(Model m){
        this.model = m;
    }
    
    public void ispisBrojaPoveznica(){
        System.out.println("Broj poveznica na url-u "+model.getCurrentUrl()+" je " +model.getBrojPoveznica());
    }
    
    public void ispisPoveznica(){
        for (UrlSingle object : model.getLista()) {
            System.out.println(object.getRedniBroj()+" : "+object.getUrl());
        }
    }
    
    public void ispisZaSite(){
        System.out.println("Učitan je site "+model.getCurrentUrl());
    }
    
    public void krivaKomanda(){
        System.out.println("Unijeli ste nepostojeću komandu! Pokušajte ponovno.");
    }
    
    public void ispisStatistike(){
        UrlState urlState = null;
        System.out.println("---Ispis Statistike rada.---");
        for (int i = 0; i < model.getStatSize(); i++) {
            urlState = model.povratiStanje(i);
            System.out.println("Stranica : " + urlState.getUrlName());
            System.out.println("Broj automatskih obnavljanja : " + urlState.getBrojAutom());
            System.out.println("Broj obnavljanja : " + urlState.getBrojRucnih());
            System.out.println("Vrijeme korištenja : ");
            long diff = urlState.getVrijeme();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.println(diffSeconds + " seconds.");
            System.out.println("Broj ukupnih promjena je : " + urlState.getBrojPromjena());
        }
        System.out.println("---Trenutni link.---");
        urlState = model.getUrlState();
        System.out.println("Stranica : " + urlState.getUrlName());
        System.out.println("Broj automatskih obnavljanja : " + urlState.getBrojAutom());
        System.out.println("Broj obnavljanja : " + urlState.getBrojRucnih());
        System.out.println("Vrijeme korištenja : ");
        long diff = System.currentTimeMillis() - model.getVrijemePocetak()+urlState.getVrijeme();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.println(diffSeconds + " seconds.");
        System.out.println("Broj ukupnih promjena je : " + urlState.getBrojPromjena());
        
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Došlo je do promjene na stranici : " + arg);
    }
    
}

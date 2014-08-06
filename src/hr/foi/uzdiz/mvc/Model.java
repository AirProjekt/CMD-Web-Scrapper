/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.mvc;

import hr.foi.uzdiz.classes.UrlSingle;
import hr.foi.uzdiz.classes.UrlState;
import hr.foi.uzdiz.memento.CareTaker;
import hr.foi.uzdiz.memento.Originator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Winner
 */
public class Model extends java.util.Observable{
    
    private String currentUrl;
    private List<UrlSingle> lista;
    private UrlState urlState;
    private long vrijemePocetak;
    private long vrijemeKraj;
    private CareTaker careTaker;
    private Originator originator;

    public Model() {
        vrijemePocetak = System.currentTimeMillis();
        urlState = new UrlState();
        originator = new Originator();
        careTaker = new CareTaker();
    }

    public void setLista(List<UrlSingle> lista) {
        this.lista = lista;
    }
    
    public List<UrlSingle> getLista(){
        return lista;
    }


    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }
    
    
    
    public int getBrojPoveznica(){
        return lista.size();
    }
    
    //Parsiranje HTML-a sa izvornim java klasama. Ne radi korektno pa sam odlučio koristit vanjski lib.
    public void ucitajStranicu(String urlParam){
        try {
            this.currentUrl = urlParam;
            int i = 1;
            URL url = new URL(urlParam);
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
            HTMLEditorKit.Parser parser = new ParserDelegator();
            HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
            parser.parse(br, callback, true);
            this.lista = new ArrayList<>();
            for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.A);
                    iterator.isValid(); iterator.next()) {

                AttributeSet attributes = iterator.getAttributes();
                String srcString = (String) attributes.getAttribute(HTML.Attribute.HREF);
                UrlSingle site = new UrlSingle(srcString,i);
                lista.add(site);
                i++;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajStranicu2(String urlParam){
        this.currentUrl = urlParam;
        urlState.setUrlName(currentUrl);
        try {
            int i = 1;
            this.lista = new ArrayList<>();
            Document doc = Jsoup.connect(urlParam).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                UrlSingle url = new UrlSingle(link.attr("abs:href"), i);
                lista.add(url);
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String izvuciSite(int n){
        return lista.get(n-1).getUrl();
    }
    
    public void spremiZaStat(){
        boolean flag = true;
        for (int i = 0; i < careTaker.getListSize(); i++) {
            UrlState urlStateTemp = povratiStanje(i);
            if (urlStateTemp.getUrlName().equals(currentUrl)) {
                vrijemeKraj = System.currentTimeMillis();
                urlStateTemp.setVrijeme(vrijemeKraj-vrijemePocetak+urlStateTemp.getVrijeme());
                urlStateTemp.setBrojPromjena(urlState.getBrojPromjena()+urlStateTemp.getBrojPromjena());
                urlStateTemp.setBrojRucnih(urlState.getBrojRucnih()+urlStateTemp.getBrojRucnih());
                urlStateTemp.setBrojAutom(urlState.getBrojAutom()+urlStateTemp.getBrojAutom());
                urlState = new UrlState();
                flag = false;
                break;
            }
            
        }
        if (flag) {
            vrijemeKraj = System.currentTimeMillis();
            urlState.setVrijeme(vrijemeKraj-vrijemePocetak);
            originator.set(urlState);
            careTaker.addMemento(originator.saveToMemento());
            urlState = new UrlState();
        }
        vrijemePocetak = System.currentTimeMillis();
    }
    
    public UrlState povratiStanje(int index){
        return originator.restoreFromMemento(careTaker.getMemento(index));
    }
    
    public int getStatSize(){
        return careTaker.getListSize();
    }
    
    public void incrementRucnih(){
        urlState.setBrojRucnih(urlState.getBrojRucnih()+1);
    }
    
    public void incrementAutom(){
        urlState.setBrojAutom(urlState.getBrojAutom()+1);
    }
    
    public void provjeriPromjene(){
        System.out.println("Stranica se obnavlja!");
        //kloniranje liste da bismo provjerili da li je došlo do promjene
        List<UrlSingle> tempLista = new ArrayList<>();
        for (UrlSingle urlSingle : lista) {
            try {
                tempLista.add((UrlSingle) urlSingle.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ucitajStranicu2(currentUrl);
        //provjera da li su liste jednake
        for (int i = 0; i < tempLista.size(); i++) {
            if (tempLista.get(i).getUrl().equals(lista.get(i).getUrl()) != true) {
                urlState.setBrojPromjena(urlState.getBrojPromjena()+1);
                setChanged();
                notifyObservers(currentUrl);
                break;
            }
            
        }
        
    }
    
    public UrlState getUrlState(){
        return urlState;
    }

    public long getVrijemePocetak() {
        return vrijemePocetak;
    }

    public long getVrijemeKraj() {
        return vrijemeKraj;
    }

}

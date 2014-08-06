/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.mvc;

import java.io.Console;

/**
 *
 * @author Winner
 */
public class Controller {
    
    private Model model;
    private View view;
    
    public void addModel(Model model){
        this.model = model;
    }
    
    public void addView(View v) {
        this.view = v;
    }

    
    public void initModel(String url){
        model.ucitajStranicu2(url);
    }
    
    public void processInput(){
        String komande[];
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        while (true) {            
            String input = c.readLine();
            
            switch(input){
                case "B":
                    view.ispisBrojaPoveznica();
                    break;
                case "I":
                    view.ispisPoveznica();
                    break;
                case "R":
                    model.incrementRucnih();
                    model.provjeriPromjene();
                    break;
                case "S":
                    view.ispisStatistike();
                    break;
                case "Q":
                    System.exit(1);
                    break;
                default:
                    if (input.matches("J ([0-9]*)")) {
                        komande = input.split(" ");
                        model.spremiZaStat();
                        initModel(model.izvuciSite(Integer.parseInt(komande[1])));
                        view.ispisZaSite();
                    }
                    else{
                        view.krivaKomanda();
                    }
                    
            }
        }
        
        
        
    }
    
}

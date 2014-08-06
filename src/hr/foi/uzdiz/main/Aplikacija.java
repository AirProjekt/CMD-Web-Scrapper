/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.main;

import hr.foi.uzdiz.mvc.Controller;
import hr.foi.uzdiz.mvc.Model;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Winner
 */
public class Aplikacija {
    
    public static void main(String[] args){
        Model model = new Model();
        Controller controller = new Controller();
        controller.addModel(model);
        controller.initModel(args[0]);
        Thread dretva = new Thread(new Dretva(controller,model));
        dretva.start();
        while (true) {            
            
            try {
                Thread.sleep(Integer.parseInt(args[1])*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Aplikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            model.provjeriPromjene();
            model.incrementAutom();
        }
    }
    
}

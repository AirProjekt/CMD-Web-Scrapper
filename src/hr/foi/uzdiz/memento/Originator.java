/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.memento;

import hr.foi.uzdiz.classes.UrlState;

/**
 *
 * @author Winner
 */
public class Originator {

    private UrlState urlState;

    public void set(UrlState urlState) {
        this.urlState = urlState;
    }

    public Object saveToMemento() {
        return new Memento(urlState);
    }

    public UrlState restoreFromMemento(Object m) {
        if (m instanceof Memento) {
            Memento memento = (Memento) m;
            urlState = memento.getState();
        }
        return urlState;
    }

    private static class Memento {

        private UrlState urlState;

        public Memento(UrlState urlState) {
            this.urlState = urlState;
        }
        
        public UrlState getState(){
            return urlState;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.memento;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Winner
 */
public class CareTaker {

    private List<Object> savedStates = new ArrayList<Object>();

    public void addMemento(Object m) {
        savedStates.add(m);
    }

    public Object getMemento(int index) {
        return savedStates.get(index);
    }
    
    public int getListSize(){
        return savedStates.size();
    }
}

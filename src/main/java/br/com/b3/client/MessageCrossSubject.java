package br.com.b3.client;

import quickfix.fix44.NewOrderCross;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MessageCrossSubject extends Observable {

    private ArrayList<Observer> observers;

    public MessageCrossSubject() {
        observers = new ArrayList<Observer>();
    }

    private NewOrderCross newOrderCross;

    public void newOrderSingleChanged() {
        setChanged();
        notifyObservers();
    }

    public void setNewOrderCross(NewOrderCross newOrderCross) {
        this.newOrderCross = newOrderCross;
        newOrderSingleChanged();
    }

    public NewOrderCross getNewOrderCross() {
        return newOrderCross;
    }


}

package br.com.b3.client;

import quickfix.fix44.NewOrderSingle;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MessageSingleSubject extends Observable {

    private ArrayList<Observer> observers;

    public MessageSingleSubject() {
        observers = new ArrayList<Observer>();
    }

    private NewOrderSingle newOrder;

    public void newOrderSingleChanged(){
        setChanged();
        notifyObservers();
    }

    public void setNewOrder(NewOrderSingle newOrder) {
        this.newOrder = newOrder;
        newOrderSingleChanged();
    }

    public NewOrderSingle getNewOrder() {
        return newOrder;
    }


}

package br.com.b3.client.observer;

import quickfix.fix44.NewOrderSingle;

public interface Observer {

    public void update(NewOrderSingle newOrder);
}

package br.com.b3.client;

import quickfix.*;
import quickfix.fix44.NewOrderSingle;

public class FixClientApplication implements Application {

    private NewOrderSingle newOrder;

    public NewOrderSingle getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(NewOrderSingle newOrder) {
        this.newOrder = newOrder;
    }

    public void onCreate(SessionID sessionID) {
        System.err.println("onCreate - sessionId:" + sessionID);
    }

    public void onLogon(SessionID sessionID) {
       System.err.println("onLogon - sessionId:" + sessionID);
       if(newOrder!=null) {
           try {
               Session.sendToTarget(newOrder, sessionID);
           } catch (SessionNotFound sessionNotFound) {
               sessionNotFound.printStackTrace();
           }
       }
    }

    public void onLogout(SessionID sessionID) {
        System.err.println("onLogout - sessionId:" + sessionID);
    }

    public void toAdmin(Message message, SessionID sessionID) {
//        System.err.println("toAdmin - sessionId:" + sessionID);
//        System.err.println("toAdmin - message" + message);
    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
//        System.err.println("fromAdmin - sessionId:" + sessionID);
//        System.err.println("fromAdmin - message" + message);
    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.err.println("toApp - sessionId:" + sessionID);
        System.err.println("toApp - message" + message);
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.err.println("fromApp - sessionId:" + sessionID);
        System.err.println("fromApp - message" + message);
    }
}

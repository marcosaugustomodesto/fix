package br.com.b3.client;

import quickfix.*;
import quickfix.fix44.NewOrderSingle;

import java.util.Observable;
import java.util.Observer;

public class FixClientApplication implements Application, Observer {

    private NewOrderSingle newOrder;
    private FixClient fixClient;

    private SessionID sessionID;

    public FixClientApplication(Observable fixClient) {
        this.fixClient = (FixClient) fixClient;
        this.fixClient.addObserver(this);
    }

    public void send(){
        System.err.println("send - sessionId:" + sessionID);
        if(newOrder!=null && sessionID != null) {
            try {
                Session.sendToTarget(newOrder, sessionID);
                newOrder = null;
            } catch (SessionNotFound sessionNotFound) {
                sessionNotFound.printStackTrace();
            }
        }
    }

    public void onCreate(SessionID sessionID) {
        System.err.println("onCreate - sessionId:" + sessionID);
    }

    public void onLogon(SessionID sessionID) {
       this.sessionID = sessionID;
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


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof FixClient){
            FixClient fc = (FixClient) o;
            this.newOrder = fc.getNewOrder();
            send();
        }
    }
}

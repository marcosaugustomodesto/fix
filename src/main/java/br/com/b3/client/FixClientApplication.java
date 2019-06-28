package br.com.b3.client;

import quickfix.*;
import quickfix.fix44.NewOrderCross;
import quickfix.fix44.NewOrderSingle;

import java.util.Observable;
import java.util.Observer;

public class FixClientApplication implements Application, Observer {

    private NewOrderSingle newOrder;
    private MessageSingleSubject messageSingleSubject;

    private NewOrderCross newOrderCross;
    private MessageCrossSubject messageCrossSubject;

    private SessionID sessionID;

    public FixClientApplication() {
    }

    public FixClientApplication(Observable observable) {
        if (observable instanceof MessageSingleSubject) {
            this.messageSingleSubject = (MessageSingleSubject) observable;
            this.messageSingleSubject.addObserver(this);
        } else if (observable instanceof MessageCrossSubject) {
            this.messageCrossSubject = (MessageCrossSubject) observable;
            this.messageCrossSubject.addObserver(this);
        }

    }

    public void setMessageSingleSubject(MessageSingleSubject messageSingleSubject) {
        this.messageSingleSubject = messageSingleSubject;
        this.messageSingleSubject.addObserver(this);
    }

    public void setMessageCrossSubject(MessageCrossSubject messageCrossSubject) {
        this.messageCrossSubject = messageCrossSubject;
        this.messageCrossSubject.addObserver(this);
    }

    public void send() {
        System.err.println("send - sessionId:" + sessionID);
        if (newOrder != null && sessionID != null) {
            try {
                Session.sendToTarget(newOrder, sessionID);
                newOrder = null;
            } catch (SessionNotFound sessionNotFound) {
                sessionNotFound.printStackTrace();
            }
        }
    }

    public void sendCross() {
        System.err.println("sendCross - sessionId:" + sessionID);
        if (newOrderCross != null && sessionID != null) {
            try {
                Session.sendToTarget(newOrderCross, sessionID);
                newOrderCross = null;
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
        if (o instanceof MessageSingleSubject) {
            System.err.println("update - MessageSingleSubject:");
            MessageSingleSubject mss = (MessageSingleSubject) o;
            this.newOrder = mss.getNewOrder();
            send();
        } else if (o instanceof MessageCrossSubject) {
            System.err.println("update - MessageCrossSubject:");
            MessageCrossSubject mcs = (MessageCrossSubject) o;
            this.newOrderCross = mcs.getNewOrderCross();
            sendCross();
        }
    }
}

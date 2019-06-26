package br.com.b3.client;

import quickfix.*;
import quickfix.field.ClOrdID;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.field.TransactTime;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

public class FixClientApplication implements Application {
    public void onCreate(SessionID sessionID) {
        System.err.println("onCreate - sessionId:" + sessionID);
    }

    public void onLogon(SessionID sessionID) {
        final String orderId = "12";
        System.err.println("onLogon - sessionId:" + sessionID);
        LocalDateTime local = LocalDateTime.now();
        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId), new Side(Side.BUY), new TransactTime(local), new OrdType(OrdType.MARKET));
        try {
            Session.sendToTarget(newOrder, sessionID);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
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

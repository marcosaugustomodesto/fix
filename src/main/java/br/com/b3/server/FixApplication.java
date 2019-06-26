package br.com.b3.server;


import quickfix.*;
import quickfix.field.ClOrdID;
import quickfix.field.ClearingAccount;
import quickfix.field.MsgType;

public class FixApplication  extends MessageCracker  implements Application {

    public void onCreate(SessionID sessionID) {
        System.err.println("onCreate - sessionId:" + sessionID);
    }

    public void onLogon(SessionID sessionID) {
        System.err.println("onLogon - sessionId:" + sessionID);
    }

    public void onLogout(SessionID sessionID) {
        System.err.println("onLogout - sessionId:" + sessionID);
    }

    public void toAdmin(Message message, SessionID sessionID) {
//        System.err.println("toAdmin - sessionId:" + sessionID);
//        System.err.println("toAdmin - message" + message);
    }

    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.err.println("fromAdmin - sessionId:" + sessionID);
        System.err.println("fromAdmin - message" + message.getHeader().getField(new MsgType()));

    }

    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        System.err.println("toApp - sessionId:" + sessionID);
        System.err.println("toApp - message" + message);
    }

    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.err.println("fromApp - sessionId:" + sessionID);
        System.err.println("fromApp - message" + message);
        crack(message, sessionID);
    }

    protected void onMessage(quickfix.Message message, SessionID sessionID) throws FieldNotFound,
            UnsupportedMessageType, IncorrectTagValue {
        ClOrdID clOrdID = new ClOrdID();
        //message.get(clOrdID);
        ClearingAccount clearingAccount = new ClearingAccount();
        //message.get(clearingAccount);

        System.err.println(message.getHeader().getField(new MsgType()));

    }


}

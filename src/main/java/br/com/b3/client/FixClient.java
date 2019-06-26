package br.com.b3.client;

import quickfix.*;
import quickfix.field.ClOrdID;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.field.TransactTime;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

public class FixClient {

    private static SessionID sessionID;

    public static void main(String[] args) throws Exception {


        SessionSettings settings = new SessionSettings("client.txt");
        Application app = new FixClientApplication();
        LocalDateTime local = LocalDateTime.now();
        final String orderId = "12";
        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId), new Side(Side.BUY), new TransactTime(local), new OrdType(OrdType.MARKET));
        ((FixClientApplication) app).setNewOrder(newOrder);

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);

        MessageFactory messageFactory = new DefaultMessageFactory();


        Initiator init = new SocketInitiator(app, storeFactory, settings, messageFactory);
        init.start();

        while(sessionID == null)
            Thread.sleep(1000);



    }
}

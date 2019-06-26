package br.com.b3.client;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

public class FixClient {

    private static SessionID sessionID;

    public static void main(String[] args) throws Exception {
        LocalDateTime local = LocalDateTime.now();

        SessionSettings settings = new SessionSettings("client.txt");
        Application app = new FixClientApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory messageFactory = new DefaultMessageFactory();
        Initiator init = new SocketInitiator(app,storeFactory,settings,messageFactory);
        init.start();

        final String orderId = "12";

        while(sessionID == null)
            Thread.sleep(1000);

        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId), new Side(Side.BUY), new TransactTime(local), new OrdType(OrdType.MARKET));
        Session.sendToTarget(newOrder, sessionID);
        Thread.sleep(5000);
        sendOrderCancelRequest();

    }

    static void sendOrderCancelRequest() throws SessionNotFound
    {
        quickfix.fix41.OrderCancelRequest message = new quickfix.fix41.OrderCancelRequest(
                new OrigClOrdID("123"),
                new ClOrdID("321"),
                new Symbol("LNUX"),
                new Side(Side.BUY));

        message.set(new Text("Cancel My Order!"));

        Session.sendToTarget(message, "TW", "TARGET");
    }
}

package br.com.b3.server;


import quickfix.*;

public class FixServer {
    public static void main(String[] args) throws Exception {
        Application app = new FixApplication();
        SessionSettings settings = new SessionSettings("settings.txt");
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        //LogFactory logFactory = new FileLogFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);

        MessageFactory messageFactory = new DefaultMessageFactory();

        Acceptor acceptor = new SocketAcceptor(app, storeFactory, settings, logFactory, messageFactory);
        acceptor.start();
        // while(condition == true) { do something
        //acceptor.stop();

//        CountDownLatch latch = new CountDownLatch(1);
//        latch.await();
    }
}

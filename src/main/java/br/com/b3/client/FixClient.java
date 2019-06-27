package br.com.b3.client;

import quickfix.*;
import quickfix.field.ClOrdID;
import quickfix.field.OrdType;
import quickfix.field.Side;
import quickfix.field.TransactTime;
import quickfix.fix44.NewOrderSingle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FixClient extends Observable {

    private static SessionID sessionID;

    private ArrayList<Observer> observers;

    public FixClient() {
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

    public static void main(String[] args) throws Exception {

        SessionSettings settings = new SessionSettings("client.txt");
        FixClient fc = new FixClient();
        Application app = new FixClientApplication(fc);

//        final String orderId = "12";
//        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(orderId), new Side(Side.BUY), new TransactTime( LocalDateTime.now()), new OrdType(OrdType.MARKET));
//        fc.setNewOrder(newOrder);


        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(600,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,4,5,5));
        JLabel lblOrderId = new JLabel("OrderId:");
        JTextField txtOrderId = new JTextField();
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(txtOrderId.getText()), new Side(Side.BUY), new TransactTime( LocalDateTime.now()), new OrdType(OrdType.MARKET));
                fc.setNewOrder(newOrder);

            }
        });

        panel.add(lblOrderId,0,0);
        panel.add(txtOrderId,0,1);
        panel.add(btnSubmit,0,2);
        frame.add(panel, BorderLayout.NORTH);

        frame.setVisible(true);


        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory messageFactory = new DefaultMessageFactory();

        Initiator init = new SocketInitiator(app, storeFactory, settings, messageFactory);
        init.start();



    }

}

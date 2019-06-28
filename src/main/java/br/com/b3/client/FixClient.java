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
import java.util.Observable;

public class FixClient extends Observable implements  ActionListener{

    private static SessionID sessionID;

    private MessageSingleSubject mss = new MessageSingleSubject();

    private JFrame frame;
    private JPanel panel;
    private JLabel lblOrderId;
    private JTextField txtOrderId;
    private JButton btnSubmit;

    public static void main(String[] args) throws Exception {
        new FixClient().init();
    }

    private void init() throws ConfigError {
        SessionSettings settings = new SessionSettings("client.txt");
        Application app = new FixClientApplication(mss);

        screenInit();

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory messageFactory = new DefaultMessageFactory();

        Initiator init = new SocketInitiator(app, storeFactory, settings, messageFactory);
        init.start();
    }

    private void screenInit() {
        this.frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(600,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new GridLayout(1,4,5,5));
        lblOrderId = new JLabel("OrderId:");
        txtOrderId = new JTextField();
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this);

        panel.add(lblOrderId,0,0);
        panel.add(txtOrderId,0,1);
        panel.add(btnSubmit,0,2);
        frame.add(panel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(txtOrderId.getText()), new Side(Side.BUY), new TransactTime( LocalDateTime.now()), new OrdType(OrdType.MARKET));
        mss.setNewOrder(newOrder);
    }

}

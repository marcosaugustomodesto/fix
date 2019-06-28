package br.com.b3.client;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.NewOrderCross;
import quickfix.fix44.NewOrderSingle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Observable;

public class FixClient extends Observable implements  ActionListener{

    private static SessionID sessionID;

    private MessageSingleSubject mss = new MessageSingleSubject();

    private MessageCrossSubject mcs = new MessageCrossSubject();

    private JTextField txtOrderId;
    private JRadioButton rbSingle;
    private JRadioButton rbCross;
    private JButton btnSubmit;
    private Application app;
    private DefaultTableModel dtm;
    public static void main(String[] args) throws Exception {
        new FixClient().init();
    }

    private void init() throws ConfigError, InterruptedException {
        SessionSettings settings = new SessionSettings("client.txt");
        //app = new FixClientApplication(mss);
        app = new FixClientApplication();

        screenInit();

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(true, true, true);
        MessageFactory messageFactory = new DefaultMessageFactory();

        Initiator init = new SocketInitiator(app, storeFactory, settings, messageFactory);
        init.start();

        while(init.getSessions() == null){
            Thread.sleep(1000);
        }
        btnSubmit.setEnabled(true);
    }

    private void screenInit() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(600,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JPanel panelTable = new JPanel();
        panel.setLayout(new GridLayout(1,5,5,5));
        panelTable.setLayout(new GridLayout(1,0,5,5));
        JLabel lblOrderId = new JLabel("OrderId:");
        txtOrderId = new JTextField();
        btnSubmit = new JButton("Submit");
        btnSubmit.setEnabled(false);

        btnSubmit.addActionListener(this);
        rbSingle = new JRadioButton("Single", true);
        rbCross = new JRadioButton("Cross");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbSingle);
        bg.add(rbCross);

        panel.add(lblOrderId,0,0);
        panel.add(txtOrderId,0,1);
        panel.add(rbSingle,0, 2);
        panel.add(rbCross,0, 3);
        panel.add(btnSubmit,0,4);


        String[] tblHead={"mensagem"};
        dtm=new DefaultTableModel(tblHead,0);
        JTable table=new JTable(dtm);
        table.setSize(400,400);
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(panelTable, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(rbSingle.isSelected()){
            ((FixClientApplication) app).setMessageSingleSubject(mss);
            NewOrderSingle newOrder = new NewOrderSingle(new ClOrdID(txtOrderId.getText()),
                    new Side(Side.BUY),
                    new TransactTime( LocalDateTime.now()),
                    new OrdType(OrdType.MARKET));
            mss.setNewOrder(newOrder);
            String[] row = { newOrder.toString() };
            dtm.addRow(row);

        } else if (rbCross.isSelected()){
            ((FixClientApplication) app).setMessageCrossSubject(mcs);
            NewOrderCross newOrderCross = new NewOrderCross(new CrossID(txtOrderId.getText()),
                    new CrossType(CrossType.CROSS_AON_CROSS_TRADE_WHICH_IS_EXECUTED_COMPLETELY_OR_NOT_BOTH_SIDES_ARE_TREATED_IN_THE_SAME_MANNER_THIS_IS_EQUIVALENT_TO_AN_ALL_OR_NONE_),
                    new CrossPrioritization(CrossPrioritization.BUY_SIDE_IS_PRIORITIZED) ,
                    new TransactTime(LocalDateTime.now()),
                    new OrdType(OrdType.MARKET));
            mcs.setNewOrderCross(newOrderCross);
            String[] row = { newOrderCross.toString() };
            dtm.addRow(row);

        }
    }

}

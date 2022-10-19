package code;

import javax.swing.*;

public class Client extends JFrame {
    private ConnectionToServer connection;
    private int tableId;
    private String clientName;
    private Hand hand;
    private Table table;
    private Chat chat;
    private Logs logs;
    private JPanel contentPane;
    private Timer timer;
    private Stats stats;

    public Client(int tableId,String[] players, ConnectionToServer connection) {
        this.setLayout(null);
        this.setBounds(0,0,1920,1080);
        this.setVisible(true);
        this.tableId = tableId;
        this.clientName = players[tableId];
        this.connection=connection;
        this.setTitle("Snapszer");
        Profile[] profiles= new Profile[4];
        for (int index=0;index<4;index++){
            profiles[index]= new Profile(players[index]);
        }
        this.table = new Table(connection, profiles, tableId);
        this.chat = new Chat(clientName,connection);
        this.logs = new Logs(tableId,clientName,connection);
        this.hand = new Hand(connection,tableId,logs);
        this.stats = new Stats();
        contentPane = new JPanel();
        contentPane.setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        contentPane.setLayout(null);
        contentPane.setVisible(true);
        contentPane.add(table);
        contentPane.add(hand);
        contentPane.add(chat);
        contentPane.add(logs);
        contentPane.add(stats);
        this.setContentPane(contentPane);
        refresh();
    }
    public void refresh() {
        timer = new Timer(1000, e -> {
             GameInformation gameInformation = connection.Refresh();
             hand.refresh(gameInformation);
             table.refresh(gameInformation);
             chat.refresh(gameInformation);
             logs.refresh(gameInformation);
             stats.refresh(gameInformation);
        });
        timer.start();
    }
}

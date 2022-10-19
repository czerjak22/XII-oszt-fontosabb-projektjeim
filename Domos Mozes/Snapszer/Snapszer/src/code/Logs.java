package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logs extends JPanel {
    private int tableId;
    private String nameOfThePlayer;
    private ConnectionToServer connection;
    private JTextArea serverMessageLabel;
    private String serverMessage;
    public Logs (int tableId,String nameOfThePlayer,ConnectionToServer connection) {
        this.tableId=tableId;
        this.connection=connection;
        this.nameOfThePlayer=nameOfThePlayer;
        this.setBounds(1500, 0, 420, 680);
        this.setLayout(null);
        this.setVisible(true);
        serverMessageLabel = new JTextArea("Hello jó játékot!");
        serverMessageLabel.setFont(new Font("Oswald", Font.BOLD,25));
        serverMessageLabel.setLineWrap(true);
        serverMessageLabel.setWrapStyleWord(true);
        serverMessageLabel.setBorder(null);
        serverMessageLabel.setFocusable(false);
        serverMessageLabel.setOpaque(false);
        serverMessageLabel.setBounds(0,0,420,660);
        JScrollPane serverInformation = new JScrollPane(serverMessageLabel);
        serverInformation.setBounds(0,0,420,660);
        serverInformation.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(serverInformation);
        this.revalidate();
        this.repaint();
    }
    public void refresh(GameInformation gameInformation){
        String message = gameInformation.getServerMessage(tableId);
        if(message!="") {
            serverMessageLabel.setText(serverMessageLabel.getText() + message);
            connection.resetServerMessage(tableId);
        }
    }
    public void setServerMessageLabel(String Message){
        serverMessageLabel.setText(serverMessageLabel.getText()+"\n"+Message);
    }
}

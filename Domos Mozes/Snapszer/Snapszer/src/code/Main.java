package code;

import java.awt.*;

public class Main {
    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
    public Main() {
        String[] players = {"Guest1","Guest2","Guest3","Guest4"};
        Server server = new Server(players, "team1", "team2");
        ConnectionToServer connection = new ConnectionToServer(server);
        Client client1 = new Client(0, players, connection);
        Client client2 = new Client(1, players, connection);
        Client client3 = new Client(2, players, connection);
        Client client4 = new Client(3, players, connection);
        /*device.setFullScreenWindow(client1);
        device.setFullScreenWindow(client2);
        device.setFullScreenWindow(client3);
        device.setFullScreenWindow(client4);*/
    }
    public static void main (String args[]){
        new Main();
    }

}

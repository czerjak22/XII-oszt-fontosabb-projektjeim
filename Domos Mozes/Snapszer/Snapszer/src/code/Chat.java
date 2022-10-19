package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Chat extends JPanel {
    private String nameOfThePlayer;
    private JTextArea chatLabel;
    private ConnectionToServer connection;
    private JTextArea sendAbleText;

    public Chat(String nameOfThePlayer, ConnectionToServer connection) {
        this.connection = connection;
        this.nameOfThePlayer = nameOfThePlayer;
        this.setBounds(0, 0, 610, 1080);
        this.setLayout(null);
        this.setVisible(true);
        chatLabel = new JTextArea();
        chatLabel.setFont(new Font("Oswald", Font.BOLD, 25));
        chatLabel.setLineWrap(true);
        chatLabel.setWrapStyleWord(true);
        chatLabel.setBorder(null);
        chatLabel.setFocusable(false);
        chatLabel.setOpaque(false);
        chatLabel.setBounds(0, 0, 500, 700);
        JScrollPane chat = new JScrollPane(chatLabel);
        chat.setBounds(0, 0, 500, 700);
        chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        sendAbleText = new JTextArea();
        sendAbleText.setFont(new Font("Oswald", Font.BOLD, 25));
        sendAbleText.setLineWrap(true);
        sendAbleText.setWrapStyleWord(true);
        sendAbleText.setBounds(0, 700, 500, 320);
        JScrollPane message = new JScrollPane(sendAbleText);
        message.setBounds(0, 700, 500, 320);
        message.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        SendTextListener sendTextListener = new SendTextListener();
        sendAbleText.addKeyListener(sendTextListener);
        this.add(chat);
        this.add(message);
        this.revalidate();
        this.repaint();
    }

    public void refresh(GameInformation gameInformation) {
        if (!chatLabel.getText().equals(gameInformation.getChat())) {
            chatLabel.setText(gameInformation.getChat());
        }
    }

    public class SendTextListener implements ActionListener, KeyListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String analize = nameOfThePlayer + ":" + sendAbleText.getText();
            String done = "";
            System.out.println();
            for (int index = 0; index < analize.length(); index++) {
                char c = analize.charAt(index);
                System.out.print(c);
                if (c == '\n' && index != (analize.length() - 1)) {
                    done = done + "\n";
                } else {
                    done = done + c;
                }
            }
            connection.updateChat(done);
            sendAbleText.requestFocus();
            sendAbleText.setText("");
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String analize = nameOfThePlayer + ":" + sendAbleText.getText();
                String done = "";
                System.out.println();
                for (int index = 0; index < analize.length(); index++) {
                    char c = analize.charAt(index);
                    System.out.print(c);
                    if (c == '\n' && index != (analize.length() - 1)) {
                        done = done + "<br>";
                    } else {
                        done = done + c;
                    }
                }
                connection.updateChat(done);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                sendAbleText.setText("");
                sendAbleText.requestFocus();
            }
        }
    }
}

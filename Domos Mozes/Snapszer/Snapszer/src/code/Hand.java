package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Hand extends JPanel {
    public Timer timer;
    private ConnectionToServer connection;
    private Logs logs;
    private JButton deal;
    private JButton colorMars;
    private JButton twenty;
    private JButton fourty;
    private JButton count;
    private JButton littleMars;
    private JButton bigMars;
    private JButton pass;
    private JButton withoutTronf;
    private LinkedList<Integer> cardItems;
    private GameInformation gameInformation;
    private int tableId;
    private int[] values = {2, 3, 4, 10, 11};
    private boolean haveTronf;
    private boolean haveColor;
    private int[] colorsOfTwenty;
    private boolean haveTwenty;
    private boolean haveFourty;
    private boolean twentyClicked;
    private boolean fourtyClicked;
    private LinkedList<Component> components;
    private LinkedList<JButton> buttons;
    private boolean alreadyCalled;
    private boolean alreadyCounted;
    private boolean haveColorMars;

    public Hand(ConnectionToServer connection, int tableId, Logs logs) {
        colorsOfTwenty = new int[]{-1, -1};
        components = new LinkedList<>();
        buttons = new LinkedList<>();
        this.logs = logs;
        this.setBounds(610, 850, 1310, 230);
        this.setVisible(true);
        this.setLayout(null);
        this.connection = connection;
        this.tableId = tableId;

        twenty = new JButton("20");
        twenty.setEnabled(false);
        twenty.setBounds(167 - 125, 100, 75, 25);
        twenty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (twentyClicked == false) {
                    twenty.setBackground(Color.green);
                    twentyClicked = true;
                    fourtyClicked = false;
                    fourty.setBackground(null);
                } else {
                    twentyClicked = false;
                    twenty.setBackground(null);
                }
            }
        });

        fourty = new JButton("40");
        fourty.setEnabled(false);
        fourty.setBounds(167 + 50, 100, 75, 25);
        fourty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fourtyClicked == false) {
                    fourty.setBackground(Color.green);
                    fourtyClicked = true;
                    twentyClicked = false;
                    twenty.setBackground(null);
                } else {
                    fourtyClicked = false;
                    fourty.setBackground(null);
                }
            }
        });

        deal = new JButton("Osztás");
        deal.setEnabled(false);
        deal.setBounds(500, 50, 150, 25);
        deal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(deal);
                connection.deal();
            }
        });

        count = new JButton("Számolás");
        count.setEnabled(false);
        count.setBounds(500, 100, 150, 25);
        count.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alreadyCounted == false) {
                    setDisablePushedComponent(count);
                    alreadyCounted = true;
                    gameInformation.count(tableId);
                }
            }
        });

        littleMars = new JButton("Kismars");
        littleMars.setEnabled(false);
        littleMars.setBounds(700, 50, 150, 25);
        littleMars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(littleMars);
                connection.ready(true, false, tableId, 1, littleMars.getText());
            }
        });

        bigMars = new JButton("Nagymars");
        bigMars.setEnabled(false);
        bigMars.setBounds(700, 100, 150, 25);
        bigMars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(bigMars);
                connection.ready(true, false, tableId, 2, bigMars.getText());
            }
        });

        pass = new JButton("Pász");
        pass.setEnabled(false);
        pass.setBounds(750 + 150, 50, 150, 25);
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(pass);
                connection.ready(false, false, tableId, 0, pass.getText());
            }
        });

        withoutTronf = new JButton("Pász Tronfnélkül");
        withoutTronf.setHorizontalAlignment(SwingConstants.CENTER);
        withoutTronf.setEnabled(false);
        withoutTronf.setBounds(750 + 150, 100, 150, 25);
        withoutTronf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(withoutTronf);
                connection.ready(false, true, tableId, 0, withoutTronf.getText());
            }
        });

        colorMars = new JButton("Színmars");
        colorMars.setBounds(750 + 150 + 150 + 50, 75, 150, 25);
        colorMars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisablePushedComponent(colorMars);
                connection.ready(true, false, tableId, 3, colorMars.getText());
            }
        });

        add(colorMars);
        add(twenty);
        add(fourty);
        add(deal);
        add(count);
        add(littleMars);
        add(bigMars);
        add(pass);
        add(withoutTronf);

        buttons.add(colorMars);
        buttons.add(twenty);
        buttons.add(fourty);
        buttons.add(deal);
        buttons.add(count);
        buttons.add(littleMars);
        buttons.add(bigMars);
        buttons.add(pass);
        buttons.add(withoutTronf);
    }

    public void refresh(GameInformation gameInformation) {
        if (gameInformation.getCall() == tableId) {
            alreadyCalled = false;
        } else {
            alreadyCounted = false;
        }
        this.gameInformation = gameInformation;
        for (Component component : this.getComponents()) {
            remove(component);
        }
        cardItems = gameInformation.getCards(tableId);
        int beggining = 0;
        int difference = 0;
        for (int iterator : cardItems) {
            Card card = new Card(iterator / 5, iterator % 5, values[iterator % 5]);
            CardClickListener l = new CardClickListener();
            card.addMouseListener(l);
            add(card);
            card.setBounds(beggining + difference, 0, 51, 83);
            difference += 70;
        }
        for (Component component : components) {
            add(component);
        }
        for (JButton jButton : buttons) {
            add(jButton);
            jButton.setEnabled(false);
        }

        if (tableId == gameInformation.getFirstCall()
                && !gameInformation.isDealFinished()
                && !gameInformation.isEndOfPreRound()) {
            haveTronf();
            haveColorMars = isColorMars();
            littleMars.setEnabled(true);
            bigMars.setEnabled(true);
            pass.setEnabled(true);
            if(!haveTronf && (tableId==((gameInformation.getDealer()+ 1) % 4) || tableId==((gameInformation.getDealer()+ 3) % 4))) {
                withoutTronf.setEnabled(true);
            }
            if(haveColorMars) {
                colorMars.setEnabled(true);
            }
        }

        if (!gameInformation.isMarsRound()) {
            haveTwenty();
            haveFourty();

            if (haveTwenty
                    && tableId == gameInformation.getFirstCall()
                    && gameInformation.isStartOfTheRound()
                    && !gameInformation.isDealFinished()
                    && gameInformation.isEndOfPreRound()) {
                twenty.setEnabled(true);
            }

            if (haveFourty
                    && tableId == gameInformation.getFirstCall()
                    && gameInformation.isStartOfTheRound()
                    && !gameInformation.isDealFinished()
                    && gameInformation.isEndOfPreRound()) {
                fourty.setEnabled(true);
            }

            if (tableId == gameInformation.getDealer()
                    && gameInformation.isDealFinished()
                    && !gameInformation.isEndOfTheRound()) {
                deal.setEnabled(true);
            } else if (tableId == gameInformation.getFirstCall()
                    && gameInformation.isStartOfTheRound()
                    && !gameInformation.isDealFinished()
                    && gameInformation.isEndOfPreRound()
                    && gameInformation.getCurrentRound() != 1
                    && !alreadyCounted
            ) {
                count.setEnabled(true);
            }
        }
        this.revalidate();
        this.repaint();
    }

    private boolean isColorMars(){
        if(!cardItems.isEmpty()) {
            int firstCardColor = cardItems.getFirst() / 5;
            for (int iterator : cardItems) {
                if (iterator / 5 != firstCardColor) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void setDisablePushedComponent(Component component) {
        component.setEnabled(false);
    }

    public void haveTronf() {
        haveTronf = false;
        for (int iterator : cardItems) {
            if (iterator / 5 == gameInformation.getTronfColor()) {
                haveTronf = true;
            }
        }
    }

    public void haveColor() {
        haveColor = false;
        for (int iterator : cardItems) {
            if (iterator / 5 == gameInformation.getFirstCallColor()) {
                haveColor = true;
            }
        }
    }

    public void haveTwenty() {
        haveTwenty = false;
        int numberOfTwenty = 0;
        boolean have3 = false;
        boolean have4 = false;
        int fromIterator = 0;
        int currentColor = 0;
        for (int iterator = 0; iterator < cardItems.size(); iterator++) {
            int currentCard = cardItems.get(iterator);
            if (currentCard / 5 != gameInformation.getTronfColor()) {
                if (have3 == false && have4 == false) {
                    if (currentCard % 5 == 1) {
                        have3 = true;
                        currentColor = currentCard / 5;
                        fromIterator = iterator;
                    } else if (currentCard % 5 == 2) {
                        have4 = true;
                        currentColor = currentCard / 5;
                        fromIterator = iterator;
                    }
                }
                if (have3 == true) {
                    for (int inIterator = fromIterator; inIterator < cardItems.size(); inIterator++) {
                        currentCard = cardItems.get(inIterator);
                        if (currentCard / 5 == currentColor && currentCard % 5 == 2) {
                            have4 = true;
                        }
                    }
                } else if (have4 == true) {
                    for (int inIterator = fromIterator; inIterator < cardItems.size(); inIterator++) {
                        currentCard = cardItems.get(inIterator);
                        if (currentCard / 5 == currentColor && currentCard % 5 == 1) {
                            have3 = true;
                        }
                    }
                }
                if (have3 && have4) {
                    colorsOfTwenty[numberOfTwenty++] = currentColor;
                    System.out.println(tableId + ":" + "colorOfTwenty:" + colorsOfTwenty[numberOfTwenty - 1]);
                    haveTwenty = true;
                }
                have3 = false;
                have4 = false;
            }
        }
    }

    public void haveFourty() {
        haveFourty = false;
        boolean have3 = false;
        boolean have4 = false;
        for (int iterator : cardItems) {
            if (iterator / 5 == gameInformation.getTronfColor()) {
                if (iterator % 5 == 1) {
                    have3 = true;
                } else if (iterator % 5 == 2) {
                    have4 = true;
                }
            }
        }
        if (have3 && have4) {
            haveFourty = true;
        }
    }

    public void resetTwentyAndForty() {
        twentyClicked = false;
        fourtyClicked = false;
        twenty.setBackground(null);
        fourty.setBackground(null);
        //colorsOfTwenty = new int[]{-1, -1};
    }

    public class CardClickListener extends MouseAdapter {
        private int cardId;
        private int cardColor;
        private Card senderCard;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (gameInformation.isEndOfPreRound() && !alreadyCalled) {
                senderCard = ((Card) e.getSource());
                setDisablePushedComponent(senderCard);
                cardId = senderCard.getId();
                cardColor = senderCard.getColor();
                haveColor();
                if (gameInformation.isMarsRound()) {
                    if (tableId == gameInformation.getFirstCall() && tableId == gameInformation.getCall() && !gameInformation.isEndOfTheRound()) {
                        marsCall();
                        alreadyCalled = true;
                        connection.setFirstCallColor(cardColor);
                    } else if (tableId == gameInformation.getCall() && !gameInformation.isEndOfTheRound()) {
                        if (haveColor) {
                            if (cardColor == gameInformation.getFirstCallColor()) {
                                marsCall();
                                alreadyCalled = true;
                            } else {
                                logs.setServerMessageLabel("A legelőször lehívott színt kell hívd");
                            }
                        } else {
                            marsCall();
                            alreadyCalled = true;
                        }
                    } else {
                        logs.setServerMessageLabel("Nem te jössz");
                    }
                } else {
                    haveTronf();
                    if (tableId == gameInformation.getFirstCall() && tableId == gameInformation.getCall() && !gameInformation.isEndOfTheRound()) {
                        if (cardId == 1 || cardId == 2) {
                            if (twentyClicked) {
                                for (int i = 0; i < 2; i++) {
                                    System.out.println("colorOfTwenty:" + colorsOfTwenty[i]);
                                    if (cardColor == colorsOfTwenty[i]) {
                                        connection.addSumm(tableId, 20);
                                        System.out.println("husz" + "+20");
                                    }
                                }
                            } else if (fourtyClicked && cardColor == gameInformation.getTronfColor()) {
                                connection.addSumm(tableId, 40);
                                System.out.println("negyven" + "40");
                            }
                        }
                        call();
                        connection.setFirstCallColor(cardColor);
                    } else if (tableId == gameInformation.getCall() && !gameInformation.isEndOfTheRound()) {
                        if (haveColor) {
                            if (cardColor == gameInformation.getFirstCallColor()) {
                                call();
                                alreadyCalled = true;
                            } else {
                                if (gameInformation.getTronfColor() == gameInformation.getFirstCallColor()) {
                                    logs.setServerMessageLabel("A legelőször lehívott színt kell hívd");
                                } else {
                                    logs.setServerMessageLabel("Tronfot kell hívj");
                                }
                            }
                        } else if (haveTronf) {
                            if (cardColor == gameInformation.getTronfColor()) {
                                call();
                                alreadyCalled = true;
                            } else {
                                logs.setServerMessageLabel("Tronfot kell hívj");
                            }
                        } else {
                            call();
                            alreadyCalled = true;
                        }
                    } else {
                        logs.setServerMessageLabel("Nem te jössz");
                    }
                    resetTwentyAndForty();
                }
            }
        }


        private void call() {
            connection.setCalledCard(tableId, cardColor * 5 + cardId);
            connection.addSumm(senderCard.getValue());
            connection.call();
        }

        private void marsCall() {
            connection.setCalledCard(tableId, cardColor * 5 + cardId);
            connection.marsCall();

        }
    }
}

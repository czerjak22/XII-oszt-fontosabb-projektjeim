package code;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class GameInformation implements Cloneable {
    private String[] names;
    private String nameOfTeam1;
    private String nameOfTeam2;
    private String winnerTeam;
    private int pointsOfTeam1 = 0, pointsOfTeam2 = 0;
    private int summTeam1, summTeam2;
    private int summ;
    private int dealer;
    private int numberOfWinsTeam1;
    private int numberOfWinsTeam2;
    private int[] calledCards;
    private int firstCallColor;
    private int tronfColor;
    private int firstCall;
    private int call;
    private boolean endOfTheRound;
    private boolean dealFinished;
    private int currentRound;
    private LinkedList<Integer>[] cards;
    private String currentChat = "Itt tudsz beszélni más játékosokkal!\nNe csalj, mert nem fer!";
    private String[] serverMessage = {"", "", "", ""};
    private Timer timer;
    private boolean marsRound;
    private int marsTableId;
    private int marsId;
    private boolean[] withoutTronf;
    private boolean reDeal;
    private boolean startOfTheRound;
    private boolean endOfPreRound;
    private boolean marsFailed;
    private int skipPlayers[] = {2, 3, 0, 1};
    private boolean team1CalledTwenty;
    private boolean team1CalledFourty;
    private boolean team2CalledTwenty;
    private boolean team2CalledFourty;


    public GameInformation(String[] players, String nameOfTeam1, String nameOfTeam2) {
        dealer = -1;
        calledCards = new int[]{-1, -1, -1, -1};
        cards = new LinkedList[4];
        withoutTronf = new boolean[4];
        for (int index = 0; index < 4; index++) {
            cards[index] = new LinkedList();
        }
        names = players;
        this.nameOfTeam1 = nameOfTeam1;
        this.nameOfTeam2 = nameOfTeam2;
        newDeal();
    }

    public void ready(boolean marsRound, boolean withoutTronf, int tableId, int marsId, String text) {
        updateServerMessage(names[tableId] + ": " + text + "!");
        firstCall = (firstCall + 1) % 4;
        if (marsRound == true) {
            if (this.marsId < marsId) {
                this.marsId = marsId;
                this.marsTableId = tableId;
                this.marsRound = true;
            }
        }
        if (withoutTronf) {
            this.withoutTronf[tableId] = true;
            if (this.withoutTronf[(dealer + 1) % 4] && this.withoutTronf[(dealer + 3) % 4])
                reDeal = true;
        }
        if (firstCall == call) {
            decidePreRound();
        }
    }

    public void decidePreRound() {
        endOfPreRound = true;
        resetCallCards();
        if (reDeal) {
            reDeal = false;
            reDeal();
        } else if (marsRound) {
            if (marsId == 3) {
                updateServerMessage(names[marsTableId] + " megy színmarsot");
                if (marsTableId % 2 == 0) {
                    pointsOfTeam1 += 12;
                } else {
                    pointsOfTeam2 += 12;
                }
                newDeal();
            } else {
                firstCall = call = marsTableId;
                if (marsId == 2) {
                    updateServerMessage(names[call] + " megy nagymarsot");
                } else {
                    updateServerMessage(names[call] + " megy kicsimarsot");
                }
                updateServerMessage(names[call] + " hív");
            }
        } else {
            updateServerMessage(names[call] + " hív");
        }
    }

    public void marsCall() {
        if (call != firstCall) {
            marsDecide();
        }
        if (marsFailed) {
            failedMarsRound();
        } else {
            call = (call + 1) % 4;
            if (call == skipPlayers[marsTableId]) {
                call = (call + 1) % 4;
            }
            updateServerMessage(names[call] + " hív");
            if (call == firstCall) {
                resetCallCards();
                marsSucceeded();
            }
        }
    }

    public void marsDecide() {
        int marsCard = calledCards[marsTableId];
        for (int card : calledCards) {
            if ((card != -1 || card == marsCard) && (card / 5 == marsCard / 5)) {
                if (marsId == 1) {
                    if (card % 5 < marsCard % 5) {
                        marsFailed = true;
                    }
                } else {
                    if (card % 5 > marsCard % 5) {
                        marsFailed = true;
                    }
                }
            }
        }
    }

    public void marsSucceeded() {
        if (currentRound == 5) {
            updateServerMessage("Mars won");
            succesfulMarsRound();
        }
        currentRound++;
    }

    public void failedMarsRound() {
        if (marsId == 1) {
            if (marsTableId % 2 == 0) {
                pointsOfTeam2 += 4;
                updateServerMessage(nameOfTeam2 + " nyerte a marsot " + 4 + " ponttal");
                winnerTeam = nameOfTeam2;
            } else {
                pointsOfTeam1 += 4;
                updateServerMessage(nameOfTeam1 + " nyerte a marsot " + 4 + " ponttal");
                winnerTeam = nameOfTeam1;
            }

        } else {
            if (marsTableId % 2 == 0) {
                pointsOfTeam2 += 6;
                updateServerMessage(nameOfTeam2 + " nyerte a marsot " + 6 + " ponttal");
                winnerTeam = nameOfTeam2;
            } else {
                pointsOfTeam1 += 6;
                updateServerMessage(nameOfTeam1 + " nyerte a marsot " + 6 + " ponttal");
                winnerTeam = nameOfTeam1;
            }
        }
        resetCallCards();
        newDeal();
    }

    public void succesfulMarsRound() {
        if (marsId == 1) {
            if (marsTableId % 2 == 0) {
                pointsOfTeam1 += 4;
                updateServerMessage(nameOfTeam1 + " nyerte a marsot " + 4 + " ponttal");
                winnerTeam = nameOfTeam1;
            } else {
                pointsOfTeam2 += 4;
                updateServerMessage(nameOfTeam2 + " nyerte a marsot " + 4 + " ponttal");
                winnerTeam = nameOfTeam2;
            }

        } else {
            if (marsTableId % 2 == 0) {
                pointsOfTeam1 += 6;
                updateServerMessage(nameOfTeam1 + " nyerte a marsot " + 6 + " ponttal");
                winnerTeam = nameOfTeam1;
            } else {
                pointsOfTeam2 += 6;
                updateServerMessage(nameOfTeam2 + " nyerte a marsot " + 6 + " ponttal");
                winnerTeam = nameOfTeam2;
            }
        }
        newDeal();
    }

    public void normalCall() {
        call = (call + 1) % 4;
        if (call == firstCall) {
            endOfTheRound = true;
            normalDecide();
        } else {
            updateServerMessage(names[call] + " hív");
        }
    }

    private void normalDecide() {
        boolean tronfRound = false;
        int[] values = {2, 3, 4, 10, 11};
        int winnerWithFirstCallCardValue = 0;
        int winnerWithTronfValue = 0;
        int winnerId = firstCall;
        int idOfTheCurrentCard = 0;
        for (int card : calledCards) {
            int currentValue = values[card % 5];
            int color = card / 5;
            if (color == tronfColor) {
                tronfRound = true;
            }
            if (tronfRound) {
                if (color == tronfColor) {
                    if (currentValue >= winnerWithTronfValue) {
                        winnerWithTronfValue = currentValue;
                        winnerId = idOfTheCurrentCard;
                    }
                }
            } else {
                if (color == firstCallColor) {
                    if (currentValue >= winnerWithFirstCallCardValue) {
                        winnerWithFirstCallCardValue = currentValue;
                        winnerId = idOfTheCurrentCard;
                    }
                }
            }
            idOfTheCurrentCard++;
        }
        setStatsAfterRound(winnerId);
        if (Math.max(summTeam1, summTeam2) >= 66) {
            int points;
            if (summTeam1 >= 66) {
                winnerTeam = nameOfTeam1;
                points = howManyPoints(2, summTeam2);
                pointsOfTeam1 += points;
                updateServerMessage(winnerTeam + " nyerte az játékot " + points + " ponttal");
            } else {
                winnerTeam = nameOfTeam2;
                points = howManyPoints(1, summTeam1);
                pointsOfTeam2 += points;
                updateServerMessage(winnerTeam + " nyerte az játékot " + points + " ponttal");
            }
            newDeal();
        } else if (currentRound == 5) {
            int points = 0;
            if (winnerId == 0 || winnerId == 2) {
                points = howManyPoints(1, summTeam1);
                pointsOfTeam1 += points;
                updateServerMessage(winnerTeam + " nyerte az játékot " + points + " ponttal");
                winnerTeam = nameOfTeam1;
            } else {
                points = howManyPoints(1, summTeam1);
                pointsOfTeam2 += points;
                updateServerMessage(winnerTeam + " nyerte az játékot " + points + " ponttal");
                winnerTeam = nameOfTeam2;
            }
            newDeal();
        } else {
            call = firstCall = winnerId;
            updateServerMessage(names[winnerId] + " nyerte a kört");
            currentRound++;
        }
        resetCallCards();
        startOfTheRound = true;
    }

    public void checkStats() {
        if (pointsOfTeam1>=21) {
            pointsOfTeam1 = 0;
            pointsOfTeam2 = 0;
            numberOfWinsTeam1++;
            updateServerMessage(winnerTeam + " nyerte a meccset!");
        }
        else if(pointsOfTeam2>=21){
            pointsOfTeam1 = 0;
            pointsOfTeam2 = 0;
            numberOfWinsTeam2++;
            updateServerMessage(winnerTeam + " nyerte a meccset!");
        }
    }

    public int howManyPoints(int teamId, int summTeam) {
        if (teamId == 1) {
            if (summTeam == 20 && team1CalledTwenty) {
                return 3;
            }
            if (summTeam == 40 && team1CalledFourty) {
                return 3;
            }
        } else {
            if (summTeam == 20 && team2CalledTwenty) {
                return 3;
            }
            if (summTeam == 40 && team2CalledFourty) {
                return 3;
            }
        }
        if (summTeam >= 33) {
            return 1;
        } else if (summTeam < 33 && summTeam != 0) {
            return 2;
        } else {
            return 3;
        }
    }

    public void setStatsAfterRound(int winnerId) {
        if (winnerId == 0 || winnerId == 2) {
            summTeam1 += summ;
            winnerTeam = nameOfTeam1;
            summ = 0;
        } else {
            summTeam2 += summ;
            winnerTeam = nameOfTeam2;
            summ = 0;
        }
    }

    public void reDeal() {
        marsFailed = false;
        withoutTronf = new boolean[4];
        marsId = 0;
        marsRound = false;
        summTeam1 = 0;
        summTeam2 = 0;
        resetCard();
        dealFinished = true;
        endOfPreRound = false;
        startOfTheRound = true;
        firstCall = call = dealer + 1;
        currentRound = 1;
        team1CalledTwenty = false;
        team1CalledFourty = false;
        team2CalledTwenty = false;
        team2CalledFourty = false;
    }

    public void newDeal() {
        checkStats();
        marsFailed = false;
        withoutTronf = new boolean[4];
        marsId = 0;
        marsRound = false;
        summTeam1 = 0;
        summTeam2 = 0;
        dealFinished = true;
        endOfPreRound = false;
        startOfTheRound = true;
        resetCard();
        dealer = (dealer + 1) % 4;
        firstCall = call = dealer + 1;
        currentRound = 1;
        team1CalledTwenty = false;
        team1CalledFourty = false;
        team2CalledTwenty = false;
        team2CalledFourty = false;
    }


    public void resetCallCards() {
        endOfTheRound = true;
        timer = new Timer(5000, e -> {
            timer.stop();
            for (int index = 0; index < 4; index++) {
                calledCards[index] = -1;
            }
            endOfTheRound = false;
        });
        timer.start();
    }

    public void resetCard() {
        for (int indexOfLists = 0; indexOfLists < 4; indexOfLists++) {
            cards[indexOfLists].clear();
        }
    }

    public int getDealer() {
        return dealer;
    }

    public void setDealFinished(boolean dealFinished) {
        this.dealFinished = dealFinished;
    }

    public LinkedList<Integer> getCards(int tableId) {
        return cards[tableId];
    }

    public void generateCards() {
        updateServerMessage(names[dealer] + " osztott kezdődik a játék");
        int numberOfGeneratedCards = 0;
        int[] generatedIds = new int[20];
        int[] ids = new int[20];
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            ids[i] = i;
        }
        for (int i = 0; i < 20; i++) {
            int position = r.nextInt(20 - i);
            generatedIds[numberOfGeneratedCards++] = ids[position];
            for (int j = position; j < 20 - i - 1; j++) {
                ids[j] = ids[j + 1];
            }
        }
        setCards(generatedIds);
    }

    public void setCards(int[] generatedIds) {
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                cards[i].add(generatedIds[index]);
                index++;
            }
            cards[1].clear();
            cards[1].add(0);
            cards[1].add(1);
            cards[1].add(5);
            cards[1].add(17);
            cards[1].add(16);
        }
    }

    public void setTronfColor() {
        tronfColor = cards[dealer].get(2) / 5;
        calledCards[dealer] = cards[dealer].get(2);
    }

    public int getFirstCallColor() {
        return firstCallColor;
    }

    public int getFirstCall() {
        return firstCall;
    }

    public int getCall() {
        return call;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getTronfColor() {
        return tronfColor;
    }

    public String getServerMessage(int tableId) {
        return serverMessage[tableId];
    }

    public void resetServerMessage(int tableId) {
        serverMessage[tableId] = "";
    }

    public String getChat() {
        return currentChat;
    }

    public boolean isStartOfTheRound() {
        return startOfTheRound;
    }

    public void updateServerMessage(String serverMessage) {
        for (int i = 0; i < 4; i++) {
            this.serverMessage[i] += "\n" + serverMessage;
        }
    }

    public void updateChat(String currentChat) {
        this.currentChat += "\n" + currentChat;
    }

    public boolean isEndOfTheRound() {
        return endOfTheRound;
    }

    public boolean isDealFinished() {
        return dealFinished;
    }

    public void setFirstCallColor(int firstCallColor) {
        this.firstCallColor = firstCallColor;
        startOfTheRound = false;
    }

    public void addSumm(int value) {
        summ += value;
    }

    public void addSumm(int tableId, int value) {
        if (tableId == 0 || tableId == 2) {
            summTeam1 += value;
            if (value == 20) {
                team1CalledTwenty = true;
                updateServerMessage(names[tableId] + ": Húsz");
            } else {
                team1CalledFourty = true;
                updateServerMessage(names[tableId] + ": Negyven");
            }
        } else {
            summTeam2 += value;
            if (value == 20) {
                team2CalledTwenty = true;
                updateServerMessage(names[tableId] + ": Húsz");
            } else {
                team2CalledFourty = true;
                updateServerMessage(names[tableId] + ": Negyven");
            }
        }
    }


    public int[] getCalledCards() {
        return calledCards;
    }

    public void setCalledCard(int tableId, int calledCard) {
        System.out.println(tableId + " " + calledCard);
        for (Integer i : cards[tableId]) {
            System.out.print(i + " ");
        }
        calledCards[tableId] = calledCard;
        cards[tableId].remove((Object) calledCard);
        System.out.println();
        for (Integer i : cards[tableId]) {
            System.out.print(i + " ");
        }
    }

    @Override
    protected GameInformation clone() throws CloneNotSupportedException {
        return (GameInformation) super.clone();
    }

    public void count(int tableId) {
        int summ;
        if (tableId % 2 == 0) {
            summ = summTeam1;
        } else {
            summ = summTeam2;
        }
        this.serverMessage[tableId] += "\n" + summ + " a fogásotok/fogásaitok összege";
    }

    public boolean isMarsRound() {
        return marsRound;
    }

    public boolean isEndOfPreRound() {
        return endOfPreRound;
    }

    public String getNameOfTeam1() {
        return nameOfTeam1;
    }

    public String getNameOfTeam2() {
        return nameOfTeam2;
    }

    public int getPointsOfTeam1() {
        return pointsOfTeam1;
    }

    public int getPointsOfTeam2() {
        return pointsOfTeam2;
    }

    public int getNumberOfWinsTeam1() {
        return numberOfWinsTeam1;
    }

    public int getNumberOfWinsTeam2() {
        return numberOfWinsTeam2;
    }
}

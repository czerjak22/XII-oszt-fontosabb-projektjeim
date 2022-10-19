package code;

public class Server {
    GameInformation gameInformation;

    public Server(String[] players, String nameOfTeam1, String nameOfTeam2) {
        gameInformation = new GameInformation(players, nameOfTeam1, nameOfTeam2);

    }

    public GameInformation getGameInformation() throws CloneNotSupportedException {
        return gameInformation.clone();
    }

    public void setCalledCard(int tableId, int calledCard) {
        gameInformation.setCalledCard(tableId, calledCard);
    }

    public void setFirstCallColor(int firstCallColor) {
        gameInformation.setFirstCallColor(firstCallColor);
    }

    public void addSumm(int value) {
        gameInformation.addSumm(value);
    }

    public void addSumm(int tableId, int value) {
        gameInformation.addSumm(tableId, value);

    }

    public void call() {
        gameInformation.normalCall();
    }

    public void marsCall(){
        gameInformation.marsCall();
    }

    public void deal() {
        gameInformation.setDealFinished(false);
        gameInformation.generateCards();
        gameInformation.setTronfColor();
    }

    public void updateChat(String currentChatUpdate) {
        gameInformation.updateChat(currentChatUpdate);
    }

    public void ready(boolean marsRound, boolean withoutTronf, int tableId, int marsId, String text) {
        gameInformation.ready(marsRound, withoutTronf, tableId, marsId, text);
    }

    public void resetServerMessage(int tableId) {
        gameInformation.resetServerMessage(tableId);
    }
}

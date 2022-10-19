package code;

public class ConnectionToServer {
    private Server server;

    public ConnectionToServer(Server server) {
        this.server = server;
    }

    public GameInformation Refresh() {
        GameInformation gameInformation = null;
        try {
            gameInformation = server.getGameInformation();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return gameInformation;
    }

    public void setCalledCard(int tableId, int calledCard) {
        server.setCalledCard(tableId, calledCard);
    }

    public void setFirstCallColor(int firstCallColor) {
        server.setFirstCallColor(firstCallColor);
    }

    public void addSumm(int value) {
        server.addSumm(value);
    }

    public void addSumm(int tableId,int value){
        server.addSumm(tableId,value);
    }

    public void call() {
        server.call();
    }

    public void marsCall(){
        server.marsCall();
    }

    public void deal() {
        server.deal();
    }

    public void updateChat(String currentChatUpdate) {
        server.updateChat(currentChatUpdate);
    }

    public void ready(boolean marsRound, boolean withoutTronf, int tableId, int marsId, String text) {
        server.ready(marsRound, withoutTronf, tableId, marsId, text);
    }
    public void resetServerMessage(int tableId){
        server.resetServerMessage(tableId);
    }
}

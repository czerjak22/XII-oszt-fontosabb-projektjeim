package code;

import javax.print.attribute.Size2DSyntax;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Table extends JPanel {
    private Profile[] profiles;
    private GameInformation gameInformation;
    private ConnectionToServer connection;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
    private String nameOfTeam1;
    private String nameOfTeam2;
    private String winnerOfTheRound;
    private String winnerOfTheDeal;
    private Point[] positionOfCalledCards;
    private Point[] positionOfSegedCard;
    private LinkedList<Component> tableComponents;
    private int ownProfileId;

    public Table(ConnectionToServer connection, Profile[] profiles, int ownProfileId) {
        this.ownProfileId=ownProfileId;
        tableComponents = new LinkedList<>();
        positionOfCalledCards=new Point[4];
        positionOfCalledCards[0]= new Point(325,500);
        positionOfCalledCards[1]= new Point(120,325);
        positionOfCalledCards[2]= new Point(325,120);
        positionOfCalledCards[3]= new Point(500,325);
        this.setBounds(610, 100, 700, 700);
        this.setVisible(true);
        this.connection = connection;
        this.setLayout(null);
        this.profiles = new Profile[4];
        for (int index=0;index<4;index++){
            this.profiles[index]=profiles[ownProfileId];
            ownProfileId=(ownProfileId+1)%4;
        }
        this.profiles[0].setLocation(300, 600);
        this.profiles[1].setLocation(15, 300);
        this.profiles[2].setLocation(300, 0);
        this.profiles[3].setLocation(600, 300);
        for (int i = 0; i < 4; i++) {
            add(this.profiles[i]);
            tableComponents.add(profiles[i]);
        }
        ImageItem table = new ImageItem("table.jpg");
        table.setBounds(100,100,500,500);
        add(table);
        tableComponents.add(table);
    }

    public void refresh(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
        int[] calledcards = gameInformation.getCalledCards();
        for (Component component : this.getComponents()){
            remove(component);
        }
        int index = ownProfileId-1;
        for (int i = 0; i < 4; i++) {
            index = (index+1)%4;

            int currentCard = calledcards[index];
            if(currentCard!=-1){
                Card card = new Card(currentCard/5,currentCard%5,0);
                if(i%2==0){
                    card.setBounds(positionOfCalledCards[i].x, positionOfCalledCards[i].y, 51, 83);
                }
                else {
                    card.rotateImg(90);
                    card.setBounds(positionOfCalledCards[i].x, positionOfCalledCards[i].y, 83, 51);
                }
                add(card);
            }
        }
        for (Component component : tableComponents){
            add(component);
        }
        revalidate();
        repaint();
    }
}

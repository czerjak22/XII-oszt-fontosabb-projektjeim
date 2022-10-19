package code;

import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {
    private Image img;
    private String nameOfTeam1;
    private String nameOfTeam2;
    private JLabel team1Label;
    private JLabel team2Label;
    private int numberOfWinsTeam1;
    private int numberOfWinsTeam2;
    private int pointsOfTeam1;
    private int pointsOfTeam2;

    public Stats() {
        this.setBounds(610, 0, 700, 100);
        this.setLayout(null);
        team1Label = new JLabel();
        team1Label.setText("<html>" + nameOfTeam1 + "<br>győzelmek: " + numberOfWinsTeam1 + "<br>pontok: " + pointsOfTeam1);
        team1Label.setBounds(100, 0, 200, 75);
        team1Label.setHorizontalAlignment(JLabel.LEFT);
        team1Label.setVerticalAlignment((int) CENTER_ALIGNMENT);
        team2Label = new JLabel();
        team2Label.setText("<html>" + nameOfTeam2 + "<br>győzelmek: " + numberOfWinsTeam2 + "<br>pontok: " + pointsOfTeam2);
        team2Label.setBounds(600 - 200, 0, 200, 75);
        team2Label.setHorizontalAlignment(JLabel.RIGHT);
        team2Label.setVerticalAlignment((int) CENTER_ALIGNMENT);
        ImageItem versus = new ImageItem("vs.png");
        versus.setBounds(100 + 216, 15, 50, 50);
        this.add(team1Label);
        this.add(team2Label);
        this.add(versus);
    }

    public void refresh(GameInformation gameInformation) {
        nameOfTeam1 = gameInformation.getNameOfTeam1();
        nameOfTeam2 = gameInformation.getNameOfTeam2();
        numberOfWinsTeam1 = gameInformation.getNumberOfWinsTeam1();
        numberOfWinsTeam2 = gameInformation.getNumberOfWinsTeam2();
        pointsOfTeam1 = gameInformation.getPointsOfTeam1();
        pointsOfTeam2 = gameInformation.getPointsOfTeam2();
        team1Label.setText("<html>" + nameOfTeam1 + "<br>győzelmek: " + numberOfWinsTeam1 + "<br>pontok: " + pointsOfTeam1);
        team2Label.setText("<html>" + nameOfTeam2 + "<br>győzelmek: " + numberOfWinsTeam2 + "<br>pontok: " + pointsOfTeam2);
        this.revalidate();
        this.repaint();
    }


}

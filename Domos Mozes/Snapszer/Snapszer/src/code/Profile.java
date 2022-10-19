package code;

import javax.swing.*;
import java.awt.*;


public class Profile extends JPanel {
    private Image img;
    private String Name;
    private JLabel nameLabel;

    public Profile(String Name) {
        setLayout(null);
        setBounds(0,0,85,100);
        this.Name = Name;
        nameLabel = new JLabel(Name);
        nameLabel.setText(Name);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setBounds(0,85,85,15);
        ImageItem imageItem = new ImageItem("profile.png");
        imageItem.setBounds(0,0,85,85);
        add(imageItem);
        add(nameLabel);
    }
}

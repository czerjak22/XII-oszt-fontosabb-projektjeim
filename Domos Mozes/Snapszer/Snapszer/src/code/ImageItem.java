package code;

import javax.swing.*;
import java.awt.*;

public class ImageItem extends JPanel {
    Image img;
    public ImageItem(String imgId){
        img = PngLoader.loadImage(imgId);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),null);
    }
}

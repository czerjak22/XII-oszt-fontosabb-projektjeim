package code;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Card extends JPanel {
    private Image img;
    private int color;
    private int id;
    private int value;

    public Card(int color, int id, int value) {
        this.color = color;
        this.id = id;
        this.value = value;
        img = PngLoader.loadImage(color + "" + id + ".jpg");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    public void rotateImg(int radius) {
        img = PngLoader.rotate((BufferedImage) img, radius);
        this.revalidate();
        this.repaint();
    }

    public int getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }
}

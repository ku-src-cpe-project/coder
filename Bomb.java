import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Bomb {
    private ImageIcon[] images;
    private int x, y, scale;

    public Bomb(int x, int y, int scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        images = new ImageIcon[2];
        images[0] = new ImageIcon("icon/bomb.png");
        images[1] = new ImageIcon("icon/bomb.png");
    }

    public void draw(Graphics g) {
        g.drawImage(images[0].getImage(), getX(), getY(), null);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
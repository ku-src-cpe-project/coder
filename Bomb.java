import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Bomb {
    private ImageIcon[] images;
    private int x, y, scale;
    private int toggle;

    public Bomb(int x, int y, int scale) {
        this.toggle = 0;
        this.x = x;
        this.y = y;
        this.scale = scale;
        images = new ImageIcon[2];
        images[0] = new ImageIcon("icon/bomb.png");
        images[1] = new ImageIcon("icon/bomb_2.png");
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), getX(), getY(), null);
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
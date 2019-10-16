import java.awt.Graphics;
import javax.swing.ImageIcon;

class Mushroom {
    private ImageIcon[] images;
    private int x, y, scale;
    private String mushroom;

    public Mushroom(int x, int y, int scale) {
        this.images = new ImageIcon[4];
        this.images[0] = new ImageIcon("icon/mushroom.png");
        this.images[1] = new ImageIcon("icon/mushroom_2.png");
        this.images[2] = new ImageIcon("icon/mushroom_3.png");
        this.images[3] = new ImageIcon("icon/mushroom_4.png");
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.mushroom = "chun-li";
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

    public String getMush() {
        return this.mushroom;
    }

}
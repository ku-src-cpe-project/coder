import java.awt.Graphics;
import javax.swing.ImageIcon;

class Portal {
    private ImageIcon[] images;
    private int x, y, scale;

    public Portal(int x, int y, int scale) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("icon/portal.png");
        this.images[1] = new ImageIcon("icon/portal_2.png");
        this.images[2] = new ImageIcon("icon/portal_3.png");
        this.images[3] = new ImageIcon("icon/portal_4.png");
        this.images[4] = new ImageIcon("icon/portal_5.png");
        this.images[5] = new ImageIcon("icon/portal_6.png");
        this.x = x;
        this.y = y;
        this.scale = scale;
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
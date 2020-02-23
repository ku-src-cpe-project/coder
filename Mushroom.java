import java.awt.Graphics;
import javax.swing.ImageIcon;

class Mushroom {
    private ImageIcon[] images;
    private int x, y, selfRow;
    private String mushroom;

    public Mushroom(int x, int y, int selfRow) {
        this.images = new ImageIcon[4];
        this.images[0] = new ImageIcon("icon/mushroom.png");
        this.images[1] = new ImageIcon("icon/mushroom_2.png");
        this.images[2] = new ImageIcon("icon/mushroom_3.png");
        this.images[3] = new ImageIcon("icon/mushroom_4.png");
        this.x = x;
        this.y = y;
        this.selfRow = selfRow;
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), this.x, this.y, null);
    }

    public String getMush() {
        return this.mushroom;
    }

    public int getSelfRow() {
        return this.selfRow;
    }
}
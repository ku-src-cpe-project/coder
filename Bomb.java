import java.awt.Graphics;
import javax.swing.ImageIcon;

class Bomb {
    private ImageIcon[] images;
    private int x, y, selfRow;

    public Bomb(int x, int y, int selfRow) {
        this.images = new ImageIcon[2];
        this.images[0] = new ImageIcon("icon/bomb.png");
        this.images[1] = new ImageIcon("icon/bomb_2.png");
        this.x = x;
        this.y = y;
        this.selfRow = selfRow;
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), this.x, this.y, null);
    }

    public int getSelfRow() {
        return this.selfRow;
    }
}
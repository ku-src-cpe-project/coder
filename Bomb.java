import java.awt.Graphics;
import javax.swing.ImageIcon;

class Bomb {
    private ImageIcon[] images;
    private int x, y, selfRow;

    public Bomb(int x, int y, int selfRow) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/etc/bomb/1.png");
        this.images[1] = new ImageIcon("src/etc/bomb/2.png");
        this.images[2] = new ImageIcon("src/etc/bomb/3.png");
        this.images[3] = new ImageIcon("src/etc/bomb/4.png");
        this.images[4] = new ImageIcon("src/etc/bomb/5.png");
        this.images[5] = new ImageIcon("src/etc/bomb/6.png");
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
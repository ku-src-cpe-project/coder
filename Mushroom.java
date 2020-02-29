import java.awt.Graphics;
import javax.swing.ImageIcon;

class Mushroom {
    private ImageIcon[] images;
    private int x, y, selfRow, selfColumn;
    private String mushroom;

    public Mushroom(int x, int y, int mapRow, int mapColumn) {
        this.images = new ImageIcon[12];
        this.images[0] = new ImageIcon("src/mushroom/a/1.png");
        this.images[1] = new ImageIcon("src/mushroom/a/2.png");
        this.images[2] = new ImageIcon("src/mushroom/a/3.png");
        this.images[3] = new ImageIcon("src/mushroom/a/4.png");
        this.images[4] = new ImageIcon("src/mushroom/a/5.png");
        this.images[5] = new ImageIcon("src/mushroom/a/6.png");

        this.images[6] = new ImageIcon("src/mushroom/b/1.png");
        this.images[7] = new ImageIcon("src/mushroom/b/2.png");
        this.images[8] = new ImageIcon("src/mushroom/b/3.png");
        this.images[9] = new ImageIcon("src/mushroom/b/4.png");
        this.images[10] = new ImageIcon("src/mushroom/b/5.png");
        this.images[11] = new ImageIcon("src/mushroom/b/6.png");
        this.x = x;
        this.y = y;
        this.selfRow = mapRow;
        this.selfColumn = mapColumn;
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

    public int getSelfColumn() {
        return this.selfColumn;
    }
}
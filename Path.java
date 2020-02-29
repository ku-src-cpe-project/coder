import java.awt.Graphics;
import javax.swing.ImageIcon;

class Path {
    private ImageIcon[] images;
    private int x, y, selfRow, selfColumn;

    public Path(int x, int y, int mapRow, int mapColumn) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/path/a/1.png");
        this.images[1] = new ImageIcon("src/path/a/2.png");
        this.images[2] = new ImageIcon("src/path/a/3.png");
        this.images[3] = new ImageIcon("src/path/a/4.png");
        this.images[4] = new ImageIcon("src/path/a/5.png");
        this.images[5] = new ImageIcon("src/path/a/6.png");
        this.x = x;
        this.y = y;
        this.selfRow = mapRow;
        this.selfColumn = mapColumn;
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), this.x, this.y, null);
    }

    public int getSelfRow() {
        return this.selfRow;
    }

    public int getSelfColumn() {
        return this.selfColumn;
    }
}
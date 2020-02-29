import java.awt.Graphics;
import javax.swing.ImageIcon;

class Portal {
    private ImageIcon[] images;
    private int x, y, selfRow, selfColumn;

    public Portal(int x, int y, int mapRow, int mapColumn) {
        this.images = new ImageIcon[18];
        this.images[0] = new ImageIcon("src/portal/a/1.png");
        this.images[1] = new ImageIcon("src/portal/a/2.png");
        this.images[2] = new ImageIcon("src/portal/a/3.png");
        this.images[3] = new ImageIcon("src/portal/a/4.png");
        this.images[4] = new ImageIcon("src/portal/a/5.png");
        this.images[5] = new ImageIcon("src/portal/a/6.png");
        
        this.images[6] = new ImageIcon("src/portal/b/1.png");
        this.images[7] = new ImageIcon("src/portal/b/2.png");
        this.images[8] = new ImageIcon("src/portal/b/3.png");
        this.images[9] = new ImageIcon("src/portal/b/4.png");
        this.images[10] = new ImageIcon("src/portal/b/5.png");
        this.images[11] = new ImageIcon("src/portal/b/6.png");
        
        this.images[12] = new ImageIcon("src/portal/c/1.png");
        this.images[13] = new ImageIcon("src/portal/c/2.png");
        this.images[14] = new ImageIcon("src/portal/c/3.png");
        this.images[15] = new ImageIcon("src/portal/c/4.png");
        this.images[16] = new ImageIcon("src/portal/c/5.png");
        this.images[17] = new ImageIcon("src/portal/c/6.png");
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
import java.awt.Graphics;
import javax.swing.ImageIcon;

class Question {
    private ImageIcon[] images;
    private int x, y, selfRow, selfColumn;

    public Question(int x, int y, int mapRow, int mapColumn) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/etc/question/1.png");
        this.images[1] = new ImageIcon("src/etc/question/2.png");
        this.images[2] = new ImageIcon("src/etc/question/3.png");
        this.images[3] = new ImageIcon("src/etc/question/4.png");
        this.images[4] = new ImageIcon("src/etc/question/5.png");
        this.images[5] = new ImageIcon("src/etc/question/6.png");
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
import java.awt.Graphics;
import javax.swing.ImageIcon;

class Treasure {
    private ImageIcon[] images;
    private int scale;
    public int[] selfPosition = { 0, 0 };
    private Map map;

    public Treasure(Map map, int scale, int mapRow, int mapColumn) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/etc/treasure/1.png");
        this.images[1] = new ImageIcon("src/etc/treasure/2.png");
        this.images[2] = new ImageIcon("src/etc/treasure/3.png");
        this.images[3] = new ImageIcon("src/etc/treasure/4.png");
        this.images[4] = new ImageIcon("src/etc/treasure/5.png");
        this.images[5] = new ImageIcon("src/etc/treasure/6.png");
        this.map = map;
        this.scale = scale;
        this.selfPosition[0] = mapRow;
        this.selfPosition[1] = mapColumn;
    }

    public void draw(Graphics g, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.selfPosition[1] * this.scale) + locationX + (padX * this.selfPosition[0]),
                (this.selfPosition[0] * this.scale) + locationY - (padY * this.selfPosition[0]) - 143 + 50, null);
    }

    public boolean checkNextStep(String dir, char a) {
        boolean bool = false;
        if (dir.equals("left")) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] - 1) == a) {
                bool = true;
            }
        } else if (dir.equals("right")) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) == a) {
                bool = true;
            }
        } else if (dir.equals("up")) {
            if (this.map.checkMap(this.selfPosition[0] - 1, this.selfPosition[1]) == a) {
                bool = true;
            }
        } else if (dir.equals("down")) {
            if (this.map.checkMap(this.selfPosition[0] + 1, this.selfPosition[1]) == a) {
                bool = true;
            }
        }
        return bool;
    }

    public int getSelfRow() {
        return this.selfPosition[0];
    }

    public int getSelfColumn() {
        return this.selfPosition[1];
    }
}
import java.awt.Graphics;
import javax.swing.ImageIcon;

class Dummy {
    private ImageIcon[] images;
    private int x, y;
    public int[] selfPosition = { 1, 1 };
    private Map map;

    public Dummy(Map map, int mapRow, int mapColumn, int x, int y) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("icon/dummy.png");
        this.images[1] = new ImageIcon("icon/dummy.png");
        this.selfPosition[0] = mapRow;
        this.selfPosition[1] = mapColumn;
        this.map = map;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), this.x, this.y, null);
    }

    public boolean checkNextStep(int dir, char a) {
        boolean bool = false;
        if (dir == 1) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] - 1) == a) {
                bool = true;
            }
        } else if (dir == 2) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) == a) {
                bool = true;
            }
        } else if (dir == 3) {
            if (this.map.checkMap(this.selfPosition[0] - 1, this.selfPosition[1]) == a) {
                bool = true;
            }
        } else if (dir == 4) {
            if (this.map.checkMap(this.selfPosition[0] + 1, this.selfPosition[1]) == a) {
                bool = true;
            }
        }
        return bool;
    }

    public void disable() {
        this.map.setMap(this.selfPosition[0], this.selfPosition[1], '0');
        this.selfPosition[0] = -99;
    }
}
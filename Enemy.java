import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.util.Random;

class Enemy {
    private ImageIcon[] images;
    private int scale;
    private Random random;
    public int[] selfPosition = { 0, 0 };
    private int[] tmpPosition = { 0, 0 };
    private Map map;
    private String state;

    public Enemy(Map map, int scale, int mapRow, int mapColumn) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/enemy/a/1.png");
        this.images[1] = new ImageIcon("src/enemy/a/2.png");
        this.images[2] = new ImageIcon("src/enemy/a/3.png");
        this.images[3] = new ImageIcon("src/enemy/a/4.png");
        this.images[4] = new ImageIcon("src/enemy/a/5.png");
        this.images[5] = new ImageIcon("src/enemy/a/6.png");
        this.random = new Random();
        this.map = map;
        this.scale = scale;
        this.selfPosition[0] = mapRow;
        this.selfPosition[1] = mapColumn;
        this.state = "live";
    }

    public void draw(Graphics g, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.selfPosition[1] * this.scale) + locationX + (padX * this.selfPosition[0]),
                (this.selfPosition[0] * this.scale) + locationY - (padY * this.selfPosition[0]) - 143 + 50, null);
    }

    public void walk() {
        if (!this.state.equals("dead")) {
            int dir = random.nextInt(5 - 1) + 1; // random 1-4
            // dir = 4;
            this.tmpPosition[0] = this.selfPosition[0];
            this.tmpPosition[1] = this.selfPosition[1];
            if (dir == 1 && collision(dir)) {
                this.selfPosition[1] -= 1;
            } else if (dir == 2 && collision(dir)) {
                this.selfPosition[1] += 1;
            } else if (dir == 3 && collision(dir)) {
                this.selfPosition[0] -= 1;
            } else if (dir == 4 && collision(dir)) {
                this.selfPosition[0] += 1;
            } else {
                // System.out.println("*** Sysntax error ***");
            }
            if (!this.state.equals("dead")) {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                this.map.setMap(this.selfPosition[0], this.selfPosition[1], '2');
            } else {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            }
        } else {
            System.err.println("Enemy are dead");
        }
    }

    public boolean collision(int dir) {
        boolean bool = true;
        if (dir == 1) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] - 1) != '0') {
                bool = false;
            }
        } else if (dir == 2) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
                bool = false;
            }
        } else if (dir == 3) {
            if (this.map.checkMap(this.selfPosition[0] - 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
        } else if (dir == 4) {
            if (this.map.checkMap(this.selfPosition[0] + 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
        }
        return bool;
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
        this.state = "dead";
    }
}
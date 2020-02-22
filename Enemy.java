import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.util.Random;

class Enemy {
    private ImageIcon[] images;
    private int x, y;
    private Random random;
    public int[] selfPosition = { 1, 1 };
    private int[] tmpPosition = { 0, 0 };
    private Map map;
    private String state;

    public Enemy(Map map, int mapRow, int mapColumn, int x, int y) {
        this.images = new ImageIcon[2];
        this.images[0] = new ImageIcon("icon/enemy.png");
        this.images[1] = new ImageIcon("icon/enemy_2.png");
        this.random = new Random();
        this.selfPosition[0] = mapRow;
        this.selfPosition[1] = mapColumn;
        this.map = map;
        this.x = x;
        this.y = y;
        this.state = "live";
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), this.x, this.y, null);
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
        checkStep(dir);
        return bool;
    }

    public void checkStep(int dir) {
        if (checkNextStep(dir, '9')) {
            System.out.println("Found Player");
        }
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

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
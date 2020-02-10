import java.awt.Graphics;
import javax.swing.ImageIcon;

class Dummy {
    private ImageIcon[] images;
    private int x, y, scale, dir;
    public int[] playerPosition = { 1, 1 };
    private int[] tmpPosition = { 0, 0 };
    private Map map;
    private String state;

    public Dummy(Map map, int scale, int x, int y, int mapX, int mapY) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("icon/dummy.png");
        this.images[1] = new ImageIcon("icon/dummy.png");
        this.playerPosition[0] = mapX;
        this.playerPosition[1] = mapY;
        this.map = map;
        // this.map.setMap(this.playerPosition[0], this.playerPosition[1], '2');
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.state = "alive";
        this.dir = 0;
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), getX(), getY(), null);
    }

    public void walk() {
        if (!this.state.equals("dead")) {
            this.tmpPosition[0] = this.playerPosition[0];
            this.tmpPosition[1] = this.playerPosition[1];
            if (dir == 1 && collision(dir)) {
                this.playerPosition[1] -= 1;
                this.x = this.x - this.scale;
            } else if (dir == 2 && collision(dir)) {
                this.playerPosition[1] += 1;
                this.x = this.x + this.scale;
            } else if (dir == 3 && collision(dir)) {
                this.playerPosition[0] -= 1;
                this.y = this.y - this.scale;
            } else if (dir == 4 && collision(dir)) {
                this.playerPosition[0] += 1;
                this.y = this.y + this.scale;
            } else {
                // System.out.println("*** Sysntax error ***");
            }
            if (!this.state.equals("dead")) {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                this.map.setMap(this.playerPosition[0], this.playerPosition[1], '2');
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
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] - 1) != '0') {
                bool = false;
            }
        } else if (dir == 2) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) != '0') {
                bool = false;
            }
        } else if (dir == 3) {
            if (this.map.cheMap(this.playerPosition[0] - 1, this.playerPosition[1]) != '0') {
                bool = false;
            }
        } else if (dir == 4) {
            if (this.map.cheMap(this.playerPosition[0] + 1, this.playerPosition[1]) != '0') {
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
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] - 1) == a) {
                bool = true;
            }
        } else if (dir == 2) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == a) {
                bool = true;
            }
        } else if (dir == 3) {
            if (this.map.cheMap(this.playerPosition[0] - 1, this.playerPosition[1]) == a) {
                bool = true;
            }
        } else if (dir == 4) {
            if (this.map.cheMap(this.playerPosition[0] + 1, this.playerPosition[1]) == a) {
                bool = true;
            }
        }
        return bool;
    }

    public void disable() {
        this.map.setMap(this.playerPosition[0], this.playerPosition[1], '0');
        this.playerPosition[0] = -99;
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

    public int getScale() {
        return this.scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
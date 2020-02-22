import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.util.Random;

class FireBall {
    private ImageIcon[] images;
    private int x, y, scale;
    private Random random;
    public int[] playerPosition = { 1, 1 };
    private int[] tmpPosition = { 0, 0 };
    private int[] nextPosition = { 0, 0 };
    private Map map;
    private String state;

    public FireBall(Map map, int scale, int x, int y, int mapX, int mapY) {
        this.images = new ImageIcon[2];
        this.images[0] = new ImageIcon("icon/fire_ball.png");
        this.images[1] = new ImageIcon("icon/fire_ball_2.png");
        this.random = new Random();
        this.playerPosition[0] = mapX;
        this.playerPosition[1] = mapY;
        this.map = map;
        this.map.setMap(this.playerPosition[0], this.playerPosition[1], '4');
        this.scale = scale;
        this.x = x;
        this.y = y;
        this.state = "live";
    }

    public void draw(Graphics g, int dir) {
        g.drawImage(images[dir].getImage(), getX(), getY(), null);
    }

    public void walk() {
        int dir = 2;
        this.tmpPosition[0] = this.playerPosition[0];
        this.tmpPosition[1] = this.playerPosition[1];
        this.nextPosition[0] = tmpPosition[0];
        this.nextPosition[1] = tmpPosition[1];
        if (dir == 2 && collision(dir)) {
            this.playerPosition[1] += 1;
            this.x = this.x + this.scale;
        } else {
            // System.out.println("*** Sysntax error ***");
            this.state = "dead";
        }
        if (!this.state.equals("dead")) {
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.playerPosition[0], this.playerPosition[1], '4');
        } else {
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
        }
    }

    public boolean collision(int dir) {
        boolean bool = true;
        if (dir == 2) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) != '0') {
                bool = false;
            }
            this.nextPosition[1] += 1;
        }
        checkStep(dir);
        return bool;
    }

    public void checkStep(int dir) {
        if (checkNextStep(dir, '2')) {
            System.out.println("Found Enemy");
        }
    }

    public boolean checkNextStep(int dir, char a) {
        boolean bool = false;
        if (dir == 2) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == a) {
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
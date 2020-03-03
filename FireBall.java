import java.awt.Graphics;
import javax.swing.ImageIcon;
// import java.util.Random;

class FireBall {
    private ImageIcon[] images;
    private int x, y;
    public int[] selfPosition = { 0, 0 };
    private int[] tmpPosition = { 0, 0 };
    private int[] nextPosition = { 0, 0 };
    private Map map;
    private String state, stateTmp;
    private boolean walking;

    public FireBall(Map map, int mapRow, int mapColumn, int x, int y) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/effect/fireball/1.png");
        this.images[1] = new ImageIcon("src/effect/fireball/2.png");
        this.images[2] = new ImageIcon("src/effect/fireball/3.png");
        this.images[3] = new ImageIcon("src/effect/fireball/4.png");
        this.images[4] = new ImageIcon("src/effect/fireball/5.png");
        this.images[5] = new ImageIcon("src/effect/fireball/6.png");
        this.selfPosition[0] = mapRow;
        this.selfPosition[1] = mapColumn;
        this.map = map;
        this.x = x;
        this.y = y;
        this.state = "live";
        this.stateTmp = "live";
        this.walking = false;
    }

    // public void draw(Graphics g, int dir) {
    // g.drawImage(images[dir].getImage(), this.x, this.y, null);

    public void draw(Graphics g, int dir, int scale, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.selfPosition[1] * scale) + locationX + (padX * this.selfPosition[0]),
                (this.selfPosition[0] * scale) + locationY - (padY * this.selfPosition[0]) - 143 + 50, null);
        // g.drawImage(this.images[0].getImage(), getX(), getY(), null);
    }

    public void walk() {
        this.walking = true;
        this.tmpPosition[0] = this.selfPosition[0];
        this.tmpPosition[1] = this.selfPosition[1];
        this.nextPosition[0] = this.selfPosition[0];
        this.nextPosition[1] = this.selfPosition[1];
        if (collision()) {
            this.tmpPosition[1]++;
        } else {
            // System.out.println("*** Sysntax error ***");
            this.stateTmp = "dead";
        }
        if (!this.stateTmp.equals("dead")) {
        } else {
        }
    }

    public void update() {
        this.map.setMap(this.selfPosition[0], this.selfPosition[1], '0');
        this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '4');
        this.selfPosition[0] = this.tmpPosition[0];
        this.selfPosition[1] = this.tmpPosition[1];
        this.state = this.stateTmp;
    }

    public boolean collision() {
        boolean bool = true;
        if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
            bool = false;
        }
        this.nextPosition[1] += 1;
        return bool;
    }

    public boolean checkNextStep(int dir, char a) {
        boolean bool = false;
        if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) == a) {
            bool = true;
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

    public boolean getWalking() {
        return this.walking;
    }

    public void setWalking(boolean a) {
        this.walking = a;
    }
}
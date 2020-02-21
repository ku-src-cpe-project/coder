// Version 0.0.1

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.applet.*;
import java.util.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Dimension;

class Player {
    private PlaySound soundMedia;
    private ImageIcon[] images;
    public int[] playerPosition = { 1, 1 };
    private int[] tmpPosition = { 0, 0 };
    private int[] nextPosition = { 0, 0 };
    private Map map;
    private String state, mushroom;
    private FireBall fireball;
    private ArrayList<FireBall> fireballs;

    public Player(Map map, PlaySound soundMedia) {
        this.soundMedia = soundMedia;
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("icon/player.png");
        this.images[1] = new ImageIcon("icon/player_2.png");
        this.images[2] = new ImageIcon("icon/player_3.png");
        this.images[3] = new ImageIcon("icon/player_4.png");
        this.images[4] = new ImageIcon("icon/player_5.png");
        this.images[5] = new ImageIcon("icon/player_6.png");
        this.map = map;
        this.map.setMap(this.playerPosition[0], this.playerPosition[1], '9');
        this.state = "alive";
        this.mushroom = "ryu";
        this.fireball = null;
        this.fireballs = new ArrayList<FireBall>();
    }

    public void draw(Graphics g, int scale, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.playerPosition[1] * scale) + locationX + (padX * this.playerPosition[0]),
                (this.playerPosition[0] * scale) + locationY - (padY * this.playerPosition[0]) - 143 + 50, null);
        // g.drawImage(this.images[0].getImage(), getX(), getY(), null);
    }

    public void walk(String dir) {
        if (!this.state.equals("dead")) {
            this.tmpPosition[0] = this.playerPosition[0];
            this.tmpPosition[1] = this.playerPosition[1];
            this.nextPosition[0] = tmpPosition[0];
            this.nextPosition[1] = tmpPosition[1];
            if (dir.equals("left") && collision(dir)) {
                this.playerPosition[1] -= 1;
            } else if (dir.equals("right") && collision(dir)) {
                this.playerPosition[1] += 1;
            } else if (dir.equals("up") && collision(dir)) {
                this.playerPosition[0] -= 1;
            } else if (dir.equals("down") && collision(dir)) {
                this.playerPosition[0] += 1;
            } else {
                System.out.println("*** Sysntax error ***");
                if (checkNextStep(dir, '3')) {
                    if (this.mushroom.equals("chun-li")) {
                        this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
                    } else {
                        this.state = "dead";
                    }
                }
            }
            if (!this.state.equals("dead")) {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                this.map.setMap(this.playerPosition[0], this.playerPosition[1], '9');
            } else {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                this.soundMedia.playSound_S("sound/dead.wav");
            }
        } else {
            System.out.println("You are dead");
        }
    }

    public boolean collision(String dir) {
        boolean bool = true;
        if (dir.equals("left")) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] - 1) != '0') {
                bool = false;
            }
            this.nextPosition[1] -= 1;
        } else if (dir.equals("right")) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) != '0') {
                bool = false;
            }
            this.nextPosition[1] += 1;
        } else if (dir.equals("up")) {
            if (this.map.cheMap(this.playerPosition[0] - 1, this.playerPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0] -= 1;
        } else if (dir.equals("down")) {
            if (this.map.cheMap(this.playerPosition[0] + 1, this.playerPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0] += 1;
        }
        checkStep(dir);
        return bool;
    }

    public void checkStep(String dir) {
        if (checkNextStep(dir, '8')) {
            this.state = "next";
            this.soundMedia.playSound_S("sound/next.wav");
        }
        if (checkNextStep(dir, '7')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.playerPosition[0] = this.map.findMap('6')[0];
            this.playerPosition[1] = this.map.findMap('6')[1];
            this.soundMedia.playSound_S("sound/portal.wav");
        }
        if (checkNextStep(dir, '5')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.playerPosition[0] = this.nextPosition[0];
            this.playerPosition[1] = this.nextPosition[1];
            this.soundMedia.playSound_S("sound/mushroom.wav");
            this.mushroom = "ken";
        }
        if (checkNextStep(dir, 'A')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.playerPosition[0] = this.nextPosition[0];
            this.playerPosition[1] = this.nextPosition[1];
            this.soundMedia.playSound_S("sound/mushroom.wav");
            this.mushroom = "chun-li";
        }
    }

    public boolean checkNextStep(String dir, char a) {
        boolean bool = false;
        if (dir.equals("left")) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] - 1) == a) {
                bool = true;
            }
        } else if (dir.equals("right")) {
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == a) {
                bool = true;
            }
        } else if (dir.equals("up")) {
            if (this.map.cheMap(this.playerPosition[0] - 1, this.playerPosition[1]) == a) {
                bool = true;
            }
        } else if (dir.equals("down")) {
            if (this.map.cheMap(this.playerPosition[0] + 1, this.playerPosition[1]) == a) {
                bool = true;
            }
        }
        return bool;
    }

    public void attack(ArrayList<Enemy> enemys) {
        if (this.mushroom.equals("ken")) {
            System.out.println("Hadouken!");
            if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '2') {
                this.map.setMap(this.playerPosition[0], this.playerPosition[1] + 1, '0');
                for (int i = 0; i < enemys.size(); i++) {
                    if (enemys.get(i).checkNextStep(1, '9')) {
                        enemys.get(i).disable();
                        enemys.remove(i);
                    }
                }
                this.soundMedia.playSound_S("sound/fire.wav");
                this.soundMedia.playSound_S("sound/hit.wav");
            } else if (this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '1'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '3'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '5'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '6'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '7'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '8'
                    || this.map.cheMap(this.playerPosition[0], this.playerPosition[1] + 1) == '9') {
                System.out.println("Have something front.");
            } else {
                // (j * scale) + locationX + (padX * i),
                // (i * scale) + locationY - (padY * i) - 143 + 50,
                // i,
                // j)
                this.soundMedia.playSound_S("sound/fire.wav");
                map.printMap();
            }
        } else {
            System.out.println("You are not Ken");
        }
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMush() {
        return this.mushroom;
    }

    public void setMush(String a) {
        this.mushroom = a;
    }

    public PlaySound getSound() {
        return this.soundMedia;
    }

    public void setSound(PlaySound a) {
        this.soundMedia = a;
    }

    public FireBall getFireball() {
        return this.fireball;
    }

    public void setFireball(FireBall a) {
        this.fireball = a;
    }

    public ArrayList<FireBall> getFireballs() {
        return this.fireballs;
    }

    public void setFireballs(ArrayList<FireBall> a) {
        this.fireballs = a;
    }
}
// Version 0.0.1

// import javax.swing.*;
// import java.awt.*;
import java.awt.Graphics;
// import java.awt.event.*;
// import java.applet.*;
// import java.util.*;
// import java.util.ArrayList;
// import java.util.StringTokenizer;
// import java.io.*;
// import java.awt.Graphics;
import javax.swing.ImageIcon;

class Player {
    private ImageIcon[] images;
    private int scale;
    public int[] selfPosition = { 0, 0 };
    private int[] tmpPosition = { 0, 0 };
    private int[] nextPosition = { 0, 0 };
    private Map map;
    private String state, stateTmp, mushroom, direction;

    public Player(Map map, int scale) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("icon/player.png");
        this.images[1] = new ImageIcon("icon/player_2.png");
        this.images[2] = new ImageIcon("icon/player_3.png");
        this.images[3] = new ImageIcon("icon/player_4.png");
        this.images[4] = new ImageIcon("icon/player_5.png");
        this.images[5] = new ImageIcon("icon/player_6.png");
        this.map = map;
        this.scale = scale;
        this.state = "live";
        this.stateTmp = "live";
        this.mushroom = "ryu";
        this.selfPosition[0] = this.map.findMap('9')[0];
        this.selfPosition[1] = this.map.findMap('9')[1];
    }

    public void draw(Graphics g, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.selfPosition[1] * this.scale) + locationX + (padX * this.selfPosition[0]),
                (this.selfPosition[0] * this.scale) + locationY - (padY * this.selfPosition[0]) - 143 + 50, null);
    }

    public void walk(String dir) {
        if (!Coder.walking) {
            this.direction = dir;
            Coder.walking = true;
            Coder.frame = 0;
            if (!this.state.equals("dead")) {
                this.tmpPosition[0] = this.selfPosition[0];
                this.tmpPosition[1] = this.selfPosition[1];
                this.nextPosition[0] = tmpPosition[0];
                this.nextPosition[1] = tmpPosition[1];
                if (dir.equals("left") && collision(dir)) {
                    this.tmpPosition[1] -= 1;
                } else if (dir.equals("right") && collision(dir)) {
                    this.tmpPosition[1] += 1;
                } else if (dir.equals("up") && collision(dir)) {
                    this.tmpPosition[0] -= 1;
                } else if (dir.equals("down") && collision(dir)) {
                    this.tmpPosition[0] += 1;
                } else {
                    System.out.println("*** Sysntax error ***");
                    if (checkNextStep(dir, '3')) {
                        if (this.mushroom.equals("chun-li")) {
                            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
                        } else {
                            this.stateTmp = "dead";
                        }
                    }
                }
            } else {
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                Coder.soundMedia.playSound_S("sound/dead.wav");
                System.out.println("You are dead");
            }
        }
    }

    public void update() {
        this.map.setMap(this.selfPosition[0], this.selfPosition[1], '0');
        this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '9');
        this.selfPosition[0] = this.tmpPosition[0];
        this.selfPosition[1] = this.tmpPosition[1];
        this.state = this.stateTmp;
    }

    public boolean collision(String dir) {
        boolean bool = true;
        if (dir.equals("left")) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] - 1) != '0') {
                bool = false;
            }
            this.nextPosition[1] -= 1;
        } else if (dir.equals("right")) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
                bool = false;
            }
            this.nextPosition[1] += 1;
        } else if (dir.equals("up")) {
            if (this.map.checkMap(this.selfPosition[0] - 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0] -= 1;
        } else if (dir.equals("down")) {
            if (this.map.checkMap(this.selfPosition[0] + 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0] += 1;
        }
        checkStep(dir);
        return bool;
    }

    public void checkStep(String dir) {
        if (checkNextStep(dir, '8')) {
            this.stateTmp = "next";
            Coder.soundMedia.playSound_S("sound/next.wav");
        } else if (checkNextStep(dir, '7')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.nextPosition[0] = this.map.findMap('6')[0];
            this.nextPosition[1] = this.map.findMap('6')[1];
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSound_S("sound/portal.wav");
        } else if (checkNextStep(dir, '6')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.nextPosition[0] = this.map.findMap('7')[0];
            this.nextPosition[1] = this.map.findMap('7')[1];
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSound_S("sound/portal.wav");
        } else if (checkNextStep(dir, '5')) {
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSound_S("sound/mushroom.wav");
            this.mushroom = "ken";
        } else if (checkNextStep(dir, 'A')) {
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSound_S("sound/mushroom.wav");
            this.mushroom = "chun-li";
        }
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

    public void attack() {
        if (this.mushroom.equals("ken") && !Coder.attacking) {
            System.out.println("Hadouken!");
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) == '2') {
                this.map.setMap(this.selfPosition[0], this.selfPosition[1] + 1, '0');
                for (int i = 0; i < Coder.enemys.size(); i++) {
                    if (Coder.enemys.get(i).checkNextStep(1, '9')) {
                        Coder.enemys.get(i).disable();
                        Coder.enemys.remove(i);
                    }
                }
                Coder.soundMedia.playSound_S("sound/fire.wav");
                Coder.soundMedia.playSound_S("sound/hit.wav");
            } else if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
                System.out.println("Have something front.");
            } else {
                this.map.setMap(this.selfPosition[0], this.selfPosition[1] + 1, '4');
                Coder.soundMedia.playSound_S("sound/fire.wav");
            }
        } else if (Coder.attacking) {
            System.out.println("You are attacking");
        } else {
            System.out.println("You are not Ken");
        }
    }

    public int getMushroomNumber() {
        if (this.mushroom.equals("ken")) {
            return 1;
        } else if (this.mushroom.equals("chun-li")) {
            return 2;
        } else {
            return 0;
        }
    }

    public String getState() {
        return this.state;
    }

    public void setState(String a) {
        this.state = a;
    }

    public String getMushroom() {
        return this.mushroom;
    }

    public void setMushroom(String a) {
        this.mushroom = a;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String a) {
        this.direction = a;
    }
}
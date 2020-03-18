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
    private int scale, treasure;
    public int[] selfPosition = { 0, 0 };
    private int[] tmpPosition = { 0, 0 };
    private int[] nextPosition = { 0, 0 };
    private Map map;
    private String state, stateTmp, mushroom, direction;
    private boolean walking;

    public Player(Map map, int scale) {
        this.images = new ImageIcon[22];
        this.images[0] = new ImageIcon("src/hero/a/1.png");
        this.images[1] = new ImageIcon("src/hero/a/2.png");
        this.images[2] = new ImageIcon("src/hero/a/3.png");
        this.images[3] = new ImageIcon("src/hero/a/4.png");
        this.images[4] = new ImageIcon("src/hero/a/5.png");
        this.images[5] = new ImageIcon("src/hero/a/6.png");

        this.images[6] = new ImageIcon("src/hero/b/1.png");
        this.images[7] = new ImageIcon("src/hero/b/2.png");
        this.images[8] = new ImageIcon("src/hero/b/3.png");
        this.images[9] = new ImageIcon("src/hero/b/4.png");
        this.images[10] = new ImageIcon("src/hero/b/5.png");
        this.images[11] = new ImageIcon("src/hero/b/6.png");

        this.images[12] = new ImageIcon("src/hero/c/1.png");
        this.images[13] = new ImageIcon("src/hero/c/2.png");
        this.images[14] = new ImageIcon("src/hero/c/3.png");
        this.images[15] = new ImageIcon("src/hero/c/4.png");
        this.images[16] = new ImageIcon("src/hero/c/5.png");
        this.images[17] = new ImageIcon("src/hero/c/6.png");

        this.images[18] = new ImageIcon("src/hero/a/left.png");
        this.images[19] = new ImageIcon("src/hero/a/right.png");
        this.images[20] = new ImageIcon("src/hero/a/up.png");
        this.images[21] = new ImageIcon("src/hero/a/down.png");

        this.map = map;
        this.scale = scale;
        this.treasure = 0;
        this.state = "live";
        this.stateTmp = "live";
        this.mushroom = "ryu";
        this.selfPosition[0] = this.map.findMap('9')[0];
        this.selfPosition[1] = this.map.findMap('9')[1];
        this.walking = false;
    }

    public void draw(Graphics g, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(this.images[dir].getImage(),
                (this.selfPosition[1] * this.scale) + locationX + (padX * this.selfPosition[0]),
                (this.selfPosition[0] * this.scale) + locationY - (padY * this.selfPosition[0]) - 143 + 50, null);
    }

    public void walk(String dir) {
        if (!this.walking && !this.state.equals("dead")) {
            this.direction = dir;
            this.walking = true;
            Coder.frameA = 0;
            if (!this.state.equals("dead")) {
                this.tmpPosition[0] = this.selfPosition[0];
                this.tmpPosition[1] = this.selfPosition[1];
                this.nextPosition[0] = this.selfPosition[0];
                this.nextPosition[1] = this.selfPosition[1];
                if (dir.equals("left") && collision(dir)) {
                    this.tmpPosition[1]--;
                } else if (dir.equals("right") && collision(dir)) {
                    this.tmpPosition[1]++;
                } else if (dir.equals("up") && collision(dir)) {
                    this.tmpPosition[0]--;
                } else if (dir.equals("down") && collision(dir)) {
                    this.tmpPosition[0]++;
                } else {
                    System.out.println("*** Sysntax error ***");
                    if (checkNextStep(dir, '3')) {
                        if (this.mushroom.equals("chun-li")) {
                            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
                        } else {
                            this.stateTmp = "dead";
                        }
                    } else if (checkNextStep(dir, 'Q')) {
                        this.stateTmp = "dead";
                        this.map.setMap(this.selfPosition[0], this.selfPosition[1], '0');
                        this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                    }
                }
            } else {
                this.stateTmp = "dead";
                this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                Coder.soundMedia.playSoundSingle("media/dead.wav");
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
            this.nextPosition[1]--;
        } else if (dir.equals("right")) {
            if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
                bool = false;
            }
            this.nextPosition[1]++;
        } else if (dir.equals("up")) {
            if (this.map.checkMap(this.selfPosition[0] - 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0]--;
        } else if (dir.equals("down")) {
            if (this.map.checkMap(this.selfPosition[0] + 1, this.selfPosition[1]) != '0') {
                bool = false;
            }
            this.nextPosition[0]++;
        }
        checkStep(dir);
        return bool;
    }

    public String CheckErrorDir(String dir) {
        String checkerror = "error";
        if (dir.equals("left")) {
            checkerror = "no error";
            return checkerror;
        }

        else if (dir.equals("right")) {
            checkerror = "no error";
            return checkerror;

        }

        else if (dir.equals("up")) {
            checkerror = "no error";
            return checkerror;

        }

        else if (dir.equals("down")) {
            checkerror = "no error";
            return checkerror;
        }

        else {
            return checkerror;
        }
    }

    public void checkStep(String dir) {
        if (checkNextStep(dir, '8')) {
            this.stateTmp = "next";
            Coder.soundMedia.playSoundSingle("media/next.wav");
        } else if (checkNextStep(dir, '7')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.nextPosition[0] = this.map.findMap('6')[0];
            this.nextPosition[1] = this.map.findMap('6')[1];
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSoundSingle("media/portal.wav");
        } else if (checkNextStep(dir, '6')) {
            this.map.setMap(this.nextPosition[0], this.nextPosition[1], '0');
            this.nextPosition[0] = this.map.findMap('7')[0];
            this.nextPosition[1] = this.map.findMap('7')[1];
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSoundSingle("media/portal.wav");
        } else if (checkNextStep(dir, '5')) {
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSoundSingle("media/mushroom.wav");
            this.mushroom = "ken";
        } else if (checkNextStep(dir, 'A')) {
            this.tmpPosition[0] = this.nextPosition[0];
            this.tmpPosition[1] = this.nextPosition[1];
            this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
            this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
            Coder.soundMedia.playSoundSingle("media/mushroom.wav");
            this.mushroom = "chun-li";
        } else if (checkNextStep(dir, 'T')) {
            for (int i = 0; i < Coder.treasures.size(); i++) {
                if (Coder.treasures.get(i).checkNextStep(invDir(dir), '9')) {
                    this.tmpPosition[0] = this.nextPosition[0];
                    this.tmpPosition[1] = this.nextPosition[1];
                    this.map.setMap(this.tmpPosition[0], this.tmpPosition[1], '0');
                    this.map.setMap(this.selfPosition[0], this.selfPosition[1], '9');
                    Coder.soundMedia.playSoundSingle("media/treasure.wav");
                    this.treasure += 50;
                    Coder.treasures.remove(i);
                }
            }
        }
    }

    public String invDir(String dir) {
        String invDir = "";
        if (dir.equals("left")) {
            invDir = "right";
        } else if (dir.equals("right")) {
            invDir = "left";
        } else if (dir.equals("up")) {
            invDir = "down";
        } else if (dir.equals("down")) {
            invDir = "up";
        }
        return invDir;
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
                Coder.soundMedia.playSoundSingle("media/fire.wav");
                Coder.soundMedia.playSoundSingle("media/hit.wav");
            } else if (this.map.checkMap(this.selfPosition[0], this.selfPosition[1] + 1) != '0') {
                System.out.println("Have something front.");
            } else {
                this.map.setMap(this.selfPosition[0], this.selfPosition[1] + 1, '4');
                Coder.soundMedia.playSoundSingle("media/fire.wav");
            }
        } else if (Coder.attacking) {
            System.out.println("You are attacking");
        } else {
            System.out.println("You are not Ken");
        }
    }

    public void search(String dir) {
        Coder.creating = true;
        if (checkNextStep(dir, 'Q')) {
            System.out.println(dir);
            if (dir.equals("left")) {
                this.map.setMap(this.selfPosition[0], this.selfPosition[1] - 1, 'T');
            } else if (dir.equals("right")) {
                this.map.setMap(this.selfPosition[0], this.selfPosition[1] + 1, 'T');
            } else if (dir.equals("up")) {
                this.map.setMap(this.selfPosition[0] - 1, this.selfPosition[1], 'T');
            } else if (dir.equals("down")) {
                this.map.setMap(this.selfPosition[0] + 1, this.selfPosition[1], 'T');
            }
        } else {
            System.out.println("Not treasure right there");
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

    public int getTreasure() {
        return this.treasure;
    }

    public void setTreasure(int a) {
        this.treasure = a;
    }

    public boolean getWalking() {
        return this.walking;
    }

    public void setWalking(boolean a) {
        this.walking = a;
    }
}
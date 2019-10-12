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

class Player {
    private ImageIcon[] images;
    private int x, y, scale;
    public boolean toggleen = false;
    public int[] playerPosition = { 1, 1 };
    private int[] tmpPosition = { 0, 0 };
    private Map map;
    private String state;

    public Player(Map map, int scale) {
        images = new ImageIcon[2];
        images[0] = new ImageIcon("icon/player.png");
        images[1] = new ImageIcon("icon/player_2.png");
        this.map = map;
        this.map.setMap(playerPosition[0], playerPosition[1], '9');
        this.scale = scale;
        setX(getScale());
        setY(getScale());
        setState("alive");
    }

    public void draw(Graphics g, int dir, int locationX, int locationY, int padX, int padY) {
        g.drawImage(images[dir].getImage(), (playerPosition[1] * getScale()) + locationX + (padX * playerPosition[0]),
                (playerPosition[0] * getScale()) + locationY - (padY * playerPosition[0]) - 143 + 50, null);
        // g.drawImage(images[0].getImage(), getX(), getY(), null);
    }

    public void walk(String dir) {
        tmpPosition[0] = playerPosition[0];
        tmpPosition[1] = playerPosition[1];
        if (dir.equals("left") && collision(dir)) {
            playerPosition[1] -= 1;
            setX(getX() - getScale());
        } else if (dir.equals("right") && collision(dir)) {
            playerPosition[1] += 1;
            setX(getX() + getScale());
        } else if (dir.equals("up") && collision(dir)) {
            playerPosition[0] -= 1;
            setY(getY() - getScale());
        } else if (dir.equals("down") && collision(dir)) {
            playerPosition[0] += 1;
            setY(getY() + getScale());
        } else {
            System.out.println("*** Sysntax error ***");
            if (checkNextStep(dir, '2')) {
                setState("dead");
            }
        }
        if (!getState().equals("dead")) {
            this.map.setMap(tmpPosition[0], tmpPosition[1], '0');
            this.map.setMap(playerPosition[0], playerPosition[1], '9');
        } else {
            this.map.setMap(tmpPosition[0], tmpPosition[1], '0');
        }
    }

    public boolean collision(String dir) {
        boolean bool = true;
        if (dir.equals("left")) {
            if (this.map.cheMap(playerPosition[0], playerPosition[1] - 1) != '0') {
                bool = false;
            }
        } else if (dir.equals("right")) {
            if (this.map.cheMap(playerPosition[0], playerPosition[1] + 1) != '0') {
                bool = false;
            }
        } else if (dir.equals("up")) {
            if (this.map.cheMap(playerPosition[0] - 1, playerPosition[1]) != '0') {
                bool = false;
            }
        } else if (dir.equals("down")) {
            if (this.map.cheMap(playerPosition[0] + 1, playerPosition[1]) != '0') {
                bool = false;
            }
        }
        checkStep(dir);
        return bool;
    }

    public void checkStep(String dir) {
        if (checkNextStep(dir, '8')) {
            setState("next");
        }
        if (checkNextStep(dir, '7')) {
            this.map.setMap(tmpPosition[0], tmpPosition[1], '0');
            playerPosition[0] = map.findMap('6')[0];
            playerPosition[1] = map.findMap('6')[1];
            this.map.setMap(playerPosition[0], playerPosition[1], '9');
        }
    }

    public boolean checkNextStep(String dir, char a) {
        boolean bool = false;
        if (dir.equals("left")) {
            if (this.map.cheMap(playerPosition[0], playerPosition[1] - 1) == a) {
                bool = true;
            }
        } else if (dir.equals("right")) {
            if (this.map.cheMap(playerPosition[0], playerPosition[1] + 1) == a) {
                bool = true;
            }
        } else if (dir.equals("up")) {
            if (this.map.cheMap(playerPosition[0] - 1, playerPosition[1]) == a) {
                bool = true;
            }
        } else if (dir.equals("down")) {
            if (this.map.cheMap(playerPosition[0] + 1, playerPosition[1]) == a) {
                bool = true;
            }
        }
        return bool;
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

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
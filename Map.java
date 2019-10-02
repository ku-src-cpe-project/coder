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

class Map {
    private int row = 5;
    private int column = 9;
    private char map[][] = new char[column][row];
    private int[] currentPosition = { 0, 0 };
    private int speed_x = 90;
    private int speed_y = 68;// 143
    private int start_x = 200;
    private int start_y = 320;// 143

    public Map(JPanel panel) {
        this.map[0] = "1111111111".toCharArray();
        this.map[1] = "1002000001".toCharArray();
        this.map[2] = "1000002001".toCharArray();
        this.map[3] = "1000020001".toCharArray();
        this.map[4] = "1111111111".toCharArray();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                System.out.print(map[i][j]);
                if (this.map[i][j] == '2') {
                    Icon icon_bomb = new ImageIcon("icon/bomb.png");
                    JLabel label_bomb = new JLabel(icon_bomb);
                    label_bomb.setBounds(((j - 1) * this.speed_x) + this.start_x,
                            ((i - 1) * this.speed_y) + this.start_y, 109, 143);
                    panel.add(label_bomb);
                } else if (this.map[i][j] == '9') {
                    Icon icon_player = new ImageIcon("icon/player.png");
                    JLabel label_player = new JLabel(icon_player);
                    label_player.setBounds(((j - 1) * this.speed_x) + this.start_x,
                            ((i - 1) * this.speed_y) + this.start_y, 109, 143);
                    panel.add(label_player);
                }
            }
            System.out.print("\n");
        }
    }

    public char getMap(int x, int y) {
        return this.map[x][y];
    }

    public void setMap(int x, int y) {
        this.map[x][y] = '9';
    }

    public void set0(int x, int y) {
        this.map[x][y] = '0';
    }

    public int[] getPosition() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                if ('9' == getMap(i, j)) {
                    setPosition(i, j);
                }
            }
        }
        return this.currentPosition;
    }

    public void setPosition(int x, int y) {
        this.currentPosition[0] = x;
        this.currentPosition[1] = y;
    }

    public void echoMap() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                System.out.print(map[i][j]);
            }
            System.out.print("\n");
        }
    }

    public int getStartPositionX() {
        return this.start_x;
    }

    public int getStartPositionY() {
        return this.start_y;
    }

    public int getSpeedX() {
        return this.speed_x;
    }

    public int getSpeedY() {
        return this.speed_y;
    }
    public void Update(JPanel panel){
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                System.out.print(map[i][j]);
                if (this.map[i][j] == '2') {
                    Icon icon_bomb = new ImageIcon("icon/bomb.png");
                    JLabel label_bomb = new JLabel(icon_bomb);
                    label_bomb.setBounds(((j - 1) * this.speed_x) + this.start_x,
                            ((i - 1) * this.speed_y) + this.start_y, 109, 143);
                    panel.add(label_bomb);
                } else if (this.map[i][j] == '9') {
                    Icon icon_player = new ImageIcon("icon/player.png");
                    JLabel label_player = new JLabel(icon_player);
                    label_player.setBounds(((j - 1) * this.speed_x) + this.start_x,
                            ((i - 1) * this.speed_y) + this.start_y, 109, 143);
                    panel.add(label_player);
                }
            }
            System.out.print("\n");
        }
    }
}

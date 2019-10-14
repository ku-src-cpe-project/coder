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

    public Map(String level) {
        System.out.println("> Map Create");
        if (level.equals("0000")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0001")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000301".toCharArray();
            this.map[2] = "1003037381".toCharArray();
            this.map[3] = "1000030001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0002")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000001".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1000030081".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0003")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000001".toCharArray();
            this.map[3] = "1000703001".toCharArray();
            this.map[3] = "1000038001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0004")) {
            setRow(6);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003003381".toCharArray();
            this.map[2] = "1000703001".toCharArray();
            this.map[3] = "1330330001".toCharArray();
            this.map[4] = "1330007001".toCharArray();
            this.map[5] = "1111111111".toCharArray();
        } else if (level.equals("0005")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003003301".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1330330001".toCharArray();
            this.map[4] = "1330000301".toCharArray();
            this.map[5] = "1000003001".toCharArray();
            this.map[6] = "1800307001".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else if (level.equals("0006")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003003301".toCharArray();
            this.map[2] = "1000703001".toCharArray();
            this.map[3] = "1330330001".toCharArray();
            this.map[4] = "1330000301".toCharArray();
            this.map[5] = "1500003801".toCharArray();
            this.map[6] = "1000300001".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else if (level.equals("0007")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000073301".toCharArray();
            this.map[2] = "1500703001".toCharArray();
            this.map[3] = "1300330001".toCharArray();
            this.map[4] = "1330000301".toCharArray();
            this.map[5] = "1000003801".toCharArray();
            this.map[6] = "1006000061".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000800501".toCharArray();
            this.map[2] = "1000700001".toCharArray();
            this.map[3] = "1320000601".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        }
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int a) {
        this.row = a;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int a) {
        this.column = a;
    }

    public char cheMap(int x, int y) {
        return this.map[x][y];
    }

    public int[] getPosition() {
        int[] position = { 0, 0 };
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                if (cheMap(i, j) == '9') {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public char[][] getMap() {
        return this.map;
    }

    public void setMap(int x, int y, char set) {
        this.map[x][y] = set;
    }

    public int[] findMap(char a) {
        int[] position = { 0, 0 };
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                if (cheMap(i, j) == a) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }
}

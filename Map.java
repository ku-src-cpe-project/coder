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
            this.map[1] = "1002000201".toCharArray();
            this.map[2] = "1002020201".toCharArray();
            this.map[3] = "1000020001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0002")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1002000001".toCharArray();
            this.map[2] = "1000002001".toCharArray();
            this.map[3] = "1000020001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0003")) {
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1002000001".toCharArray();
            this.map[2] = "1000002001".toCharArray();
            this.map[3] = "1000020001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0004")) {
            setRow(6);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1002002201".toCharArray();
            this.map[2] = "1000002001".toCharArray();
            this.map[3] = "1220220001".toCharArray();
            this.map[4] = "1220000001".toCharArray();
            this.map[5] = "1111111111".toCharArray();
        } else {
            this.map[0] = "1111001111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
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
}

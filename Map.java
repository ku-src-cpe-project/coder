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

    /*
     * 1 = wall 
     * 2 = enemy 
     * 3 = bomb 
     * 4 = fire ball 
     * 5 = mushroom 
     * 6 = des. portal 
     * 7 = src. protal 
     * 8 = finish portal 
     * 9 = player
     */

    public Map(String level) {
        System.out.println("> Map Create");
        if (level.equals("0000")) {
            setRow(5);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0001")) {
            setRow(5);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000381".toCharArray();
            this.map[2] = "1003030301".toCharArray();
            this.map[3] = "1000030001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0002")) {
            setRow(5);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000001".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1700036081".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0003")) {
            setRow(5);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000001".toCharArray();
            this.map[3] = "1002003001".toCharArray();
            this.map[3] = "1000038001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
        } else if (level.equals("0004")) {
            setRow(6);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003703381".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1330330001".toCharArray();
            this.map[4] = "1330000061".toCharArray();
            this.map[5] = "1111111111".toCharArray();
        } else if (level.equals("0005")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003003301".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1330330001".toCharArray();
            this.map[4] = "1330000331".toCharArray();
            this.map[5] = "1000003081".toCharArray();
            this.map[6] = "1000300001".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else if (level.equals("0006")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000051".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1333333301".toCharArray();
            this.map[4] = "1000000301".toCharArray();
            this.map[5] = "1020030301".toCharArray();
            this.map[6] = "1800030001".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else if (level.equals("0007")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000073321".toCharArray();
            this.map[2] = "1000003001".toCharArray();
            this.map[3] = "1300330001".toCharArray();
            this.map[4] = "1330000301".toCharArray();
            this.map[5] = "1000003801".toCharArray();
            this.map[6] = "1000000061".toCharArray();
            this.map[7] = "1111111111".toCharArray();
        } else if (level.equals("0008")) {
            setRow(8);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000021".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1000050001".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1200000081".toCharArray();
            
            this.map[7] = "1111111111".toCharArray();
        } else {
            setRow(7);
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000302081".toCharArray();
            this.map[3] = "1A00008001".toCharArray();
            this.map[4] = "1500203001".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
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
        if (x > 0) {
            return this.map[x][y];
        } else {
            return this.map[0][0];
        }
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

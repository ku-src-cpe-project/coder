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

    // 1=wall 2=enemy 3=bomb
    // 4=fire ball 
    // 6=des.portal 7=src.protal
    // 8=finish portal 
    // 9=player
    // A=mush.Chun 5=mush.Ken

    public Map(JLabel hint, String level) {
        System.out.println("> Map Create");
        if (level.equals("0000")) {
            setRow(7);
            hint.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000003001".toCharArray();
            this.map[4] = "1000003001".toCharArray();
            this.map[5] = "1000003081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0001")) {
            setRow(7);
            hint.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003000381".toCharArray();
            this.map[2] = "1003030301".toCharArray();
            this.map[3] = "1003030301".toCharArray();
            this.map[4] = "1003030301".toCharArray();
            this.map[5] = "1000030001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0002")) {
            setRow(7);
            hint.setText("Escape from maze.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1030003001".toCharArray();
            this.map[2] = "1030303031".toCharArray();
            this.map[3] = "1000300031".toCharArray();
            this.map[4] = "1330303331".toCharArray();
            this.map[5] = "1000300081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0003")) {
            setRow(7);
            hint.setText("Find the portal");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000033301".toCharArray();
            this.map[2] = "1033000001".toCharArray();
            this.map[3] = "1000030371".toCharArray();
            this.map[4] = "1333333331".toCharArray();
            this.map[5] = "1600000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0004")) {
            setRow(7);
            hint.setText("How many you enter portal");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1037363071".toCharArray();
            this.map[2] = "1030300031".toCharArray();
            this.map[3] = "1000333331".toCharArray();
            this.map[4] = "1333363601".toCharArray();
            this.map[5] = "1700003081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0005")) {
            setRow(7);
            hint.setText("Jail break.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000300001".toCharArray();
            this.map[2] = "1003000001".toCharArray();
            this.map[3] = "1003000081".toCharArray();
            this.map[4] = "1033333001".toCharArray();
            this.map[5] = "10000A3001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0006")) {
            setRow(7);
            hint.setText("Destroy all bomb. then you will see goal.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000031".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000030001".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "13000000A1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0007")) {
            setRow(7);
            hint.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000071".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1333300001".toCharArray();
            this.map[4] = "1006300331".toCharArray();
            this.map[5] = "1A00300381".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0008")) {
            setRow(7);
            hint.setText("Where the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1000000201".toCharArray();
            this.map[5] = "1000000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0009")) {
            setRow(7);
            hint.setText("More the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000002001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1002000001".toCharArray();
            this.map[5] = "1000000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0010")) {
            setRow(7);
            hint.setText("Room of death.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000030081".toCharArray();
            this.map[2] = "1000030201".toCharArray();
            this.map[3] = "1000033331".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "10000000A1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0011")) {
            setRow(7);
            hint.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1030303031".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1303030301".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "1030383031".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0012")) {
            setRow(7);
            hint.setText("Kill the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000021".toCharArray();
            this.map[3] = "1500000001".toCharArray();
            this.map[4] = "1000000021".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0013")) {
            setRow(7);
            hint.setText("Kill the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "10000000A1".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000021".toCharArray();
            this.map[4] = "1000000331".toCharArray();
            this.map[5] = "1000000351".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0014")) {
            setRow(7);
            hint.setText("Kill the enemy then mushroom will append.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000333001".toCharArray();
            this.map[3] = "1020383501".toCharArray();
            this.map[4] = "1000333001".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (level.equals("0015")) {
            setRow(7);
            hint.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1038000301".toCharArray();
            this.map[2] = "1033333301".toCharArray();
            this.map[3] = "103A000001".toCharArray();
            this.map[4] = "1033333301".toCharArray();
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

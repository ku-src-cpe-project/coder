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
    private ImageIcon[] images;
    private ImageIcon tutorialBackground;
    private int row = 5;
    private int column = 9;
    private char map[][] = new char[column][row];
    private boolean smoke = false, puzzle = false, hintText = false;
    private String tutorialText;

    private int dummy;
    private String mapName;

    // 1=wall 2=enemy 3=bomb
    // 4=fire ball
    // 6=des.portal 7=src.protal
    // 8=finish portal
    // 9=player
    // A=mush.Chun 5=mush.Ken
    // D=dummy

    public Map(JLabel hintText, JLabel tutorialBackground, JLabel tutorialText, String mapName) {
        this.images = new ImageIcon[3];
        this.images[0] = new ImageIcon("icon/player.png");
        this.images[1] = new ImageIcon("icon/player_3.png");
        this.images[2] = new ImageIcon("icon/player_5.png");
        this.mapName = mapName;
        System.out.println("> Map Create");
        if (mapName.equals("0000")) { // walk(dire)
            setRow(7);
            hintText.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1030000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1030000001".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "1000000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setTutorial(true);
            setHintText("0000\nwalk(direct)");
            tutorialText.setText(getHintText());
            // R
            // R
            // R
            // R
            // R
            // R
            // R
            // R
            // D
            // D
            // D
            // D
        } else if (mapName.equals("0001")) { // test walk(dir)
            setRow(7);
            hintText.setText("Escape from maze.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1030003001".toCharArray();
            this.map[2] = "1000303031".toCharArray();
            this.map[3] = "1330300031".toCharArray();
            this.map[4] = "1330303331".toCharArray();
            this.map[5] = "1000300081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setTutorial(true);
            setHintText("0001\ntest walk(direct)");
            tutorialText.setText(getHintText());
            // D
            // D
            // R
            // R
            // U
            // U
            // R
            // R
            // D
            // D
            // D
            // D
            // R
            // R
            // R
        } else if (mapName.equals("0002")) { // attack(dir)
            setRow(7);
            hintText.setText("Escape from maze.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "15000000D1".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "10000000D1".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setSmoke(true);
            setPuzzle(true);
            setDummy(2);
            // D
            // A
            // A
            // D
            // R
            // R
            // R
            // R
            // R
            // R
            // R
        } else if (mapName.equals("0003")) { // while(cou) fix line
            setRow(7);
            hintText.setText("Find the portal");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000081".toCharArray();
            this.map[2] = "1000000801".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1000080001".toCharArray();
            this.map[5] = "1800000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            // W(4){
            // D
            // }
        } else if (mapName.equals("0004")) { // test while(cou) fix line
            setRow(7);
            hintText.setText("How many you enter portal");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1333333001".toCharArray();
            this.map[3] = "1000003001".toCharArray();
            this.map[4] = "1000003001".toCharArray();
            this.map[5] = "1000003081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            // W(7){
            // R
            // }
            // W(4){
            // D
            // }
        } else if (mapName.equals("0005")) { // check(dir)
            setRow(7);
            hintText.setText("Jail break.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1003333331".toCharArray();
            this.map[2] = "1003333331".toCharArray();
            this.map[3] = "1008003031".toCharArray();
            this.map[4] = "1333333331".toCharArray();
            this.map[5] = "1333333331".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // D
            // D
            // IF(C(D)){
            // R
            // R
            // }
        } else if (mapName.equals("0006")) { // if
            setRow(7);
            hintText.setText("Destroy all bomb. then you will see goal.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000333331".toCharArray();
            this.map[2] = "1330033331".toCharArray();
            this.map[3] = "1333003331".toCharArray();
            this.map[4] = "1333300331".toCharArray();
            this.map[5] = "1333330081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // W(11){
            // IF(C(R)){
            // D
            // }
            // IF(C(D)){
            // R
            // }
            // }
        } else if (mapName.equals("0007")) { // if else
            setRow(7);
            hintText.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1033333081".toCharArray();
            this.map[2] = "1033330031".toCharArray();
            this.map[3] = "1033300031".toCharArray();
            this.map[4] = "1033000331".toCharArray();
            this.map[5] = "1000003331".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // D
            // D
            // D
            // D
            // W(11){
            // IF(C(R)){
            // U
            // }
            // EL{
            // R
            // }
            // }
        } else if (mapName.equals("0008")) { // test if else
            setRow(7);
            hintText.setText("Where the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1000000201".toCharArray();
            this.map[5] = "1000000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0009")) { // mushroom attack
            setRow(7);
            hintText.setText("More the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000002001".toCharArray();
            this.map[3] = "1000000001".toCharArray();
            this.map[4] = "1002000001".toCharArray();
            this.map[5] = "1000000081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0010")) { // enemy
            setRow(7);
            hintText.setText("Room of death.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000030081".toCharArray();
            this.map[2] = "1000030201".toCharArray();
            this.map[3] = "1000033331".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "10000000A1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // ========================================================
            // World 1
            // ========================================================
        } else if (mapName.equals("0011")) {
            setRow(7);
            hintText.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1030303031".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1303030301".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "1030383031".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0012")) {
            setRow(7);
            hintText.setText("Kill the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000000021".toCharArray();
            this.map[3] = "1500000001".toCharArray();
            this.map[4] = "1000000021".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0013")) {
            setRow(7);
            hintText.setText("Kill the enemy.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "10000000A1".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1000000021".toCharArray();
            this.map[4] = "1000000331".toCharArray();
            this.map[5] = "1000000351".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0014")) {
            setRow(7);
            hintText.setText("Kill the enemy then mushroom will append.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1000333001".toCharArray();
            this.map[3] = "1020383501".toCharArray();
            this.map[4] = "1000333001".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapName.equals("0015")) {
            setRow(7);
            hintText.setText("Go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1038000301".toCharArray();
            this.map[2] = "1033333301".toCharArray();
            this.map[3] = "103A000001".toCharArray();
            this.map[4] = "1033333301".toCharArray();
            this.map[5] = "1000000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        }
    }

    public void update(boolean runable, ArrayList<String> lines) {
        if (mapName.equals("0002")) { // attack(dir)
            if (!getPuzzle()) {
                this.map[5][2] = '8';
            }
        }
        if (mapName.equals("0003")) { // while(cou) fix line
            if (runable) {
                // System.out.println(Coder.lines.size());
                if (getPuzzle() && ((lines.size() - 2) > 3)) {
                    runable = false;
                    setPuzzle(false);
                }
            }
        }
        if (mapName.equals("0004")) { // test while(cou) fix line
            if (runable) {
                // System.out.println(Coder.lines.size());
                if (getPuzzle() && ((lines.size() - 2) > 6)) {
                    runable = false;
                    setPuzzle(false);
                }
            }
        }
    }

    public void printMap() {
        for (int i = 0; i < this.row; i++) { // debug
            for (int j = 0; j <= this.column; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.print("\n");
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

    public boolean getSmoke() {
        return this.smoke;
    }

    public void setSmoke(boolean a) {
        this.smoke = a;
    }

    public boolean getTutorial() {
        return this.hintText;
    }

    public void setTutorial(boolean a) {
        this.hintText = a;
    }

    public String getHintText() {
        return this.tutorialText;
    }

    public void setHintText(String a) {
        this.tutorialText = a;
    }

    public ImageIcon getHint_pic() {
        return this.tutorialBackground;
    }

    public void setHint_pic(ImageIcon a) {
        this.tutorialBackground = a;
    }

    public int getDummy() {
        return this.dummy;
    }

    public void setDummy(int a) {
        this.dummy = a;
    }

    public boolean getPuzzle() {
        return this.puzzle;
    }

    public void setPuzzle(boolean a) {
        this.puzzle = a;
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

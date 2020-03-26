// Version 0.0.1

import javax.swing.*;
// import java.awt.*;
// import java.awt.Graphics;
// import java.awt.event.*;
// import java.applet.*;
// import java.util.*;
import java.util.ArrayList;
// import java.util.StringTokenizer;
// import java.io.*;

class Map {
    private ImageIcon[] images, tutorials;
    private int row = 5;
    private int column = 9;
    private char map[][] = new char[column][row];
    private boolean puzzle = false, objectiveText = false;
    private int countDummy, world;
    private String mapNow;
    private JLabel tutorialBackground;

    // 1=wall 2=enemy 3=bomb
    // 4=fire ball
    // 6=des.portal 7=src.protal
    // 8=finish portal
    // 9=player
    // A=mush.Chun 5=mush.Ken
    // D=dummy
    // Q=treasure box
    // T=treasure

    public Map(JTextArea objectiveText, JLabel tutorialBackground, String mapNow, ArrayList<Enemy> enemys) {
        this.images = new ImageIcon[6];
        this.images[0] = new ImageIcon("src/world/a/1.png");
        this.images[1] = new ImageIcon("src/world/a/2.png");
        this.images[2] = new ImageIcon("src/world/a/3.png");
        this.images[3] = new ImageIcon("src/world/a/4.png");
        this.images[4] = new ImageIcon("src/world/a/5.png");
        this.images[5] = new ImageIcon("src/world/a/6.png");
        this.tutorials = new ImageIcon[10];
        if (Coder.language.equals("TH")) {
            this.tutorials[0] = new ImageIcon("src/etc/tutorial/walk_th.png");
            this.tutorials[3] = new ImageIcon("src/etc/tutorial/attack_th.png");
            this.tutorials[4] = new ImageIcon("src/etc/tutorial/for_th.png");
            this.tutorials[6] = new ImageIcon("src/etc/tutorial/if_th.png");
            this.tutorials[8] = new ImageIcon("src/etc/tutorial/if_else_th.png");
            this.tutorials[9] = new ImageIcon("src/etc/tutorial/while_th.png");
        } else {
            this.tutorials[0] = new ImageIcon("src/etc/tutorial/walk.png");
            this.tutorials[3] = new ImageIcon("src/etc/tutorial/attack.png");
            this.tutorials[4] = new ImageIcon("src/etc/tutorial/for.png");
            this.tutorials[6] = new ImageIcon("src/etc/tutorial/if.png");
            this.tutorials[8] = new ImageIcon("src/etc/tutorial/if_else.png");
            this.tutorials[9] = new ImageIcon("src/etc/tutorial/while.png");
        }
        this.tutorialBackground = tutorialBackground;
        this.world = 0;
        this.mapNow = mapNow;
        System.out.println("> Map Create");
        if (mapNow.equals("0000")) {
            setRow(7);
            objectiveText.setText("Test.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1110000111".toCharArray();
            this.map[2] = "1150000D11".toCharArray();
            this.map[3] = "1190000011".toCharArray();
            this.map[4] = "1100000011".toCharArray();
            this.map[5] = "1110000111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            //
        }
        // ========================================================
        // World 0
        // ========================================================
        else if (mapNow.equals("0001")) { // walk(dir)
            setRow(7);
            objectiveText.setText("Easy walk go to portal blue.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111000031".toCharArray();
            this.map[2] = "1111011111".toCharArray();
            this.map[3] = "1900000081".toCharArray();
            this.map[4] = "1111111111".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setTutorial(true);
            setTutorialBackground(1);
            // Coder.input.setText(
            // "walk(right);\nwalk(right);\nwalk(right);\nwalk(right);\nwalk(right);\nwalk(right);\nwalk(right);");
        } else if (mapNow.equals("0002")) { // walk(dir)
            setRow(7);
            objectiveText.setText("Now. Try harder escape from maze.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1810001001".toCharArray();
            this.map[2] = "1000101011".toCharArray();
            this.map[3] = "1110100011".toCharArray();
            this.map[4] = "1110101111".toCharArray();
            this.map[5] = "1000100091".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // Coder.input.setText(
            // "walk(left);\nwalk(left);\nwalk(left);\nwalk(up);\nwalk(up);\nwalk(up);\nwalk(up);\nwalk(left);\nwalk(left);\nwalk(down);\nwalk(left);\nwalk(left);\nwalk(up);");
        } else if (mapNow.equals("0003")) { // walk(dir)
            setRow(7);
            objectiveText.setText("Escape from maze.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1011111011".toCharArray();
            this.map[3] = "1000190001".toCharArray();
            this.map[4] = "1110A11101".toCharArray();
            this.map[5] = "180000T151".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            // Coder.input.setText(
            // "walk(right);\nwalk(right);\nwalk(up);\nwalk(up);\nwalk(left);\nwalk(left);\nwalk(left);\nwalk(left);\nwalk(left);\nwalk(left);\nwalk(down);\nwalk(down);\nwalk(right);\nwalk(right);\nwalk(down);\nwalk(down);\nwalk(left);\nwalk(left);\n");
        } else if (mapNow.equals("0004")) { // attack()
            setRow(7);
            objectiveText.setText("Attack dummy 2 time.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1911111111".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "15000000D1".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            setCountDummy(2);
            setTutorial(true);
            setTutorialBackground(4);
            // Coder.input.setText(
            // "walk(down);\nwalk(down);\nattack();\nattack();\nwalk(right);\nwalk(right);\nwalk(right);\n");
        } else if (mapNow.equals("0005")) { // for
            setRow(7);
            objectiveText.setText("Try write code under 7 line.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1901111111".toCharArray();
            this.map[2] = "1100111111".toCharArray();
            this.map[3] = "1110011111".toCharArray();
            this.map[4] = "1111001111".toCharArray();
            this.map[5] = "1111100081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            // For(4){
            // W(R)
            // W(D)
            // }
            // For(3){
            // W(R)
            // }

        } else if (mapNow.equals("0006")) { // for
            setRow(7);
            objectiveText.setText("Try write code under 3 line.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111111111".toCharArray();
            this.map[2] = "1111111111".toCharArray();
            this.map[3] = "1800000091".toCharArray();
            this.map[4] = "1111111111".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            setTutorial(true);
            setTutorialBackground(11);
            // For(7){
            // W(L)
            // }
        } else if (mapNow.equals("0007")) { // if
            setRow(7);
            objectiveText.setText("Get a treasure after you search treasure box.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111111111".toCharArray();
            this.map[2] = "1111111111".toCharArray();
            this.map[3] = "190QQQ0001".toCharArray();
            this.map[4] = "1111111111".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            setTutorial(true);
            setTutorialBackground(8);
            // Coder.input.setText(
            // "while(check(down)){\n if(check(right)=treasure_box){\n search(right);\n
            // walk(right);\n }\n else{\n walk(right);\n }\n}\n");
        } else if (mapNow.equals("0008")) { // if
            setRow(7);
            objectiveText.setText("Get a treasure after you search treasure box.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111111111".toCharArray();
            this.map[2] = "1111111111".toCharArray();
            this.map[3] = "1111111001".toCharArray();
            this.map[4] = "1111000Q11".toCharArray();
            this.map[5] = "1900Q11111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
        } else if (mapNow.equals("0009")) { // if else
            setRow(7);
            objectiveText.setText("Choose path you should go.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1911111111".toCharArray();
            this.map[2] = "1011111111".toCharArray();
            this.map[3] = "1000000081".toCharArray();
            this.map[4] = "1011111111".toCharArray();
            this.map[5] = "1311111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setTutorial(true);
            setTutorialBackground(10);
            // While(D){
            // IF(R){
            // W(R)
            // }
            // ELSE{
            // W(D)
            // }
            // }

        } else if (mapNow.equals("0010")) { // while(check(dir))
            setRow(7);
            objectiveText.setText("Try write code under 4 line.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111110811".toCharArray();
            this.map[2] = "1111100111".toCharArray();
            this.map[3] = "1111001111".toCharArray();
            this.map[4] = "1110011111".toCharArray();
            this.map[5] = "1190111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            setTutorial(true);
            setTutorialBackground(5);
            // While(R){
            // W(R)
            // W(U)
            // }
            // Coder.input.setText("while(check(right)){\n walk(right);\n
            // walk(up);\n}\nwalk(right);\n");
        } else if (mapNow.equals("0011")) { // while(check(dir))
            setRow(7);
            objectiveText.setText("Try write code under 10 line.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111111111".toCharArray();
            this.map[2] = "1801000191".toCharArray();
            this.map[3] = "1101010101".toCharArray();
            this.map[4] = "1100010001".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            // While(U){
            // W(D)
            // W(D)
            // W(L)
            // W(L)
            // W(U)
            // W(U)
            // W(L)
            // W(L)
            // }
            // Coder.input.setText(
            // "while(check(down)){\n walk(down);\n walk(down);\n walk(left);\n
            // walk(left);\n walk(up);\n walk(up);\n walk(left);\n walk(left);\n}\n");
        } else if (mapNow.equals("0012")) { // while(check(dir))
            setRow(7);
            objectiveText.setText("Try write code under 7 line.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111110091".toCharArray();
            this.map[2] = "1131000111".toCharArray();
            this.map[3] = "1300011111".toCharArray();
            this.map[4] = "1118111111".toCharArray();
            this.map[5] = "1111111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
            // While(L){
            // W(L)
            // W(L)
            // W(D)
            // }
            // W(R)
            // W(D)
            // Coder.input.setText(
            // "while(check(left)){\n walk(left);\n walk(left);\n
            // walk(down);\n}\nwalk(right);\nwalk(down);\n");
        }
        // ========================================================
        // World 1
        // ========================================================
        else if (mapNow.equals("0013")) {
            setWorld(getWorld() + 1);
            setRow(7);
            objectiveText.setText("How many line you can pass this stage.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1100010001".toCharArray();
            this.map[2] = "1101010101".toCharArray();
            this.map[3] = "1101010101".toCharArray();
            this.map[4] = "1101010101".toCharArray();
            this.map[5] = "1803000391".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0014")) {
            setRow(7);
            objectiveText.setText("Destroy bomb to pass stage.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000038391".toCharArray();
            this.map[2] = "1011111101".toCharArray();
            this.map[3] = "1012010001".toCharArray();
            this.map[4] = "1011110101".toCharArray();
            this.map[5] = "1A00000151".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0015")) {
            setRow(7);
            objectiveText.setText("Destroy bomb to pass stage.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1803000001".toCharArray();
            this.map[2] = "1003A11101".toCharArray();
            this.map[3] = "1331111101".toCharArray();
            this.map[4] = "10A1111101".toCharArray();
            this.map[5] = "1000000091".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0016")) {
            setRow(7);
            objectiveText.setText("Destroy bomb to pass stage.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1000000001".toCharArray();
            this.map[2] = "1011111301".toCharArray();
            this.map[3] = "1930038101".toCharArray();
            this.map[4] = "1A10111101".toCharArray();
            this.map[5] = "1150000001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0017")) {
            setRow(7);
            objectiveText.setText("Find way to go blue portal.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1160000091".toCharArray();
            this.map[2] = "1111111111".toCharArray();
            this.map[3] = "1600000081".toCharArray();
            this.map[4] = "1111111111".toCharArray();
            this.map[5] = "1607117071".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0018")) {
            setRow(7);
            objectiveText.setText("Destroy the enemy if you can.");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1101110011".toCharArray();
            this.map[2] = "1000000001".toCharArray();
            this.map[3] = "1900000281".toCharArray();
            this.map[4] = "1000000001".toCharArray();
            this.map[5] = "1151000011".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        }
        // ========================================================
        // World 2
        // ========================================================
        else if (mapNow.equals("0019")) {
            setWorld(getWorld() + 1);
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1100D11171".toCharArray();
            this.map[2] = "1110111001".toCharArray();
            this.map[3] = "1110000011".toCharArray();
            this.map[4] = "1110011911".toCharArray();
            this.map[5] = "1600111111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
        } else if (mapNow.equals("0020")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1910011111".toCharArray();
            this.map[2] = "10000000D1".toCharArray();
            this.map[3] = "1011011011".toCharArray();
            this.map[4] = "1011011011".toCharArray();
            this.map[5] = "10000000D1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0021")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1100819001".toCharArray();
            this.map[2] = "1131110111".toCharArray();
            this.map[3] = "1001110111".toCharArray();
            this.map[4] = "1A00000011".toCharArray();
            this.map[5] = "1111100511".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0022")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1180000111".toCharArray();
            this.map[2] = "1111110071".toCharArray();
            this.map[3] = "1111611111".toCharArray();
            this.map[4] = "1111000Q11".toCharArray();
            this.map[5] = "1111911111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0023")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1811111111".toCharArray();
            this.map[2] = "1011111111".toCharArray();
            this.map[3] = "1Q00000091".toCharArray();
            this.map[4] = "10010A0051".toCharArray();
            this.map[5] = "1111113111".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0024")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1100000031".toCharArray();
            this.map[2] = "1900011101".toCharArray();
            this.map[3] = "1011111821".toCharArray();
            this.map[4] = "1A00020101".toCharArray();
            this.map[5] = "1111110001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        }
        // ========================================================
        // World 3
        // ========================================================
        else if (mapNow.equals("0025")) {
            setWorld(getWorld() + 1);
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111111111".toCharArray();
            this.map[2] = "11Q0000011".toCharArray();
            this.map[3] = "1111011011".toCharArray();
            this.map[4] = "1D0000A001".toCharArray();
            this.map[5] = "1111191101".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0026")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1901111161".toCharArray();
            this.map[2] = "1100111001".toCharArray();
            this.map[3] = "1110010011".toCharArray();
            this.map[4] = "11Q1000111".toCharArray();
            this.map[5] = "1701111081".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
        } else if (mapNow.equals("0027")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1101191111".toCharArray();
            this.map[2] = "11Q0100051".toCharArray();
            this.map[3] = "1110301001".toCharArray();
            this.map[4] = "1111010101".toCharArray();
            this.map[5] = "111000D1A1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
            setPuzzle(true);
        } else if (mapNow.equals("0028")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "170Q111161".toCharArray();
            this.map[2] = "1101000001".toCharArray();
            this.map[3] = "1501191111".toCharArray();
            this.map[4] = "1110000001".toCharArray();
            this.map[5] = "11103111A1".toCharArray();
            this.map[6] = "1111811111".toCharArray();
        } else if (mapNow.equals("0029")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1806111901".toCharArray();
            this.map[2] = "1111200101".toCharArray();
            this.map[3] = "1111100001".toCharArray();
            this.map[4] = "1113000101".toCharArray();
            this.map[5] = "17001111A1".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        } else if (mapNow.equals("0030")) {
            setRow(7);
            objectiveText.setText(".");
            this.map[0] = "1111111111".toCharArray();
            this.map[1] = "1111570001".toCharArray();
            this.map[2] = "1901000001".toCharArray();
            this.map[3] = "1011000021".toCharArray();
            this.map[4] = "1010110001".toCharArray();
            this.map[5] = "1601100001".toCharArray();
            this.map[6] = "1111111111".toCharArray();
        }
    }

    public void update(Player player, ArrayList<Enemy> enemys, ArrayList<Portal> portal8s, int scale, int locationX,
            int locationY, int padX, int padY) {
        // ========================================================
        // Example Setting Map Puzzle
        // ========================================================
        // set if puzzle clear with anything condition
        if (mapNow.equals("9999")) {
            if (!getPuzzle()) {
                int row = 3;
                int col = 4;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        // count line
        if (mapNow.equals("9999")) {
            int line = 3;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        // ========================================================
        //
        // ========================================================
        if (mapNow.equals("0004")) {
            if (getCountDummy() == 0) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 3;
                int col = 4;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0005")) {
            int line = 7;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        if (mapNow.equals("0006")) {
            int line = 3;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        if (mapNow.equals("0007")) {
            int treasure = 100;
            if (player.getTreasure() > treasure - 1) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 3;
                int col = 8;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0008")) {
            int treasure = 150;
            if (player.getTreasure() > treasure - 1) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 3;
                int col = 8;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0010")) {
            int line = 4;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        if (mapNow.equals("0011")) {
            int line = 10;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        if (mapNow.equals("0012")) {
            int line = 7;
            if (Coder.runable) {
                if (getPuzzle() && ((Coder.lines.size() - 2) > line)) {
                    Coder.runable = false;
                    setPuzzle(false);
                }
            }
        }
        // ========================================================
        // World 2
        // ========================================================
        if (mapNow.equals("0019")) {
            if (getCountDummy() == 0) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 7;
                int col = 4;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0020")) {
            if (getCountDummy() == 0) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 1;
                int col = 1;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        // ========================================================
        // World 3
        // ========================================================
        if (mapNow.equals("0025")) {
            int treasure = 150;
            if (player.getTreasure() > treasure - 1) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 5;
                int col = 8;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0026")) {
            int treasure = 50;
            if (player.getTreasure() > treasure - 1) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 5;
                int col = 3;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
        }
        if (mapNow.equals("0027")) {
            if (getCountDummy() == 0) {
                setPuzzle(false);
            }
            if (!getPuzzle()) {
                int row = 1;
                int col = 2;
                this.map[row][col] = '8';
                Portal portal = new Portal((col * scale) + locationX + (padX * row),
                        (row * scale) + locationY - (padY * row) - 143 + 50, row, col);
                portal8s.add(portal);
            }
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

    public boolean getTutorial() {
        return this.objectiveText;
    }

    public void setTutorial(boolean a) {
        this.objectiveText = a;
    }

    public int getCountDummy() {
        return this.countDummy;
    }

    public void setCountDummy(int a) {
        this.countDummy = a;
    }

    public boolean getPuzzle() {
        return this.puzzle;
    }

    public void setPuzzle(boolean a) {
        this.puzzle = a;
    }

    public void setTutorialBackground(int a) {
        this.tutorialBackground.setIcon(this.tutorials[a - 1]);
    }

    public char checkMap(int x, int y) {
        if (x > 0) {
            return this.map[x][y];
        } else {
            return this.map[0][0];
        }
    }

    public char[][] getMap() {
        return this.map;
    }

    public void setMap(int x, int y, char set) {
        try {
            this.map[x][y] = set;
        } catch (Exception e) {
            System.out.println("Error: function setMap()");
        }
    }

    public int[] getPosition() {
        int[] position = { 0, 0 };
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                if (checkMap(i, j) == '9') {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public int[] findMap(char a) {
        int[] position = { 0, 0 };
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j <= this.column; j++) {
                if (checkMap(i, j) == a) {
                    position[0] = i;
                    position[1] = j;
                }
            }
        }
        return position;
    }

    public void printMap() {
        for (int i = 0; i < this.row; i++) { // debug
            for (int j = 0; j <= this.column; j++) {
                if (this.map[i][j] != '0' && this.map[i][j] != '1') {
                    System.out.print(this.map[i][j] + " ");
                } else if (this.map[i][j] == '1') {
                    System.out.print("+ ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public ImageIcon getWorldImage() {
        return this.images[world];
    }

    public int getWorld() {
        return this.world;
    }

    public void setWorld(int a) {
        this.world = a;
    }
}

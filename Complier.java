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
import java.awt.List;
// Timer
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

class Complier {
    private ArrayList<String> parses, tokens, lines;
    private int pointer;
    private int pointerWhile;
    private int loopWhile;
    private Stack process;

    public Complier() {
        this.pointer = 0;
    }

    public ArrayList<String> tokenToLines(ArrayList<String> tokens) {
        this.lines = new ArrayList<String>();
        String tmp = "";
        this.lines.add("START");
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(";")) {
                this.lines.add(tmp);
                tmp = "";
            } else if (tokens.get(i).equals("while")) {
                tmp = tmp.concat(tokens.get(i) + "");
                tmp = tmp.concat(tokens.get(i + 1) + "");
                tmp = tmp.concat(tokens.get(i + 2) + "");
                tmp = tmp.concat(tokens.get(i + 3) + "");
                tmp = tmp.concat(tokens.get(i + 4) + "");
                this.lines.add(tmp);
                tmp = "";
                i += 4;
            } else if (tokens.get(i).equals("}")) {
                this.lines.add("}");
            } else if (!parses.get(i).equals("\n")) {
                tmp = tmp.concat(tokens.get(i) + "");
            }
        }
        this.lines.add("END");
        // System.out.println(this.lines);
        return this.lines;
    }

    public ArrayList<String> textToParses(String text) {
        this.parses = new ArrayList<String>();
        for (int i = 0; i < text.length(); i++) {
            this.parses.add(text.charAt(i) + "");
        }
        // System.out.println("" + this.parses);
        return this.parses;
    }

    public ArrayList<String> parseToTokens(ArrayList<String> parses) {
        this.tokens = new ArrayList<String>();
        String tmp = "";
        for (int i = 0; i < parses.size(); i++) {
            if (checkOperater(parses.get(i)) && !parses.get(i).equals(";")) {
                if (parses.get(i).equals("{")) {
                    this.tokens.add("{");
                } else if (parses.get(i).equals("}")) {
                    this.tokens.add("}");
                } else {
                    // System.out.println(parses.get(i) + " is Operater");
                    this.tokens.add(tmp);
                    tmp = "";
                    this.tokens.add(parses.get(i) + "");
                }
            } else if (checkOperater(parses.get(i)) && parses.get(i).equals(";")) {
                this.tokens.add(";");
            } else if (!parses.get(i).equals("\n")) {
                // System.out.println(parses.get(i) + " is Not Operater");
                tmp = tmp.concat(parses.get(i) + "");
                // System.out.println(tmp);
            }
        }
        // System.out.println("" + this.tokens);
        return this.tokens;
    }

    public boolean checkOperater(String parse) {
        if (parse.equals("(") || parse.equals(")") || parse.equals("{") || parse.equals("}") || parse.equals(";")) {
            return true;
        } else {
            return false;
        }
    }

    // ========================================================
    // Runable
    // ========================================================

    public void checkMethod(Player player, ArrayList<String> token) {
        for (int i = 0; i < token.size(); i++) {
            if (token.get(i).equals("walk")) {
                player.walk(token.get(i + 2));
                // i += 3;
            } else if (token.get(i).equals("while")) {
                setPointerWhile(getPointer());
                setLoopWhile(Integer.parseInt(token.get(i + 2)));
                // System.out.println(getLoopWhile());
            } else if (token.get(i).equals("check")) {
                player.collision(token.get(i + 2));
                System.out.println(player.collision(token.get(i + 2)));
            } else if (token.get(i).equals("}")) {
                if (getLoopWhile() > 0) {
                    setPointer(getPointerWhile());
                    actLoopWhile();
                }
            } else {
                // System.out.println("*** Nothing happen ***");
            }
        }
    }

    public void readLine(Player player, String token) {
        // System.out.println(token);
        ArrayList<String> parses = textToParses(token);
        ArrayList<String> tokens = parseToTokens(parses);
        checkMethod(player, tokens);
    }

    public void Runable(Player player, ArrayList<String> lines) {
        readLine(player, lines.get(getPointer()));
        actPointer();
    }

    public void actPointer() {
        this.pointer += 1;
    }

    public int endPointer(int sizeLine) {
        return sizeLine - 1;
    }

    public int getPointer() {
        return this.pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getPointerWhile() {
        return this.pointerWhile;
    }

    public void setPointerWhile(int pointer) {
        this.pointerWhile = pointer;
    }

    public int getLoopWhile() {
        return this.loopWhile;
    }

    public void setLoopWhile(int loop) {
        this.loopWhile = loop - 1;
    }

    public void actLoopWhile() {
        this.loopWhile -= 1;
    }
}

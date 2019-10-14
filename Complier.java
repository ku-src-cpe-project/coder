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
    private ArrayList<Integer> positionWhile;
    private int pointer;
    private int pointerWhile;
    private int count, countState;
    private ArrayList<String> process;
    private boolean expression, _if;
    private String state;

    public Complier() {
        this.process = new ArrayList<String>();
        this.pointer = 0;
        this.expression = true;
        this._if = false;
        this.state = "null";
    }

    public ArrayList<String> tokenToLines(ArrayList<String> tokens) {
        this.lines = new ArrayList<String>();
        this.positionWhile = new ArrayList<Integer>();
        String tmp = "";
        this.lines.add("START");
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(";")) {
                this.lines.add(tmp);
                tmp = "";
            } else if (tokens.get(i).equals("while")) {
                tmp = tmp.concat(tokens.get(i) + ""); // while
                tmp = tmp.concat(tokens.get(i + 1) + ""); // (
                tmp = tmp.concat(tokens.get(i + 2) + ""); // number
                tmp = tmp.concat(tokens.get(i + 3) + ""); // )
                tmp = tmp.concat(tokens.get(i + 4) + ""); // {
                this.lines.add(tmp);
                tmp = "";
                i += 4;
            } else if (tokens.get(i).equals("if")) {
                tmp = tmp.concat(tokens.get(i) + ""); // if
                tmp = tmp.concat(tokens.get(i + 1) + ""); // (
                tmp = tmp.concat(tokens.get(i + 2) + ""); // check
                tmp = tmp.concat(tokens.get(i + 3) + ""); // (
                tmp = tmp.concat(tokens.get(i + 4) + ""); // right
                tmp = tmp.concat(tokens.get(i + 5) + ""); // )
                tmp = tmp.concat(tokens.get(i + 6) + ""); // )
                tmp = tmp.concat(tokens.get(i + 7) + ""); // {
                this.lines.add(tmp);
                tmp = "";
                i += 7;
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
                    if (tmp != "") {
                        this.tokens.add(tmp);
                    }
                    tmp = "";
                    this.tokens.add(parses.get(i) + "");
                }
            } else if (checkOperater(parses.get(i)) && parses.get(i).equals(";")) {
                this.tokens.add(";");
            } else {
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

    public void checkMethod(Player player, ArrayList<String> token) { // WALK, (, RIGHT, )
        boolean condition = false;
        for (int i = 0; i < token.size(); i++) {
            if (token.get(i).equals("walk")) {
                player.walk(token.get(i + 2));
            } else if (token.get(i).equals("check")) {
                if (player.collision(token.get(i + 2))) {
                    this.expression = true;
                    this._if = false;
                } else {
                    this.expression = false;
                }
            } else if (token.get(i).equals("while")) {
                // old while
                // setPointerWhile(this.pointer);
                // setLoopWhile(Integer.parseInt(token.get(i + 2)) - 1);
                // new while
                condition = true;
                this.positionWhile.add(this.pointer);
            } else if (token.get(i).equals("{")) {
                this.countState++;
            } else if (token.get(i).equals("}")) {
                // old while
                // if (getLoopWhile() > 0) {
                // this.pointer = getPointerWhile();
                // setLoopWhile(getLoopWhile() - 1);
                // }
                // new while
                // if (getIf() && !getState().equals("while")) {
                // setCountState(getCountState() - 1);
                // if (getCountState() == 0) {
                // setExp(true);
                // }
                // } else {
                String Y = "";
                int y = 0;
                // if (this.process.size() > 1) {
                Y = this.process.get(this.process.size() - 2);
                y = Integer.parseInt(Y.charAt(6) + "");
                y -= 1;
                // }
                if (y != 0) {
                    this.pointer = this.positionWhile.get(this.positionWhile.size() - 1);
                    // this.pointer = this.pointer - (getCount() + 1);
                    String tmp = Y.substring(0, 6) + (y + "") + Y.substring(7, 9);
                    this.process.set(this.process.size() - 2, tmp);
                    this.state = "while";
                } else {
                    popPosWhile();
                    popStack();
                    this._if = true;
                    this.state = "null";
                }
                // }
                this.count = -1;
            }
            // else if (token.get(i).equals("if")) {
            // setIf(true);
            // }
            else {
                // System.out.println("*** Nothing happen ***");
            }
        }
        if (condition) {
            this.count = 0;
        } else {
            popStack();
            this.count++;
        }
    }

    public void readLine(Player player, String token) {
        ArrayList<String> parses = textToParses(token);
        ArrayList<String> tokens = parseToTokens(parses);
        checkMethod(player, tokens);
    }

    public void readStack(Player player, String process) {
        ArrayList<String> parses = textToParses(process);
        ArrayList<String> tokens = parseToTokens(parses);
        checkMethod(player, tokens);
    }

    public void Runable(Player player, ArrayList<String> lines) {
        // old readable
        // readLine(player, lines.get(this.pointer));
        // this.pointer = this.pointer + 1;
        // new readable
        pushStack(getLines().get(this.pointer));
        readStack(player, peekStack());
        this.pointer = this.pointer + 1;

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

    public ArrayList<Integer> getPosWhile() {
        return this.positionWhile;
    }

    public void popPosWhile() {
        if (this.positionWhile.size() > 0) {
            this.positionWhile.remove(this.positionWhile.size() - 1);
        }
    }

    public int peekPosWhile() {
        return this.positionWhile.get(this.positionWhile.size() - 1);
    }

    public ArrayList<String> getStack() {
        return this.process;
    }

    public void pushStack(String process) {
        this.process.add(process);
    }

    public void popStack() {
        if (this.process.size() > 0) {
            this.process.remove(this.process.size() - 1);
        }
    }

    public String peekStack() {
        return this.process.get(this.process.size() - 1);
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<String> getLines() {
        return this.lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public boolean getExp() {
        return this.expression;
    }

    public void setExp(boolean exp) {
        this.expression = exp;
    }

    public int getCountState() {
        return this.countState;
    }

    public void setCountState(int a) {
        this.countState = a;
    }

    public boolean getIf() {
        return this._if;
    }

    public void setIf(boolean a) {
        this._if = a;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String a) {
        this.state = a;
    }
}

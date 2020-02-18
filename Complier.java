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
    private ArrayList<String> parses, tokens, lines, controller;
    private ArrayList<Integer> positionWhile;
    private int pointer;
    private int pointerWhile, count_if, position_else;
    private int count, countState, count_braketOP, count_braketCL, find_braketOP_else, find_braketCL_else,
            count_braketCL_else, find_braketCL_while;
    private ArrayList<String> process, find_braketOP, find_braketCL;
    private boolean expression, _if, conditionofif, foundif, foundelse, conditionwhile, foundwhile;
    private String state, checkif, statusif, statuselse, check_braket, check_braket_else, str, m, n, check_else,
            check_if_out, check_braket2, check_token;

    public Complier() {
        this.process = new ArrayList<String>();
        this.controller = new ArrayList<String>();
        this.controller.add("x");
        this.controller.add("x");
        this.find_braketOP = new ArrayList<String>();
        this.find_braketCL = new ArrayList<String>();
        this.pointer = 0;
        this.count_braketOP = 0;
        this.count_if = 0;
        this.count_braketCL = 0;
        this.count_braketCL_else = 0;
        this.find_braketOP_else = 0;
        this.find_braketCL_else = 0;
        this.position_else = 0;
        this.expression = true;
        this._if = false;
        this.conditionofif = true;
        this.foundif = false;
        this.foundelse = false;

        this.check_if_out = "out";
        this.check_token = "x";

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
            } else if (tokens.get(i).equals("while")) { // while(check(right)){
                tmp = tmp.concat(tokens.get(i) + ""); // while
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
            } else if (tokens.get(i).equals("if")) { // if(check(right)){
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
            } else if (tokens.get(i).equals("else")) { // else(){
                tmp = tmp.concat(tokens.get(i) + ""); // else
                tmp = tmp.concat(tokens.get(i + 1) + ""); // {
                this.lines.add(tmp);
                tmp = "";
                i += 1;
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
        int count_else = 0;
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
            }

            else if (checkOperater(parses.get(i)) && parses.get(i).equals(";")) {
                this.tokens.add(";");
            } else {
                // System.out.println(parses.get(i) + " is Not Operater");
                if (parses.get(i).equals("e") && (parses.get(i + 1).equals("l")) && (parses.get(i + 2).equals("s"))
                        && (parses.get(i + 3).equals("e"))) {
                    i += 3;

                    this.tokens.add("else");
                } else {
                    tmp = tmp.concat(parses.get(i) + "");
                }
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

    public void checkMethod(Player player, ArrayList<String> token) { // WHILE, (, CHECK, (, DOWN, ), ), {
        boolean condition = false;
        for (int i = 0; i < token.size(); i++) {
            if ((this.foundelse == true) && (this.controller.get(this.position_else).equals("F"))) // found else and
                                                                                                   // condition if ==
                                                                                                   // False

            {
                // System.out.println("elsccccccc");
                // System.out.println("checcheckkkkk" + " " +
                // this.controller.get(this.position_else));
                this.count_braketCL_else = this.find_braketOP_else;
                this.n = Integer.toString(this.count_braketCL_else);
                if (token.get(i).equals("{")) {
                    // System.out.println("found { in else F");
                    this.find_braketOP_else += 1;
                    popStack();
                    this.count++;
                    break;

                }

                else if (token.get(i).equals("}")) {
                    this.check_braket_else = "{" + "else" + n;
                    // System.out.println("else F");
                    // System.out.println(this.check_braket_else);
                    if (this.check_braket_else.equals(this.statuselse)) {
                        // System.out.println("found braket close for else");
                        this.checkif = "0";
                        this.foundelse = false;
                        this.position_else -= 1;

                    } else {
                        this.find_braketOP_else -= 1;

                    }

                } else {
                    this.count++;
                }

            } else if ((this.foundelse == true) && (this.controller.get(this.position_else).equals("T"))) {

                // System.out.println("cccccccccc" + " " +
                // this.controller.get(this.position_else));
                // System.out.println(this.position_else);
                this.count_braketCL_else = this.find_braketOP_else;
                this.n = Integer.toString(this.count_braketCL_else);
                if (token.get(i).equals("{")) {
                    // System.out.println("else T");
                    this.find_braketOP_else += 1;
                    popStack();
                    this.count++;
                    break;

                }
                if (token.get(i).equals("}")) { // skip code in case else
                    this.check_braket_else = "{" + "else" + n;
                    // System.out.println("else F");
                    // System.out.println(this.check_braket_else);

                    if (this.check_braket_else.equals(this.statuselse)) {
                        // System.out.println("found braket close for else");
                        this.checkif = "0";
                        this.foundelse = false;
                        this.position_else -= 1;
                        popStack();
                        this.count++;
                        break;

                    } else {
                        this.find_braketOP_else -= 1;
                        popStack();
                        this.count++;
                        break;

                    }
                } else {
                    popStack();
                    this.count++;
                    break;
                }
            } else if ((this.foundif)) // found IF
            {
                this.count_braketCL = this.count_braketOP;
                // System.out.println("check bbb");
                // System.out.println(this.count_braketOP);
                // System.out.println(this.count_braketCL);
                this.m = Integer.toString(this.count_braketCL);
                // System.out.println(this.m);
                // System.out.println(this.str);
                // System.out.println(this.m.equals(this.str) );
                if (this.controller.get(this.position_else).equals("T")) // condition true for check out or in {}
                {

                    if (token.get(i).equals("{")) {
                        // System.out.println("if true {");
                        this.count_braketOP += 1;

                    }

                    if (token.get(i).equals("}")) {

                        this.check_braket2 = "{" + m + "&";

                        // System.out.println("found }");
                        // System.out.println( this.check_braket);
                        // System.out.println( this.statusif);
                        // System.out.println(this.check_braket.equals(this.statusif));
                        if (this.check_braket2.equals(this.check_if_out)) {

                            // System.out.println("bra == bra(if true)" + this.position_else);
                            // System.out.println(this.controller.get(this.position_else));
                            // System.out.println("setcon check_if_out = out");
                            this.check_if_out = "out";
                            this.foundif = false;

                            popStack();
                            this.count++;
                            break;
                        } else {
                            this.count_braketOP -= 1;
                            popStack();
                            this.count++;
                            break;
                        }
                    }

                }
                if (this.controller.get(this.position_else).equals("F")) // condition false (for skip code)
                {
                    // System.out.println("get token" + token.get(i));
                    if (token.get(i).equals("if")) {
                        this.check_token = token.get(i + 7);
                        // System.out.println("token+6=" + check_token);
                    }

                    if (this.check_token.equals("{")) {
                        // System.out.println("check array true" + this.position_else);
                        this.count_braketOP += 1;
                        popStack();
                        this.count++;
                        this.check_token = "x";
                        break;
                    }
                    if (token.get(i).equals("}")) {

                        this.check_braket = "{" + m + "!";

                        // System.out.println("found }");
                        // System.out.println(this.check_braket);
                        // System.out.println(this.statusif);
                        // System.out.println(this.check_braket.equals(this.statusif));
                        if (this.check_braket.equals(this.statusif)) {

                            // System.out.println("check array false" + this.position_else);
                            // System.out.println(this.controller.get(this.position_else));
                            // System.out.println("setcon=true");
                            this.conditionofif = true;
                            this.foundif = false;
                            popStack();
                            this.count++;
                            break;
                        } else {
                            this.count_braketOP -= 1;
                            popStack();
                            this.count++;
                            break;
                        }

                    } else {
                        popStack();
                        this.count++;
                        break;
                    }

                }

            }
            // this.foundif = false;
            else if (token.get(i).equals("}") && this.foundwhile) {
                setPointer(getPosWhile().get(1));
                // break;
            }

            else if (token.get(i).equals("walk")) {

                player.walk(token.get(i + 2));
            }
            // if (token.get(i).equals("check")) {
            // if (player.collision(token.get(i + 2))) {
            // this.expression = true;
            // this._if = false;
            // } else {
            // this.expression = false;
            // }
            // }
            else if (token.get(i).equals("while")) { // function for find while
                // old while
                // setPointerWhile(this.pointer);
                // setLoopWhile(Integer.parseInt(token.get(i + 2)) - 1);
                // -----------------------------------------------
                // new while
                // condition = true;
                // this.positionWhile.add(this.pointer);
                // --------------------------------------------
                // System.out.println(token.get(i + 2));
                if (token.get(i + 2).equals("check")) {
                    String dir = token.get(i + 4);
                    // System.out.println(dir);
                    if (!player.collision(dir)) { // condition in while == true
                        getPosWhile().add(getPointer() - 1);
                        // System.out.println(this.pointer);
                        this.foundwhile = true;
                        System.out.println(getPosWhile());
                        // this.conditionwhile = true;
                        // this.find_braketCL_while = 0;
                        // this.state = "{" + 0 + "w";
                        System.out.println("set-Exp-True");
                    } else {
                        getPosWhile().add(getPointer() - 1);
                        // System.out.println(this.pointer);
                        // this.conditionwhile = true;
                        this.foundwhile = true;
                        System.out.println(getPosWhile());
                        System.out.println("set-Exp-False");
                        // this.count++;

                    }
                }
            }

            else if (token.get(i).equals("if")) { // function for find if
                // System.out.println("found if");
                // this.state = "if";
                this.foundif = true;
                if (this.check_if_out.equals("out")) // check if in or out {}
                {
                    // System.out.println(token.get(i + 2));
                    if (token.get(i + 2).equals("check")) {
                        String test = token.get(i + 4);
                        // System.out.println(test);
                        if (!player.collision(token.get(i + 4))) // condition in if = true
                        {
                            this.count_braketOP += 1;
                            this.conditionofif = true;
                            this.checkif = "0";
                            this.str = Integer.toString(this.count_braketOP);
                            this.check_if_out = "{" + str + "&";
                            this.position_else = 0;
                            this.controller.set(0, "T");
                            // System.out.println(this.controller);
                            // System.out.println("serExpTrue");

                        } else {
                            this.count_braketOP += 1;
                            this.str = Integer.toString(this.count_braketOP);

                            this.statusif = "{" + str + "!";
                            // System.out.println(this.statusif);
                            this.conditionofif = false;
                            this.checkif = "1";
                            this.position_else = 0;
                            this.controller.set(0, "F");
                            // System.out.println(this.controller);
                            // System.out.println("setExpFalse");
                            // this.count++;
                        }
                    }

                } else {
                    // System.out.println(token.get(i + 2));
                    if (token.get(i + 2).equals("check")) {
                        String test = token.get(i + 4);

                        // System.out.println(test);
                        if (!player.collision(token.get(i + 4))) {
                            this.count_braketOP += 1;
                            this.conditionofif = true;
                            this.checkif = "0";
                            this.str = Integer.toString(this.count_braketOP);
                            this.check_else = "{" + str + "&";
                            this.str = Integer.toString(this.count_braketOP);
                            // this.check_else = "{"+str+"&";
                            this.position_else = 1;
                            this.controller.set(1, "T");
                            // System.out.println(this.controller);
                            // System.out.println("serExpTrue");

                        } else {
                            this.count_braketOP += 1;
                            this.str = Integer.toString(this.count_braketOP);

                            this.statusif = "{" + str + "!";
                            // System.out.println(this.statusif);
                            this.conditionofif = false;
                            this.checkif = "1";
                            this.position_else = 1;
                            this.controller.set(1, "F");
                            // System.out.println(this.controller);
                            // System.out.println("setExpFalse");
                            // this.count++;
                        }
                    }
                }
            }
            // if(getExp())
            // {
            // setState("if");

            // }

            else if (token.get(i).equals("else")) // function for find else
            {

                // this.foundelse = true;
                if (token.get(i + 1).equals("{")) {
                    // System.out.println("\nELSE");
                    this.state = "else";
                    this.foundelse = true;
                    this.find_braketOP_else = 0;
                    this.statuselse = "{" + "else" + this.find_braketOP_else;
                    // System.out.println("////////////" + this.statuselse);

                }

            }
            // else if (token.get(i).equals("{"))
            // {
            // if (this.state.equals("else"))
            // {
            // // System.out.println("\nELSE");
            // }
            // else
            // {
            // this.countState++;
            // }
            // } else if (token.get(i).equals("}")) {
            // // old while
            // // if (getLoopWhile() > 0) {
            // // this.pointer = getPointerWhile();
            // // setLoopWhile(getLoopWhile() - 1);
            // // }
            // // new while
            // // if (getIf() && !getState().equals("while")) {
            // // setCountState(getCountState() - 1);
            // // if (getCountState() == 0) {
            // // setExp(true);
            // // }
            // // } else {
            // if (this.state.equals("else"))
            // {
            // System.out.println("END '}'");
            // }
            // else
            // {
            // String Y = "";
            // int y = 0;
            // // if (this.process.size() > 1) {
            // Y = this.process.get(this.process.size() - 2);
            // y = Integer.parseInt(Y.charAt(6) + "");
            // y -= 1;
            // // }
            // if (y != 0)
            // {
            // this.pointer = this.positionWhile.get(this.positionWhile.size() - 1);
            // // this.pointer = this.pointer - (getCount() + 1);
            // String tmp = Y.substring(0, 6) + (y + "") + Y.substring(7, 9);
            // this.process.set(this.process.size() - 2, tmp);
            // this.state = "while";
            // }
            // else
            // {
            // popPosWhile();
            // popStack();
            // this._if = true;
            // this.state = "null";
            // }
            // // }
            // this.count = -1;
            // }
            // }

            else {
                // System.out.println("*** Nothing happen ***");
            }
        }
        // if (condition) {
        // this.count = 0;
        // } else {
        // popStack();
        // this.count++;
        // }
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
        // System.out.println(getLines().get(this.pointer));
        pushStack(getLines().get(getPointer()));
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

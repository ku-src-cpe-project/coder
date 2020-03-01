// Version 0.0.1

// import javax.swing.*;
// import java.awt.*;
// import java.awt.Graphics;
// import java.awt.event.*;
// import java.applet.*;
// import java.util.*;
import java.util.ArrayList;
// import java.util.StringTokenizer;
// import java.io.*;
// import java.awt.List;
// Timer
// import java.text.SimpleDateFormat;
// import java.util.Calendar;
// import java.util.Timer;
// import java.util.TimerTask;

class Complier {
    private ArrayList<String> parses, tokens, lines, controller;
    private ArrayList<Integer> positionWhile, forloop;
    private int pointer, markfor;
    private int pointerWhile, count_if, position_else;
    private int count, countState, count_braketOP, count_braketCL, find_braketOP_else, find_braketCL_else,
            count_braketCL_else, find_braketCL_while, count_braketCL_while, count_braket_forOut, count_braket_forIn,
            count_braket_for, count_braket_forOut_real, count_braket_forIn_real;
    private ArrayList<String> process, find_braketOP, find_braketCL;
    private boolean expression, _if, conditionofif, foundif, foundelse, conditionwhile, foundwhile, whilecheck,
            foundfor;
    private String state, checkif, statusif, statuselse, check_braket, check_braket_else, str, m, n, check_else,
            check_if_out, check_braket2, check_token, check_braket_while_out, check_braket_while_in,
            check_braket_forOut, check_braket_forIn, check_braket_for;

    public Complier() {
        this.process = new ArrayList<String>();
        this.controller = new ArrayList<String>();
        this.controller.add("x");
        this.controller.add("x");
        this.find_braketOP = new ArrayList<String>();
        this.find_braketCL = new ArrayList<String>();
        this.forloop = new ArrayList<Integer>();
        this.pointer = 0;
        this.count_braketOP = 0;
        this.count_if = 0;
        this.count_braketCL = 0;
        this.count_braketCL_else = 0;
        this.find_braketOP_else = 0;
        this.find_braketCL_else = 0;
        this.position_else = 0;
        this.count_braket_forIn = 0;
        this.count_braket_forOut = 0;
        this.find_braketCL_while = 0;
        this.count_braketCL_while = 0;
        this.expression = true;
        this._if = false;
        this.conditionofif = true;
        this.foundif = false;
        this.foundelse = false;
        this.foundfor = false;
        this.check_if_out = "out";
        this.check_token = "x";
        this.check_braket_while_in = "null";
        this.check_braket_while_out = "null";
        this.state = "out";
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
            } else if (tokens.get(i).equals("for")) { // for(number){
                tmp = tmp.concat(tokens.get(i) + ""); // for
                tmp = tmp.concat(tokens.get(i + 1) + ""); // (
                tmp = tmp.concat(tokens.get(i + 2) + ""); // number
                tmp = tmp.concat(tokens.get(i + 3) + ""); // )
                tmp = tmp.concat(tokens.get(i + 4) + ""); // {
                this.lines.add(tmp);
                tmp = "";
                i += 4;
            } else if (tokens.get(i).equals("if")) { // if(check(right)){
                tmp = tmp.concat(tokens.get(i) + ""); // if
                tmp = tmp.concat(tokens.get(i + 1) + ""); // (
                tmp = tmp.concat(tokens.get(i + 2) + ""); // check
                tmp = tmp.concat(tokens.get(i + 3) + ""); // (
                tmp = tmp.concat(tokens.get(i + 4) + ""); // right
                tmp = tmp.concat(tokens.get(i + 5) + ""); // )
                tmp = tmp.concat(tokens.get(i + 6) + ""); // =
                tmp = tmp.concat(tokens.get(i + 7) + ""); // 3
                tmp = tmp.concat(tokens.get(i + 8) + ""); // )
                tmp = tmp.concat(tokens.get(i + 9) + ""); // {
                this.lines.add(tmp);
                tmp = "";
                i += 9;
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
                if (parses.get(i).equals("e") && (parses.get(i + 1).equals("l")) && (parses.get(i + 2).equals("s"))
                        && (parses.get(i + 3).equals("e"))) {
                    this.tokens.add("else");
                    i += 3;
                } else if (parses.get(i).equals("=")) {
                    this.tokens.add("=");
                    this.tokens.add(parses.get(i + 1));
                    this.tokens.add(")");
                    this.tokens.add("{");
                    i += 3;
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
        for (int i = 0; i < token.size(); i++) {
            // System.out.println(token);
            if ((this.foundelse == true) && this.position_else == 1 && this.check_if_out.equals("out")) {
                this.position_else = 0;
            }
            if ((this.foundelse == true) && (this.controller.get(this.position_else).equals("F"))) { // found else and
                                                                                                     // condition if ==
                                                                                                     // False
                                                                                                     // System.out.println("elsccccccc");
                                                                                                     // System.out.println("checcheckkkkk"
                                                                                                     // + " " +
                                                                                                     // this.controller.get(this.position_else));
                this.count_braketCL_else = this.find_braketOP_else;
                this.n = Integer.toString(this.count_braketCL_else);
                if (token.get(i).equals("{")) {
                    this.find_braketOP_else += 1;
                    popStack();
                    this.count++;
                    break;
                } else if (token.get(i).equals("}")) {
                    this.check_braket_else = "{" + "else" + n;
                    if (this.check_braket_else.equals(this.statuselse)) {
                        this.checkif = "0";
                        this.foundelse = false;
                        this.position_else -= 1;
                    } else {
                        this.find_braketOP_else -= 1;
                    }
                } else {
                    this.count++;
                }
            }
            if ((this.foundelse == true) && (this.controller.get(this.position_else).equals("T"))) {
                this.count_braketCL_else = this.find_braketOP_else;
                this.n = Integer.toString(this.count_braketCL_else);
                if (token.get(i).equals("{")) {
                    this.find_braketOP_else += 1;
                    popStack();
                    this.count++;
                    break;
                }
                if (token.get(i).equals("}")) { // skip code in case else
                    this.check_braket_else = "{" + "else" + n;
                    if (this.check_braket_else.equals(this.statuselse)) {
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
            }
            if ((this.foundif)) { // found IF
                this.count_braketCL = this.count_braketOP;
                this.m = Integer.toString(this.count_braketCL);
                if (this.controller.get(this.position_else).equals("T")) { // condition true for check out or in {}
                    if (token.get(i).equals("{")) {
                        this.count_braketOP += 1;
                    }
                    if (token.get(i).equals("}")) {
                        this.check_braket2 = "{" + m + "&";
                        if (this.check_braket2.equals(this.check_if_out)) {
                            this.check_if_out = "out";
                            this.foundif = false;

                        } else {
                            this.count_braketOP -= 1;
                        }
                    }
                }
                if (this.controller.get(this.position_else).equals("F")) { // condition false (for skip code)
                    if (token.get(i).equals("if")) {
                        this.check_token = token.get(i + 7);
                    }
                    if (this.check_token.equals("{")) {
                        this.count_braketOP += 1;
                        popStack();
                        this.count++;
                        this.check_token = "x";
                        break;
                    }
                    if (token.get(i).equals("}")) {
                        this.check_braket = "{" + m + "!";
                        if (this.check_braket.equals(this.statusif)) {
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
            if (this.foundfor) { // function when found for
                if (this.forloop.size() == 2) { // check for out or in (out)
                    this.count_braket_for = this.count_braket_forOut;
                    if (token.get(i).equals("{")) {
                        this.count_braket_forOut += 1;
                    }
                    if (token.get(i).equals("}")) {
                        this.check_braket_for = "for" + (this.count_braket_for - 1) + "{";
                        System.out.println(this.check_braket_for);
                        System.out.println(this.check_braket_forOut);
                        if (this.check_braket_forOut.equals(this.check_braket_for)) {
                            System.out.println(">>>>>>>>>>");
                            if (this.forloop.get(1) > 1) {
                                this.forloop.set(1, this.forloop.get(1) - 1);
                                this.count_braket_forOut = this.count_braket_forOut_real + 1;
                                System.out.println(this.forloop);
                                setPointer(this.forloop.get(0));
                            } else {
                                this.foundfor = false;
                                this.forloop.remove(1);
                                this.forloop.remove(0);
                                System.out.println(this.forloop);
                            }
                        } else {
                            this.count_braket_forOut -= 1;
                        }
                    }
                }
                if (this.forloop.size() == 4)// check for out or in (in)
                {
                    this.count_braket_for = this.count_braket_forIn;
                    if (token.get(i).equals("{")) {
                        this.count_braket_forIn += 1;
                    }
                    if (token.get(i).equals("}")) {
                        this.check_braket_for = "for" + (this.count_braket_for - 1) + "{";
                        System.out.println(this.check_braket_for);
                        System.out.println(this.check_braket_forIn);
                        if (this.check_braket_forIn.equals(this.check_braket_for)) // check braket for in
                        {
                            System.out.println("++++++++++");
                            if (this.forloop.get(3) > 1) //
                            {
                                this.forloop.set(3, this.forloop.get(3) - 1);
                                this.count_braket_forIn = this.count_braket_forIn_real + 1;
                                setPointer(this.forloop.get(2));
                                System.out.println(this.forloop);
                            } else {
                                this.foundfor = true;
                                this.forloop.remove(3);
                                this.forloop.remove(2);
                                System.out.println(this.forloop);
                            }
                        } else {
                            this.count_braket_forIn -= 1;
                        }
                    }
                }
            }
            if (this.foundwhile) { // in case found while
                // setPointer(getPosWhile().get(1));
                // break;
                if (this.conditionwhile) { // in case found while and condition == true
                    if (token.get(i).equals("}")) {
                        setPointer(getPosWhile().get(+this.getPosWhile().size() - 1));
                        // System.out.println(">>>>>>>"+this.getPosWhile());
                        // System.out.println("><><><><><"+this.getPosWhile().size());
                    }
                } else { // in case condition == false (skip code)
                    if (token.get(i).equals("}")) {
                        if (this.getPosWhile().size() > 1) {
                            this.foundwhile = true;
                            this.conditionwhile = true;
                            // System.out.println(">>>>>>>"+this.getPosWhile()+"<<<<<<");
                            setPointer(getPosWhile().get(+this.getPosWhile().size() - 1));
                        }
                        if (this.getPosWhile().get(0) == 1000) {
                            this.foundwhile = false;
                            System.out.println("++++++");
                            this.getPosWhile().remove(0);
                        } else if (this.getPosWhile().get(0) == 99999) {
                            this.getPosWhile().remove(0);
                            this.getPosWhile().add(1000);
                        } else if (this.getPosWhile().size() == 1) {
                            this.foundwhile = true;
                            this.conditionwhile = true;
                            // setPointer(getPosWhile().get(+this.getPosWhile().size()-1));
                            // System.out.println(">>>>>>>"+this.getPosWhile());
                            // System.out.println(">>>>>>>"+this.getPosWhile()+"<<<<<<");
                            popStack();
                            this.count++;
                        } else {
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
            if (token.get(i).equals("walk")) {
                player.walk(token.get(i + 2));
            }
            if (token.get(i).equals("attack")) {
                player.attack();
            }
            if (token.get(i).equals("while")) { // function for find while
                // old while
                // setPointerWhile(this.pointer);
                // setLoopWhile(Integer.parseInt(token.get(i + 2)) - 1);
                // -----------------------------------------------
                // new while
                // condition = true;
                // this.positionWhile.add(this.pointer);
                // --------------------------------------------
                // System.out.println(token.get(i + 2));
                this.foundwhile = true;
                if (token.get(i + 2).equals("check")) {
                    String dir = token.get(i + 4);
                    if (player.collision(dir)) { // condition in while == true
                        // System.out.println(">>>>>>>> TRUE");
                        getPosWhile().add(getPointer() - 1);
                        if (this.getPosWhile().size() > 1) // [1]
                        {
                            if (this.getPosWhile().get(0) != null && this.getPosWhile().get(0) == getPointer() - 1) // [1,1]
                            {
                                this.getPosWhile().remove(1); // [1]
                            }
                        }
                        if (this.getPosWhile().size() > 2) // [1,2,2]
                        {
                            if (this.getPosWhile().get(1) == getPointer() - 1) // [1,2,2]
                            {
                                this.getPosWhile().remove(1); // [1,2]
                            }
                        }
                        // System.out.println(this.pointer);
                        // System.out.println(getPosWhile());
                        this.conditionwhile = true;
                        // this.find_braketCL_while += 1;
                        // this.state = "{" + this.find_braketCL_while + "w";
                        // System.out.println("set-Exp-True");
                    } else { // condition in while == false
                        // System.out.println(">>>>>>>> FALSE");
                        // System.out.println(">>>>>>>>>"+getPointer());
                        // System.out.println(this.getPosWhile());
                        if (this.getPosWhile().isEmpty()) {
                            this.getPosWhile().add(1000);
                        }
                        if (this.getPosWhile().size() > 1) // [1,2]
                        {
                            this.getPosWhile().remove(1); // [1]
                            this.conditionwhile = false;
                        } else if (this.getPosWhile().get(0) == getPointer() - 1) // [1]
                        {
                            // System.out.println("check in");
                            this.conditionwhile = false;

                            this.getPosWhile().remove(0);
                            this.getPosWhile().add(99999);
                            // System.out.println(getPosWhile());
                        } else if (this.getPosWhile().size() == 1) {
                            this.conditionwhile = false;

                        }
                        // this.find_braketCL_while += 1;
                        // this.state = "{" + this.find_braketCL_while + "w";
                        // this.getPosWhile().remove(1);
                        // System.out.println("set-Exp-False");
                        break;
                        // this.count++;
                    }
                }
            }
            if (token.get(i).equals("for")) {// function for find for
                this.foundfor = true;
                if (this.forloop.isEmpty()) {
                    this.forloop.add((this.pointer));
                    this.forloop.add(Integer.parseInt(token.get(i + 2)));
                    this.count_braket_forOut = 0;
                    this.count_braket_forOut_real = this.count_braket_forOut;
                    this.check_braket_forOut = "for" + this.count_braket_forOut + "{";
                } else {
                    this.forloop.add((this.pointer));
                    this.forloop.add(Integer.parseInt(token.get(i + 2)));
                    this.count_braket_forIn = 0;
                    this.count_braket_forIn_real = this.count_braket_forIn;
                    this.check_braket_forIn = "for" + this.count_braket_forIn_real + "{";
                }
                System.out.println(this.forloop);
            }
            if (token.get(i).equals("if")) { // function for find if
                // IF, (, CHECK, (, RIGHT, ), ), {
                // IF, (, CHECK, (, RIGHT, ), =, 3, ), {
                this.foundif = true;
                if (this.check_if_out.equals("out")) { // check if in or out {}
                    if (token.get(i + 2).equals("check")) {
                        if (player.checkNextStep(token.get(i + 4), token.get(i + 7).charAt(0))) { // condition in if =
                                                                                                  // true
                            this.count_braketOP += 1;
                            this.conditionofif = true;
                            this.checkif = "0";
                            this.str = Integer.toString(this.count_braketOP);
                            this.check_if_out = "{" + str + "&";
                            this.position_else = 0;
                            this.controller.set(0, "T");
                        } else {
                            this.count_braketOP += 1;
                            this.str = Integer.toString(this.count_braketOP);
                            this.statusif = "{" + str + "!";
                            this.conditionofif = false;
                            this.checkif = "1";
                            this.position_else = 0;
                            this.controller.set(0, "F");
                        }
                    }
                } else {
                    if (token.get(i + 2).equals("check")) {
                        if (player.checkNextStep(token.get(i + 4), token.get(i + 7).charAt(0))) {
                            this.count_braketOP += 1;
                            this.conditionofif = true;
                            this.checkif = "0";
                            this.str = Integer.toString(this.count_braketOP);
                            this.check_else = "{" + str + "&";
                            this.str = Integer.toString(this.count_braketOP);
                            this.position_else = 1;
                            this.controller.set(1, "T");
                        } else {
                            this.count_braketOP += 1;
                            this.str = Integer.toString(this.count_braketOP);
                            this.statusif = "{" + str + "!";
                            this.conditionofif = false;
                            this.checkif = "1";
                            this.position_else = 1;
                            this.controller.set(1, "F");
                        }
                    }
                }
            } else if (token.get(i).equals("else")) { // function for find else
                if (this.foundif == false) {
                    System.out.println("Error");
                }
                if (token.get(i + 1).equals("{")) {
                    // this.state = "else";
                    this.foundelse = true;
                    this.find_braketOP_else = 0;
                    this.statuselse = "{" + "else" + this.find_braketOP_else;
                }
            }
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
        System.out.println(tokens);
        checkMethod(player, tokens);
    }

    public void Runable(Player player, ArrayList<String> lines) {
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

    public int getPointerFor() {
        return this.pointerWhile;
    }

    public void setPointerFor(int pointer) {
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

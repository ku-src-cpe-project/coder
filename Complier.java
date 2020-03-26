// Version 0.0.1

// import javax.swing.*;
// import java.awt.*;
// import java.awt.Graphics;
// import java.awt.event.*;
// import java.applet.*;
import java.util.*;
// import java.util.StringTokenizer;
// import java.io.*;
// import java.awt.List;
// Timer
// import java.text.SimpleDateFormat;
// import java.util.Calendar;
// import java.util.Timer;
// import java.util.TimerTask;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

class Complier {
    private ArrayList<String> parses, tokens, lines, process;
    private ArrayList<PositionStatement> positionStatements;
    private int pointer;
    private String type;
    private boolean condition;

    public Complier() {
        this.process = new ArrayList<String>();
        this.pointer = 0;
        this.type = "";
        this.condition = true;
        this.positionStatements = new ArrayList<PositionStatement>();
    }

    public ArrayList<String> tokenToLines(ArrayList<String> tokens) {
        this.lines = new ArrayList<String>();
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
                String type = convType(tokens.get(i + 7));
                tmp = tmp.concat(tokens.get(i) + ""); // if
                tmp = tmp.concat(tokens.get(i + 1) + ""); // (
                tmp = tmp.concat(tokens.get(i + 2) + ""); // check
                tmp = tmp.concat(tokens.get(i + 3) + ""); // (
                tmp = tmp.concat(tokens.get(i + 4) + ""); // right
                tmp = tmp.concat(tokens.get(i + 5) + ""); // )
                tmp = tmp.concat(tokens.get(i + 6) + ""); // =
                tmp = tmp.concat(type); // bomb
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
        System.out.println(this.lines);
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
                } else if (parses.get(i).equals("=")) {
                    this.tokens.add("=");
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
        if (parse.equals("(") || parse.equals(")") || parse.equals("{") || parse.equals("}") || parse.equals(";")
                || parse.equals("=")) {
            return true;
        } else {
            return false;
        }
    }

    public String convType(String _type) {
        if (_type.equals("path")) {
            this.type = "0";
        } else if (_type.equals("wall")) {
            this.type = "1";
        } else if (_type.equals("enemy")) {
            this.type = "2";
        } else if (_type.equals("bomb")) {
            this.type = "3";
        } else if (_type.equals("mushroom_yellow")) {
            this.type = "5";
        } else if (_type.equals("portal_red")) {
            this.type = "6";
        } else if (_type.equals("portal_green")) {
            this.type = "7";
        } else if (_type.equals("portal_blue")) {
            this.type = "8";
        } else if (_type.equals("mushroom_red")) {
            this.type = "A";
        } else if (_type.equals("treasure_box")) {
            this.type = "Q";
        } else if (_type.equals("treasure")) {
            this.type = "T";
        }
        return this.type;
    }

    // ========================================================
    // Runable
    // ========================================================

    public void checkMethod(Player player, ArrayList<String> token) { // WHILE, (, CHECK, (, DOWN, ), ), {
        for (int i = 0; i < token.size(); i++) {
            // System.out.println(token);
            if (token.get(i).equals("walk") && this.condition) {
                player.walk(token.get(i + 2));
            } else if (token.get(i).equals("for") && this.condition) {
            } else {
                popStack();
                break;
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

    }

    public Collection<PositionStatement> getPositionStatements() {
        return this.positionStatements;
    }

    public PositionStatement popPositionStatements() {
        PositionStatement a = null;
        if (this.positionStatements.size() > 0) {
            a = this.positionStatements.get(0);
        }
        return a;
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

    public ArrayList<String> getLines() {
        return this.lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}

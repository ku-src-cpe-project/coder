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

class Complier {
    private JPanel panel;
    private JLabel label_response;
    private Map map;
    private Player player;
    private ArrayList<Character> parses;
    private ArrayList<String> tokens;
    private String text;
    private char parse;

    public Complier(JPanel panel, JLabel label_response, Map map, Player player) {
        this.panel = panel;
        this.label_response = label_response;
        this.map = map;
        this.player = player;
    }

    public ArrayList<Character> textToParses(String text) {
        this.parses = new ArrayList<Character>();
        for (int i = 0; i < text.length(); i++) {
            this.parses.add(text.charAt(i));
        }
        System.out.println(this.parses);
        return this.parses;
    }

    public ArrayList<String> parseToTokens(ArrayList<Character> parses) {
        this.tokens = new ArrayList<String>();
        String tmp = "";
        for (int i = 0; i < parses.size(); i++) {
            if (checkOperater(parses.get(i))) {
                // System.out.println(parses.get(i) + " is Operater");
                this.tokens.add(tmp);
                tmp = "";
                this.tokens.add(parses.get(i) + "");
            } else {
                // System.out.println(parses.get(i) + " is Not Operater");
                tmp = tmp.concat(parses.get(i) + "");
                // System.out.println(tmp);
            }
        }
        System.out.println(this.tokens);
        return this.tokens;
    }

    public boolean checkOperater(char parse) {
        if (parse == '(' || parse == ')') {
            return true;
        } else {
            return false;
        }
    }

    public void Run(JPanel panel, JLabel label_response, Map map, Player player, ArrayList<String> tokens) {
        System.out.println("=== PROGRAM ALREADY RUNNING ===");
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("DEFINE NUMBER: " + i + " " + tokens.get(i));
            if (tokens.get(i).equals("walk")) {
                player.walk(player, tokens.get(i + 2));

            } else {
                System.out.println("Nothing happen");
            }
        }
        map.echoMap();
    }
}

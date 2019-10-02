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

class Player {
    private JPanel panel;
    private JLabel label_response;
    private Map map;
    private Icon icon_player;
    private JLabel label_player;
    private int position_x;// 0
    private int position_y;// 28
    private int[] currentPosition = { 0, 0 };

    public Player(JPanel panel, JLabel label_response, Map map) {
        // System.out.println("Player Create");
        this.panel = panel;
        this.label_response = label_response;
        this.map = map;
        this.position_x=this.map.getStartPositionX();
        this.position_y=this.map.getStartPositionY();
        this.icon_player = new ImageIcon("icon/player.png");
        this.label_player = new JLabel(this.icon_player);
        this.map.setMap(1, 1);
        currentPosition = this.map.getPosition();
        // System.out.println(currentPosition[0] + " " + currentPosition[1]);
        this.label_player.setBounds(((1 - 1) * this.map.getSpeedX()) + this.map.getStartPositionX(),
                ((1 - 1) * this.map.getSpeedY()) + this.map.getStartPositionY(), 109, 143);
        panel.add(this.label_player);
        panel.add(this.label_response);
    }

    public void walk(Player player, String dir) {
        // System.out.println("Player Walk " + dir);
        currentPosition = this.map.getPosition();
        // System.out.println(this.map.getMap(currentPosition[0], currentPosition[1] + 1));
        if (dir.equals("forward") && collision("forward")) {
            this.label_player.setBounds(this.position_x + this.map.getSpeedX(), this.position_y, 109, 143);
            this.position_x = this.position_x + this.map.getSpeedX();
            this.map.set0(currentPosition[0], currentPosition[1]);
            this.map.setMap(currentPosition[0], currentPosition[1] + 1);
        } else if (dir.equals("back") && collision("back")) {
            this.label_player.setBounds(this.position_x - this.map.getSpeedX(), this.position_y, 109, 143);
            this.position_x = this.position_x - this.map.getSpeedX();
            this.map.set0(currentPosition[0], currentPosition[1]);
            this.map.setMap(currentPosition[0], currentPosition[1] - 1);
        } else if (dir.equals("down") && collision("down")) {
            this.label_player.setBounds(this.position_x, this.position_y + this.map.getSpeedY(), 109, 143);
            this.position_y = this.position_y + this.map.getSpeedY();
            this.map.set0(currentPosition[0], currentPosition[1]);
            this.map.setMap(currentPosition[0] + 1, currentPosition[1]);
        } else if (dir.equals("up") && collision("up")) {
            this.label_player.setBounds(this.position_x, this.position_y - this.map.getSpeedY(), 109, 143);
            this.position_y = this.position_y - this.map.getSpeedY();
            this.map.set0(currentPosition[0], currentPosition[1]);
            this.map.setMap(currentPosition[0] - 1, currentPosition[1]);
        }
        else {
            System.out.println("*** Sysntax error ***");
        }
        // this.map.echoMap();
        panel.add(this.label_player);
        panel.add(this.label_response);
    }

    public boolean collision(String dir) {
        boolean bool = true;
        if (dir == "forward") {
            if (this.map.getMap(this.currentPosition[0], this.currentPosition[1] + 1) == '0') {
                bool = true;
            } else {
                bool = false;
            }
        }
        if (dir == "back") {
            if (this.map.getMap(this.currentPosition[0], this.currentPosition[1] - 1) == '0') {
                bool = true;
            } else {
                bool = false;
            }
        }
        if (dir == "down") {
            if (this.map.getMap(this.currentPosition[0] + 1, this.currentPosition[1]) == '0') {
                bool = true;
            } else {
                bool = false;
            }
        }
        if (dir == "up") {
            if (this.map.getMap(this.currentPosition[0] - 1, this.currentPosition[1]) == '0') {
                bool = true;
            } else {
                bool = false;
            }
        }
        return bool;
    }
}

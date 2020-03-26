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

class PositionStatement {
    private int line;
    private String type;

    public void PositionStatement(int a, String b) {
        this.line = a;
        this.type = b;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int a) {
        this.line = a;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String a) {
        this.type = a;
    }
}

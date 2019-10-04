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
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.Color;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
// =============================================================================
// Timer Import
// =============================================================================
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
// =============================================================================
// 
// =============================================================================
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.Color;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
//ww  w  .  j av a2s  . com
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//
public class Coder extends JPanel implements Runnable {
	private Thread thread;
	private boolean running;
	private boolean pause = false;
	private Graphics gr;
	private int screenx = 1115;
	private int screeny = 638;
	private Image screen;
	private ImageIcon bg;
	// ========================================================
	// Variable
	// ========================================================
	private int blockX = 100;
	private int blockY = 100;
	private Map map;
	private int scale = 105;
	private Random random;
	private Player player;
	private JTextArea input;
	private String text_value;
	private JButton submit;
	private JButton restart;
	private Complier complier;
	private ArrayList<String> parses;
	private ArrayList<String> tokens;
	private ArrayList<String> lines;
	private int line;
	private boolean runable;
	private Bomb bomb;

	// ========================================================
	// Constructure
	// ========================================================
	public Coder() {
		init();
	} // Game()

	// ========================================================
	// init
	// ========================================================
	public void init() {
		screenx = 10 * scale;
		screeny = 5 * scale;
		setPreferredSize(new Dimension(screenx, screeny));
		running = true;
		bg = new ImageIcon("icon/background.pngl");
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY + 1);
		thread.start();

		complier = new Complier();
		runable = false;
		line = 0;
		input = new JTextArea();
		input.setText("Coding here...");
		input.setBackground(Color.green);
		add(input);
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// text_value = input.getText();
				// text_value = "walk(right);while(2){walk(down);}";
				// text_value =
				// "walk(right);\nwhile(2){\nwalk(down);\n}walk(right);walk(right);";
				// text_value = "walk(right);walk(down);walk(right);";
				text_value = "walk(right);walk(right);walk(right);walk(right);walk(right);";
				// text_value =
				// "walk(right);walk(right);walk(right);walk(down);walk(right);walk(right);walk(right);walk(right);";
				// text_value = "walk(right);check(right);";
				// text_value = "check(right);walk(right);";
				text_value = text_value.replace(" ", "");
				text_value = text_value.replace("\n", "");
				parses = complier.textToParses(text_value);
				tokens = complier.parseToTokens(parses);
				lines = complier.tokenToLines(tokens);
				runable = true;
			}
		});
		restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				newGame();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
			}
		});
		add(submit);
		add(restart);
		newGame();
	}

	// ========================================================
	// New Game
	// ========================================================
	public void newGame() {
		System.out.println("==============================");
		System.out.println("           New Game");
		System.out.println("==============================");
		map = new Map("0002");
		for (int i = 0; i < map.getRow(); i++) { // debug
			for (int j = 0; j <= map.getColumn(); j++) {
				System.out.print(map.getMap()[i][j]);
			}
			System.out.print("\n");
		}
		player = new Player(map, scale);
	}

	// ========================================================
	// Update
	// ========================================================
	public void update() {
		// System.out.println("> Update");
		if (runable && player.getState().equals("alive")) {
			if (complier.getPointer() == 0) {
				System.out.println("==============================");
				System.out.println("    PROGRAM ALREADY RUNNING");
				System.out.println("==============================");
			}
			System.out.println("Line: " + complier.getPointer() + "\t" + lines.get(complier.getPointer()));
			complier.Runable(player, lines);
			if (complier.getPointer() < lines.size()) {
				line++;
			}
		} else if (!player.getState().equals("alive")) {
			player.setY(600);
		}
	}

	// ========================================================
	// Make Screen
	// ========================================================
	private void makeFrameToScreen(Graphics g) {
		screen = createImage(screenx, screeny);
		gr = screen.getGraphics();
		gr.drawImage(bg.getImage(), 0, 0, null);
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j <= map.getColumn(); j++) {
				if (map.getMap()[i][j] == '0') {
					// gr.setColor(Color.WHITE);
					// gr.fillRect(j * scale, i * scale, blockX, blockY);
				}
				if (map.getMap()[i][j] == '1') {
					gr.setColor(Color.RED);
					gr.fillRect(j * scale, i * scale, blockX, blockY);
				}
				if (map.getMap()[i][j] == '2') {
					// gr.setColor(Color.GREEN);
					// gr.fillRect(j * scale, i * scale, blockX, blockY);
					bomb = new Bomb(j * scale, i * scale, scale);
					bomb.draw(gr);
				}
				if (map.getMap()[i][j] == '9') {
					// gr.setColor(Color.PINK);
					// gr.fillRect(j * scale, i * scale, blockX, blockY);
				}
			}
		}
		player.draw(gr);
		update();
		g.drawImage(screen, 0, 0, null);
	}

	// ========================================================
	// Override
	// ========================================================
	@Override
	public void paintComponent(Graphics g) {
		makeFrameToScreen(g);
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1000);
				repaint();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
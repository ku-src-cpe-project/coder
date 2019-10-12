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
	private int scale = 100; // 105 *From variable*
	private int screenx = 10 * scale; // 1115
	private int screeny = 6 * scale; // 638
	private Image screen;
	private ImageIcon bg;
	// ========================================================
	// Variable
	// ========================================================
	private int locationX = 80;
	private int locationY = 200;
	private int blockX = 50;
	public int blockY = 50;
	private int padX = 20;
	private int padY = 50;
	private Map map;
	private Random random;
	private Player player;
	private JTextArea input;
	private String text_value;
	private JButton submit, restart;
	private JButton next;
	private Complier complier;
	private ArrayList<String> parses;
	private ArrayList<String> tokens;
	private ArrayList<String> lines;
	private int line;
	private boolean runable;
	private Bomb bomb;
	private Portal portal;
	private int buttonLocationX = 150, buttonLocationY = 0;
	private int buttonSizeX = 100, buttonSizeY = 50;
	private int dir = 0;
	private String currentMap;
	private int mapNumber = 0;
	private JLabel mapNmberJ;

	// ========================================================
	// Debug
	// ========================================================
	private JButton up, down, left, right;

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
		screenx = 11 * scale; // 10
		screeny = 6 * scale; // 5
		setPreferredSize(new Dimension(screenx, screeny));
		running = true;
		bg = new ImageIcon("icon/background.png");
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY + 1);
		thread.start();
		setLayout(null); // set position by self <<<<<<<< obj.setBouns(location_x, location_y, size_x,
							// size_y)

		complier = new Complier();
		runable = false;
		line = 0;
		input = new JTextArea("Coding here...");
		input.setBackground(Color.green);
		input.setBounds(0, 0, 150, screeny - 300);
		add(input);
		mapNmberJ = new JLabel(mapNumber + "");
		mapNmberJ.setBounds(screenx - 150, 0, 150, 75);
		mapNmberJ.setFont(new Font("Serif", Font.PLAIN, 75));
		mapNmberJ.setForeground(Color.RED);
		add(mapNmberJ);

		// ========================================================
		// Debug
		// ========================================================
		int coreX = 450, coreY = 80;
		int sizeX = 50, sizeY = 50;
		up = new JButton("^");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("up");
			}
		});
		add(up);
		up.setBounds(coreX, coreY - sizeY, sizeX, sizeY);
		down = new JButton("V");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("down");
			}
		});
		add(down);
		down.setBounds(coreX, coreY + sizeY, sizeX, sizeY);
		left = new JButton("<");
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("left");
			}
		});
		add(left);
		left.setBounds(coreX - sizeX, coreY, sizeX, sizeY);
		right = new JButton(">");
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("right");
			}
		});
		add(right);
		right.setBounds(coreX + sizeX, coreY, sizeX, sizeY);

		// ========================================================
		//
		// ========================================================
		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// Restart
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
				text_value = input.getText();

				// normal
				// text_value = "walk(right);walk(down);walk(right);";
				// text_value = "walk(right);walk(right);walk(right);walk(right);walk(right);";
				// text_value =
				// "walk(right);walk(right);walk(right);walk(down);walk(right);walk(right);walk(right);walk(right);";
				// text_value = "walk(right);check(right);";
				// text_value = "check(right);walk(right);";

				// while
				// text_value = "walk(down);while(2){walk(right);walk(right);}";
				// text_value =
				// "walk(right);\nwhile(2){\nwalk(down);\n}walk(right);walk(right);";
				// text_value =
				// "walk(right);while(1){walk(down);}while(3){walk(right);}walk(up);while(3){walk(right);}";
				// text_value = "while(2){walk(down);while(3){walk(right);}};";

				// if
				// text_value =
				// "walk(right);if(check(right)){walk(right);walk(right);}walk(down);";
				// text_value =
				// "walk(right);if(check(right)){while(2){walk(right);}walk(right);}walk(down);";

				text_value = text_value.replace(" ", "");
				text_value = text_value.replace("\n", "");
				text_value = text_value.replace("\t", "");
				parses = complier.textToParses(text_value);
				tokens = complier.parseToTokens(parses);
				lines = complier.tokenToLines(tokens);
				runable = true;
			}
		});
		add(submit);
		submit.setBounds(buttonLocationX, buttonLocationY, buttonSizeX, buttonSizeY);
		restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				map = new Map(currentMap);
				newGame();
				complier.setPointer(0);
				complier.setExp(true);
				complier.setIf(false);
				complier.setState("null");
				runable = false;
				line = complier.getPointer();
			}
		});
		add(restart);
		restart.setBounds(buttonLocationX, buttonLocationY + buttonSizeY, buttonSizeX, buttonSizeY);
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				map = new Map(randMap());
				mapNumber++;
				mapNmberJ.setText(mapNumber + "");
				newGame();
				complier.setPointer(0);
				complier.setExp(true);
				complier.setIf(false);
				complier.setState("null");
				runable = false;
				line = complier.getPointer();
			}
		});
		add(next);
		next.setBounds(buttonLocationX, buttonLocationY + buttonSizeY * 2, buttonSizeX, buttonSizeY);
		map = new Map(randMap());
		newGame();
	}

	// ========================================================
	// New Game
	// ========================================================
	public void newGame() {
		System.out.println("==============================");
		System.out.println("           New Game");
		System.out.println("==============================");
		map = new Map("000");
		screenx = (map.getColumn() + 2) * scale + locationX - scale + 50;
		screeny = (map.getRow()) * blockY + locationY;
		setPreferredSize(new Dimension(screenx, screeny));
		for (int i = 0; i < map.getRow(); i++) { // debug
			for (int j = 0; j <= map.getColumn(); j++) {
				System.out.print(map.getMap()[i][j]);
			}
			System.out.print("\n");
		}
		player = new Player(map, scale);
	}

	// ========================================================
	// Random Map
	// ========================================================
	public String randMap() {
		String mapName = "";
		String tmp = "0000";
		random = new Random();
		int randNumberI = random.nextInt(6 - 1) + 1; // random 1-5
		String randNumberS = randNumberI + "";
		tmp = tmp.concat(randNumberS);
		mapName = tmp.substring(tmp.length() - 4, tmp.length());
		currentMap = mapName;
		return mapName;
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
				// System.out.println("Parse:");
				// System.out.println("\t" + parses);
				// System.out.println("\nTokens:");
				// System.out.println("\t" + tokens);
				// System.out.println("\nLines:");
				// System.out.println("\t" + lines);
				// System.out.println();
			}
			if (complier.getPointer() < lines.size()) { // lines.size()-1
				System.out.println("Line: " + complier.getPointer() + "  \t" + lines.get(complier.getPointer()));
				complier.Runable(player, lines);
				line++;
			}
		} else if (player.getState().equals("next")) {
			map = new Map(randMap());
			mapNumber++;
			mapNmberJ.setText(mapNumber + "");
			newGame();
			complier.setPointer(0);
			complier.setExp(true);
			complier.setIf(false);
			complier.setState("null");
			runable = false;
			line = complier.getPointer();
			player.setState("alive");
		} else if (player.getState().equals("dead")) {
			player.playerPosition[0] = -99;
		}
		if (dir >= 1) {
			dir = 0;
		} else {
			dir++;
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
					gr.setColor(Color.WHITE);
					gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY - (padY * i), blockX,
							blockY);
				}
				if (map.getMap()[i][j] == '1') {
					gr.setColor(Color.RED);
					gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY - (padY * i), blockX,
							blockY);
				}
				if (map.getMap()[i][j] == '2') {
					// gr.setColor(Color.GREEN);
					// gr.fillRect(j * scale, i * scale, blockX, blockY);
					bomb = new Bomb((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					bomb.draw(gr, dir);
				}
				if (map.getMap()[i][j] == '8') {
					portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					portal.draw(gr, dir);
				}
				if (map.getMap()[i][j] == '7') {
					portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					portal.draw(gr, dir + 2);
				}
				if (map.getMap()[i][j] == '6') {
					portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					portal.draw(gr, dir + 4);
				}
				if (map.getMap()[i][j] == '9') {
					// gr.setColor(Color.PINK);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
				}
			}
		}
		player.draw(gr, dir, locationX, locationY, padX, padY);
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
				Thread.sleep(200);
				repaint();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
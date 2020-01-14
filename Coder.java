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
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.JFrame;
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

// =============================================================================
// 
// =============================================================================

//
public class Coder extends JPanel implements Runnable {
	private Thread thread;
	private boolean running;
	private boolean pause = false;
	private Graphics gr;
	private int scale = 100; // 105 *From variable*
	private int screenx = 1280; // 1115
	private int screeny = 500; // 638
	private Image screen;
	private ImageIcon bg;
	// ========================================================
	// Variable
	// ========================================================
	private int locationX = 110;
	private int locationY = 230;
	private int blockX = 50;
	public int blockY = 50;
	private int padX = 15;
	private int padY = 45;
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
	private Mushroom mushroom;
	private Enemy enemy;
	public static ArrayList<Enemy> enemys;
	private FireBall fireball;
	private int buttonLocationX = 325, buttonLocationY = 25;
	private int buttonSizeX = 100, buttonSizeY = 50;
	private int dir = 0;
	private String currentMap;
	private int mapNumber = 1;
	private JLabel mapNmberJ, hintJ, UhintJ;
	private int delay = 0, delay_2 = 0;
	private boolean first;
	private boolean attacking;

	public static PlaySound pl = new PlaySound();

	private ImageIcon[] images;
	private int anima;
	private boolean hit;
	private int tmpX, tmpY;

	// ========================================================
	// Debug
	// ========================================================
	private JButton up, down, left, right, fire;

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
		screenx = 1065; // 11 * scale; // 10;
		screeny = 600; // 6 * scale; // 5;
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
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		input = new JTextArea("");
		input.setBackground(new Color(70, 220, 90));
		input.setBounds(11, 10, 195, 332);
		input.setFont(f1);
		add(input);
		mapNmberJ = new JLabel(mapNumber + "");
		mapNmberJ.setBounds(1010, 10, 150, 75);
		mapNmberJ.setFont(new Font("Serif", Font.PLAIN, 75));
		mapNmberJ.setForeground(Color.BLACK);
		add(mapNmberJ);
		UhintJ = new JLabel("Hint: ");
		UhintJ.setBounds(250, 95, 70, 75);
		UhintJ.setBackground(new Color(70, 220, 90));
		UhintJ.setFont(f1);
		add(UhintJ);
		hintJ = new JLabel("");
		hintJ.setBounds(300, 95, 770, 75);
		hintJ.setBackground(new Color(70, 220, 90));
		hintJ.setFont(f1);
		add(hintJ);
		pl.playSound_L("sound/bgm.wav", 999);

		images = new ImageIcon[5];
		// images[0] = new ImageIcon("icon/kaboom.gif");
		images[0] = new ImageIcon("icon/anima/kaboom_1.png");
		images[1] = new ImageIcon("icon/anima/kaboom_2.png");
		images[2] = new ImageIcon("icon/anima/kaboom_3.png");
		images[3] = new ImageIcon("icon/anima/kaboom_4.png");
		images[4] = new ImageIcon("icon/anima/kaboom_5.png");

		// ========================================================
		// Debug
		// ========================================================
		int coreX = 70, coreY = 450;
		int sizeX = 50, sizeY = 50;
		up = new JButton("^");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("up");
			}
		});
		up.setBounds(coreX, coreY - sizeY, sizeX, sizeY);
		down = new JButton("V");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("down");
			}
		});
		down.setBounds(coreX, coreY + sizeY, sizeX, sizeY);
		left = new JButton("<");
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("left");
			}
		});
		left.setBounds(coreX - sizeX, coreY, sizeX, sizeY);
		right = new JButton(">");
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("right");
			}
		});
		right.setBounds(coreX + sizeX, coreY, sizeX, sizeY);
		fire = new JButton("F");
		fire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.attack();
			}
		});
		add(up);
		add(down);
		add(left);
		add(right);
		add(fire);
		fire.setBounds(coreX, coreY, sizeX, sizeY);

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
				map = new Map(hintJ, currentMap);
				newGame();
				complier.setPointer(0);
				// complier.setExp(true);
				// complier.setIf(false);
				// complier.setState("null");
				runable = false;
				line = complier.getPointer();
			}
		});
		add(restart);
		restart.setBounds(buttonLocationX + 250, buttonLocationY, buttonSizeX, buttonSizeY);
		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// map = new Map(randMap());
				map = new Map(hintJ, convMap(mapNumber));
				mapNumber++;
				mapNmberJ.setText(mapNumber + "");
				newGame();
				complier.setPointer(0);
				// complier.setExp(true);
				// complier.setIf(false);
				// complier.setState("null");
				runable = false;
				line = complier.getPointer();
			}
		});
		add(next);
		next.setBounds(buttonLocationX + 500, buttonLocationY, buttonSizeX, buttonSizeY);
		// map = new Map(randMap());
		map = new Map(hintJ, "0000");
		currentMap = "0000";
		newGame();
	}

	// ========================================================
	// New Game
	// ========================================================
	public void newGame() {
		System.out.println("==============================");
		System.out.println("           New Game");
		System.out.println("==============================");
		// map = new Map("0009");
		// screenx = (map.getColumn() + 2) * scale + locationX - scale + 50;
		// screeny = (map.getRow()) * blockY + locationY;
		setPreferredSize(new Dimension(screenx, screeny));
		for (int i = 0; i < map.getRow(); i++) { // debug
			for (int j = 0; j <= map.getColumn(); j++) {
				System.out.print(map.getMap()[i][j]);
			}
			System.out.print("\n");
		}
		player = new Player(map, scale);
		enemys = new ArrayList<Enemy>();
		first = true;
		attacking = false;
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
	// Convert Map Integer to String
	// ========================================================
	public String convMap(int a) {
		String mapName = "";
		String tmp = "0000";
		random = new Random();
		String randNumberS = a + "";
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
			// ========================================================
			// Enemy Delay
			// ========================================================
			if (delay > 20) {
				for (int i = 0; i < enemys.size(); i++) {
					enemys.get(i).walk();
				}
				// if (attacking) {
				// fireball.walk();
				// }
				delay = 0;
			} else {
				delay++;
			}
			if (delay_2 > 1) {
				if (attacking) {
					if (fireball != null) {
						if (!fireball.checkNextStep(2, '0')) {
							for (int i = 0; i < enemys.size(); i++) {
								if (enemys.get(i).checkNextStep(1, '4')) {
									enemys.get(i).disable();
									enemys.remove(i);
								}
							}
							tmpX = fireball.getX();
							tmpY = fireball.getY();
							fireball.disable();
							fireball = null;
							attacking = false;
							pl.playSound_S("sound/hit.wav");
							hit = true;
							anima = 0;
						}
					}
					if (attacking) {
						fireball.walk();
					}
				}
				delay_2 = 0;
			} else {
				delay_2++;
			}
			// ========================================================
			//
			// ========================================================
		} else if (player.getState().equals("next")) {
			mapNumber++;
			map = new Map(hintJ, convMap(mapNumber));
			newGame();
			mapNmberJ.setText(mapNumber + "");
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
		if (anima >= 4) {
			anima = 0;
		} else {
			anima++;
		}
	}

	// ========================================================
	// Make Screen
	// ========================================================
	private void makeFrameToScreen(Graphics g) {
		screen = createImage(screenx, screeny);
		gr = screen.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screenx, screeny);
		gr.drawImage(bg.getImage(), 0, 0, null);
		// for (int i = 0; i < map.getRow(); i++) {
		// for (int j = 0; j <= map.getColumn(); j++) {
		// System.out.print(map.cheMap(i, j));
		// }
		// System.out.println();
		// }
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j <= map.getColumn(); j++) {
				if (map.getMap()[i][j] == '0') {
					// gr.setColor(Color.WHITE);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
				}
				if (map.getMap()[i][j] == '1') {
					// gr.setColor(Color.RED);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
				}
				if (map.getMap()[i][j] == '2') {
					enemy = new Enemy(map, scale, (j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					enemy.draw(gr, dir);
					if (first) {
						enemys.add(enemy);
					}
				}
				if (map.getMap()[i][j] == '3') {
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
				if (map.getMap()[i][j] == '5') {
					mushroom = new Mushroom((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					mushroom.draw(gr, dir);
				}
				if (map.getMap()[i][j] == 'A') {
					mushroom = new Mushroom((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, scale);
					mushroom.draw(gr, dir + 2);
				}
				if (map.getMap()[i][j] == '4') {
					fireball = new FireBall(map, scale, (j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					fireball.draw(gr, dir);
					attacking = true;
				}
				if (map.getMap()[i][j] == '9') {
					// gr.setColor(Color.PINK);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
					if (player.getMush().equals("chun-li")) {
						player.draw(gr, dir + 4, locationX, locationY, padX, padY);
					} else if (player.getMush().equals("ken")) {
						player.draw(gr, dir + 2, locationX, locationY, padX, padY);
					} else {
						player.draw(gr, dir, locationX, locationY, padX, padY);
					}
				}
			}
		}
		if (hit) {
			gr.drawImage(images[anima].getImage(), tmpX - 118, tmpY - 74, null); // (456, 294) /2 = (228, 147) /2 =
																					// (118, 74)
			if (anima >= 4) {
				hit = false;
			}
		}
		first = false;
		update();
		g.drawImage(screen, 0, 0, null);
		// g.drawImage(images[0].getImage(), 100, 100, null); // insert picture .gif
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
				Thread.sleep(300);
				repaint();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
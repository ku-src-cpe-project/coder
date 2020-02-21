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

import javax.sound.sampled.*;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Image;
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
	// private boolean pause = false;
	private Graphics gr;
	private int scale = 100; // 105 *From variable*
	private int screenx, screeny; // x => 1115 // 1280 // y => 638 // 500
	private Image screen;
	private ImageIcon[] backgrounds;
	// ========================================================
	// Function
	// ========================================================
	private Random random;
	private PlaySound soundMedia;
	private ReadFile readFile;
	private Font fontA;

	// ========================================================
	// Debug
	// ========================================================
	private JButton up, down, left, right, fire;

	// ========================================================
	// Variable
	// ========================================================
	private int locationX = 110, locationY = 230;
	private int blockX = 50, blockY = 50;
	private int padX = 15, padY = 45;
	private boolean windowStart = true, windowPlay = false;

	// Complier
	private Complier complier;
	private ArrayList<String> parses, tokens, lines;
	private int line;
	private boolean runable, submit;
	private boolean firstTime;

	// Object
	private Map map;
	private Player player;
	private Enemy enemy;
	private Dummy dummy;
	private Bomb bomb;
	private Portal portal;
	private Mushroom mushroom;
	private FireBall fireball;

	// Input
	private JTextArea input;
	private String textValue;

	// Button
	private JLabel buttonSubmit, buttonClear, buttonRestart, buttonNext, buttonStart;
	private int buttonLocationX = 280, buttonLocationY = 0;
	private int buttonSizeX = 182, buttonSizeY = 103;

	// Map
	private boolean stateMapEnd, stateMapStart;
	private JLabel tutorialBackground, tutorialText;
	private JLabel hintText, hintTextForm;
	private String mapName, mapNumberStr;
	private int mapNumber;
	private JLabel mapNumberLabel;

	// Update
	private int delayWalkEnemy, delayWalkFireBall, delayMapEnd;
	private boolean attacking, hitting;
	private ImageIcon[] effectBombs, effectStars, effectSmokes;
	private int desStar, desBomb, desDirection, desSmoke, desBackground;
	private int tmpX, tmpY;
	private int timing;

	// Variable
	private ArrayList<Enemy> enemys;
	private ArrayList<Dummy> dummys;
	private ArrayList<FireBall> fireballs;

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
		thread = new Thread(this);
		thread.setPriority(Thread.MIN_PRIORITY + 1);
		thread.start();
		setLayout(null); // set position by self <<<<<<<< obj.setBouns(location_x, location_y, size_x,
							// size_y)

		// ========================================================
		// Save file
		// ========================================================
		readFile = new ReadFile();
		readFile.openRead();
		readFile.ReadFile();
		readFile.closeRead();

		// ========================================================
		// Sound
		// ========================================================
		soundMedia = new PlaySound();
		soundMedia.playSound_L("sound/bgm.wav", 999);

		// ========================================================
		// Init
		// ========================================================
		complier = new Complier();
		runable = false;
		line = 0;
		fontA = new Font("SansSerif", Font.BOLD, 20);

		input = new JTextArea("");
		input.setBackground(new Color(70, 220, 90));
		input.setFont(fontA);
		input.setLineWrap(true);

		mapNumberStr = readFile.getMapName();
		mapNumberLabel = new JLabel(mapNumberStr);
		mapNumberLabel.setFont(new Font("Serif", Font.PLAIN, 75));
		mapNumberLabel.setForeground(Color.BLACK);
		mapNumber = Integer.parseInt(mapNumberStr);
		mapNumberLabel.setText(mapNumber + "");
		stateMapStart = true;
		stateMapEnd = false;

		hintText = new JLabel("");
		hintText.setBackground(new Color(70, 220, 90));
		hintText.setFont(fontA);
		hintTextForm = new JLabel("Hint: ");
		hintTextForm.setBackground(new Color(70, 220, 90));
		hintTextForm.setFont(fontA);

		tutorialText = new JLabel("");
		tutorialText.setBackground(new Color(70, 220, 90));
		tutorialText.setFont(fontA);

		// ========================================================
		// Picture Store
		// ========================================================
		effectBombs = new ImageIcon[5];
		effectStars = new ImageIcon[3];
		effectSmokes = new ImageIcon[2];
		backgrounds = new ImageIcon[2];
		backgrounds[0] = new ImageIcon("icon/background_start.png");
		backgrounds[1] = new ImageIcon("icon/background_play.png");
		// effectBombs[0] = new ImageIcon("icon/kaboom.gif");
		effectBombs[0] = new ImageIcon("icon/anima/kaboom_1.png");
		effectBombs[1] = new ImageIcon("icon/anima/kaboom_2.png");
		effectBombs[2] = new ImageIcon("icon/anima/kaboom_3.png");
		effectBombs[3] = new ImageIcon("icon/anima/kaboom_4.png");
		effectBombs[4] = new ImageIcon("icon/anima/kaboom_5.png");
		effectStars[0] = new ImageIcon("icon/1-star.png");
		effectStars[1] = new ImageIcon("icon/2-star.png");
		effectStars[2] = new ImageIcon("icon/3-star.png");
		effectSmokes[0] = new ImageIcon("icon/smoke.png");
		effectSmokes[1] = new ImageIcon("icon/smoke.png");
		tutorialBackground = new JLabel(new ImageIcon("icon/hint.png"));
		buttonStart = new JLabel(new ImageIcon("icon/button_start.png"));
		buttonSubmit = new JLabel(new ImageIcon("icon/button_submit.png"));
		buttonClear = new JLabel(new ImageIcon("icon/button_clear.png"));
		buttonNext = new JLabel(new ImageIcon("icon/button_next.png"));
		buttonRestart = new JLabel(new ImageIcon("icon/button_restart.png"));

		// ========================================================
		// Debug
		// ========================================================
		int coreX = 70, coreY = 450;
		int sizeX = 50, sizeY = 50;
		up = new JButton("^");
		down = new JButton("V");
		left = new JButton("<");
		right = new JButton(">");
		fire = new JButton("F");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("up");
			}
		});
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("down");
			}
		});
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("left");
			}
		});
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.walk("right");
			}
		});
		fire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.attack(enemys);
			}
		});
		right.setBounds(coreX + sizeX, coreY, sizeX, sizeY);
		up.setBounds(coreX, coreY - sizeY, sizeX, sizeY);
		down.setBounds(coreX, coreY + sizeY, sizeX, sizeY);
		left.setBounds(coreX - sizeX, coreY, sizeX, sizeY);
		fire.setBounds(coreX, coreY, sizeX, sizeY);
		add(up);
		add(down);
		add(left);
		add(right);
		add(fire);

		// ========================================================
		// Function
		// ========================================================
		// buttonSubmit.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent ae) {
		buttonSubmit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// Restart
				complier.setPointer(0);
				runable = false;
				submit = true;
				line = complier.getPointer();
				textValue = input.getText();

				// normal
				// textValue = "walk(right);walk(down);walk(right);";
				// textValue = "walk(right);walk(right);walk(right);walk(right);walk(right);";
				// textValue =
				// "walk(right);walk(right);walk(right);walk(down);walk(right);walk(right);walk(right);walk(right);";
				// textValue = "walk(right);check(right);";
				// textValue = "check(right);walk(right);";

				// while
				// textValue = "walk(down);while(2){walk(right);walk(right);}";
				// textValue =
				// "walk(right);\nwhile(2){\nwalk(down);\n}walk(right);walk(right);";
				// textValue =
				// "walk(right);while(1){walk(down);}while(3){walk(right);}walk(up);while(3){walk(right);}";
				// textValue = "while(2){walk(down);while(3){walk(right);}};";
				// textValue = "while(check(down)){walk(down);}";

				// if
				// textValue =
				// "walk(right);if(check(right)){walk(right);walk(right);}walk(down);";
				// textValue =
				// "walk(right);if(check(right)){while(2){walk(right);}walk(right);}walk(down);";

				// else
				// textValue = "else{walk(down);}";

				map.setSmoke(false);
				textValue = textValue.replace(" ", "");
				textValue = textValue.replace("\n", "");
				textValue = textValue.replace("\t", "");
				parses = complier.textToParses(textValue);
				tokens = complier.parseToTokens(parses);
				lines = complier.tokenToLines(tokens);
				runable = true;
			}
		});
		buttonClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				newGame();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
			}
		});
		buttonNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				player.setState("next");
			}
		});
		buttonRestart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				readFile.openWrite();
				readFile.write("0");
				readFile.closeWrite();
				mapNumber = 0;
				mapNumberStr = "0";
				mapNumberLabel.setText(mapNumber + "");
				newGame();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
				timing = 0;
			}
		});
		tutorialBackground.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackground.setVisible(false);
				tutorialText.setVisible(false);
				map.setTutorial(false);
			}
		});
		buttonStart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				windowStart = false;
				windowPlay = true;
			}
		});
		buttonStart.setBounds((screenx / 2) - (416 / 2), (screeny / 2) - (234 / 2), 416, 234);

		// ========================================================
		// Position
		// ========================================================
		buttonSubmit.setBounds(buttonLocationX, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonClear.setBounds(buttonLocationX + buttonSizeX * 1, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonNext.setBounds(buttonLocationX + buttonSizeX * 3, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonRestart.setBounds(buttonLocationX + buttonSizeX * 2, buttonLocationY, buttonSizeX, buttonSizeY);
		input.setBounds(11, 10, 195, 332);
		hintTextForm.setBounds(250, 95, 70, 75);
		hintText.setBounds(300, 95, 770, 75);
		mapNumberLabel.setBounds(1010 - 200, 10 + 100, 150, 75);
		tutorialBackground.setBounds((screenx / 2) - (1066 / 2), (screeny / 2) - (600 / 2), 1066, 600);
		tutorialText.setBounds(100, -200, 1066, 600);

		// ========================================================
		// Add
		// ========================================================
		add(tutorialText);
		add(tutorialBackground);
		add(buttonStart);
		add(buttonSubmit);
		add(buttonClear);
		add(buttonNext);
		add(buttonRestart);
		add(input);
		add(hintTextForm);
		add(hintText);
		add(mapNumberLabel);

		// ========================================================
		//
		// ========================================================
		// map = new Map(randMap());
		// mapNumberStr = convMap(mapNumber);
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
		map = new Map(hintText, tutorialBackground, tutorialText, convMap(mapNumber));
		mapNumberLabel.setText(mapNumber + "");
		for (int i = 0; i < map.getRow(); i++) { // debug
			for (int j = 0; j <= map.getColumn(); j++) {
				System.out.print(map.getMap()[i][j]);
			}
			System.out.print("\n");
		}
		player = new Player(map, soundMedia);
		enemys = new ArrayList<Enemy>();
		dummys = new ArrayList<Dummy>();
		firstTime = true;
		attacking = false;
		runable = false;
		stateMapStart = true;
		line = complier.getPointer();
		complier.setState("null");
		player.setState("alive");
	}

	// ========================================================
	// Random Map
	// ========================================================
	public String randMap() {
		mapName = "";
		String tmp = "0000";
		random = new Random();
		int randNumberI = random.nextInt(6 - 1) + 1; // random 1-5
		String randNumberS = randNumberI + "";
		tmp = tmp.concat(randNumberS);
		mapName = tmp.substring(tmp.length() - 4, tmp.length());
		mapNumberStr = mapName;
		return mapName;
	}

	// ========================================================
	// Convert Map Integer to String
	// ========================================================
	public String convMap(int a) {
		mapName = "";
		String tmp = "0000";
		random = new Random();
		String randNumberS = a + "";
		tmp = tmp.concat(randNumberS);
		mapName = tmp.substring(tmp.length() - 4, tmp.length());
		mapNumberStr = mapName;
		return mapName;
	}

	// ========================================================
	// Update
	// ========================================================
	public void update() {
		if (windowStart) {
			desBackground = 0;
			tutorialBackground.setVisible(false);
			tutorialText.setVisible(false);
			input.setVisible(false);
			mapNumberLabel.setVisible(false);
			hintTextForm.setVisible(false);
			hintText.setVisible(false);
			buttonRestart.setVisible(false);
			buttonNext.setVisible(false);
			buttonClear.setVisible(false);
			buttonSubmit.setVisible(false);
		} else if (windowPlay) {
			desBackground = 1;
			buttonStart.setVisible(false);
			input.setVisible(true);
			mapNumberLabel.setVisible(true);
			hintTextForm.setVisible(true);
			hintText.setVisible(true);
			buttonRestart.setVisible(true);
			buttonNext.setVisible(true);
			buttonClear.setVisible(true);
			buttonSubmit.setVisible(true);
			if (stateMapEnd) {
				if (delayMapEnd >= 20) {
					delayMapEnd = 0;
					stateMapEnd = false;
					stateMapStart = false;
				} else {
					delayMapEnd++;
				}
				tutorialBackground.setVisible(false);
				tutorialText.setVisible(false);
				map.setTutorial(false);
			} else {
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
					if (complier.getPointer() < lines.size()) {
						// lines.size()-1
						System.out
								.println("Line: " + complier.getPointer() + "  \t" + lines.get(complier.getPointer()));
						line = complier.getPointer();
						complier.Runable(player, lines);
						line++;
						if (line == (lines.size())) {
							runable = false;
						}
					}
				} else if (player.getState().equals("next")) {
					if (stateMapStart) {
						stateMapEnd = true;
					} else {
						mapNumber++;
						newGame();

						// ========================================================
						// Save file
						// ========================================================
						readFile.openWrite();
						readFile.write(mapNumberStr);
						readFile.closeWrite();

						// ========================================================
						// Score
						// ========================================================
						System.out.println("==============================");
						System.out.println("    SCORE");
						System.out.println("==============================");
						System.out.println(timing);
						timing = 0;
					}
				} else if (player.getState().equals("dead")) {
					player.playerPosition[0] = -99;
				}
				if (delayWalkEnemy > 20) {
					for (int i = 0; i < enemys.size(); i++) {
						enemys.get(i).walk();
					}
					delayWalkEnemy = 0;
				} else {
					delayWalkEnemy++;
				}
				if (delayWalkFireBall > 1) {
					if (attacking) {
						if (fireball != null) {
							if (!fireball.checkNextStep(2, '0')) {
								for (int i = 0; i < enemys.size(); i++) {
									if (enemys.get(i).checkNextStep(1, '4')) {
										enemys.get(i).disable();
										enemys.remove(i);
									}
								}
								for (int i = 0; i < dummys.size(); i++) {
									if (dummys.get(i).checkNextStep(1, '4')) {
										map.setDummy(map.getDummy() - 1);
										if (map.getDummy() == 0) {
											map.setPuzzle(false);
										}
									}
								}
								tmpX = fireball.getX();
								tmpY = fireball.getY();
								fireball.disable();
								fireball = null;
								attacking = false;
								soundMedia.playSound_S("sound/hit.wav");
								hitting = true;
								desBomb = 0;
							}
						}
						if (attacking) {
							for (int i = 0; i < fireballs.size(); i++) {
								fireballs.get(i).walk();
							}
						}
					}
					delayWalkFireBall = 0;
				} else {
					delayWalkFireBall++;
				}
				if (desDirection >= 1) {
					desDirection = 0;
				} else {
					desDirection++;
				}
				if (desSmoke >= 1) {
					desSmoke = 0;
				} else {
					desSmoke++;
				}
				if (desBomb >= 4) {
					desBomb = 0;
					timing++;
				} else {
					desBomb++;
				}
				if (timing < 3) {
					desStar = 0;
				} else if (timing < 6) {
					desStar = 1;
				} else {
					desStar = 2;
				}
				if (map.getTutorial()) {
					tutorialBackground.setVisible(true);
					tutorialText.setVisible(true);
				} else {
					tutorialBackground.setVisible(false);
					tutorialText.setVisible(false);
				}
				// System.out.print(timing);
				map.update(runable, lines);
			}
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
		gr.drawImage(backgrounds[desBackground].getImage(), 0, 0, null);
		// for (int i = 0; i < map.getRow(); i++) {
		// for (int j = 0; j <= map.getColumn(); j++) {
		// System.out.print(map.cheMap(i, j));
		// }
		// System.out.println();
		// }
		if (windowStart) {
		} else if (windowPlay) {
			desBackground = 1;
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
						enemy.draw(gr, desDirection);
						if (firstTime) {
							enemys.add(enemy);
						}
					}
					if (map.getMap()[i][j] == '3') {
						// gr.setColor(Color.GREEN);
						// gr.fillRect(j * scale, i * scale, blockX, blockY);
						bomb = new Bomb((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						bomb.draw(gr, desDirection);
					}
					if (map.getMap()[i][j] == '8') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						portal.draw(gr, desDirection);
					}
					if (map.getMap()[i][j] == '7') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						portal.draw(gr, desDirection + 2);
					}
					if (map.getMap()[i][j] == '6') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						portal.draw(gr, desDirection + 4);
					}
					if (map.getMap()[i][j] == '5') {
						mushroom = new Mushroom((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						mushroom.draw(gr, desDirection);
					}
					if (map.getMap()[i][j] == 'A') {
						mushroom = new Mushroom((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, scale);
						mushroom.draw(gr, desDirection + 2);
					}
					if (map.getMap()[i][j] == '4') {
						fireball = new FireBall(map, scale, (j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
						fireball.draw(gr, desDirection);
						attacking = true;
						fireballs.add(fireball);
					}
					if (map.getMap()[i][j] == 'D') {
						dummy = new Dummy(map, scale, (j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
						dummy.draw(gr, desDirection);
						if (firstTime) {
							dummys.add(dummy);
						}
					}
					if (map.getMap()[i][j] == '9') {
						// gr.setColor(Color.PINK);
						// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
						// (padY * i), blockX,
						// blockY);
						if (player.getMush().equals("chun-li")) {
							player.draw(gr, desDirection + 4, locationX, locationY, padX, padY);
						} else if (player.getMush().equals("ken")) {
							player.draw(gr, desDirection + 2, locationX, locationY, padX, padY);
						} else {
							player.draw(gr, desDirection, locationX, locationY, padX, padY);
						}
					}
				}
			}
			if (hitting) {
				gr.drawImage(effectBombs[desBomb].getImage(), tmpX - 118, tmpY - 74, null);
				// (456, 294) /2 = (228,
				// 147) /2 =
				// (118, 74)
				if (desBomb >= 4) {
					hitting = false;
				}
			}
			if (map.getSmoke()) {
				gr.drawImage(effectSmokes[desSmoke].getImage(), 230, 150, null);
			}
			if (stateMapEnd) {
				gr.drawImage(effectStars[desStar].getImage(), (screenx / 2) - (1066 / 2), (screeny / 2) - (600 / 2),
						null);
			}
			firstTime = false;
		}
		update();
		g.drawImage(screen, 0, 0, null);
		// g.drawImage(effectBombs[0].getImage(), 100, 100, null); // insert picture
		// .gif
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
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
	private int screenx; // 1115 // 1280
	private int screeny; // 638 // 500
	private Image screen;
	private ImageIcon bg;
	// ========================================================
	// Variable
	// ========================================================
	private int locationX = 110, locationY = 230;
	private int blockX = 50, blockY = 50;
	private int padX = 15, padY = 45;

	// Function
	private Random random;

	// Object
	private Map map;
	private Player player;
	private Complier complier;
	private Bomb bomb;
	private Portal portal;
	private Mushroom mushroom;
	private Enemy enemy;
	private Dummy dummy;
	private FireBall fireball;
	private ReadFile readFile;
	public static PlaySound soundMedia;

	// Complier
	private JTextArea input;
	private String textValue;
	private int line;
	private ArrayList<String> parses, tokens;
	public static ArrayList<String> lines;
	public static boolean runable;

	// Button
	private JLabel buttonSubmit, buttonClear, buttonRestart, buttonNext, buttonStart;
	private int buttonLocationX = 280, buttonLocationY = 0;
	private int buttonSizeX = 182, buttonSizeY = 103;

	// Store
	private ArrayList<Dummy> dummys;
	public static ArrayList<Enemy> enemys;

	// Update
	private int direction, chooseStart;
	private int delayA, delayB, delayMapEnd;
	private int timing;
	private int effectBoom, effectBoomLcationX, effectBoomLcationY;
	private boolean firstMake, attacking, hitting, starting, playing;

	// Map
	private String mapNow;
	private int mapNumber;
	private JLabel mapNumberLabel, objectiveLabel, objectiveLabelForm;
	private JLabel tutorialBackground;
	private JLabel tutorialText;
	private boolean mapStateEnd = false;
	private boolean mapStateFirst = true;
	public static String mapNummberSave;

	// Image
	private ImageIcon[] imageBooms;
	private ImageIcon[] imageStars;
	private ImageIcon[] imageSmokes;

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
		Font f1 = new Font("SansSerif", Font.BOLD, 20);
		complier = new Complier();
		runable = false;
		line = 0;
		soundMedia = new PlaySound();
		soundMedia.playSound_L("sound/bgm.wav", 999);

		// ========================================================
		// init
		// ========================================================
		starting = true;
		playing = false;
		direction = 0;
		delayA = 0;
		delayB = 0;

		input = new JTextArea("");
		input.setBackground(new Color(70, 220, 90));
		input.setFont(f1);
		input.setLineWrap(true);
		mapNumberLabel = new JLabel(mapNummberSave);
		mapNumberLabel.setFont(new Font("Serif", Font.PLAIN, 75));
		mapNumberLabel.setForeground(Color.BLACK);
		objectiveLabelForm = new JLabel("Hint: ");
		objectiveLabelForm.setBackground(new Color(70, 220, 90));
		objectiveLabelForm.setFont(f1);
		objectiveLabel = new JLabel("");
		objectiveLabel.setBackground(new Color(70, 220, 90));
		objectiveLabel.setFont(f1);
		tutorialText = new JLabel("");
		tutorialText.setBackground(new Color(70, 220, 90));
		tutorialText.setFont(f1);

		// imageBooms[0] = new ImageIcon("icon/kaboom.gif");
		imageBooms = new ImageIcon[5];
		imageSmokes = new ImageIcon[2];
		imageStars = new ImageIcon[3];
		imageBooms[0] = new ImageIcon("icon/effectBoom/kaboom_1.png");
		imageBooms[1] = new ImageIcon("icon/effectBoom/kaboom_2.png");
		imageBooms[2] = new ImageIcon("icon/effectBoom/kaboom_3.png");
		imageBooms[3] = new ImageIcon("icon/effectBoom/kaboom_4.png");
		imageBooms[4] = new ImageIcon("icon/effectBoom/kaboom_5.png");
		imageSmokes[0] = new ImageIcon("icon/smoke.png");
		imageSmokes[1] = new ImageIcon("icon/smoke.png");
		imageStars[0] = new ImageIcon("icon/2-star.png");
		imageStars[1] = new ImageIcon("icon/2-star.png");
		imageStars[2] = new ImageIcon("icon/2-star.png");
		tutorialBackground = new JLabel(new ImageIcon("icon/hint.png"));
		buttonStart = new JLabel(new ImageIcon("icon/button_start.png"));
		buttonSubmit = new JLabel(new ImageIcon("icon/button_submit.png"));
		buttonClear = new JLabel(new ImageIcon("icon/button_clear.png"));
		buttonNext = new JLabel(new ImageIcon("icon/button_next.png"));
		buttonRestart = new JLabel(new ImageIcon("icon/button_restart.png"));

		// ========================================================
		// Save file
		// ========================================================
		readFile = new ReadFile();
		readFile.OpenFile_read();
		readFile.ReadFile();
		readFile.CloseFile_read();
		mapNumber = Integer.parseInt(mapNummberSave);
		mapNumberLabel.setText(mapNummberSave);

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
				player.attack();
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
		//
		// ========================================================
		// buttonSubmit.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent ae) {
		buttonSubmit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// Restart
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
				textValue = input.getText();

				// normal
				// textValue = "walk(right);walk(down);walk(right);";
				// textValue = "walk(right);walk(right);walk(right);walk(right);walk(right);";
				// textValue =
				// "walk(right);walk(right);walk(right);walk(down);walk(right);walk(right);walk(right);walk(right);";
				// textValue = "walk(right);check(right);";
				// textValue = "check(right);walk(right);";
				textValue = "walk(down);attack();walk(down);attack();walk(down);attack();";

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
				map = new Map(objectiveLabel, tutorialBackground, tutorialText, mapNow);
				newGame();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
			}
		});
		buttonNext.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				player.setState("buttonNext");
			}
		});
		buttonRestart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				readFile.OpenFile_write();
				readFile.AddRecord("0");
				readFile.CloseFile_write();
				mapNumber = 0;
				mapNummberSave = "0";
				map = new Map(objectiveLabel, tutorialBackground, tutorialText, convMap(mapNumber));
				mapNumberLabel.setText(mapNummberSave);
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
				map.setHint(false);
			}
		});
		buttonStart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				starting = false;
				playing = true;
			}
		});

		buttonRestart.setBounds(buttonLocationX + buttonSizeX * 2, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonNext.setBounds(buttonLocationX + buttonSizeX * 3, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonClear.setBounds(buttonLocationX + buttonSizeX * 1, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonSubmit.setBounds(buttonLocationX, buttonLocationY, buttonSizeX, buttonSizeY);

		tutorialBackground.setBounds((screenx / 2) - (1066 / 2), (screeny / 2) - (600 / 2), 1066, 600);
		tutorialText.setBounds(100, -200, 1066, 600);
		input.setBounds(11, 10, 195, 332);
		mapNumberLabel.setBounds(1010, 10, 150, 75);
		objectiveLabelForm.setBounds(250, 95, 70, 75);
		objectiveLabel.setBounds(300, 95, 770, 75);

		buttonStart.setBounds((screenx / 2) - (416 / 2), (screeny / 2) - (234 / 2), 416, 234);

		add(buttonStart);
		add(tutorialText);
		add(tutorialBackground);

		add(input);
		add(mapNumberLabel);
		add(objectiveLabelForm);
		add(objectiveLabel);

		add(buttonRestart);
		add(buttonNext);
		add(buttonClear);
		add(buttonSubmit);
		// map = new Map(randMap());
		// map = new Map(objectiveLabel, tutorialBackground, tutorialText, convMap(mapNumber));
		// mapNow = convMap(mapNumber);
		newGame();
		starting = false;
		playing = true;
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
		map = new Map(objectiveLabel, tutorialBackground, tutorialText, convMap(mapNumber));
		map.printMap();
		player = new Player(map, scale);
		enemys = new ArrayList<Enemy>();
		dummys = new ArrayList<Dummy>();
		firstMake = true;
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
		mapNow = mapName;
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
		mapNow = mapName;
		return mapName;
	}

	// ========================================================
	// Update
	// ========================================================
	public void update() {
		if (starting) {
			bg = new ImageIcon("icon/background_start.png");
			tutorialBackground.setVisible(false);
			tutorialText.setVisible(false);
			input.setVisible(false);
			mapNumberLabel.setVisible(false);
			objectiveLabelForm.setVisible(false);
			objectiveLabel.setVisible(false);
			buttonRestart.setVisible(false);
			buttonNext.setVisible(false);
			buttonClear.setVisible(false);
			buttonSubmit.setVisible(false);
		} else if (playing) {
			buttonStart.setVisible(false);
			input.setVisible(true);
			mapNumberLabel.setVisible(true);
			objectiveLabelForm.setVisible(true);
			objectiveLabel.setVisible(true);
			buttonRestart.setVisible(true);
			buttonNext.setVisible(true);
			buttonClear.setVisible(true);
			buttonSubmit.setVisible(true);
			bg = new ImageIcon("icon/background.png");
			if (mapStateEnd) {
				if (delayMapEnd >= 20) {
					delayMapEnd = 0;
					mapStateEnd = false;
					mapStateFirst = false;
				} else {
					delayMapEnd++;
				}
			} else {
				if (runable && player.getState().equals("live")) {
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
						System.out
								.println("Line: " + complier.getPointer() + "  \t" + lines.get(complier.getPointer()));
						line = complier.getPointer();
						complier.Runable(player, lines);
						// line++;
						if (line == (lines.size())) {
							runable = false;
						}
					}
					// ========================================================
					// Enemy Delay
					// ========================================================
					if (delayA > 20) {
						for (int i = 0; i < enemys.size(); i++) {
							enemys.get(i).walk();
						}
						delayA = 0;
					} else {
						delayA++;
					}
					if (delayB > 1) {
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
									effectBoomLcationX = fireball.getX();
									effectBoomLcationY = fireball.getY();
									fireball.disable();
									fireball = null;
									attacking = false;
									soundMedia.playSound_S("sound/hit.wav");
									hitting = true;
									effectBoom = 0;
								}
							}
							if (attacking) {
								fireball.walk();
							}
						}
						delayB = 0;
					} else {
						delayB++;
					}
					// ========================================================
					//
					// ========================================================
				} else if (player.getState().equals("next")) {
					if (mapStateFirst) {
						mapStateEnd = true;
					} else {
						tutorialBackground.setVisible(false);
						tutorialText.setVisible(false);
						map.setHint(false);
						mapNumber++;
						map = new Map(objectiveLabel, tutorialBackground, tutorialText, convMap(mapNumber));
						newGame();
						mapNumberLabel.setText(mapNumber + "");
						complier.setPointer(0);
						complier.setExp(true);
						complier.setIf(false);
						complier.setState("null");
						runable = false;
						line = complier.getPointer();
						player.setState("live");

						// ========================================================
						// Save file
						// ========================================================
						mapNummberSave = mapNumber + "";
						mapNumberLabel.setText(mapNummberSave);
						readFile.OpenFile_write();
						readFile.AddRecord(mapNummberSave);
						readFile.CloseFile_write();

						// ========================================================
						// Score
						// ========================================================
						System.out.println("==============================");
						System.out.println("    SCORE");
						System.out.println("==============================");
						System.out.println(timing);
						timing = 0;
						mapStateFirst = true;
					}
				} else if (player.getState().equals("dead")) {
					player.selfPosition[0] = -99;
				}
				if (direction >= 1) {
					direction = 0;
				} else {
					direction++;
				}
				if (effectBoom >= 4) {
					effectBoom = 0;
					timing++;
				} else {
					effectBoom++;
				}
				if (timing < 3) {
					chooseStart = 0;
				} else if (timing < 6) {
					chooseStart = 1;
				} else {
					chooseStart = 2;
				}
				if (map.getHint()) {
					tutorialBackground.setVisible(true);
					tutorialText.setVisible(true);
				} else {
					tutorialBackground.setVisible(false);
					tutorialText.setVisible(false);
				}
				// System.out.print(timing);
			}
			map.update();
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
		if (starting) {
			bg = new ImageIcon("icon/background_start.png");
		} else if (playing) {
			bg = new ImageIcon("icon/background.png");
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
						enemy.draw(gr, direction);
						if (firstMake) {
							enemys.add(enemy);
						}
					}
					if (map.getMap()[i][j] == '3') {
						// gr.setColor(Color.GREEN);
						// gr.fillRect(j * scale, i * scale, blockX, blockY);
						bomb = new Bomb((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						bomb.draw(gr, direction);
					}
					if (map.getMap()[i][j] == '8') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						portal.draw(gr, direction);
					}
					if (map.getMap()[i][j] == '7') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						portal.draw(gr, direction + 2);
					}
					if (map.getMap()[i][j] == '6') {
						portal = new Portal((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						portal.draw(gr, direction + 4);
					}
					if (map.getMap()[i][j] == '5') {
						mushroom = new Mushroom((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						mushroom.draw(gr, direction);
					}
					if (map.getMap()[i][j] == 'A') {
						mushroom = new Mushroom((j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						mushroom.draw(gr, direction + 2);
					}
					if (map.getMap()[i][j] == '4') {
						fireball = new FireBall(map, scale, (j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
						fireball.draw(gr, direction);
						attacking = true;
					}
					if (map.getMap()[i][j] == 'D') {
						dummy = new Dummy(map, (j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
						dummy.draw(gr, direction);
						if (firstMake) {
							dummys.add(dummy);
						}
					}
					if (map.getMap()[i][j] == '9') {
						// gr.setColor(Color.PINK);
						// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
						// (padY * i), blockX,
						// blockY);
						if (player.getMush().equals("chun-li")) {
							player.draw(gr, direction + 4, locationX, locationY, padX, padY);
						} else if (player.getMush().equals("ken")) {
							player.draw(gr, direction + 2, locationX, locationY, padX, padY);
						} else {
							player.draw(gr, direction, locationX, locationY, padX, padY);
						}
					}
				}
			}
			if (hitting) {
				gr.drawImage(imageBooms[effectBoom].getImage(), effectBoomLcationX - 118, effectBoomLcationY - 74,
						null);
				// (456,
				// 294)
				// /2
				// =
				// (228,
				// 147)
				// /2
				// =
				// (118, 74)
				if (effectBoom >= 4) {
					hitting = false;
				}
			}
			if (map.getSmoke()) {
				gr.drawImage(imageSmokes[direction].getImage(), 230, 150, null);
			}
			if (mapStateEnd) {
				gr.drawImage(imageStars[chooseStart].getImage(), (screenx / 2) - (1066 / 2), (screeny / 2) - (600 / 2),
						null);
			}
			firstMake = false;
		}
		update();
		g.drawImage(screen, 0, 0, null);
		// g.drawImage(imageBooms[0].getImage(), 100, 100, null); // insert picture .gif
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
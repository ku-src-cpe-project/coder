// Version 0.0.1

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
// import java.applet.*;
// import java.util.*;
import java.util.ArrayList;
// import java.util.StringTokenizer;
import java.io.*;

// import javax.media.sampled.*;
import java.awt.Color;
// import java.util.Collections;
// import java.util.List;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Image;
// =============================================================================
// Timer Import
// =============================================================================
// import java.text.SimpleDateFormat;
// import java.util.Calendar;
// import java.util.Timer;
// import java.util.TimerTask;
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
	private int locationX = 110, locationY = 260;
	private int blockX = 50, blockY = 50;
	private int padX = 15, padY = 45;
	private float multipleFrameX = 15.0f;
	private float multipleFrameY = 9.0f;
	private float multipleFrameZ = 2.5f;
	private int volumeMin = 0, volumeMax = 100, volumeInit = 50;
	public static String language;

	// Function
	private Random random;

	// Object
	private Map map, mapTmp;
	private MapStore mapStore;
	private Player player;
	private Complier complier;
	private Bomb bomb;
	private Portal portal;
	private Mushroom mushroom;
	private Enemy enemy;
	private Dummy dummy;
	private Treasure treasure;
	private FireBall fireball;
	private ReadFile readFile;
	public static PlaySound soundMedia;

	// Complier
	// private JTextArea input;
	public static JTextArea input;
	private String textValue;
	private int line;
	private ArrayList<String> parses, tokens;
	public static ArrayList<String> lines;
	public static boolean runable, processing;

	// Button
	private JLabel buttonSubmit, buttonClear, buttonRestart, buttonNext, buttonStart, buttonLoad, buttonExit,
			buttonTutorialWalk, buttonTutorialAttack, buttonTutorialFor, buttonTutorialWhile, buttonTutorialIf,
			buttonTutorialSearch, buttonLanguageEN, buttonLanguageTH;
	private int buttonLocationX = 250, buttonLocationY = 8;
	private int buttonSizeX = 250, buttonSizeY = 83;
	private int buttonStartSizeX = 400, buttonStartSizeY = 133;
	private int buttonSubmitSizeX = 120, buttonSubmitSizeY = 120;
	private int buttonSizeLanguage = 50;
	private int buttonTutorialLocationX = 20, buttonTutorialLocationY = 376;
	private int buttonTutorialSizeX = 90, buttonTutorialSizeY = 30, buttonTutorialPadX = buttonTutorialSizeX + 20,
			buttonTutorialPadY = buttonTutorialSizeY + 14;
	private JLabel tutorialBackgroundWalk, tutorialBackgroundAttack, tutorialBackgroundFor, tutorialBackgroundWhile,
			tutorialBackgroundIf, tutorialBackgroundSearch;
	private JSlider volume;

	// Store
	private ArrayList<MapStore> mapStores;
	private ArrayList<Path> paths;
	private ArrayList<Wall> walls;
	private ArrayList<Dummy> dummys;
	private ArrayList<Bomb> bombs;
	private ArrayList<Portal> portal6s;
	private ArrayList<Portal> portal7s;
	private ArrayList<Portal> portal8s;
	private ArrayList<Mushroom> mushroom5s;
	private ArrayList<Mushroom> mushroomAs;
	private ArrayList<Question> questions;
	public static ArrayList<Treasure> treasures;
	public static ArrayList<Enemy> enemys;

	// Update
	private int direction, chooseStart;
	private int delayA, delayB, delayMapEnd;
	private int timing, frameCount = 4;
	private int effectBoom, effectBoomLcationX, effectBoomLcationY;
	private boolean firstMake, hitting, starting, playing, loading;
	public static boolean attacking, creating;
	public static int frameA, frameB, frameC;

	// Map
	private int mapTotal = 30;
	private String mapNow;
	private JLabel mapNumberLabel;
	private JLabel tutorialBackground;
	private JTextArea objectiveLabel;
	private boolean mapStateEnd = false;
	private boolean mapStateFirst = true;
	public static int mapNumber;
	public static String mapNummberSave;

	// Image
	private int starSizeX = 224, starSizeY = 224;
	private ImageIcon[] imageBooms;
	private ImageIcon[] imageStars;
	private ImageIcon[] imageSmokes;
	private ImageIcon[] imageQuestions;

	// ========================================================
	// Debug
	// ========================================================
	private JButton up, down, left, right, fire, print, checkLeft, checkRight, checkUp, checkDown;

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
		bg = new ImageIcon("src/background/starting.png");
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
		soundMedia.playSoundLoop("media/bgm.wav");
		language = "EN";

		// ========================================================
		// init
		// ========================================================
		mapStores = new ArrayList<MapStore>();
		starting = true;
		loading = false;
		playing = false;
		direction = 0;
		delayA = 0;
		delayB = 0;

		input = new JTextArea("");
		// input.setBackground(new Color(70, 220, 90));
		input.setFont(f1);
		input.setLineWrap(true);
		mapNumberLabel = new JLabel(mapNummberSave);
		mapNumberLabel.setFont(new Font("Serif", Font.PLAIN, 75));
		mapNumberLabel.setForeground(Color.BLACK);
		objectiveLabel = new JTextArea("");
		objectiveLabel.setBackground(new Color(70, 220, 90));
		objectiveLabel.setFont(f1);
		objectiveLabel.setEditable(false);
		objectiveLabel.setLineWrap(true);

		// Sound Volume Bar
		volume = new JSlider(JSlider.VERTICAL, volumeMin, volumeMax, volumeInit);

		imageBooms = new ImageIcon[6];
		imageSmokes = new ImageIcon[6];
		imageQuestions = new ImageIcon[6];
		imageStars = new ImageIcon[3];
		imageBooms[0] = new ImageIcon("src/effect/boom/1.png");
		imageBooms[1] = new ImageIcon("src/effect/boom/2.png");
		imageBooms[2] = new ImageIcon("src/effect/boom/3.png");
		imageBooms[3] = new ImageIcon("src/effect/boom/4.png");
		imageBooms[4] = new ImageIcon("src/effect/boom/5.png");
		imageBooms[5] = new ImageIcon("src/effect/boom/6.png");
		imageStars[0] = new ImageIcon("src/etc/star/1.png");
		imageStars[1] = new ImageIcon("src/etc/star/2.png");
		imageStars[2] = new ImageIcon("src/etc/star/3.png");
		tutorialBackground = new JLabel(new ImageIcon("src/etc/tutorial/1.png"));
		buttonStart = new JLabel(new ImageIcon("src/button/button_start.png"));
		buttonLoad = new JLabel(new ImageIcon("src/button/button_load.png"));
		buttonSubmit = new JLabel(new ImageIcon("src/button/button_submit.png"));
		buttonClear = new JLabel(new ImageIcon("src/button/button_clear.png"));
		buttonNext = new JLabel(new ImageIcon("src/button/button_next.png"));
		buttonRestart = new JLabel(new ImageIcon("src/button/button_restart.png"));
		buttonExit = new JLabel(new ImageIcon("src/button/button_exit.png"));
		buttonTutorialWalk = new JLabel(new ImageIcon("src/button/button_tutorial_walk.png"));
		buttonTutorialAttack = new JLabel(new ImageIcon("src/button/button_tutorial_attack.png"));
		buttonTutorialFor = new JLabel(new ImageIcon("src/button/button_tutorial_for.png"));
		buttonTutorialWhile = new JLabel(new ImageIcon("src/button/button_tutorial_while.png"));
		buttonTutorialIf = new JLabel(new ImageIcon("src/button/button_tutorial_if.png"));
		buttonTutorialSearch = new JLabel(new ImageIcon("src/button/button_tutorial_search.png"));
		tutorialBackgroundWalk = new JLabel(new ImageIcon("src/tutorial/tutorial_walk.gif"));
		tutorialBackgroundAttack = new JLabel(new ImageIcon("src/tutorial/tutorial_attack.gif"));
		tutorialBackgroundFor = new JLabel(new ImageIcon("src/tutorial/tutorial_for.gif"));
		tutorialBackgroundWhile = new JLabel(new ImageIcon("src/tutorial/tutorial_while.gif"));
		tutorialBackgroundIf = new JLabel(new ImageIcon("src/tutorial/tutorial_if.gif"));
		tutorialBackgroundSearch = new JLabel(new ImageIcon("src/tutorial/tutorial_search.gif"));

		// Button Change Language
		buttonLanguageEN = new JLabel(new ImageIcon("src/button/button_language_EN.png"));
		buttonLanguageTH = new JLabel(new ImageIcon("src/button/button_language_TH.png"));

		// ========================================================
		// Save file
		// ========================================================
		readFile = new ReadFile();
		readFile.openFileRead();
		readFile.ReadFile();
		readFile.closeFileRead();
		mapNumber = Integer.parseInt(mapNummberSave);
		mapNumberLabel.setText(mapNummberSave);

		// ========================================================
		// Debug
		// ========================================================
		int coreX = 70, coreY = 450;
		int core2X = 70, core2Y = 300;
		int sizeX = 50, sizeY = 50;
		up = new JButton("^");
		down = new JButton("V");
		left = new JButton("<");
		right = new JButton(">");
		fire = new JButton("F");
		print = new JButton("P");
		checkLeft = new JButton("CL");
		checkRight = new JButton("CR");
		checkUp = new JButton("CU");
		checkDown = new JButton("CD");
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
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				map.printMap();
			}
		});
		checkLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.search("left");
			}
		});
		checkRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.search("right");
			}
		});
		checkUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.search("up");
			}
		});
		checkDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				player.search("down");
			}
		});
		right.setBounds(coreX + sizeX, coreY, sizeX, sizeY);
		up.setBounds(coreX, coreY - sizeY, sizeX, sizeY);
		down.setBounds(coreX, coreY + sizeY, sizeX, sizeY);
		left.setBounds(coreX - sizeX, coreY, sizeX, sizeY);
		fire.setBounds(coreX, coreY, sizeX, sizeY);
		print.setBounds(coreX + sizeX, coreY + sizeY, sizeX, sizeY);
		checkRight.setBounds(coreX + sizeX, core2Y, sizeX, sizeY);
		checkUp.setBounds(coreX, core2Y - sizeY, sizeX, sizeY);
		checkDown.setBounds(coreX, core2Y + sizeY, sizeX, sizeY);
		checkLeft.setBounds(coreX - sizeX, core2Y, sizeX, sizeY);
		// add(up);
		// add(down);
		// add(left);
		// add(right);
		// add(fire);
		// add(print);
		// add(checkLeft);
		// add(checkRight);
		// add(checkUp);
		// add(checkDown);

		// ========================================================
		// Starting
		// ========================================================
		buttonStart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				starting = false;
				loading = false;
				playing = true;
				newGame();
			}
		});
		buttonLoad.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				starting = false;
				loading = true;
				playing = false;
			}
		});

		// ========================================================
		// Loading
		// ========================================================
		for (int i = 0; i < mapTotal; i++) {
			MapStore mapStore = new MapStore(this, i);
			mapStores.add(mapStore);
			mapStores.get(i).getMapStoreText().setVisible(false);
			mapStores.get(i).getMapStoreBackground().setVisible(false);
		}

		// ========================================================
		// Playing
		// ========================================================
		buttonSubmit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// Restart
				newGame();
				tutorialBackground.setVisible(false);
				map.setTutorial(false);
				complier = new Complier();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
				textValue = input.getText();
				textValue = textValue.replace(" ", "");
				textValue = textValue.replace("\n", "");
				textValue = textValue.replace("\t", "");
				parses = complier.textToParses(textValue);
				tokens = complier.parseToTokens(parses);
				lines = complier.tokenToLines(tokens);
				runable = true;
				processing = true;
			}
		});
		buttonClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// map = new Map(objectiveLabel, tutorialBackground, mapNow);
				newGame();
				// complier.setPointer(0);
				complier = new Complier();
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
				readFile.openFileWrite();
				readFile.write("0");
				readFile.closeFileWrite();
				mapNumber = 0;
				mapNummberSave = "0";
				map = new Map(objectiveLabel, tutorialBackground, convMap(mapNumber), enemys);
				mapNumberLabel.setText(mapNummberSave);
				newGame();
				complier.setPointer(0);
				runable = false;
				line = complier.getPointer();
				timing = 0;
			}
		});
		buttonExit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				starting = true;
				loading = false;
				playing = false;
			}
		});
		tutorialBackground.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackground.setVisible(false);
				map.setTutorial(false);
			}
		});
		buttonTutorialWalk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundWalk.setVisible(true);
			}
		});
		buttonTutorialAttack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundAttack.setVisible(true);
			}
		});
		buttonTutorialFor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundFor.setVisible(true);
			}
		});
		buttonTutorialWhile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundWhile.setVisible(true);
			}
		});
		buttonTutorialIf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundIf.setVisible(true);
			}
		});
		buttonTutorialSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundSearch.setVisible(true);
			}
		});
		tutorialBackgroundWalk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundWalk.setVisible(false);
			}
		});
		tutorialBackgroundAttack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundAttack.setVisible(false);
			}
		});
		tutorialBackgroundFor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundFor.setVisible(false);
			}
		});
		tutorialBackgroundWhile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundWhile.setVisible(false);
			}
		});
		tutorialBackgroundIf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundIf.setVisible(false);
			}
		});
		tutorialBackgroundSearch.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				tutorialBackgroundSearch.setVisible(false);
			}
		});
		volume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = volume.getValue();
				soundMedia.update(value);
			}
		});
		buttonLanguageEN.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				buttonStart.setIcon(new ImageIcon("src/button/button_start.png"));
				buttonLoad.setIcon(new ImageIcon("src/button/button_load.png"));
				buttonClear.setIcon(new ImageIcon("src/button/button_clear.png"));
				buttonExit.setIcon(new ImageIcon("src/button/button_exit.png"));
			}
		});
		buttonLanguageTH.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				buttonStart.setIcon(new ImageIcon("src/button/button_start_th.png"));
				buttonLoad.setIcon(new ImageIcon("src/button/button_load_th.png"));
				buttonClear.setIcon(new ImageIcon("src/button/button_clear_th.png"));
				buttonExit.setIcon(new ImageIcon("src/button/button_exit_th.png"));
				language = "TH";
			}
		});

		buttonStart.setBounds((screenx / 2) - (buttonStartSizeX / 2), (screeny / 2) - (buttonStartSizeY / 2) + 200,
				buttonStartSizeX, buttonStartSizeY);
		buttonLoad.setBounds(buttonLocationX + buttonSizeX * 1, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonSubmit.setBounds(screenx - buttonSubmitSizeX - 10, 85, buttonSubmitSizeX, buttonSubmitSizeY);
		buttonClear.setBounds(buttonLocationX + buttonSizeX * 0, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonNext.setBounds(buttonLocationX + buttonSizeX * 2 - 100, buttonLocationY + buttonSizeY, buttonSizeX,
				buttonSizeY);
		buttonRestart.setBounds(buttonLocationX + buttonSizeX * 1, buttonLocationY, buttonSizeX, buttonSizeY);
		buttonExit.setBounds(buttonLocationX + buttonSizeX * 2, buttonLocationY, buttonSizeX, buttonSizeY);
		tutorialBackground.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx, screeny);
		input.setBounds(18, 12, 207, 332);
		mapNumberLabel.setBounds(235, 110, 120, 120);
		mapNumberLabel.setHorizontalAlignment(JLabel.CENTER);
		objectiveLabel.setBounds(380, 105, 275, 135);
		objectiveLabel.setOpaque(false);
		buttonTutorialWalk.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 0),
				buttonTutorialLocationY + (buttonTutorialPadY * 0), buttonTutorialSizeX, buttonTutorialSizeY);
		buttonTutorialAttack.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 0),
				buttonTutorialLocationY + (buttonTutorialPadY * 1), buttonTutorialSizeX, buttonTutorialSizeY);
		buttonTutorialFor.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 0),
				buttonTutorialLocationY + (buttonTutorialPadY * 2), buttonTutorialSizeX, buttonTutorialSizeY);
		buttonTutorialWhile.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 0),
				buttonTutorialLocationY + (buttonTutorialPadY * 3), buttonTutorialSizeX, buttonTutorialSizeY);
		buttonTutorialIf.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 0),
				buttonTutorialLocationY + (buttonTutorialPadY * 4), buttonTutorialSizeX, buttonTutorialSizeY);
		buttonTutorialSearch.setBounds(buttonTutorialLocationX + (buttonTutorialPadX * 1),
				buttonTutorialLocationY + (buttonTutorialPadY * 0), buttonTutorialSizeX, buttonTutorialSizeY);
		tutorialBackgroundWalk.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx,
				screeny);
		tutorialBackgroundAttack.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx,
				screeny);
		tutorialBackgroundFor.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx, screeny);
		tutorialBackgroundWhile.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx,
				screeny);
		tutorialBackgroundIf.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx, screeny);
		tutorialBackgroundSearch.setBounds((screenx / 2) - (screenx / 2), (screeny / 2) - (screeny / 2), screenx,
				screeny);
		volume.setBounds(0, screeny - 100, 20, 100);
		buttonLanguageEN.setBounds(screenx - buttonSizeLanguage, screeny - buttonSizeLanguage, buttonSizeLanguage,
				buttonSizeLanguage);
		buttonLanguageTH.setBounds(screenx - (buttonSizeLanguage * 2), screeny - buttonSizeLanguage, buttonSizeLanguage,
				buttonSizeLanguage);

		add(tutorialBackground);
		add(tutorialBackgroundWalk);
		add(tutorialBackgroundAttack);
		add(tutorialBackgroundFor);
		add(tutorialBackgroundWhile);
		add(tutorialBackgroundIf);
		add(tutorialBackgroundSearch);
		add(buttonStart);
		add(buttonLoad);
		add(volume);
		add(buttonLanguageEN);
		add(buttonLanguageTH);
		add(input);
		add(mapNumberLabel);
		add(objectiveLabel);
		add(buttonRestart);
		add(buttonNext);
		add(buttonExit);
		add(buttonClear);
		add(buttonSubmit);
		add(buttonTutorialWalk);
		add(buttonTutorialAttack);
		add(buttonTutorialFor);
		add(buttonTutorialWhile);
		add(buttonTutorialIf);
		add(buttonTutorialSearch);

		// ========================================================
		// Shortcut Starting
		// ========================================================
		starting = false;
		loading = false;
		playing = true;
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
		// mapNumber = 3;
		setPreferredSize(new Dimension(screenx, screeny));
		map = new Map(objectiveLabel, tutorialBackground, convMap(mapNumber), enemys);
		mapTmp = new Map(objectiveLabel, tutorialBackground, convMap(mapNumber), enemys);
		mapNumberLabel.setText(mapNumber + "");
		map.printMap();
		player = new Player(map, scale);
		paths = new ArrayList<Path>();
		walls = new ArrayList<Wall>();
		enemys = new ArrayList<Enemy>();
		dummys = new ArrayList<Dummy>();
		bombs = new ArrayList<Bomb>();
		portal6s = new ArrayList<Portal>();
		portal7s = new ArrayList<Portal>();
		portal8s = new ArrayList<Portal>();
		mushroom5s = new ArrayList<Mushroom>();
		mushroomAs = new ArrayList<Mushroom>();
		treasures = new ArrayList<Treasure>();
		questions = new ArrayList<Question>();
		line = complier.getPointer();
		runable = false;
		processing = false;
		firstMake = true;
		for (int i = 0; i < enemys.size(); i++) {
			enemys.get(i).setWalking(false);
		}
		attacking = false;
		creating = false;
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
	// Create Path Map
	// ========================================================
	public void makePath(int i, int j) {
		Path path = new Path((j * scale) + locationX + (padX * i), (i * scale) + locationY - (padY * i) - 143 + 50, i,
				j);
		paths.add(path);
	}

	// ========================================================
	// Create Object Map
	// ========================================================
	public void makeObject() {
		fireball = new FireBall(map, 0, 0, (0 * scale) + locationX + (padX * 0),
				(0 * scale) + locationY - (padY * 0) - 143 + 50);
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j <= map.getColumn(); j++) {
				if (map.getMap()[i][j] == '0') {
				} else if (map.getMap()[i][j] == '1') {
				} else if (map.getMap()[i][j] == '2') {
					Enemy enemy = new Enemy(map, scale, i, j);
					enemys.add(enemy);
				} else if (map.getMap()[i][j] == '3') {
					Bomb bomb = new Bomb((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					bombs.add(bomb);
				} else if (map.getMap()[i][j] == '4') {
				} else if (map.getMap()[i][j] == '5') {
					Mushroom mushroom = new Mushroom((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					mushroom5s.add(mushroom);
				} else if (map.getMap()[i][j] == '6') {
					Portal portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					portal6s.add(portal);
				} else if (map.getMap()[i][j] == '7') {
					Portal portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					portal7s.add(portal);
				} else if (map.getMap()[i][j] == '8') {
					Portal portal = new Portal((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					portal8s.add(portal);
				} else if (map.getMap()[i][j] == '9') {
				} else if (map.getMap()[i][j] == 'A') {
					Mushroom mushroom = new Mushroom((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					mushroomAs.add(mushroom);
				} else if (map.getMap()[i][j] == 'D') {
					Dummy dummy = new Dummy(map, scale, i, j);
					dummys.add(dummy);
				} else if (map.getMap()[i][j] == 'Q') {
					Question question = new Question((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					questions.add(question);
					Treasure treasure = new Treasure(map, scale, i, j);
					treasures.add(treasure);
				} else if (map.getMap()[i][j] == 'T') {
					Treasure treasure = new Treasure(map, scale, i, j);
					treasures.add(treasure);
				} else if (map.getMap()[i][j] == 'W') {
					Wall wall = new Wall((j * scale) + locationX + (padX * i),
							(i * scale) + locationY - (padY * i) - 143 + 50, i, j);
					walls.add(wall);
				}
				if (mapTmp.getMap()[i][j] != '1') {
					makePath(i, j);
					mapTmp.setMap(i, j, '0');
				}
			}
		}
	}

	// ========================================================
	// Draw Path Map
	// ========================================================
	public void drawPath(Graphics gr) {
		for (int i = 0; i < mapTmp.getRow(); i++) {
			for (int j = 0; j <= mapTmp.getColumn(); j++) {
				if (mapTmp.getMap()[i][j] == '0') {
					for (int k = 0; k < paths.size(); k++) {
						if (paths.get(k).getSelfRow() == i && paths.get(k).getSelfColumn() == j) {
							paths.get(k).draw(gr, 0);
						}
					}
				}
			}
		}
	}

	// ========================================================
	// Draw Map
	// ========================================================
	public void drawMap(Graphics gr) {
		for (int i = 0; i < map.getRow(); i++) {
			for (int j = 0; j <= map.getColumn(); j++) {
				if (map.getMap()[i][j] == '0') {
					// gr.setColor(Color.WHITE);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
				} else if (map.getMap()[i][j] == '1') {
					// gr.setColor(Color.RED);
					// gr.fillRect((j * scale) + locationX + (padX * i), (i * scale) + locationY -
					// (padY * i), blockX,
					// blockY);
				} else if (map.getMap()[i][j] == '2') {
					for (int k = 0; k < enemys.size(); k++) {
						if (!enemys.get(k).getState().equals("dead")) {
							if (!enemys.get(k).getWalking()) {
								enemys.get(k).draw(gr, direction, locationX, locationY, padX, padY);
							} else {
								multipleFrameX = 15.0f;
								if (enemys.get(k).getDirection() == 1) {
									enemys.get(k).draw(gr, direction, (int) (locationX - (frameC * multipleFrameX)),
											locationY, padX, padY);
								} else if (enemys.get(k).getDirection() == 2) {
									enemys.get(k).draw(gr, direction, (int) (locationX + (frameC * multipleFrameX)),
											locationY, padX, padY);
								} else if (enemys.get(k).getDirection() == 3) {
									enemys.get(k).draw(gr, direction, (int) (locationX - (frameC * multipleFrameZ)),
											locationY - (int) ((frameC * multipleFrameY)), padX, padY);
								} else if (enemys.get(k).getDirection() == 4) {
									enemys.get(k).draw(gr, direction, (int) (locationX + (frameC * multipleFrameZ)),
											locationY + (int) ((frameC * multipleFrameY)), padX, padY);
								}
							}
						}
					}
				} else if (map.getMap()[i][j] == '3') {
					for (int k = 0; k < bombs.size(); k++) {
						if (bombs.get(k).getSelfRow() == i && bombs.get(k).getSelfColumn() == j) {
							bombs.get(k).draw(gr, direction);
						}
					}
				} else if (map.getMap()[i][j] == '4') {
					if (!fireball.getWalking()) {
						fireball = new FireBall(map, i, j, (j * scale) + locationX + (padX * i),
								(i * scale) + locationY - (padY * i) - 143 + 50);
						fireball.draw(gr, direction, scale, locationX, locationY, padX, padY);
					} else {
						multipleFrameX = 18.0f; // 19
						fireball.draw(gr, direction, scale, (int) (locationX + (frameB * multipleFrameX)), locationY,
								padX, padY);
					}
					attacking = true;
				} else if (map.getMap()[i][j] == '5') {
					for (int k = 0; k < mushroom5s.size(); k++) {
						if (mushroom5s.get(k).getSelfRow() == i && mushroom5s.get(k).getSelfColumn() == j) {
							mushroom5s.get(k).draw(gr, direction);
						}
					}
				} else if (map.getMap()[i][j] == '6') {
					for (int k = 0; k < portal6s.size(); k++) {
						if (portal6s.get(k).getSelfRow() == i && portal6s.get(k).getSelfColumn() == j) {
							portal6s.get(k).draw(gr, direction + 12);
						}
					}
				} else if (map.getMap()[i][j] == '7') {
					for (int k = 0; k < portal7s.size(); k++) {
						if (portal7s.get(k).getSelfRow() == i && portal7s.get(k).getSelfColumn() == j) {
							portal7s.get(k).draw(gr, direction + 6);
						}
					}
				} else if (map.getMap()[i][j] == '8') {
					for (int k = 0; k < portal8s.size(); k++) {
						if (portal8s.get(k).getSelfRow() == i && portal8s.get(k).getSelfColumn() == j) {
							portal8s.get(k).draw(gr, direction);
						}
					}
				} else if (map.getMap()[i][j] == '9') {
					if (!player.getState().equals("dead")) {
						if (!player.getWalking()) {
							if (player.getMushroom().equals("chun-li")) {
								player.draw(gr, direction + 12, locationX, locationY, padX, padY);
							} else if (player.getMushroom().equals("ken")) {
								player.draw(gr, direction + 6, locationX, locationY, padX, padY);
							} else {
								player.draw(gr, direction, locationX, locationY, padX, padY);
							}
						} else {
							int hero = 0;
							multipleFrameX = 15.0f;
							// w 124 = 124 / frameA
							// h 45 = 45 / frameA
							if (player.getMushroomNumber() == 2) {
								hero = direction + 12;
							} else if (player.getMushroomNumber() == 1) {
								hero = direction + 6;
							} else {
								hero = direction;
							}
							if (player.getDirection().equals("left")) {
								player.draw(gr, 18, (int) (locationX - (frameA * multipleFrameX)), locationY, padX,
										padY);
							} else if (player.getDirection().equals("right")) {
								player.draw(gr, 19, (int) (locationX + (frameA * multipleFrameX)), locationY, padX,
										padY);
							} else if (player.getDirection().equals("up")) {
								player.draw(gr, 20, (int) (locationX - (frameA * multipleFrameZ)),
										locationY - (int) ((frameA * multipleFrameY)), padX, padY);
							} else if (player.getDirection().equals("down")) {
								player.draw(gr, 21, (int) (locationX + (frameA * multipleFrameZ)),
										locationY + (int) ((frameA * multipleFrameY)), padX, padY);
							}
						}
					}
				} else if (map.getMap()[i][j] == 'A') {
					for (int k = 0; k < mushroomAs.size(); k++) {
						if (mushroomAs.get(k).getSelfRow() == i && mushroomAs.get(k).getSelfColumn() == j) {
							mushroomAs.get(k).draw(gr, direction + 6);
						}
					}
				} else if (map.getMap()[i][j] == 'D') {
					for (int k = 0; k < dummys.size(); k++) {
						if (dummys.get(k).getSelfRow() == i && dummys.get(k).getSelfColumn() == j) {
							dummys.get(k).draw(gr, direction, locationX, locationY, padX, padY);
						}
					}
				} else if (map.getMap()[i][j] == 'Q') {
					for (int k = 0; k < questions.size(); k++) {
						if (questions.get(k).getSelfRow() == i && questions.get(k).getSelfColumn() == j) {
							questions.get(k).draw(gr, direction);
						}
					}
				} else if (map.getMap()[i][j] == 'T') {
					for (int k = 0; k < treasures.size(); k++) {
						if (treasures.get(k).getSelfRow() == i && treasures.get(k).getSelfColumn() == j) {
							treasures.get(k).draw(gr, direction, locationX, locationY, padX, padY);
						}
					}
				} else if (map.getMap()[i][j] == 'W') {
					for (int k = 0; k < walls.size(); k++) {
						if (walls.get(k).getSelfRow() == i && walls.get(k).getSelfColumn() == j) {
							walls.get(k).draw(gr, direction);
						}
					}
				}
			}
		}
		if (hitting) {
			gr.drawImage(imageBooms[effectBoom].getImage(), effectBoomLcationX - 118, effectBoomLcationY - 74, null);
		}
		if (mapStateEnd) {
			gr.drawImage(imageStars[chooseStart].getImage(), (screenx / 2) - (starSizeX / 2),
					(screeny / 2) - (starSizeY / 2), null);
		}
		map.update(player, enemys, portal8s, scale, locationX, locationY, padX, padY);
	}

	// ========================================================
	// Update
	// ========================================================
	public void update() {
		if (starting) {
			bg = new ImageIcon("src/background/starting.png");
			buttonStart.setVisible(true);
			buttonLoad.setVisible(true);
			volume.setVisible(true);
			buttonLanguageEN.setVisible(true);
			buttonLanguageTH.setVisible(true);
			tutorialBackground.setVisible(false);
			input.setVisible(false);
			mapNumberLabel.setVisible(false);
			objectiveLabel.setVisible(false);
			buttonRestart.setVisible(false);
			buttonNext.setVisible(false);
			buttonClear.setVisible(false);
			buttonSubmit.setVisible(false);
			buttonExit.setVisible(false);
			buttonTutorialWalk.setVisible(false);
			buttonTutorialAttack.setVisible(false);
			buttonTutorialFor.setVisible(false);
			buttonTutorialWhile.setVisible(false);
			buttonTutorialIf.setVisible(false);
			buttonTutorialSearch.setVisible(false);
			tutorialBackgroundWalk.setVisible(false);
			tutorialBackgroundAttack.setVisible(false);
			tutorialBackgroundFor.setVisible(false);
			tutorialBackgroundWhile.setVisible(false);
			tutorialBackgroundIf.setVisible(false);
			tutorialBackgroundSearch.setVisible(false);
		} else if (loading) {
			bg = new ImageIcon("src/background/loading.png");
			buttonStart.setVisible(true);
			buttonLoad.setVisible(false);
			volume.setVisible(false);
			buttonLanguageEN.setVisible(false);
			buttonLanguageTH.setVisible(false);
			tutorialBackground.setVisible(false);
			input.setVisible(false);
			mapNumberLabel.setVisible(false);
			objectiveLabel.setVisible(false);
			buttonRestart.setVisible(false);
			buttonNext.setVisible(false);
			buttonClear.setVisible(false);
			buttonSubmit.setVisible(false);
			buttonExit.setVisible(false);
			buttonTutorialWalk.setVisible(false);
			buttonTutorialAttack.setVisible(false);
			buttonTutorialFor.setVisible(false);
			buttonTutorialWhile.setVisible(false);
			buttonTutorialIf.setVisible(false);
			buttonTutorialSearch.setVisible(false);
			tutorialBackgroundWalk.setVisible(false);
			tutorialBackgroundAttack.setVisible(false);
			tutorialBackgroundFor.setVisible(false);
			tutorialBackgroundWhile.setVisible(false);
			tutorialBackgroundIf.setVisible(false);
			tutorialBackgroundSearch.setVisible(false);
			for (int i = 0; i < mapTotal; i++) {
				mapStores.get(i).getMapStoreText().setVisible(true);
				mapStores.get(i).getMapStoreBackground().setVisible(true);
			}
		} else if (playing) {
			bg = map.getWorldImage();
			buttonStart.setVisible(false);
			buttonLoad.setVisible(true);
			volume.setVisible(false);
			buttonLanguageEN.setVisible(false);
			buttonLanguageTH.setVisible(false);
			tutorialBackground.setVisible(false);
			input.setVisible(true);
			mapNumberLabel.setVisible(true);
			objectiveLabel.setVisible(true);
			buttonRestart.setVisible(false);
			buttonNext.setVisible(true);
			buttonClear.setVisible(true);
			buttonSubmit.setVisible(true);
			buttonExit.setVisible(true);
			buttonTutorialWalk.setVisible(true);
			buttonTutorialAttack.setVisible(true);
			buttonTutorialFor.setVisible(true);
			buttonTutorialWhile.setVisible(true);
			buttonTutorialIf.setVisible(true);
			buttonTutorialSearch.setVisible(true);
			tutorialBackgroundWalk.setVisible(false);
			tutorialBackgroundAttack.setVisible(false);
			tutorialBackgroundFor.setVisible(false);
			tutorialBackgroundWhile.setVisible(false);
			tutorialBackgroundIf.setVisible(false);
			tutorialBackgroundSearch.setVisible(false);
			for (int i = 0; i < mapTotal; i++) {
				mapStores.get(i).getMapStoreText().setVisible(false);
				mapStores.get(i).getMapStoreBackground().setVisible(false);
			}
			if (mapStateEnd) {
				if (delayMapEnd >= 40) {
					delayMapEnd = 0;
					mapStateEnd = false;
					mapStateFirst = false;
				} else {
					delayMapEnd++;
				}
			} else {
				// ========================================================
				// Update Player State
				// ========================================================
				if (player.getState().equals("next")) {
					if (mapStateFirst) {
						mapStateEnd = true;
					} else {
						mapStores.get(mapNumber).setStatus(true);
						mapStores.get(mapNumber).setMapStoreBackground(0);

						mapNumber++;
						newGame();
						mapNumberLabel.setText(mapNumber + "");
						complier.setPointer(0);

						// ========================================================
						// Save file
						// ========================================================
						mapNummberSave = mapNumber + "";
						readFile.openFileWrite();
						readFile.write(mapNummberSave);
						readFile.closeFileWrite();

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
					map.setMap(player.selfPosition[0], player.selfPosition[1], '0');
					runable = false;
					processing = false;
					// player.selfPosition[0] = -99;
					// complier.setPointer(lines.size());
				} else if (runable && player.getState().equals("live")) {
					if (complier.getPointer() == 0) {
						System.out.println("==============================");
						System.out.println("    PROGRAM ALREADY RUNNING");
						System.out.println("==============================");
					}
					if (complier.getPointer() < lines.size()) { // lines.size()-1
						if (!player.getWalking() && !attacking) {
							System.out.println(
									"Line: " + complier.getPointer() + "  \t" + lines.get(complier.getPointer()));
							if (lines.get(complier.getPointer()).equals("END")) {
								complier = new Complier();
								runable = false;
							} else {
								line = complier.getPointer();
								complier.Runable(player, lines);
								// line++;
								if (line == (lines.size())) {
									runable = false;
								}
							}
						}
					}
				}
				// ========================================================
				// Update Playing Condition
				// ========================================================
				if (delayA > 20 && processing) {
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
										map.setCountDummy(map.getCountDummy() - 1);
									}
								}
								effectBoomLcationX = fireball.getX();
								effectBoomLcationY = fireball.getY() - 50;
								fireball.disable();
								attacking = false;
								hitting = true;
								effectBoom = 0;
								soundMedia.playSoundSingle("media/hit.wav");
							}
						}
						if (attacking && !fireball.getWalking()) {
							fireball.walk();
						}
					}
					delayB = 0;
				} else {
					delayB++;
				}
				if (timing < 3) {
					chooseStart = 2;
				} else if (timing < 6) {
					chooseStart = 1;
				} else {
					chooseStart = 0;
				}
			}
			if (direction > 4) {
				direction = 0;
			} else {
				direction++;
			}
			if (effectBoom > 4) {
				effectBoom = 0;
				hitting = false;
				timing++;
			} else {
				effectBoom++;
			}
			if (map.getTutorial()) {
				tutorialBackground.setVisible(true);
			} else {
				tutorialBackground.setVisible(false);
			}
			if (frameA > frameCount) {
				frameA = 0;
				player.setWalking(false);
				player.update();
			} else {
				frameA++;
			}
			if (frameB > frameCount) {
				frameB = 0;
				fireball.setWalking(false);
				fireball.update();
			} else {
				frameB++;
			}
			if (frameC > frameCount) {
				frameC = 0;
				for (int i = 0; i < enemys.size(); i++) {
					enemys.get(i).setWalking(false);
					enemys.get(i).update();
				}
			} else {
				frameC++;
			}
			if (!player.getWalking()) {
				frameA = 0;
			}
			if (!fireball.getWalking()) {
				frameB = 0;
			}
			for (int i = 0; i < enemys.size(); i++) {
				if (!enemys.get(i).getWalking()) {
					frameC = 0;
				}
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
		gr.drawImage(bg.getImage(), 0, 0, null);
		// map.printMap();
		if (starting) {
		} else if (loading) {
		} else if (playing) {
			if (firstMake) {
				makeObject();
				firstMake = false;
			}
			drawPath(gr);
			drawMap(gr);
		}
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
				Thread.sleep(50);
				repaint();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
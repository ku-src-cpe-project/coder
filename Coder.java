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

class Coder {
	private String text_value;
	private JLabel label;

	public void paint(Graphics g) {
		g.drawRect(10, 10, 10, 10);
	}

	public Coder() {
		JFrame frame = new JFrame();
		// JPanel panel = new JPanel();
		ImagePanel panel = new ImagePanel(new ImageIcon("icon/background.png").getImage());
		panel.setBounds(0, 0, 1100, 600);
		Icon icon_response = new ImageIcon("icon/background.png");
		JLabel label_response = new JLabel(icon_response);
		////////////////////////////////////////////////////////////////////////////
		///////////////////////////// * Background *////////////////////////////////
		////////////////////////////////////////////////////////////////////////////
		Map map = new Map(panel);
		Player player = new Player(panel, label_response, map);
		Complier complier = new Complier(panel, label_response, map, player);

		////////////////////////////////////////////////////////////////////////////
		///////////////////////////// * UI *////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////
		JTextField text = new JTextField();
		text.setBounds(50, 50, 200, 50);
		label = new JLabel(text_value);


		JButton forward = new JButton("Forward");
		forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.walk(player, "forward");
			}
		});
		forward.setBounds(200 + (50 * (1)), 200 + (50 * (0)), 50, 50);
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.walk(player, "back");
			}
		});
		back.setBounds(200 + (50 * (-1)), 200 + (50 * (0)), 50, 50);
		JButton down = new JButton("DOWN");
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.walk(player, "down");
			}
		});
		down.setBounds(200 + (50 * (0)), 200 + (50 * (1)), 50, 50);
		JButton up = new JButton("UP");
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player.walk(player, "up");
			}
		});
		up.setBounds(200 + (50 * (0)), 200 + (50 * (-1)), 50, 50);


		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				text_value = text.getText();
				// text_value = "walk(forward)";
				ArrayList<Character> parses = complier.textToParses(text_value);
				label.setText(parses.get(0)+"");
				ArrayList<String> tokens = complier.parseToTokens(parses);
				complier.Run(panel, label_response, map, player, tokens);

			}
		});
		submit.setBounds(250, 50, 50, 50);
		label.setBounds(50, 100, 100, 20);
		
		////////////////////////////////////////////////////////////////////////////
		///////////////////////////// * Configue *//////////////////////////////////
		////////////////////////////////////////////////////////////////////////////
		panel.setBackground(Color.pink);
		panel.add(forward);
		panel.add(back);
		panel.add(down);
		panel.add(up);
		panel.add(text);
		panel.add(submit);
		panel.add(label);

		////////////////////////////////////////////////////////////////////////////
		///////////////////////////// * Toggle Frame Rate */////////////////////////
		////////////////////////////////////////////////////////////////////////////
		panel.setLayout(null);
		panel.add(label_response);

		////////////////////////////////////////////////////////////////////////////
		///////////////////////////// * Frame */////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////
		frame.add(panel);
		frame.setSize(1115, 638);// x800,y600
		frame.setLocation(1650, 100);// x705,y100
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new Coder();
	}
}

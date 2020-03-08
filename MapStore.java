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

class MapStore extends JPanel {
    private ImageIcon[] images;
    private int mapStore, dir;
    private JLabel mapStoreBackground, mapStoreText;
    // private ReadFile readFile;
    private boolean status;

    public MapStore(JPanel panel, int mapStore) {
        this.images = new ImageIcon[2];
        this.images[0] = new ImageIcon("src/button/map_store.png");
        this.images[1] = new ImageIcon("src/button/map_store_disable.png");
        // readFile = new ReadFile();
        Font f1 = new Font("SansSerif", Font.BOLD, 30);
        // this.mapStore = mapStore + 0;
        this.mapStore = mapStore + 1;
        this.mapStoreText = new JLabel(this.mapStore + "");
        this.mapStoreText.setFont(f1);
        if (Coder.mapNumber > mapStore) {
            this.status = true;
            this.dir = 0;
        } else {
            this.status = false;
            this.dir = 1;
        }
        this.mapStoreBackground = new JLabel(this.images[this.dir]);
        this.mapStoreBackground.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (getStatus()) {
                    System.out.println("==============================");
                    System.out.println("    LOAD SUCCESS");
                    System.out.println("==============================");
                    System.out.println("> MapStore " + getMapStore());
                    Coder.mapNumber = getMapStore();
                    // readFile.openFileWrite();
                    // readFile.write(getMapStore() + "");
                    // readFile.closeFileWrite();
                }
            }
        });
        this.mapStoreText.setBounds(80 + ((mapStore % 5) * 210), 50 + (65 * (mapStore / 5)), 220, 48);
        this.mapStoreBackground.setBounds(5 + ((mapStore % 5) * 210), 50 + (65 * (mapStore / 5)), 220, 48);
        panel.add(this.mapStoreText);
        panel.add(this.mapStoreBackground);
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean a) {
        this.status = a;
    }

    public int getMapStore() {
        return this.mapStore;
    }

    public void setMapStore(int a) {
        this.mapStore = a;
    }

    public JLabel getMapStoreText() {
        return this.mapStoreText;
    }

    public JLabel getMapStoreBackground() {
        return this.mapStoreBackground;
    }

    public void setMapStoreBackground(int a) {
        this.mapStoreBackground.setIcon(this.images[a]);
    }
}

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
    private int mapStore;
    private JLabel mapStoreLabel;
    private ReadFile readFile;

    public MapStore(JPanel panel, int mapStore) {
        readFile = new ReadFile();
        this.mapStore = mapStore;
        this.mapStoreLabel = new JLabel(new ImageIcon("icon/map_store.png"));
        this.mapStoreLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.out.println("==============================");
                System.out.println("    LOAD SUCCESS");
                System.out.println("==============================");
                System.out.println("> MapStore " + getMapStore());
                Coder.mapNumber = getMapStore();
                readFile.openFileWrite();
                readFile.write(getMapStore() + "");
                readFile.closeFileWrite();
            }
        });
        this.mapStoreLabel.setBounds(50 + (mapStore * 150), 100, 149, 84);
        panel.add(this.mapStoreLabel);
    }

    public int getMapStore() {
        return this.mapStore;
    }

    public void setMapStore(int a) {
        this.mapStore = a;
    }

    public JLabel getMapStoreLabel() {
        return this.mapStoreLabel;
    }

    public void setMapStoreLabel(JLabel a) {
        this.mapStoreLabel = a;
    }
}

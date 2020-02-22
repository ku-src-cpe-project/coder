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

class MapStore {
    private int mapStore;
    private JLabel mapStoreLabel;

    public MapStore(JPanel panel, int mapStore) {
        this.mapStore = mapStore;
        System.out.println("MapStore Create " + this.mapStore);
        mapStoreLabel = new JLabel(new ImageIcon("icon/map_store.png"));
        mapStoreLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.out.println("LOAD SUCCESS");
            }
        });
        mapStoreLabel.setBounds(50, 50, 149, 84);
        panel.add(mapStoreLabel);
    }

    public int getMapStore() {
        return this.mapStore;
    }

    public void setMapStore(int a) {
        this.mapStore = a;
    }
}

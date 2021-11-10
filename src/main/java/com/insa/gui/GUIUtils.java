package com.insa.gui;

import javax.swing.UIManager;

public class GUIUtils {
    protected static int yPos = 0;
    
    static void initGTK() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

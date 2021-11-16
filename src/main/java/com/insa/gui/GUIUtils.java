package com.insa.gui;

import javax.swing.UIManager;
import java.awt.*;
import java.net.URL;

public class GUIUtils {
    protected static int yPos = 0;
    
    static void initGTK() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static Image getLogo() {
        return getImage("logo_s.png");
    }
}

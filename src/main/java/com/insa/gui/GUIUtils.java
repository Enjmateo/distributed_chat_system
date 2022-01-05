package com.insa.gui;

import javax.swing.UIManager;

import com.insa.utils.ExitHandler;

import javafx.scene.image.Image;

import java.net.URL;

public class GUIUtils {
    protected static final int mainWindowsWidth = 1000;
    protected static final int mainWindowsHeight = 800;

    protected static int yPos = 0;
    
    static void initGTK() {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            ExitHandler.error(e);
        }
    }

    private static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return new Image(url.toString());
    }

    public static Image getLogo() {
        return getImage("logo.png");
    }

    public static int getMWWidth(double proportion) {
        return (int)(proportion * mainWindowsWidth);
    }

    public static int getMWHeight(double proportion) {
        return (int)(proportion * mainWindowsHeight);
    }
}

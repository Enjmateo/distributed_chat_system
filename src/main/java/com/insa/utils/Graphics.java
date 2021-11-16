package com.insa.utils;

import java.awt.*;
import java.net.URL;

public class Graphics {
    private static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static Image getLogo() {
        return getImage("logo_s.png");
    }
}

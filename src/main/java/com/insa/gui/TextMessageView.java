package com.insa.gui;

import java.awt.*;
import javax.swing.*;

import java.awt.font.*;
import java.text.*;

public class TextMessageView extends JTextArea {
    private String sender;
    private String message;

    

    public TextMessageView(String sender, String message){
        this.sender = sender;
        this.message = message;
        this.setLineWrap(true);
    }

    private int getWrappedLines()
	{
		AttributedString text = new AttributedString(this.getText());
        FontRenderContext frc = this.getFontMetrics(this.getFont()).getFontRenderContext();
        AttributedCharacterIterator charIt = text.getIterator();
        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
        float formatWidth = 600;
        lineMeasurer.setPosition(charIt.getBeginIndex());

        int noLines = 0;
        while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
            lineMeasurer.nextLayout(formatWidth);
            noLines++;
        }

    return noLines;
	}

    public void addToPanel(Container panel){
        this.setEditable(false);
        this.setText(sender + ": " + message);
        this.setBackground(Color.black);
        this.setForeground(Color.white);

        int height = this.getPreferredSize().height*this.getWrappedLines();

        // TODO: Mettre au propre  
        this.setBounds(10, GUIUtils.yPos, 600, height);
        GUIUtils.yPos = GUIUtils.yPos + height + 8;

        panel.add(this);
    }
}

package com.insa.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Modality;

import com.insa.utils.Consts;

import javafx.geometry.Insets;


public class ErrorWindow {
		public ErrorWindow(String msg){
			Stage window = new Stage();
			Label text = new Label(msg);
			Button button = new Button("Close");
			VBox layout = new VBox(10);

            window.setResizable(false);
			button.setOnAction(e->window.close());
			window.setTitle("Alert");
			window.initModality(Modality.APPLICATION_MODAL);
			window.setMinWidth(250);
			layout.getChildren().addAll(text,button);
			layout.setAlignment(Pos.CENTER);
			layout.setPadding(new Insets(Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING));


			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.showAndWait();		

			}
	}
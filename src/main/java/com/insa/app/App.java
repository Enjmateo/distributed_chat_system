package com.insa.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    ConnexionWindow connexionWindow = new ConnexionWindow();
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        connexionWindow.start();
        
    }
    @Override
	public void stop() throws Exception{
		super.stop();
	}
    public static void main(String[] args) throws Exception {
        launch();
    }
}

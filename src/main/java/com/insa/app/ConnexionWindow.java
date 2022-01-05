package com.insa.app;

import com.insa.communication.Data;
import com.insa.gui.ErrorWindow;
import com.insa.gui.GUIUtils;
import com.insa.utils.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

public class ConnexionWindow {
	Stage window = new Stage();

	Label connectLabel = new Label("Enter a pseudo to connect to ODD");

	Label pseudoLabel = new Label("Pseudo : ");
	TextField pseudoField = new TextField();

	Image logo = GUIUtils.getLogo();
	ImageView logoView = new ImageView(logo);
	
	/*
	Label ipAdressFieldLabel = new Label("IP : ");
	TextField ipAdressField = new TextField("localhost");
	Label portInFieldLabel = new Label("Input port : ");
	TextField portInField = new TextField("7777");
	Label portOutFieldLabel = new Label("Output port : ");
	TextField portOutField = new TextField("7778");
	Button connectButton = new Button("Connect");
	*/
	Button connectButton = new Button("Connect");
	Button configButton = new Button("Change config file");
	FileChooser fileChooser = new FileChooser();

	boolean pseudoSet = false;

	public void start(){
		window.setTitle("ODD - Connect");
		window.setResizable(false);

		logoView.setPreserveRatio(true);
		logoView.setFitWidth(Consts.IMAGE_CONNECTION_WINDOW_SIZE);
		
		pseudoField.setMaxWidth(Consts.PSEUDO_FIELD_SIZE);

		HBox pseudoLayout = new HBox(Consts.ELEMENTS_GAP);
		pseudoLayout.getChildren().addAll(pseudoLabel,pseudoField);
		pseudoLayout.setAlignment(Pos.CENTER);

		HBox buttonsLayout= new HBox(Consts.ELEMENTS_GAP);
		buttonsLayout.getChildren().addAll(configButton,connectButton);
		buttonsLayout.setAlignment(Pos.CENTER);

		VBox connectLayout = new VBox(Consts.ELEMENTS_GAP);
		connectLayout.getChildren().addAll(logoView,connectLabel,pseudoLayout,buttonsLayout);
		connectLayout.setAlignment(Pos.CENTER);
		connectLayout.setPadding(new Insets(Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING));

		pseudoField.setOnAction(e->connectButtonHandler());
		configButton.setOnAction(e -> setConfigFile());
		connectButton.setOnAction(e-> connectButtonHandler());

		/*
		GridPane gridpane = new GridPane();
		gridpane.add(ipAdressFieldLabel, 0, 0);
		gridpane.add(ipAdressField, 1, 0);
		gridpane.add(portInFieldLabel, 0, 1);
		gridpane.add(portInField, 1, 1);
		gridpane.add(portOutFieldLabel, 0, 2);
		gridpane.add(portOutField, 1, 2);
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(Consts.FIELDS_GAP); 
		gridpane.setVgap(Consts.FIELDS_GAP); 

		*/
		

		Scene scene = new Scene(connectLayout);

		window.setScene(scene);
		window.showAndWait();

		//Si la fermeture n'est pas due au fait de mettre un pseudo on quitte l'application
		if(!pseudoSet){
			ExitHandler.exit();
		}
		
	}

	void setConfigFile(){ 
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File selectedFile = fileChooser.showOpenDialog(window);
		if(selectedFile != null){
			File oldConfigFile = new File(Consts.CONFIG_FILE);

            String newConfigFile = selectedFile.getAbsolutePath();
            System.out.println("[+] Config. file: " + newConfigFile);

            oldConfigFile.delete();
            selectedFile.renameTo(new File(Consts.CONFIG_FILE)); 

            Data.reloadData();
		}
	}
	private void connectButtonHandler(){
		String pseudo = pseudoField.getText();
		if(pseudo.equals("")){
			new ErrorWindow("Please enter a Pseudo.");
			return;
		}
		if(UsersHandler.getPseudos().contains(pseudo)){ 
			new ErrorWindow(pseudo+" is already used, please choose another pseudo.");
			return;
		}
		try {
            InputStream fileInput = new FileInputStream(Consts.CONFIG_FILE);
            fileInput.close();
        } catch (IOException e) {
            new ErrorWindow("No valid configuration found, please select the configuration file.");
            return;
        }
		Data.reloadData();
		
		UsersHandler.getLocalUser().setPseudo(pseudo);
		pseudoSet = true;
		window.close();
	}
    /*
	private void connectButtonHandler(){
		String ip = ipAdressField.getText();
		String portIn = portInField.getText();
		String portOut = portOutField.getText();
		if ((ip == null) || ip.equals("")) {
			new ErrorWindow("Please enter an ip adress.");
			return;
		}
		if ((portIn == null) || portIn.equals("")) {
			new ErrorWindow("Please enter a input port number.");
			return;
		}
		if ((portOut == null) || portOut.equals("")) {
			new ErrorWindow("Please enter a output port number.");
			return;
		}
		try {
			int portOutInt = Integer.parseInt(portOut);
			int portInInt = Integer.parseInt(portIn);

			this.app.objectSender = new ObjectSender(new Socket(ip,portOutInt));
			this.app.objectReceiver = new ObjectReceiver(this.app, new Socket(ip,portInInt));

			window.close();
		}catch(Exception e){
			new ErrorWindow("An error as occured please verify informations.");
		}
	}*/

}
package com.insa.app;

import com.insa.utils.*;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.net.Socket;
import java.net.ServerSocket;

public class ConnexionWindow {
	Stage window = new Stage();

	Label connectLabel = new Label("Enter IP and port to connect to the stock exchange :");
	Label ipAdressFieldLabel = new Label("IP : ");
	TextField ipAdressField = new TextField("localhost");
	Label portInFieldLabel = new Label("Input port : ");
	TextField portInField = new TextField("7777");
	Label portOutFieldLabel = new Label("Output port : ");
	TextField portOutField = new TextField("7778");
	Button connectButton = new Button("Connect");

	public void start(){
		window.setTitle("Stock Exchange Client - Connect");

		//connectButton.setOnAction(e->connectButtonHandler());
		ipAdressField.setPromptText("IP adress");
		ipAdressField.setMaxWidth(Consts.IP_ADRESS_FIELD_SIZE);
		portInField.setPromptText("In-port");
		portInField.setMaxWidth(Consts.PORT_FIELD_SIZE);
		portOutField.setPromptText("Out-port");
		portOutField.setMaxWidth(Consts.PORT_FIELD_SIZE);

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

		VBox connectLayout = new VBox(Consts.ELEMENTS_GAP);
		connectLayout.getChildren().addAll(connectLabel,gridpane,connectButton);
		connectLayout.setAlignment(Pos.CENTER);
		connectLayout.setPadding(new Insets(Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING,Consts.SCENE_PADDING));

		Scene scene = new Scene(connectLayout);

		window.setScene(scene);
		window.showAndWait();
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
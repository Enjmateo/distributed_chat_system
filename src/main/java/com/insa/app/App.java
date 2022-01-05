package com.insa.app;

import com.insa.communication.Data;
import com.insa.gui.ConnexionWindow;
import com.insa.gui.MainWindow;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;
import com.insa.utils.udp.ConfigMessage;
import com.insa.utils.udp.UDPObjectReceiver;
import com.insa.utils.udp.UDPObjectSender;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    ConnexionWindow connexionWindow = new ConnexionWindow();
    MainWindow mainWindow = new MainWindow();
    private  static UsersHandler uh;
    private static UDPObjectSender udpos;
    private static UDPObjectReceiver udpor;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Affiche tout sauf l'UDP
        LogHandler.set(1,3,4,5);

        LogHandler.display(1,"[+] Launching app" );
        LogHandler.display(1,"[+] Creating UserHandler" );
        uh = new UsersHandler();

        LogHandler.display(1,"[+] Creating UDP Object sender and receiver" );
        udpos = new UDPObjectSender();
        udpor = new UDPObjectReceiver();

        LogHandler.display(1,"[+] Begin client discovery" );
        UDPObjectSender.broadcastMessage(new ConfigMessage(), Consts.UDP_PORT);
        LogHandler.display(1,"  [+] Wainting responses..." );

        Thread.sleep(Consts.DISCOVERY_TIMEOUT_MS);
        LogHandler.display(1,"[+] Discovery finished");
        UsersHandler.listUsers();
        LogHandler.display(1,"[+] Printing connexion window");
        connexionWindow.start();
        LogHandler.display(1,"[+] Sending pseudo choosen");
        UsersHandler.updateSelfPseudo(UsersHandler.getLocalUser().getPseudo());
        mainWindow.start();
              
    }
    @Override
	public void stop() throws Exception{
		super.stop();
	}
    public static void begin(String[] args) throws Exception {
        launch();
    }
}

package com.insa.app;

import com.insa.communication.DatabaseHandler;
import com.insa.gui.ConnectionWindow;
import com.insa.gui.MainWindow;
import com.insa.utils.Consts;
import com.insa.utils.LogHandler;
import com.insa.utils.tcp.TCPConnectionReceiver;
import com.insa.utils.udp.ConfigMessage;
import com.insa.utils.udp.UDPObjectReceiver;
import com.insa.utils.udp.UDPObjectSender;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    ConnectionWindow connexionWindow = new ConnectionWindow();
    MainWindow mainWindow = new MainWindow();

    @Override
    public void start(Stage primaryStage) throws Exception {
        LogHandler.set(1);

        LogHandler.display(1, "[+] Launching app");
        LogHandler.display(1, "[+] Creating UserHandler");
        new UsersHandler();

        LogHandler.display(1, "[+] Creating UDP Object sender and receiver");
        new UDPObjectSender();
        new UDPObjectReceiver();

        LogHandler.display(1, "[+] Begin client discovery");
        UDPObjectSender.broadcastMessage(new ConfigMessage(), Consts.UDP_PORT);
        LogHandler.display(1, "  [+] Wainting responses...");

        Thread.sleep(Consts.DISCOVERY_TIMEOUT_MS);
        LogHandler.display(1, "[+] Discovery finished");
        UsersHandler.listUsers();
        LogHandler.display(1, "[+] Starting TCPConnexionReceivers");
        new TCPConnectionReceiver(true);
        new TCPConnectionReceiver(false);

        LogHandler.display(1, "[+] Printing connexion window");
        connexionWindow.start();

        if (DatabaseHandler.getUseDatabase()) {
            LogHandler.display(1, "[+] Connecting to database");
            DatabaseHandler db = new DatabaseHandler();
            db.connect();
        }

        mainWindow.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void begin(String[] args) throws Exception {
        launch();
    }
}

package com.insa.app;

import java.util.ArrayList;
import java.util.UUID;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;
import com.insa.utils.ObjectMessage;
import com.insa.utils.tcp.*;
import com.insa.communication.*;
import com.insa.gui.ErrorWindow;
import com.insa.gui.chattabs.UserDiscussionView;

import java.io.IOException;
import java.net.*;

public class User {
    private StringProperty pseudo = new SimpleStringProperty();
    private IntegerProperty unreadMessagesCount = new SimpleIntegerProperty(0);
    
    private BooleanProperty aliveProperty = new SimpleBooleanProperty(false);
    private boolean alive = true;
    //si =false -> ne donne pas de signe de vie (surement crash)

    private UUID uuid;

    private InetAddress address;

    private boolean instantAlive = false;    
    //Permet de détecter si l'utilisateur s'est notifié vivant 

    private boolean historyRetrieved = false;

    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;
    private Status status = Status.WAITING;

    public BooleanProperty getAliveProperty() {
        return aliveProperty;
    }
    public IntegerProperty getUnreadMessagesCount(){ 
        return unreadMessagesCount;
    }
    public void resetUnreadMessagesCount() {
        unreadMessagesCount.set(0);
    }
    //TODO implementMVC and rename
    private UserDiscussionView tab;
    public UserDiscussionView getUserDiscussionView() {
        if(tab == null) {
            tab = new UserDiscussionView(this);
        }
        return this.tab
        ;}
    public void setUserDiscussionView(UserDiscussionView tab) {this.tab = tab;}
    
    //WAITING = phase de connexion UDP
    //AVAILABLE = connectable
    //CONNECTED = connecté en TCP
    public enum Status {WAITING,AVAILABLE,CONNECTED}

    /**
     * Public class user
     * @param pseudo Set user pseudo
     * @param uuid Set user UUID
     * @param local True if the user is local 
     */
    public User(String pseudo, UUID uuid) {
        this.pseudo.setValue(pseudo);
        this.uuid = uuid;
    }

    // cas d'une connexion provoqué par l'autre
    public void connect(Socket socket) throws Exception {
        if(socket.getLocalPort()==Consts.TCP_PORT_A){
            receiver = new TCPObjectReceiver(socket,this);
        }else{
            sender = new TCPObjectSender(socket);
        }
        if(receiver != null & sender != null) this.status = Status.CONNECTED;
    }

    private void retrieveHistory() {
        historyRetrieved = true;
        ArrayList<Message> messageList = DatabaseHandler.getMessages(uuid);
        for(Message message : messageList) {
            addMessage(message);
        }
    }
    
    public void connect() throws Exception {
            //if(receiver != null & sender != null) {return;}
            if(DatabaseHandler.getUseDatabase() && !historyRetrieved) retrieveHistory();
            if(status == Status.CONNECTED) {return;}
            Socket socketA = new Socket();
            SocketAddress sAdressA = new InetSocketAddress(address, Consts.TCP_PORT_A);
            socketA.connect(sAdressA, Consts.TCP_TIMEOUT);
            this.sender = new TCPObjectSender(socketA);

            Socket socketB = new Socket();
            SocketAddress sAdressB = new InetSocketAddress(address, Consts.TCP_PORT_A);
            socketB.connect(sAdressB, Consts.TCP_TIMEOUT);
            this.receiver = new TCPObjectReceiver(socketB,this);
            this.status = Status.CONNECTED;
    }


    public void disconnect() throws Exception {
        this.sender.close();
        this.receiver.close(true);
        this.status = Status.AVAILABLE;
        setAlive(false);
    }

    public synchronized void setPseudo(String newpseudo) {
        Platform.runLater(new Runnable() {
            public void run() {
                pseudo.setValue(newpseudo);
            }
        });
        
    }

    public InetAddress getInetAddress() {return address;}

    public void setInetAddress(InetAddress address){ this.address = address; }
    
    public synchronized String getPseudo() {
        return this.pseudo.getValue();
    }

    public synchronized StringProperty getPseudoProperty() {
        return this.pseudo;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public synchronized boolean isAlive(){
        return this.aliveProperty.getValue();
    }

    //mofify state -> if change return true
    public synchronized void setAlive(boolean newState){ 
       aliveProperty.set(newState);
    }

    public synchronized boolean isInstantAlive(){
        return this.instantAlive;
    }
    public synchronized void setInstantAlive(boolean alive){ this.instantAlive = alive; }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public String toString(){
        return (this.pseudo == null? "undefined" : this.pseudo.getValue()) + " (" + this.uuid.toString() + ") : "+this.status+ (this.aliveProperty.getValue()?"":"(DEAD)");
    }

    public boolean sendMessage(Message message) {
        LogHandler.display(3,"[+] Sending a new text message to "+this.address);
        try{ 
            sender.sendMessageObject((ObjectMessage) message);          
        }catch(Exception e){
            LogHandler.display(5,"[-] Failed to send message, attempt to reconnect");
            try {
                LogHandler.display(5,"  [-] Deconnecting");
                disconnect();
                LogHandler.display(5,"  [-] Reconnecting");
                connect();
                LogHandler.display(5,"[+] Succes");
                return sendMessage(message);
            } catch(Exception e1){
                new ErrorWindow("User unreachable");
                LogHandler.display(5,"[-] Failed");
                return false;
            }

        }
        //TODO avec la base de données ----------------------------------------------------
        //discussion.addMessage(message);
        //message.sendToDatabase();
        if(DatabaseHandler.getUseDatabase())message.sendToDatabase();
        getUserDiscussionView().addMessage(message,true);
        return true;
    }
    public void addMessage(Message message) {
        
        Platform.runLater(new Runnable() {
            public void run() {
                //On ajoute le message en fonction de qui il vient 
                getUserDiscussionView().addMessage(message,((ObjectMessage)message).getSender()==UsersHandler.getLocalUser().getUUID());
                unreadMessagesCount.set(unreadMessagesCount.getValue()+1);
            }
        });
        
    }
}

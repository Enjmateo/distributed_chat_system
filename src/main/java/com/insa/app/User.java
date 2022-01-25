package com.insa.app;

import java.util.UUID;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.ObjectMessage;
import com.insa.utils.tcp.*;
import com.insa.communication.*;
import com.insa.gui.chattabs.UserTab;

import java.net.*;

public class User {
    private StringProperty pseudo = new SimpleStringProperty();
    
    private UUID uuid;

    private InetAddress address;

    private boolean instantAlive = false;    
    //Permet de détecter si l'utilisateur s'est notifié vivant 

    private boolean alive = true;
    //si =false -> ne donne pas de signe de vie (surement crash)

    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;
    private Discussion discussion;
    private Status status = Status.WAITING;

    //TODO implementMVC
    private UserTab tab;
    public UserTab getTab() {return this.tab;}
    public void setTab(UserTab tab) {this.tab = tab;}
    
    //WAITING = phase de connexion UDP
    //AVAILABLE = connectable
    //CONNECTED = connecté en TCP
    enum Status {WAITING,AVAILABLE,CONNECTED}

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

    public void connect(Socket socket) throws Exception {
        if(socket.getLocalPort()==Consts.TCP_PORT_A){
            receiver = new TCPObjectReceiver(socket);
        }else{
            sender = new TCPObjectSender(socket);
        }
        if(receiver != null & sender != null) this.status = Status.CONNECTED;
    }
    
    public void connect() throws Exception {
        Socket socketA = new Socket();
        SocketAddress sAdressA = new InetSocketAddress(address, Consts.TCP_PORT_A);
        socketA.connect(sAdressA, Consts.TCP_TIMEOUT);
        this.sender = new TCPObjectSender(socketA);

        Socket socketB = new Socket();
        SocketAddress sAdressB = new InetSocketAddress(address, Consts.TCP_PORT_A);
        socketB.connect(sAdressB, Consts.TCP_TIMEOUT);
        this.receiver = new TCPObjectReceiver(socketB);
    }


    public void disconnect(boolean quit) throws Exception {
        this.sender.close();
        this.receiver.close(true);
        if(quit){
            alive =false;
        }else{
            this.status = Status.AVAILABLE;
        }
    }

    public void setPseudo(String pseudo) {
        this.pseudo.setValue(pseudo);
    }

    public InetAddress getInetAddress() {return address;}

    public void setInetAddress(InetAddress address){ this.address = address; }
    
    public String getPseudo() {
        return this.pseudo.getValue();
    }

    public StringProperty getPseudoProperty() {
        return this.pseudo;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public synchronized boolean isAlive(){
        return this.alive;
    }

    //mofify state -> if change return true
    public synchronized boolean setAlive(boolean newState){ 
        /*
        if(this.alive == newState){
            return false;
        }else{
            this.alive = newState;
            return true;
        }*/
        boolean oldState = this.alive;
        this.alive = newState; 
        return newState=!oldState;
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
/*     public String getStatusString() {
        switch(status) {
            case ALIVE: return "ALIVE";
            case DEAD: return "DEAD";
            case CONNECTED:
                return "Connected";
            default: 
                return "Waiting";
        }
    } */
    public String toString(){
        return (this.pseudo == null? "undefined" : this.pseudo.getValue()) + " (" + this.uuid.toString() + ") : "+this.status+ (this.alive?"":"(DEAD)");
    }

    public void sendMessage(Message message) {
        try{ 
            sender.sendMessageObject((ObjectMessage) message);
        }catch(Exception e){
            ExitHandler.error(e);
        }
        discussion.addMessage(message);
        tab.addMessage(message,true);
        message.sendToDatabase();
        
    }
}

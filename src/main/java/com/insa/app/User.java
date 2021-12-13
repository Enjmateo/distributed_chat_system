package com.insa.app;

import java.util.UUID;

import com.insa.utils.tcp.*;
import java.net.*;

public class User {
    private String pseudo;
    private UUID uuid;

    private InetAddress address;

    private boolean alive = false;
    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;

    //WAITING = phase de connexion UDP
    //DEAD = ne donne pas de signe de vie (surement crash)
    //ALIVE = connectable
    //CONNECTED = connecté en TCP
    enum Status {WAITING, DEAD, ALIVE, CONNECTED};
    Status status;

    /**
     * Public class user
     * @param pseudo Set user pseudo
     * @param uuid Set user UUID
     * @param local True if the user is local 
     */
    public User(String pseudo, UUID uuid) {
        this.pseudo = pseudo;
        this.uuid = uuid;
        this.status = Status.ALIVE;
    }

    public void connect(Socket socket) throws Exception {
        this.sender = new TCPObjectSender(socket);
        this.receiver = new TCPObjectReceiver(socket);
        this.status = Status.CONNECTED;
    }

    public void disconnect() throws Exception {
        this.sender.close();
        this.receiver.close(true);
        this.status = Status.ALIVE;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public InetAddress getInetAddress() {return address;}

    public void setInetAddress(InetAddress address){ this.address = address; }
    
    public String getPseudo() {
        return this.pseudo;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isAlive(){
        return this.alive;
    }
    public synchronized void setAlive(boolean alive){ this.alive = alive; }

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
        return (this.pseudo == null? "undefined" : this.pseudo) + " (" + this.uuid.toString() + ") : "+this.status;
    }
}

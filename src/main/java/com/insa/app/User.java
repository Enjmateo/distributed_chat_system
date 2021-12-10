package com.insa.app;

import java.util.UUID;

import com.insa.utils.tcp.*;
import java.net.*;

public class User {
    private String pseudo;
    private UUID uuid;

    private InetAddress address;

    private boolean connected = false;
    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;

    enum Status {WAITING, IDLE, CONNECTED, DISCONNECTED};
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
        this.status = Status.IDLE;
    }

    public void connect(Socket socket) throws Exception {
        this.sender = new TCPObjectSender(socket);
        this.receiver = new TCPObjectReceiver(socket);
        this.connected = true;
        this.status = Status.CONNECTED;
    }

    public void disconnect() throws Exception {
        this.sender.close();
        this.receiver.close(true);
        this.connected = false;
        this.status = Status.DISCONNECTED;
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

    public boolean isConnected(){
        return this.connected;
    }

    public void setIdle() {
        this.status = Status.IDLE;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusString() {
        switch(status) {
            case DISCONNECTED:
                return "Disconnected";
            case CONNECTED:
                return "Connected";
            case WAITING:
                return "Waiting";
            default:
                return "Idle";
        }
    }
    public String toString(){
        return this.pseudo + " (" + this.uuid.toString() + ") : "+this.status;
    }
}

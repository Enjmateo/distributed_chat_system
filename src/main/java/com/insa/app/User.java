package com.insa.app;

import java.util.UUID;

import com.insa.utils.tcp.*;
import java.net.*;

public class User {
    private String pseudo;
    private UUID uuid;
    private boolean local;

    private InetAddress address;

    private boolean connected = false;
    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;

    /**
     * Public class user
     * @param pseudo Set user pseudo
     * @param uuid Set user UUID
     * @param local True if the user is local 
     */
    public User(String pseudo, UUID uuid, boolean local) {
        this.pseudo = pseudo;
        this.uuid = uuid;
        this.local = local;
    }

    public void connect(Socket socket) throws Exception {
        this.sender = new TCPObjectSender(socket);
        this.receiver = new TCPObjectReceiver(socket);
        this.connected = true;
    }

    public void disconnect() throws Exception {
        this.sender.close();
        this.receiver.close(true);
        this.connected = false;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public InetAddress getInetAddress() {return address;}

    public String getPseudo() {
        return this.pseudo;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isLocal() {
        return this.local;
    }
}

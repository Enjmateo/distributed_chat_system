package com.insa.app;

import java.util.UUID;

import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.ObjectMessage;
import com.insa.utils.tcp.*;
import com.insa.communication.*;
import java.net.*;

public class User {
    private String pseudo;
    private UUID uuid;

    private InetAddress address;

    private boolean alive = false;
    private TCPObjectSender sender;
    private TCPObjectReceiver receiver;
    private Discussion discussion;

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

    public void sendMessage(Message message) {
        try{ 
            sender.sendMessageObject((ObjectMessage) message);
        }catch(Exception e){
            ExitHandler.error(e);
        }
        discussion.addMessage(message);
        message.sendToDatabase();
        
    }
}

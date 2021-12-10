package com.insa.utils.udp;
import com.insa.utils.*;
import java.net.*;
public class ConfigMessage extends ObjectMessage {
    InetAddress address;
    String pseudo;
    MessageType type;
    public enum MessageType {NOTIFY, NOTIFY_REPLY, PSEUDO_SET}
    public void setAddress(InetAddress address) {
        this.address = address;
    }
    public ConfigMessage(){
        super();
        type = MessageType.NOTIFY;
    }
    public ConfigMessage(String pseudo, MessageType type){
        super();
        this.pseudo = pseudo;
        this.type = type;
    }
    public MessageType getType(){
        return this.type;
    }
    public InetAddress getAddress() {
        return this.address;
    }
    public String getPseudo() {
        return this.pseudo;
    }
}
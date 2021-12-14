package com.insa.app;

import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.ObjectMessage;
import com.insa.utils.udp.*;
import javax.swing.*;

public class ObjectHandler {

    //Permet d'appeler la méthode action sur l'objet objectMessage avec en argument l'objet associé à la classe de objectMessage
    public static void handleObject(ObjectMessage objectMessage){
        if (objectMessage instanceof ConfigMessage) {
            new Thread(new Runnable() { public void run() {handleInitiateConnectionMessage((ConfigMessage)objectMessage); }}).start();
        }
    }


    private static void handleInitiateConnectionMessage(ConfigMessage obj){
        User user;
        System.out.println("   [>] Handling config message ("+obj.getType()+")...");
        try{
            user = UsersHandler.getUserByUUID(obj.getSender());
        }catch(Exception e){
            user = new User(null,obj.getSender());
            user.setStatus(User.Status.WAITING);
            UsersHandler.addUser(user);
        }
        try {
            //TODO Renvoyer un objet avec le pseudo et l'adresse
            //ATTENTION - A OPERER SUR LE THREAD PRINCIPAL (OU INITILISER L'ENVOYEUR DANS LE RECEVEUR UDP)  
            if(obj.getType() == ConfigMessage.MessageType.NOTIFY){
            UDPObjectSender.invokeLater(
                new Runnable() {
                    public void run() {
                    try {
                        System.out.println("Sending my pseudo ("+UsersHandler.getLocalUser().getPseudo()+") to "+obj.getAddress());
                        UDPObjectSender.sendMessage( 
                        new ConfigMessage(UsersHandler.getLocalUser().getPseudo(),ConfigMessage.MessageType.KEEP_ALIVE),obj.getAddress(),Consts.UDP_PORT);
                    } catch (Exception e) {
                        ExitHandler.error(e);
                    }}
                 }
                );
            }else if (obj.getType()== ConfigMessage.MessageType.KEEP_ALIVE){
                UsersHandler.getUserByUUID(obj.getSender()).setAlive(true);
            }
        }catch (Exception e){
            ExitHandler.error(e);
        }
        //On mets à jour l'adresse IP de l'utilisateur 
        user.setInetAddress(obj.getAddress());

        // S'il y a une modification de pseudo : 
        if (obj.getPseudo()!= null) {
            System.out.println("[+] Updating pseudo for " + obj.getPseudo());
            user.setPseudo(obj.getPseudo());
        }

        UsersHandler.listUsers();
    }
}
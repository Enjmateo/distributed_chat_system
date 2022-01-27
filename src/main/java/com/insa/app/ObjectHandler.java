package com.insa.app;

import com.insa.communication.TextMessage;
import com.insa.gui.MainWindow;
import com.insa.utils.Consts;
import com.insa.utils.ExitHandler;
import com.insa.utils.LogHandler;
import com.insa.utils.ObjectMessage;
import com.insa.utils.udp.*;


public class ObjectHandler {

    /**
     * Permet d'appeler la méthode action sur l'objet objectMessage avec en argument l'objet associé à la classe de objectMessage
     * Ces actions sont réalisés dans des threads secondaire afin de ne pas bloquer la reception de nouveaux messages
     * @param objectMessage
     */
    public static void handleObject(ObjectMessage objectMessage){
        if (objectMessage instanceof ConfigMessage) {
            new Thread(new Runnable() { public void run() {handleInitiateConnectionMessage((ConfigMessage)objectMessage); }}).start();
        }else if(objectMessage instanceof TextMessage) {
            new Thread(new Runnable() { public void run() {handleTextMessage((TextMessage)objectMessage); }}).start();
        }

    }

    private static void handleTextMessage(TextMessage message) {
        LogHandler.display(3,"[+] Handling a text message");
        try {
         UsersHandler.getUserByUUID(message.getSender()).addMessage(message);
        }catch(Exception e){
            ExitHandler.error(e);
        }
    }

    private static void handleInitiateConnectionMessage(ConfigMessage obj){
        User user;
        LogHandler.display(2,"   [>] Handling config message ("+obj.getType()+")...");
        try{
            user = UsersHandler.getUserByUUID(obj.getSender());
        }catch(Exception e){
            user = new User(null,obj.getSender());
            user.setStatus(User.Status.WAITING);
            UsersHandler.addUser(user);
        }
        try {  
            if(obj.getType() == ConfigMessage.MessageType.NOTIFY){
            UDPObjectSender.invokeLater(
                new Runnable() {
                    public void run() {
                    try {
                        LogHandler.display(2,"Sending my pseudo ("+UsersHandler.getLocalUser().getPseudo()+") to "+obj.getAddress());
                        UDPObjectSender.sendMessage( 
                        new ConfigMessage(UsersHandler.getLocalUser().getPseudo(),ConfigMessage.MessageType.KEEP_ALIVE),obj.getAddress(),Consts.UDP_PORT);
                    } catch (Exception e) {
                        ExitHandler.error(e);
                    }}
                 }
                );
            }else if (obj.getType()== ConfigMessage.MessageType.KEEP_ALIVE){
                UsersHandler.getUserByUUID(obj.getSender()).setInstantAlive(true);
            }
        }catch (Exception e){
            ExitHandler.error(e);
        }
        //On mets à jour l'adresse IP de l'utilisateur 
        user.setInetAddress(obj.getAddress());

        // S'il y a une modification de pseudo où la création : 
        if (obj.getPseudo()!= null && !obj.getPseudo().equals(user.getPseudo())) {
            //Dans le cas où on reçoit pour la première fois le pseudo on rajoute l'utilisateur à la liste d'utilisateurs connectés de l'interface graphique
            if(user.getStatus()==User.Status.WAITING){
                user.setStatus(User.Status.AVAILABLE);
                MainWindow.addUser(user);
            }
            LogHandler.display(2,"[+] Updating pseudo for " + obj.getPseudo());
            user.setPseudo(obj.getPseudo());
        }

        UsersHandler.listUsers();
    }
}
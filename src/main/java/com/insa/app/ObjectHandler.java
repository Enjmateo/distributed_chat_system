package com.insa.app;

import com.insa.utils.ObjectMessage;
import com.insa.utils.udp.*;

public class ObjectHandler {

    //Permet d'appeler la méthode action sur l'objet objectMessage avec en argument l'objet associé à la classe de objectMessage
    public static void handleObject(ObjectMessage objectMessage){
        if (objectMessage instanceof ConfigMessage) {
            System.out.println("   [>] Handling config message...");
            handleInitiateConnectionMessage((ConfigMessage)objectMessage); 
        }
    }



    private static void handleInitiateConnectionMessage(ConfigMessage obj){
        User user;
        try{
            user = UsersHandler.getUserByUUID(obj.getSender());
        }catch(Exception e){
            user = new User(null,obj.getSender());
            user.setStatus(User.Status.WAITING);
            UsersHandler.addUser(user);

            //TODO Renvoyer un objet avec le pseudo et l'adresse
            //ATTENTION - A OPERER SUR LE THREAD PRINCIPAL (OU INITILISER L'ENVOYEUR DANS LE RECEVEUR UDP)
            try {
                if(obj.getType() == ConfigMessage.MessageType.NOTIFY) UDPObjectSender.sendMessage( new ConfigMessage(UsersHandler.getLocalUser().getPseudo(),ConfigMessage.MessageType.NOTIFY_REPLY),obj.getAddress(),1234);
                else ; //GERER LA CREATION DE PSEUDO
            }catch (Exception e2){}
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
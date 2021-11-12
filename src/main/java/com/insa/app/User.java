package com.insa.app;

import java.util.UUID;

import com.insa.utils.tcp.*;

public class User {
    private String pseudo;
    private UUID uuid;
    private boolean local;

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

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isLocal() {
        return this.local;
    }
}

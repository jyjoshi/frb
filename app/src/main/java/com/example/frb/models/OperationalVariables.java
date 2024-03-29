package com.example.frb.models;

public class OperationalVariables {
    private int token;
    private String MessageId;
    private boolean isOpen;

    public OperationalVariables(){}

    public OperationalVariables(int token, String messageId, boolean isOpen) {
        this.token = token;
        MessageId = messageId;
        this.isOpen = isOpen;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}

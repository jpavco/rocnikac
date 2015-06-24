package com.me.mygdxgame;

public class Packet{
    private String message;

    public Packet()
    {
    }

    public Packet(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
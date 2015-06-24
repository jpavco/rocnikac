package com.me.mygdxgame;

import com.badlogic.gdx.utils.Array;

public class RollPacket {
    private String message;
    Array<Integer> k;

    public RollPacket()
    {
    }

    public RollPacket(Array<Integer> kocky)
    {
        k=kocky;
    }

    public Array<Integer> getKocky()
    {
        return k;
    }

   
}


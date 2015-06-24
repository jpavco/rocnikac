package com.me.mygdxgame;

import com.badlogic.gdx.utils.Array;

public class PickDicePacket {
	    Integer k;

	    public PickDicePacket()
	    {
	    }

	    public PickDicePacket(int q)
	    {
	        k=q;
	    }

	    public Integer getInt()
	    {
	        return k;
	    }

	   
	}


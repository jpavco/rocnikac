package com.me.mygdxgame;



import java.io.IOException;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Client class for testing network interface
 */
public class KryoClient
{
    private Client client;
    private Heckmeck game;

    public KryoClient() throws IOException
    {
        client = new Client();
        Kryo kryo = client.getKryo();
        kryo.register(float[].class);
        kryo.register(Packet.class);
        kryo.register(Grill.class);
        kryo.register(Board.class);
        kryo.register(Kocky.class);
        kryo.register(Heckmeck.class);
        kryo.register(SpriteBatch.class);
        kryo.register(BitmapFont.class);
    	kryo.register(Stage.class);
        kryo.register(OrthographicCamera.class);
        kryo.register(MyButton.class);
        kryo.register(Mesh[].class);
        kryo.register(Mesh.class);
        kryo.register(RollPacket.class);
        kryo.register(Array.class);
        kryo.register(Object[].class);
        kryo.register(PickDicePacket.class);
        kryo.register(Integer.class);
        kryo.register(PickGrillPacket.class);
        kryo.register(EndTurnPacket.class);
        
        //kryo.register(String.class);
        client.addListener(new Listener() {
            public void connected(Connection connection)
            {
                //connection.sendTCP(new float[] {5.0f, 6.0f, 7.0f, 8.0f});
                connection.sendTCP(new Packet("Hello, Connecting!"));
            }

            @Override
            public void received(Connection connection, Object object)
            {
                if (object instanceof float[])
                {
                    float[] array = (float[]) object;
                    for(float a : array)
                    {
                        System.out.println(a);
                    }
              
                	 //connection.sendTCP(new Packet("The packet has arrived to client."));
                } else if(object instanceof RollPacket){
                	RollPacket packet = (RollPacket)object;
                	game.setRoll(packet.getKocky());
                }else if(object instanceof PickDicePacket){
                	PickDicePacket packet = (PickDicePacket)object;
                	game.kocky.pickDice(packet.k);
                }else if(object instanceof PickGrillPacket){
                	PickGrillPacket packet = (PickGrillPacket)object;
                	game.grill.pickFromGrill(packet.k);
                }else if(object instanceof EndTurnPacket){
                	game.endTurn();
                }
            }

            @Override
            public void disconnected(Connection arg0)
            {
                System.out.println("Server disconnected.");
            }
        });
        client.start();
        client.connect(5000, "127.0.0.1", 2305, 2306);
    }

    public Client getClient()
    {
        return client;
    }
    
    public void setGame(Heckmeck g){
    	game=g;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
}
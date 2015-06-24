package com.me.mygdxgame;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayDeque;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
/**
 * Server class for testing network interface
 */
public class KryoServer
{
    private Server server;
    private Heckmeck game;

    public KryoServer() throws IOException
    {
        server = new Server();
        Kryo kryo = server.getKryo(); 
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
        server.start();
        server.bind(2305, 2306);
        server.addListener(new Listener()
        {
            @Override
            public void connected(Connection connection)
            {
                connection.sendTCP(new Packet("Server says, connected to server."));
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
                }
                else if(object instanceof Packet)
                {
                    Packet packet = (Packet) object;
                    System.out.println("Zaciname prenos" + packet.getMessage());	
                }else if(object instanceof RollPacket){
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
            public void disconnected(Connection connection)
            {
                System.out.println("Client disconnected.");
            }
        });
    }

    public Server getServer()
    {
        return server;
    }
    
    public void setGame(Heckmeck g){
    	game=g;
    }

    public void setServer(Server server)
    {
        this.server = server;
    }
}

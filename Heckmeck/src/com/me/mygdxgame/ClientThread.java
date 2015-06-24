package com.me.mygdxgame;

public class ClientThread extends Thread
{
    private KryoClient client;
    private volatile boolean running;

    public ClientThread(KryoClient client)
    {
        this.client = client;
        running = true;
    }

    @Override
    public void run()
    {
        long initTime = System.currentTimeMillis();
        while(running)
        {
            if(System.currentTimeMillis() - initTime > 1000)
            {
                initTime = System.currentTimeMillis();
                client.getClient().sendTCP(new Packet("Hello from " + System.currentTimeMillis()));
                //should have used Thread.sleep(1000); instead
            }
        }
    }

    public void stopThread()
    {
        running = false;
    }
}


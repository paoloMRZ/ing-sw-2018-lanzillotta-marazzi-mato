package it.polimi.se2018;

import it.polimi.se2018.server.Network.SingletonServer;

import java.net.ServerSocket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        SingletonServer.getInstance().run();
    }
}

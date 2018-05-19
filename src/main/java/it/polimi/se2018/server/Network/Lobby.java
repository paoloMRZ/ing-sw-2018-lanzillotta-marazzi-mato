package it.polimi.se2018.server.Network;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private ArrayList<Client> players;

    public Lobby(){

        players = new ArrayList<>();
    }

    public void add(Client player){
        if(player != null){
            players.add(player);
        }
    }


    public Client remove(int pos){
        return  null;
    }

    public List<String> getNicknameList(){

        ArrayList<String> nick = new ArrayList<>();

        for(Client client: players){
            nick.add(client.getNickname());
        }

        return nick;
    }

}

package it.polimi.se2018.server.network;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private ArrayList<FakeClient> players;

    public Lobby(){

        players = new ArrayList<>();
    }

    public void add(FakeClient player){
        if(player != null){
            players.add(player);
        }
    }


    public FakeClient remove(int pos){
        return  null;
    }

    public List<String> getNicknameList(){

        ArrayList<String> nick = new ArrayList<>();

        for(FakeClient client: players){
            nick.add(client.getNickname());
        }

        return nick;
    }

}

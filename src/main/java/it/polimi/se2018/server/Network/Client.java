package it.polimi.se2018.server.Network;

import it.polimi.se2018.server.fake_view.FakeView;

public class Client {

    private String nickname;
    private Connection connectionType;
    private FakeView virtualView;

    public Client(String nickname, Connection connectionType, FakeView virtualView) {
        this.nickname = nickname;
        this.connectionType = connectionType;
        this.virtualView = virtualView;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(String mex){

    }

    public String receiveMessage(){
        return null;
    }

    public void setVirtualView(FakeView virtualView){

        if(virtualView != null)
            this.virtualView = virtualView;
    }
}

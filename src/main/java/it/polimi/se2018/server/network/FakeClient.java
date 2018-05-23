package it.polimi.se2018.server.network;

import it.polimi.se2018.server.fake_view.FakeView;

public class FakeClient {

    private String nickname;
    private Connection connectionType;
    private FakeView virtualView;

    public FakeClient(String nickname, Connection connectionType, FakeView virtualView) {
        this.nickname = nickname;
        this.connectionType = connectionType;
        this.virtualView = virtualView;
    }

    public String getNickname() {
        return nickname;
    }

    public void send(String mex){
        connectionType.send(mex);
    }

    public String receive(){
        return connectionType.receive();
    }

    public void setVirtualView(FakeView virtualView){

        if(virtualView != null)
            this.virtualView = virtualView;
    }
}
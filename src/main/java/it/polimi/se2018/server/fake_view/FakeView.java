package it.polimi.se2018.server.fake_view;


public class FakeView{

    private FakeVChat chat;

    public FakeView(){
        this.chat=new FakeVChat(this);
    }


}

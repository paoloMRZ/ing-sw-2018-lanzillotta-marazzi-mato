package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

public class DieInfo extends Info {


    public DieInfo(Ansi.Color color, int number){
        super(color,number);

        if(!isAcceptableCombiantion(color,number))
            throw new InvalidParameterException();
    }

    private boolean isAcceptableCombiantion(Ansi.Color color, int number){
        return !(color == Ansi.Color.WHITE && number != 0) && !(color != Ansi.Color.WHITE && number == 0);
    }
}

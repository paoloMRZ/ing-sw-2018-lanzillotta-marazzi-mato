package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.security.InvalidParameterException;

public class CellInfo extends Info {

    public CellInfo(Ansi.Color color, int number){

        super(color, number);

        if(!isAcceptableCombination(color, number))
            throw new InvalidParameterException();
    }

    private boolean isAcceptableCombination(Ansi.Color color, int number){
        return !(color != Ansi.Color.WHITE && number != 0);
    }
}

package it.polimi.se2018.client.cli.game.info;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.security.InvalidParameterException;

public abstract class Info implements Serializable {

    private Ansi.Color color;
    private int number;

    Info(Ansi.Color color, int number){

        if(isAcceptableColor(color) && isAcceptableNumber(number)) {
            this.color = color;
            this.number = number;
        }
        else
            throw new InvalidParameterException();
    }

    private boolean isAcceptableColor(Ansi.Color color){
        return  color == Ansi.Color.RED ||
                color == Ansi.Color.GREEN ||
                color == Ansi.Color.YELLOW ||
                color == Ansi.Color.MAGENTA ||
                color == Ansi.Color.BLUE ||
                color == Ansi.Color.WHITE;
    }

    private boolean isAcceptableNumber(int number) {
        return number >= 0 && number <= 6;
    }

    public Ansi.Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }
}

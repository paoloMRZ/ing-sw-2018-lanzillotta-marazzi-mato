package it.polimi.se2018.server.model.card;

import it.polimi.se2018.server.controller.Visitor;

public interface Visitable {
//TODO DOVREBBERO IMPLEMENTARE IL METODO TUTTE LE CLASSI O BASTA LA CLASSE PADRE?
        public void accept(Visitor visitor);

}

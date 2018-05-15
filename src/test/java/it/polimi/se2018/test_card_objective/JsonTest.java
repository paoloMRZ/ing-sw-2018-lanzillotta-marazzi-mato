package it.polimi.se2018.test_card_objective;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class JsonTest {

    /*
        Il metodo setupCard ha la funzione di parser del file Json aggiunto nella cartella main -> accedendo al file, lo scandisce e va a creare gli oggetti Java
        (in questo caso le PatternCard) memorizzandole in una apposita collezione. In secondo luogo andrà ad estrarre in modo randomico le carte necessarie per la
        scelta del Player.

        Le carte generate in modo randomio dovranno poi essere rese disponibili al player tramite un oppurtuno metodo del controller.

    */
    @Test
    public void setupCard() throws IOException {

        InputStream is = getClass().getClassLoader().getResourceAsStream("myJSON.json");
        ObjectMapper mapper = new ObjectMapper();
        Side[] SideTest = null;
        SideTest = mapper.readValue(is, Side[].class);
    }

}

package it.polimi.se2018.test_controller;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.exceptions.InvalidValueException;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ControllerTest {

    public Controller controller;

    @Before
    public void setup(){ controller = new Controller(new ArrayList<>(Arrays.asList("1","2","3","4"))); }


    @Test
    public void checkUtensilsCreation(){

        assertTrue(!controller.getLobby().getUtensils(0).getDesciption().equals(controller.getLobby().getUtensils(1).getDesciption()));
        assertTrue(!controller.getLobby().getUtensils(1).getDesciption().equals(controller.getLobby().getUtensils(2).getDesciption()));
        assertTrue(!controller.getLobby().getUtensils(0).getDesciption().equals(controller.getLobby().getUtensils(2).getDesciption()));
    }

    @Test
    public void checkObjectivesCreation(){

        assertTrue(!controller.getLobby().getObjective(0).getName().equals(controller.getLobby().getObjective(1).getName()));
        assertTrue(!controller.getLobby().getObjective(1).getName().equals(controller.getLobby().getObjective(2).getName()));
        assertTrue(!controller.getLobby().getObjective(0).getName().equals(controller.getLobby().getObjective(2).getName()));
    }

    @Test
    public void checkPlayersCreation() throws InvalidValueException {

        assertTrue(!controller.getLobby().callPlayerByName("1").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("2").getMyObjective().getName()));
        assertTrue(!controller.getLobby().callPlayerByName("1").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("3").getMyObjective().getName()));
        assertTrue(!controller.getLobby().callPlayerByName("1").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("4").getMyObjective().getName()));
        assertTrue(!controller.getLobby().callPlayerByName("2").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("3").getMyObjective().getName()));
        assertTrue(!controller.getLobby().callPlayerByName("2").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("4").getMyObjective().getName()));
        assertTrue(!controller.getLobby().callPlayerByName("3").getMyObjective().getName().equals(controller.getLobby().callPlayerByName("4").getMyObjective().getName()));
    }


}

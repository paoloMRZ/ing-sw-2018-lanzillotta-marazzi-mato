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
    public void checkUtensilsCreation() {

        assertTrue(!controller.getTableUtensils().get(0).getDesciption().equals(controller.getTableUtensils().get(1).getDesciption()));
        assertTrue(!controller.getTableUtensils().get(1).getDesciption().equals(controller.getTableUtensils().get(2).getDesciption()));
        assertTrue(!controller.getTableUtensils().get(2).getDesciption().equals(controller.getTableUtensils().get(0).getDesciption()));
    }

    @Test
    public void checkObjectivesCreation(){

        assertTrue(!controller.getTableObjective().get(0).getName().equals(controller.getTableObjective().get(1).getName()));
        assertTrue(!controller.getTableObjective().get(1).getName().equals(controller.getTableObjective().get(2).getName()));
        assertTrue(!controller.getTableObjective().get(2).getName().equals(controller.getTableObjective().get(0).getName()));
    }

    @Test
    public void checkPlayersCreation() throws InvalidValueException {

        assertTrue(!controller.getPlayerByName("1").getMyObjective().getName().equals(controller.getPlayerByName("2").getMyObjective().getName()));
        assertTrue(!controller.getPlayerByName("1").getMyObjective().getName().equals(controller.getPlayerByName("3").getMyObjective().getName()));
        assertTrue(!controller.getPlayerByName("1").getMyObjective().getName().equals(controller.getPlayerByName("4").getMyObjective().getName()));
        assertTrue(!controller.getPlayerByName("2").getMyObjective().getName().equals(controller.getPlayerByName("3").getMyObjective().getName()));
        assertTrue(!controller.getPlayerByName("2").getMyObjective().getName().equals(controller.getPlayerByName("4").getMyObjective().getName()));
        assertTrue(!controller.getPlayerByName("3").getMyObjective().getName().equals(controller.getPlayerByName("4").getMyObjective().getName()));
    }


}

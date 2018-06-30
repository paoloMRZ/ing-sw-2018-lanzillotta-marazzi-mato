package it.polimi.se2018.test_controller;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.server.controller.ControllerPoints;
import it.polimi.se2018.server.exceptions.invalid_cell_exceptios.*;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidCoordinatesException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidFavoursValueException;
import it.polimi.se2018.server.exceptions.invalid_value_exceptios.InvalidShadeValueException;
import it.polimi.se2018.server.fake_view.FakeView;
import it.polimi.se2018.server.model.Color;
import it.polimi.se2018.server.model.NotifyModel;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.Table;
import it.polimi.se2018.server.model.card.card_objective.Objective;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ColorDiagonals;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.CoupleOfShades;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadeVariety;
import it.polimi.se2018.server.model.card.card_objective.obj_algos.algos.ShadesOfCard;
import it.polimi.se2018.server.model.card.card_schema.Cell;
import it.polimi.se2018.server.model.card.card_schema.Side;
import it.polimi.se2018.server.model.dice_sachet.Dice;
import it.polimi.se2018.server.model.dice_sachet.DiceSachet;
import it.polimi.se2018.server.model.grid.RoundGrid;
import it.polimi.se2018.server.model.grid.ScoreGrid;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ControllerPointsTest {

    private static ArrayList<Cell> casualListSideOne = new ArrayList<>(20);
    private static ArrayList<Cell> casualListSideTwo = new ArrayList<>(20);
    private static ArrayList<Side> sides = new ArrayList<>();
    private static ArrayList<Objective> objectives = new ArrayList<>();
    private static ArrayList<Player> players = new ArrayList<>();
    private static ControllerPoints controllerPoints;
    private static Table lobby;
    private static Objective objectivePrivateOne;
    private static Objective objectivePrivateTwo;
    private FakeView f;
    private Controller controller;

    @Before
    public void setuUpCard() throws InvalidShadeValueException, InvalidFavoursValueException, InvalidShadeException, NoDicesNearException, NotEmptyCellException, InvalidColorException, InvalidCoordinatesException, NearDiceInvalidException {

        //Creazione carte Side

        //Aurorae Sagradis

        casualListSideOne.add(new Cell(Color.RED, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.BLUE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.YELLOW, 0));

        casualListSideOne.add(new Cell(Color.WHITE, 4));
        casualListSideOne.add(new Cell(Color.PURPLE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 3));
        casualListSideOne.add(new Cell(Color.GREEN, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 2));

        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 1));
        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 5));
        casualListSideOne.add(new Cell(Color.WHITE, 0));

        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 6));
        casualListSideOne.add(new Cell(Color.WHITE, 0));
        casualListSideOne.add(new Cell(Color.WHITE, 0));

        //Kaleidoscopic Dream

        casualListSideTwo.add(new Cell(Color.YELLOW , 0));
        casualListSideTwo.add(new Cell(Color.BLUE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 1));

        casualListSideTwo.add(new Cell(Color.GREEN, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 5));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 4));

        casualListSideTwo.add(new Cell(Color.WHITE, 3));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.RED, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.GREEN, 0));

        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.WHITE, 0));
        casualListSideTwo.add(new Cell(Color.BLUE, 0));
        casualListSideTwo.add(new Cell(Color.YELLOW, 0));


        sides.add(new Side("Aurorae Sagratis",4, casualListSideOne));
        sides.add(new Side("Kaleidoscopic Dream",4, casualListSideTwo));

        //Settaggio manuale dell carte obbiettivo pubbliche
        objectives.add(new Objective("TesterName1", "TesterDescription1", 0, new ColorDiagonals(), false));
        objectives.add(new Objective("TesterName2", "TesterDescription2", 5, new ShadeVariety(), false));
        objectives.add(new Objective("TesterName3", "TesterDescription3", 2, new CoupleOfShades(1,2), false));


        f= new FakeView();
        controller =new Controller(new ArrayList<>(Arrays.asList("SIMONE","MARCO")));
        f.register(controller);
    }



    @Test
    public void checkPointObjective() throws Exception {

        sides.get(0).put(0,0, new Dice(Color.RED, 3));
        sides.get(0).put(0,1, new Dice(Color.GREEN, 2));
        sides.get(0).put(0,2, new Dice(Color.BLUE, 6));
        sides.get(0).put(0,3, new Dice(Color.PURPLE, 2));
        sides.get(0).put(0,4, new Dice(Color.YELLOW, 3));

        sides.get(0).put(1,0, new Dice(Color.YELLOW, 4));
        sides.get(0).put(1,1, new Dice(Color.PURPLE, 5));
        sides.get(0).put(1,2, new Dice(Color.RED, 3));
        sides.get(0).put(1,3, new Dice(Color.GREEN, 1));
        sides.get(0).put(1,4, new Dice(Color.PURPLE, 2));

        sides.get(0).put(2,0, new Dice(Color.PURPLE, 3));
        sides.get(0).put(2,1, new Dice(Color.YELLOW, 1));
        sides.get(0).put(2,2, new Dice(Color.PURPLE, 2));
        sides.get(0).put(2,3, new Dice(Color.BLUE, 5));
        sides.get(0).put(2,4, new Dice(Color.GREEN, 3));

        sides.get(0).put(3,0, new Dice(Color.YELLOW, 5));
        sides.get(0).put(3,1, new Dice(Color.GREEN, 4));
        sides.get(0).put(3,2, new Dice(Color.YELLOW, 6));

        //Settaggio manuale della carta obbiettivo Privata del player 1
        objectivePrivateOne = new Objective("TesterNamePrivate", "TesterDescriptionPrivate", 0, new ShadesOfCard(Color.YELLOW), true);

        //Settaggio manuale dei player
        players.add(new Player(objectivePrivateOne, "SIMONE", null));


        //Settaggio carta Side per i player
        players.get(0).setSideSelection(sides);
        players.get(0).setMySide(0);
        players.get(0).setFavours();

        lobby = new Table(null, objectives, players, null);
        controllerPoints = new ControllerPoints(lobby, controller);

        controllerPoints.updateScoreOfPlayer();

        assertEquals( 46,lobby.getScoreGrid().getPlayersPoint(0));
    }


    @Test
    public void searchWinnerTwoPlayers() throws Exception {

        sides.get(1).put(0,0, new Dice(Color.YELLOW, 6));
        sides.get(1).put(0,1, new Dice(Color.BLUE, 2));
        sides.get(1).put(0,2, new Dice(Color.YELLOW, 1));
        sides.get(1).put(0,3, new Dice(Color.PURPLE, 6));
        sides.get(1).put(0,4, new Dice(Color.RED, 1));

        sides.get(1).put(1,0, new Dice(Color.GREEN, 5));
        sides.get(1).put(1,1, new Dice(Color.RED, 1));
        sides.get(1).put(1,2, new Dice(Color.PURPLE, 5));
        sides.get(1).put(1,3, new Dice(Color.RED, 2));
        sides.get(1).put(1,4, new Dice(Color.YELLOW, 4));

        sides.get(1).put(2,0, new Dice(Color.RED, 3));
        sides.get(1).put(2,1, new Dice(Color.PURPLE, 2));
        sides.get(1).put(2,2, new Dice(Color.RED, 6));
        sides.get(1).put(2,3, new Dice(Color.PURPLE, 3));
        sides.get(1).put(2,4, new Dice(Color.GREEN, 6));

        sides.get(1).put(3,0, new Dice(Color.PURPLE, 2));
        sides.get(1).put(3,1, new Dice(Color.RED, 5));
        sides.get(1).put(3,2, new Dice(Color.PURPLE, 1));
        sides.get(1).put(3,3, new Dice(Color.BLUE, 2));

        players= new ArrayList<>();
        //Settaggio manuale della carta obbiettivo Privata del player 1
        objectivePrivateOne = new Objective("TesterNamePrivate", "TesterDescriptionPrivate", 0, new ShadesOfCard(Color.YELLOW), true);

        //Settaggio manuale dei player
        players.add(new Player(objectivePrivateOne, "SIMONE", null));


        //Settaggio carta Side per i player
        players.get(0).setSideSelection(sides);
        players.get(0).setMySide(0);
        players.get(0).setFavours();
        //Settaggio manuale della carta obbiettivo Privata del player 2
        objectivePrivateTwo = new Objective("TesterNamePrivate", "TesterDescriptionPrivate", 0, new ShadesOfCard(Color.RED), true);

        players.add(new Player(objectivePrivateTwo, "MARCO", null));

        players.get(1).setSideSelection(sides);
        players.get(1).setMySide(1);
        players.get(1).setFavours();

        lobby = new Table(null, objectives, players, null);
        controllerPoints = new ControllerPoints(lobby, controller);

        assertEquals("SIMONE", controllerPoints.nameOfWinner());
    }
}
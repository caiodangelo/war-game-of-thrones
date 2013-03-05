/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rodrigo
 */
public class BoardTest {

    Board board;

    @BeforeClass
    public void setUp() {
        board = Board.getInstance();
    }

    @Test
    public void construtorDeveInicializarVariaveis() {
        board = new Board();
        assertNotNull(board.players);
    }

    @Test
    public void adicionarPlayerValidoDeveRetornarTrue() {
        Player player = mock(Player.class);
        assertTrue(board.addPlayer(player, 0, Board.HUMAN_PLAYER));
    }

    @Test
    public void adicionarMesmoJogadorDuasVezesDeveRetornarFalse() {
        Player player = mock(Player.class);
        board.addPlayer(player, 0, Board.HUMAN_PLAYER);
        assertFalse(board.addPlayer(player, 0, Board.HUMAN_PLAYER));
    }

    @Test
    public void adicionarSetimoPlayerDeveRetornarFalse() {
        for (int i = 0; i < 6; i++) {
            Player player = mock(Player.class);
            board.addPlayer(player, i, Board.HUMAN_PLAYER);
        }
        Player player = mock(Player.class);
        assertFalse(board.addPlayer(player, 1, Board.HUMAN_PLAYER));
    }

    @Test
    public void getHousesDeveRetornarAsCasasDosJogadores() {        
        board.getPlayers().clear();          
        Player playerStark = mock(Player.class);
        Player playerTully = mock(Player.class);
        House stark = mock(House.class);
        House tully = mock(House.class);
        when(stark.getName()).thenReturn("Stark");
        when(tully.getName()).thenReturn("Tully");
        when(playerStark.getHouse()).thenReturn(stark);
        when(playerTully.getHouse()).thenReturn(tully);
        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerTully, 1, Board.HUMAN_PLAYER);
        List<House> houses = board.getHouses();
    //          assertEquals(board.getPlayers().size(), 2);
        assertEquals(stark, houses.get(0));
        assertEquals(tully, houses.get(1));
    }

    @Test
    public void isPlayerCountValidDeveRetornarTrueQuandoTiverEntreDoisESeisPlayers() {
        board.players = mock(LinkedList.class);
        when(board.players.size()).thenReturn(5);
        assertTrue(board.isPlayerCountValid());
    }

    @Test
    public void isPlayerCountValidDeveRetornarFalseQuandoTiverUmOuMenosPlayers() {
        board.players = mock(LinkedList.class);
        when(board.players.size()).thenReturn(1);
        assertFalse(board.isPlayerCountValid());
    }

    @Test
    public void isPlayerCountValidDeveRetornarFalseQuandoTiverMaisDeSeisPlayers() {
        board.players = mock(LinkedList.class);
        when(board.players.size()).thenReturn(7);
        assertFalse(board.isPlayerCountValid());
    }

//    @Test
//    public void getAbsentHousesDeveRetornarTodasAsCasasAusentesDoJogo() {        
//        board.getPlayers().clear();
//        Player playerStark = mock(Player.class);
//        Player playerLannister = mock(Player.class);
//        Player playerTargaryen = mock(Player.class);
//        Player playerBaratheon = mock(Player.class);
//        Player playerFreeFolk = mock(Player.class);
//        Player playerGreyjoy = mock(Player.class);
//
//        House stark = mock(House.class);
//        House lannister = mock(House.class);
//        House targaryen = mock(House.class);
//        House baratheon = mock(House.class);
//        House freeFolk = mock(House.class);
//        House greyjoy = mock(House.class);
//
//        when(playerStark.getHouse()).thenReturn(stark);
//        when(playerLannister.getHouse()).thenReturn(lannister);
//        when(playerTargaryen.getHouse()).thenReturn(targaryen);
//        when(playerBaratheon.getHouse()).thenReturn(baratheon);
//        when(playerFreeFolk.getHouse()).thenReturn(freeFolk);
//        when(playerGreyjoy.getHouse()).thenReturn(lannister);
//
//        board.addPlayer(playerTargaryen, 0, Board.HUMAN_PLAYER);
//        board.addPlayer(playerBaratheon, 1, Board.HUMAN_PLAYER);
////        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
////        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
////        board.addPlayer(playerFreeFolk, 4, Board.HUMAN_PLAYER);
////        board.addPlayer(playerGreyjoy, 5, Board.HUMAN_PLAYER);
//
//        LinkedList<House> allHouses = new LinkedList<House>();
//        allHouses.add(stark);
//        allHouses.add(lannister);
//        allHouses.add(targaryen);
//        allHouses.add(baratheon);
//        allHouses.add(freeFolk);
//        allHouses.add(greyjoy);
//       
//        List<House> absentHouses = board.getAbsentHouses();
//        assertEquals(stark, absentHouses.get(0));
//        assertEquals(lannister, absentHouses.get(1));
//        assertEquals(freeFolk, absentHouses.get(2));
//        assertEquals(greyjoy, absentHouses.get(3));
//    }

    @Test
    public void playerDeveTerTodosOsTerritoriosDeAcordoComAsCartasDeTerritorioNoInicioDoJogo() {
        board = new Board();
        Player playerStark = mock(Player.class);
        Player playerLannister = mock(Player.class);
        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        BackEndTerritory winterfell = mock(BackEndTerritory.class);
        BackEndTerritory rochedoCasterly = mock(BackEndTerritory.class);
        BackEndTerritory harrenhal = mock(BackEndTerritory.class);
        BackEndTerritory dorne = mock(BackEndTerritory.class);
        CardTerritory cardWinterfell = mock(CardTerritory.class);
        CardTerritory cardRochedoCasterly = mock(CardTerritory.class);
        CardTerritory cardHarrenhal = mock(CardTerritory.class);
        CardTerritory cardDorne = mock(CardTerritory.class);

        when(cardWinterfell.getTerritory()).thenReturn(winterfell);
        when(cardRochedoCasterly.getTerritory()).thenReturn(rochedoCasterly);
        when(cardHarrenhal.getTerritory()).thenReturn(harrenhal);
        when(cardDorne.getTerritory()).thenReturn(dorne);

        playerStark.addCard(cardDorne);
        playerStark.addCard(cardWinterfell);
        playerLannister.addCard(cardRochedoCasterly);
        playerLannister.addCard(cardHarrenhal);
        
        board.distributeInitialTerritories();

        for (Player player : board.getPlayers()) {
            for (int i = 0; i < player.getCards().size(); i++) {
                assertEquals(player.getCards().get(i).getTerritory(), player.getTerritories().get(i));
            }
        }
    }
    
//    @Test
//    public void removeMissionsDeveRemoverAsMissoesDasCasasAusentesDoJogo() {
//        board = new Board();
//
//        House stark = mock(House.class);
//        House lannister = mock(House.class);
//        House baratheon = mock(House.class);
//        House targaryen = mock(House.class);
//        House freeFolk = mock(House.class);
//        House greyjoy = mock(House.class);
//
//        Player playerStark = mock(Player.class);
//        Player playerLannister = mock(Player.class);
//        Player playerTargaryen = mock(Player.class);
//
//        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
//        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
//        board.addPlayer(playerTargaryen, 2, Board.HUMAN_PLAYER);
//        when(playerStark.getHouse()).thenReturn(stark);
//        when(playerLannister.getHouse()).thenReturn(lannister);
//        when(playerTargaryen.getHouse()).thenReturn(targaryen);
//
//
//        LinkedList<House> allHouses = new LinkedList<House>();
//        allHouses.add(stark);
//        allHouses.add(lannister);
//        allHouses.add(targaryen);
//        allHouses.add(baratheon);
//        allHouses.add(freeFolk);
//        allHouses.add(greyjoy);
//
//        Mission missionStark = new Mission(null, null, Mission.TYPE_HOUSE);
//        Mission missionLannister = new Mission(null, null, Mission.TYPE_HOUSE);
//        Mission missionBaratheon = new Mission(null, null, Mission.TYPE_HOUSE);
//        Mission missionRegion = new Mission(null, null, Mission.TYPE_REGION);
//        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
//
//        missionStark.setHouse(stark);
//        missionLannister.setHouse(lannister);
//        missionBaratheon.setHouse(baratheon);
//
//        LinkedList<Mission> allMissions = new LinkedList<Mission>();
//        allMissions.add(missionStark);
//        allMissions.add(missionLannister);
//        allMissions.add(missionBaratheon);
//        allMissions.add(missionRegion);
//        allMissions.add(missionTerritory);
//
//        ArrayList<Mission> r = board.removeMissions();
//
//        assertEquals(missionStark, r.get(0));
//        assertEquals(missionLannister, r.get(1));
//        assertEquals(missionRegion, r.get(2));
//        assertEquals(missionTerritory, r.get(3));
//    }

    @Test
    public void CadaJogadorDeveReceberUmaMissao() {
        Board board = new Board();

        House stark = mock(House.class);
        House lannister = mock(House.class);
        House targaryen = mock(House.class);
//        House baratheon = mock(House.class);
//        House freeFolk = mock(House.class);
//        House greyjoy = mock(House.class);
// 
        Player playerStark = new HumanPlayer("Stark", stark);
        Player playerLannister = new HumanPlayer("Lannister", lannister);
        Player playerTargaryen = new HumanPlayer("Targaryen", targaryen);

        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        board.addPlayer(playerTargaryen, 2, Board.HUMAN_PLAYER);

        ArrayList<House> allHouses = new ArrayList<House>();
        allHouses.add(stark);
        allHouses.add(lannister);
        allHouses.add(targaryen);
        board.setHouses(allHouses);

        Mission missionStark = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionLannister = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionBaratheon = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionT = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionRegion = new Mission(null, null, Mission.TYPE_REGION);
        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);

        missionStark.setHouse(stark);
        missionLannister.setHouse(lannister);
        //missionBaratheon.setHouse(baratheon);
        missionT.setHouse(targaryen);

        ArrayList<Mission> allMissions = new ArrayList<Mission>();
        allMissions.add(missionStark);
        allMissions.add(missionLannister);
        allMissions.add(missionBaratheon);
        allMissions.add(missionT);
        allMissions.add(missionRegion);
        allMissions.add(missionTerritory);
        board.setMissions(allMissions);
        
        board.raffleMission();

        assertNotNull(playerStark.getMission());
        assertNotNull(playerLannister.getMission());
        assertNotNull(playerTargaryen.getMission());
//        assertNotEquals(playerStark.getMission(), missionStark);
//        assertNotEquals(playerLannister.getMission(), missionLannister);
//        assertNotEquals(playerTargaryen.getMission(), missionT);
    }

    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

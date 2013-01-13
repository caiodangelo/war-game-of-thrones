/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
        for (int i = 0; i < board.getPlayers().size(); i++) {        
            board.removePlayer(board.getPlayer(i));
            i--;
        }        
        
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
        //      assertEquals(board.getPlayers().size(), 2);
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

    @Test
    public void getAbsentHousesDeveRetornarTodasAsCasasAusentesDoJogo() {
        Player playerStark = mock(Player.class);
        Player playerLannister = mock(Player.class);
        Player playerTargaryen = mock(Player.class);
        Player playerBaratheon = mock(Player.class);
        Player playerFreeFolk = mock(Player.class);
        Player playerGreyjoy = mock(Player.class);

        House stark = mock(House.class);
        House lannister = mock(House.class);
        House targaryen = mock(House.class);
        House baratheon = mock(House.class);
        House freeFolk = mock(House.class);
        House greyjoy = mock(House.class);

        when(stark.getName()).thenReturn("Stark");
        when(lannister.getName()).thenReturn("Lannister");
        when(targaryen.getName()).thenReturn("Targaryen");
        when(baratheon.getName()).thenReturn("Baratheon");
        when(freeFolk.getName()).thenReturn("Rayder");
        when(greyjoy.getName()).thenReturn("Greyjoy");

        when(playerStark.getHouse()).thenReturn(stark);
        when(playerLannister.getHouse()).thenReturn(lannister);
        when(playerTargaryen.getHouse()).thenReturn(targaryen);
        when(playerBaratheon.getHouse()).thenReturn(baratheon);
        when(playerFreeFolk.getHouse()).thenReturn(freeFolk);
        when(playerGreyjoy.getHouse()).thenReturn(lannister);

        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
//        board.addPlayer(playerTargaryen, 2, Board.HUMAN_PLAYER);
//        board.addPlayer(playerBaratheon, 3, Board.HUMAN_PLAYER);
//        board.addPlayer(playerFreeFolk, 4, Board.HUMAN_PLAYER);
//        board.addPlayer(playerGreyjoy, 5, Board.HUMAN_PLAYER);

        LinkedList<House> allHouses = new LinkedList<House>();
        allHouses.add(stark);
        allHouses.add(lannister);
        allHouses.add(targaryen);
        allHouses.add(baratheon);
        allHouses.add(freeFolk);
        allHouses.add(greyjoy);

        List<House> absentHouses = board.getAbsentHouses(allHouses);
        assertEquals(targaryen, absentHouses.get(0));
        assertEquals(baratheon, absentHouses.get(1));
        assertEquals(freeFolk, absentHouses.get(2));
        assertEquals(greyjoy, absentHouses.get(3));
        //         assertNull(absentHouses);
    }

    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

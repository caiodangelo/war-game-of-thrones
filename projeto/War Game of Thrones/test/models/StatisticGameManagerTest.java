/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Anderson
 */
public class StatisticGameManagerTest {
    
    Board board;
    StatisticGameManager gameStatistic;
    
    @BeforeClass
    public void setUp() {
        board = new Board();
        for (int i = 0; i < 6; i++) {
            Player player = new HumanPlayer(null);
            board.addPlayer(player, i, Board.HUMAN_PLAYER);
        }
        gameStatistic = board.getStatistic();
    }
    
    @Test
    public void getMoreAttackerDeveRetornarOJogadorMaisAtacanteDoJogo() {
        board.getPlayer(0).getStatisticPlayerManager().setNumberOfAttacks(34);
        board.getPlayer(1).getStatisticPlayerManager().setNumberOfAttacks(54);
        board.getPlayer(2).getStatisticPlayerManager().setNumberOfAttacks(54);
        board.getPlayer(3).getStatisticPlayerManager().setNumberOfAttacks(14);
        board.getPlayer(4).getStatisticPlayerManager().setNumberOfAttacks(44);
        board.getPlayer(5).getStatisticPlayerManager().setNumberOfAttacks(53);
        
        ArrayList<Player> answer = new ArrayList<Player>();
        answer.add(board.getPlayer(1));
        answer.add(board.getPlayer(2));
        
        assertEquals(gameStatistic.getMoreAttacker(),answer);
    }
    
    @Test
    public void getMoreDefenderDeveRetornarOJogadorMaisAtacadoDoJogo() {
        board.getPlayer(0).getStatisticPlayerManager().setNumberOfDefences(34);
        board.getPlayer(1).getStatisticPlayerManager().setNumberOfDefences(54);
        board.getPlayer(2).getStatisticPlayerManager().setNumberOfDefences(524);
        board.getPlayer(3).getStatisticPlayerManager().setNumberOfDefences(14);
        board.getPlayer(4).getStatisticPlayerManager().setNumberOfDefences(44);
        board.getPlayer(5).getStatisticPlayerManager().setNumberOfDefences(53);
        
        ArrayList<Player> answer = new ArrayList<Player>();
        answer.add(board.getPlayer(2));
        
        assertEquals(gameStatistic.getMoreDefender(), answer);
    }
    
    @Test
    public void getMostWinnerAttacksDeveRetornarOJogadorQueGanhouOMaiorNumeroDeAtaquesDoJogo() {
        board.getPlayer(0).getStatisticPlayerManager().setNumberOfAttackWins(34);
        board.getPlayer(1).getStatisticPlayerManager().setNumberOfAttackWins(34);
        board.getPlayer(2).getStatisticPlayerManager().setNumberOfAttackWins(24);
        board.getPlayer(3).getStatisticPlayerManager().setNumberOfAttackWins(14);
        board.getPlayer(4).getStatisticPlayerManager().setNumberOfAttackWins(44);
        board.getPlayer(5).getStatisticPlayerManager().setNumberOfAttackWins(53);
        
        ArrayList<Player> answer = new ArrayList<Player>();
        answer.add(board.getPlayer(5));
        
        assertEquals(gameStatistic.getMostWinnerAttacks(), answer);
    }
    
    @Test
    public void getMostWinnerDefencesDeveRetornarOJogadorQueGanhouOMaiorNumeroDeDefesasDoJogo() {
        board.getPlayer(0).getStatisticPlayerManager().setNumberOfDefenceWins(34);
        board.getPlayer(1).getStatisticPlayerManager().setNumberOfDefenceWins(34);
        board.getPlayer(2).getStatisticPlayerManager().setNumberOfDefenceWins(24);
        board.getPlayer(3).getStatisticPlayerManager().setNumberOfDefenceWins(14);
        board.getPlayer(4).getStatisticPlayerManager().setNumberOfDefenceWins(74);
        board.getPlayer(5).getStatisticPlayerManager().setNumberOfDefenceWins(53);
        
        ArrayList<Player> answer = new ArrayList<Player>();
        answer.add(board.getPlayer(4));
        
        assertEquals(gameStatistic.getMostWinnerDefences(), answer);
    }
    
    @Test
    public void getTerritoryMoreAttackedDeveRetornarOTerritorioQueFoiOMaisAtacadoDoJogo() {
        board.getTerritories()[0].setNumAttacks(1);
        board.getTerritories()[10].setNumAttacks(45);
        board.getTerritories()[12].setNumAttacks(2);
        board.getTerritories()[23].setNumAttacks(33);
        board.getTerritories()[24].setNumAttacks(13);
        board.getTerritories()[5].setNumAttacks(23);
        board.getTerritories()[19].setNumAttacks(53);
        
        ArrayList<BackEndTerritory> answer = new ArrayList<BackEndTerritory>();
        answer.add(board.getTerritories()[19]);
        
        assertEquals(gameStatistic.getTerritoryMoreAttacked(), answer);
    }
    
    @Test
    public void getTerritoryMoreConqueredDeveRetornarOTerritorioQueFoiOMaisAtacadoDoJogo() {
        board.getTerritories()[0].setNumConquests(1);
        board.getTerritories()[10].setNumConquests(45);
        board.getTerritories()[12].setNumConquests(2);
        board.getTerritories()[23].setNumConquests(63);
        board.getTerritories()[24].setNumConquests(13);
        board.getTerritories()[5].setNumConquests(23);
        board.getTerritories()[19].setNumConquests(53);
        
        ArrayList<BackEndTerritory> answer = new ArrayList<BackEndTerritory>();
        answer.add(board.getTerritories()[23]);
        
        assertEquals(gameStatistic.getTerritoryMoreConquered(), answer);
    }
       
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}
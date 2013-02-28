/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.LinkedList;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Anderson
 */
public class StatisticPlayerManagerTest {
    
    Player player;
    StatisticPlayerManager statistic;
    
    @BeforeClass
    public void setUp() {
        House a = new House();
        player = mock(Player.class);      
        when(player.getStatisticPlayerManager()).thenReturn(statistic);
    }
    
    @Test
    public void averageAttackTablesDeveRetornarAMediaDosDados () {
        statistic = new StatisticPlayerManager();
        Integer [] dice= {3,4,6};
        statistic.averageAttackDices(dice);
        assertEquals(statistic.getDicesOfAttackAverage(), 4.33f);
    }
    
    @Test
    public void averageDefenceTablesDeveRetornarAMediaDosDados () {
        statistic = new StatisticPlayerManager();
        Integer [] dice= {2,1,5};
        statistic.averageDefenceDices(dice);
        assertEquals(statistic.getDicesOfDefenceAverage(), 2.67f);
    }
    
    @Test
    public void averageAttackTablesDeveRetornarAMediaDosDadosComMaisDeUmDadoJogado () {
        statistic = new StatisticPlayerManager();
        Integer [] dice1= {3,6,5};
        statistic.averageAttackDices(dice1);
        Integer [] dice2= {1,1,1};
        statistic.averageAttackDices(dice2);
        Integer [] dice3= {5,6,3};
        statistic.averageAttackDices(dice3);
        assertEquals(statistic.getNumberOfAttackDicesPlayed(), 9);
        assertEquals(statistic.getDicesOfAttackAverage(), 3.45f);
    }
    
    @Test
    public void averageDefenceTablesDeveRetornarAMediaDosDadosComMaisDeUmDadoJogado() {
        statistic = new StatisticPlayerManager();
        Integer [] dice1= {3,6,6};
        statistic.averageDefenceDices(dice1);
        Integer [] dice2= {1,2,1};
        statistic.averageDefenceDices(dice2);
        Integer [] dice3= {5,4,3};
        statistic.averageDefenceDices(dice3);
        assertEquals(statistic.getDicesOfDefenceAverage(), 3.45f);
    }
    
    @Test
    public void setSuccessfulAttackPercentageDeveRetornarAPorcentagemDeAtaquesBemSucedidos() {
        StatisticPlayerManager estatistica = new StatisticPlayerManager();
        estatistica.setNumberOfAttacks(32);
        estatistica.setNumberOfAttackWins(5);
        estatistica.setSuccessfulAttackPercentage();
        assertEquals(estatistica.getSuccessfulAttackPercentage(), 15.63f);
    }
    
    @Test
    public void setSuccessfulDefencePercentageDeveRetornarAPorcentagemDeDefesasBemSucedidos() {
        StatisticPlayerManager estatistica = new StatisticPlayerManager();
        estatistica.setNumberOfDefences(32);
        estatistica.setNumberOfDefenceWins(16);
        estatistica.setSuccessfulDefencePercentage();
        assertEquals(estatistica.getSuccessfulDefencePercentage(), 50f);
    }
    
    @Test
    public void setUpdateTableAtualizaATabelaDeJogadoresAtacadosDoJogadorCorrente(){
        Board board = new Board();
        Player a = mock (Player.class);
        Player b = mock (Player.class);
        Player c = mock (Player.class);
        board.addPlayer(a, 0, Board.HUMAN_PLAYER);
        board.addPlayer(b, 1, Board.HUMAN_PLAYER);
        board.addPlayer(c, 2, Board.HUMAN_PLAYER);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(b);
        statistic.updateAttackTable(c);
        assertEquals(statistic.getAttackTable()[0], 3);
        assertEquals(statistic.getAttackTable()[1], 1);
        assertEquals(statistic.getAttackTable()[2], 1);
    }
    
    @Test
    public void setYouAttackedMoreRetornaOsJogadoresQueVoceAtacouMais() {
        Board board = new Board();
        Player a = mock (Player.class);
        Player b = mock (Player.class);
        Player c = mock (Player.class);
        board.addPlayer(a, 0, Board.HUMAN_PLAYER);
        board.addPlayer(b, 1, Board.HUMAN_PLAYER);
        board.addPlayer(c, 2, Board.HUMAN_PLAYER);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(a);
        statistic.updateAttackTable(b);
        statistic.updateAttackTable(c);
        assertEquals(statistic.getYouAttackMore(), a);
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

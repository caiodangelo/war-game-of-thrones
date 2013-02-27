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
        statistic = mock(StatisticPlayerManager.class);
        when(statistic.getNumberOfAttacks()).thenReturn(32);
        when(statistic.getNumberOfAttackWins()).thenReturn(32);
        statistic.setSuccessfulAttackPercentage();
        assertEquals(statistic.getSuccessfulAttackPercentage(), 0.5f);
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

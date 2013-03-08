package models;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;


/**
 *
 * @author Rodrigo
 */
public class PlayerTest {
    
    Player player;
    House house;
    
    @BeforeClass
    public void setUp() {
//        player = new HumanPlayer("Player One", null);
        house = mock(House.class);
    }
    
    @Test
    public void construtorDeveriaSetarVariaveisCorretamente() {
        String nome = "Meu nome";
        player = new HumanPlayer(nome, house);
        assertEquals(player.getName(), nome);
        assertEquals(player.getHouse(), house);
    }
    
    @Test
    public void construtorDeveInicializarVariaveis() {
        player = new HumanPlayer(null);
        assertEquals(player.getTotalPendingArmies(), 0);
        assertNotNull(player.getTerritories());
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

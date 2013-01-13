package models;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author rodrigo
 */
public class BattleTest {

    Battle battle;
    Territory attacker;
    Territory defender;

    @BeforeClass
    public void setUp() {
        attacker = mock(Territory.class);
        defender = mock(Territory.class);
        Player attackerPlayer = mock(Player.class);
        Player defenderPlayer = mock(Player.class);
        when(attacker.getOwner()).thenReturn(attackerPlayer);
        when(attacker.getNumArmies()).thenReturn(3);
        when(defender.getNumArmies()).thenReturn(3);
        when(defender.getOwner()).thenReturn(defenderPlayer);
        when(attacker.isNeighbour(defender)).thenReturn(true);
    }

    @Test
    public void isArmiesCountValidDeveValidarCorretamenteONumeroDeExercitos() {
        battle = new Battle(attacker, defender, 0, 0);
        assertFalse(battle.isArmiesCountValid());
        battle = new Battle(attacker, defender, 1, 1);
        assertTrue(battle.isArmiesCountValid());
        battle = new Battle(attacker, defender, 2, 2);
        assertTrue(battle.isArmiesCountValid());
        battle = new Battle(attacker, defender, 3, 3);
        assertTrue(battle.isArmiesCountValid());
        battle = new Battle(attacker, defender, 4, 4);
        assertFalse(battle.isArmiesCountValid());
    }

    @Test
    public void construtorDeveValidarOAtaqueCorretamente() {
        battle = new Battle(attacker, defender, 1, 1);
        assertTrue(battle.valid);
        battle = new Battle(attacker, attacker, 1, 1);
        assertFalse(battle.valid); // Atacando território próprio
        battle = new Battle(attacker, defender, 3, 1);
        assertFalse(battle.valid); // Atacando com mais exércitos do que pode
    }

    @Test
    public void compareDicesDeveSerTrueCasoAtaqueVenca() {
        battle = new Battle(attacker, defender, 1, 1);
        assertTrue(battle.compareDices(2, 1));
    }

    @Test
    public void compareDicesDeveSerFalseCasoDefesaVenca() {
        battle = new Battle(attacker, defender, 1, 1);
        assertFalse(battle.compareDices(1, 2));
    }

    @Test
    public void compareDicesDeveFavorecerDefesaNoEmpate() {
        battle = new Battle(attacker, defender, 1, 1);
        assertFalse(battle.compareDices(1, 1));
    }

    @Test
    public void attackDeveMatarUmDefensorQuandoAtaqueGanharEmUmDado() {
        battle = new Battle(attacker, defender, 1, 2);
        when(battle.rollDices(1)).thenReturn(new Integer[] { 5 });
        when(battle.rollDices(2)).thenReturn(new Integer[] { 4, 3 });
        battle.attack();
        assertEquals(1, battle.defenderDeaths);
    }

    @Test
    public void attackDeveMatarUmDefensorEUmAtacante() {
        battle = mock (Battle.class);
        when(battle.rollDices(2)).thenReturn(new Integer[] { 5, 6 });
        when(battle.rollDices(3)).thenReturn(new Integer[] { 5, 5, 3 });
        battle.attack();
        assertEquals(1, battle.defenderDeaths);
        assertEquals(1, battle.attackerDeaths);
    }

    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

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
public class MissionTest {

    Board b;
    Mission mission;
    House stark, lannister, greyjoy;
    Player p1, p2, p3;
    
    @BeforeClass
    public void setUp() {
        b = Board.getInstance();
        
        stark = new House();
        stark.setName("Stark");
        p1 = new HumanPlayer(null, stark);
        b.addPlayer(p1, 0, Board.HUMAN_PLAYER);
        b.addHouse(stark);
        
        lannister = new House();
        lannister.setName("Lannister");
        p2 = new HumanPlayer(null, lannister);
        b.addPlayer(p2, 0, Board.HUMAN_PLAYER);
        b.addHouse(lannister);
        
        greyjoy = new House();
        greyjoy.setName("Greyjoy");
        p3 = new HumanPlayer(null, stark);
        b.addPlayer(p3, 0, Board.HUMAN_PLAYER);
        b.addHouse(greyjoy);
        
        b.createMissions();
    }

    @Test
    public void construtorDeveriaSetarVariaveisCorretamente() {
        String description = "Missão Impossível com Tom Cruise!";
        mission = new Mission(description, Mission.TYPE_TERRITORY);
        assertEquals(mission.description, description);
        assertEquals(mission.type, Mission.TYPE_TERRITORY);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeTerritorios() {
        mission = new Mission(null, Mission.TYPE_TERRITORY);
        assertNotNull(mission.territories);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeContinentes() {
        mission = new Mission(null, Mission.TYPE_REGION);
        assertNotNull(mission.regions);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeHouse() {
        mission = new Mission(null, Mission.TYPE_HOUSE);
        assertNotNull(mission.getHouse());
    }

    @Test
    public void addRegionDeveAdicionarRegionParaMissaoDeRegion() {
        mission = new Mission(null, Mission.TYPE_REGION);
        Region region = Board.getInstance().getRegions()[0];
        assertTrue(mission.addRegion(region));
    }

    @Test
    public void addRegionNaoDeveAdicionarRegionParaOutrasMissoes() {
        mission = new Mission(null, Mission.TYPE_TERRITORY);
        Region region = Board.getInstance().getRegions()[0];
        assertFalse(mission.addRegion(region));
    }

    @Test
    public void hasSameHouseDeveRetornarTrueQuandoOJogadorTemAMesmaCasaDaMissao() {
        House lannister = new House();
        Player playerLannister = new HumanPlayer(null,lannister);
        Mission m = b.getMissions().get(8);
        m.setHouse(lannister);

        assertTrue(m.hasSameHouse(playerLannister));
    }

    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionEstiverCompleta() {
        Mission missionRegion1 = b.getMissions().get(1);        
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionRegion1);
        missionRegion1.setPlayer(playerStark);
        
        playerStark.addTerritory(b.getTerritories()[0]);
        playerStark.addTerritory(b.getTerritories()[1]);
        playerStark.addTerritory(b.getTerritories()[2]);
        playerStark.addTerritory(b.getTerritories()[3]);
        playerStark.addTerritory(b.getTerritories()[4]);
        playerStark.addTerritory(b.getTerritories()[5]);
        playerStark.addTerritory(b.getTerritories()[6]);
        playerStark.addTerritory(b.getTerritories()[7]);
        playerStark.addTerritory(b.getTerritories()[8]);
        playerStark.addTerritory(b.getTerritories()[9]);
        playerStark.addTerritory(b.getTerritories()[10]);
        playerStark.addTerritory(b.getTerritories()[11]);
        playerStark.addTerritory(b.getTerritories()[12]);
        playerStark.addTerritory(b.getTerritories()[13]);
        playerStark.addTerritory(b.getTerritories()[14]);
        
        assertEquals(missionRegion1.getPlayer(), playerStark);
        assertEquals(missionRegion1.getRegions().size(), 2);
        assertEquals(playerStark.getMission(), missionRegion1);
        assertEquals(playerStark.getTerritories().size(), 15);

        assertTrue(missionRegion1.isRegionMissionCompleted());
    }
    
//    @Test
//    public void isRegionMissionCompletedDeveRetornarFalseCasoAMissaoDeRegiaoNaoEstiverCompleta() {
//        Mission missionRegion = b.getMissions().get(5);        
//
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionRegion);
//        missionRegion.setPlayer(playerStark);
//        
//        playerStark.addTerritory(b.getTerritories()[26]);
//        playerStark.addTerritory(b.getTerritories()[27]);
//        playerStark.addTerritory(b.getTerritories()[28]);
//        playerStark.addTerritory(b.getTerritories()[29]);
//        playerStark.addTerritory(b.getTerritories()[30]);
//        playerStark.addTerritory(b.getTerritories()[31]);
//        playerStark.addTerritory(b.getTerritories()[32]);
//       // playerStark.addTerritory(b.getTerritories()[33]);
//        playerStark.addTerritory(b.getTerritories()[34]);
//        playerStark.addTerritory(b.getTerritories()[35]);
//        playerStark.addTerritory(b.getTerritories()[36]);
//        playerStark.addTerritory(b.getTerritories()[37]);
//        playerStark.addTerritory(b.getTerritories()[38]);
//
//        assertFalse(missionRegion.isRegionMissionCompleted());
//    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionQueContenhaRegiaoNulaEstiverCompleta() {
        Mission missionRegion = b.getMissions().get(0);        
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionRegion);
        missionRegion.setPlayer(playerStark);
        
        playerStark.addTerritory(b.getTerritories()[0]);
        playerStark.addTerritory(b.getTerritories()[1]);
        playerStark.addTerritory(b.getTerritories()[2]);
        playerStark.addTerritory(b.getTerritories()[3]);
        playerStark.addTerritory(b.getTerritories()[4]);
        playerStark.addTerritory(b.getTerritories()[5]);
        playerStark.addTerritory(b.getTerritories()[6]);
        playerStark.addTerritory(b.getTerritories()[7]);
        playerStark.addTerritory(b.getTerritories()[8]);
        playerStark.addTerritory(b.getTerritories()[9]);
        playerStark.addTerritory(b.getTerritories()[10]);
        playerStark.addTerritory(b.getTerritories()[11]);
        playerStark.addTerritory(b.getTerritories()[12]);
        playerStark.addTerritory(b.getTerritories()[13]);
        playerStark.addTerritory(b.getTerritories()[14]);
        playerStark.addTerritory(b.getTerritories()[15]);
        playerStark.addTerritory(b.getTerritories()[16]);
        playerStark.addTerritory(b.getTerritories()[17]);
        playerStark.addTerritory(b.getTerritories()[18]);
        playerStark.addTerritory(b.getTerritories()[19]);
        playerStark.addTerritory(b.getTerritories()[30]);
        playerStark.addTerritory(b.getTerritories()[31]);
        
        assertTrue(missionRegion.isRegionMissionCompleted());
    }
    
    @Test
    public void isTerritoryMissionCompletedDeveRetornarTrueCasoAMissaoDeTerritorioEstiverCompleta() {
        Mission missionTerritory = b.getMissions().get(6);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        for (int i = 0; i < 26; i++) {
            playerStark.addTerritory(b.getTerritories()[i]); 
        }
              
        assertTrue(missionTerritory.isTerritoryMissionCompleted());
    }
    
    @Test
    public void isTerritoryMissionCompletedDeveRetornarTrueCasoAMissaoDeTerritorioCom17ExercitosEstiverCompleta() {
        Mission missionTerritory = b.getMissions().get(7);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        for (int i = 0; i < 22; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);        
            playerStark.getTerritories().get(i).setNumArmies(3);
        }
              
        assertTrue(missionTerritory.isTerritoryMissionCompleted());        
    }
    
    @Test
    public void isTerritoryMissionCompletedDeveRetornarFalseCasoAMissaoDeTerritorioCom17ExercitosNaoEstiverCompleta() {
        Mission missionTerritory = b.getMissions().get(7);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        for (int i = 0; i < 22; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);  
            if (i % 2 == 0)
                playerStark.getTerritories().get(i).setNumArmies(1);
            else    
                playerStark.getTerritories().get(i).setNumArmies(4);
        }
              
        assertFalse(missionTerritory.isTerritoryMissionCompleted());        
    }
    
    @Test
    public void isHouseMissionCompletedDeveRetornarTrueCasoAMissaoDeCasaEstiverCompleta() {   
        Mission missionHouse = b.getMissions().get(9);
        missionHouse.setHouse(lannister);
        
        p1.setMission(missionHouse); //P1 é Stark
        missionHouse.setPlayer(p1);
        
        assertTrue(missionHouse.isHouseMissionCompleted());
    }
    
    @Test
    public void isHouseMissionCompletedDeveRetornarFalseCasoAMissaoDeCasaNaoEstiverCompleta() {   
        Mission missionHouse = b.getMissions().get(9);
        missionHouse.setHouse(lannister);
        
        p1.setMission(missionHouse); //P1 é Stark
        missionHouse.setPlayer(p1);
        
        p2.addTerritory(b.getTerritories()[0]); //P2 é Lannister
        
        assertTrue(missionHouse.isHouseMissionCompleted());
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}
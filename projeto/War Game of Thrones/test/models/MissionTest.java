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
    Player playerStark, playerLannister, playerGreyjoy;
    
    @BeforeClass
    public void setUp() {
        b = Board.getInstance();
        
        stark = new House();
        stark.setName("Stark");
        playerStark = new HumanPlayer(null, stark);
        b.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        b.addHouse(stark);
        
        lannister = new House();
        lannister.setName("Lannister");
        playerLannister = new HumanPlayer(null, lannister);
        b.addPlayer(playerLannister, 0, Board.HUMAN_PLAYER);
        b.addHouse(lannister);
        
        greyjoy = new House();
        greyjoy.setName("Greyjoy");
        playerGreyjoy = new HumanPlayer(null, stark);
        b.addPlayer(playerGreyjoy, 0, Board.HUMAN_PLAYER);
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
        Mission m = b.getMissions().get(8);
        m.setHouse(lannister);

        assertTrue(m.hasSameHouse(playerLannister));
    }

    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionEstiverCompleta() {
        Mission missionRegion1 = b.getMissions().get(1);        
        
        playerStark = new HumanPlayer("stark");
        playerStark.setMission(missionRegion1);
        missionRegion1.setPlayer(playerStark);
        
        for (int i = 0; i < 15; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);
        }
        for (int i = 15; i < 39; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);
        }                

        assertTrue(missionRegion1.isRegionMissionCompleted());
    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarFalseCasoAMissaoDeRegiaoNaoEstiverCompleta() {
        Mission missionRegion = b.getMissions().get(5);   

        playerStark.setMission(missionRegion);
        missionRegion.setPlayer(playerStark);
        
        for (int i = 0; i < 28; i++) {
            playerLannister.addTerritory(b.getTerritories()[i]);
        }
        for (int i = 28; i < 39; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);
        }

        assertFalse(missionRegion.isRegionMissionCompleted());
    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionQueContenhaRegiaoNulaEstiverCompleta() {
        Mission missionRegion = b.getMissions().get(0);        
        
        playerStark.setMission(missionRegion);
        missionRegion.setPlayer(playerStark);
        
        for (int i = 0; i < 32; i++) {
            playerStark.addTerritory(b.getTerritories()[i]);
        }
        
        assertTrue(missionRegion.isRegionMissionCompleted());
    }
    
    @Test
    public void isTerritoryMissionCompletedDeveRetornarTrueCasoAMissaoDeTerritorioEstiverCompleta() {
        Mission missionTerritory = b.getMissions().get(6);
        
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
        
        playerStark.setMission(missionHouse);
        missionHouse.setPlayer(playerStark);
        
        assertTrue(missionHouse.isHouseMissionCompleted());
    }
    
    @Test
    public void isHouseMissionCompletedDeveRetornarFalseCasoAMissaoDeCasaNaoEstiverCompleta() {   
        Mission missionHouse = b.getMissions().get(9);
        missionHouse.setHouse(lannister);
        
        playerStark.setMission(missionHouse);
        missionHouse.setPlayer(playerStark);
        
        playerLannister.addTerritory(b.getTerritories()[0]);
        
        assertTrue(missionHouse.isHouseMissionCompleted());
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}
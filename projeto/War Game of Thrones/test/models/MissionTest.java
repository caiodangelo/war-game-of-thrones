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
 * @author Rodrigo
 */
public class MissionTest {

    Mission mission;
    private House stark;

    @BeforeClass
    public void setUp() {
    }

    @Test
    public void construtorDeveriaSetarVariaveisCorretamente() {
        String name = "Missão Impossível";
        String description = "Com Tom Cruise!";
        mission = new Mission(name, description, Mission.TYPE_TERRITORY);
        assertEquals(mission.name, name);
//        assertEquals(mission.description, description);
        assertEquals(mission.type, Mission.TYPE_TERRITORY);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeTerritorios() {
        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
        assertNotNull(mission.territories);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeContinentes() {
        mission = new Mission(null, null, Mission.TYPE_REGION);
        assertNotNull(mission.regions);
    }

    @Test
    public void construtorDeveInicializarVariaveisParaMissaoDeHouse() {
        mission = new Mission(null, null, Mission.TYPE_HOUSE);
        assertNotNull(mission.getHouse());
    }

    @Test
    public void addRegionDeveAdicionarRegionParaMissaoDeRegion() {
        mission = new Mission(null, null, Mission.TYPE_REGION);
        Region region = mock(Region.class);
        assertTrue(mission.addRegion(region));
    }

    @Test
    public void addRegioneNaoDeveAdicionarRegionParaOutrasMissoes() {
        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
        Region region = mock(Region.class);
        assertFalse(mission.addRegion(region));
    }

    @Test
    public void hasSameHouseDeveRetornarTrueQuandoOJogadorTemAMesmaCasaDaMissao() {
        Player playerStark = mock(Player.class);
        Mission m = new Mission(null, null, Mission.TYPE_HOUSE);
        House stark = mock(House.class);

        when(playerStark.getHouse()).thenReturn(stark);
        m.setHouse(stark);

        assertTrue(m.hasSameHouse(playerStark));
    }

    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");
        Region tridente = new Region("Tridente");
        Region beyondTheWall = new Region("Beyond the Wall");
        Region theEast = new Region("The East");
        Region dothraki = new Region("Dothraki");
        
        ArrayList<Region> allRegions = new ArrayList<Region>();
        allRegions.add(theNorth);
        allRegions.add(theSouth);
        allRegions.add(tridente);
        allRegions.add(beyondTheWall);
        allRegions.add(theEast);
        allRegions.add(dothraki);        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);
        Territory pracaDeTorrhen = new Territory(null, theNorth);
        Territory karhold = new Territory(null, theNorth);
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);
        Territory jardimDeCima = new Territory(null, theSouth);
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);

        theNorth.addTerritory(winterfell);
        theNorth.addTerritory(portoBranco);
        theNorth.addTerritory(bosqueProfundo);
        theNorth.addTerritory(forteDoPavor);
        theNorth.addTerritory(pracaDeTorrhen);
        theNorth.addTerritory(karhold);
        theSouth.addTerritory(pontaTempestade);
        theSouth.addTerritory(jardimDeCima);
        theSouth.addTerritory(monteChifre);
        theSouth.addTerritory(dorne);
        theSouth.addTerritory(tarth);

        Mission missionRegion1 = new Mission(null, null, Mission.TYPE_REGION);        
        missionRegion1.addRegion(theNorth);
        missionRegion1.addRegion(theSouth);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionRegion1);
        missionRegion1.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);
        playerStark.addTerritory(pracaDeTorrhen);
        playerStark.addTerritory(karhold);
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);
        playerStark.addTerritory(jardimDeCima);
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(dorne);
        playerStark.addTerritory(tarth);

        assertTrue(missionRegion1.isRegionMissionCompleted(allRegions));
    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarFalseCasoAMissaoDeRegionNaoEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");
        Region tridente = new Region("Tridente");
        Region beyondTheWall = new Region("Beyond the Wall");
        Region theEast = new Region("The East");
        Region dothraki = new Region("Dothraki");
                
        ArrayList<Region> allRegions = new ArrayList<Region>();
        allRegions.add(theNorth);
        allRegions.add(theSouth);
        allRegions.add(tridente);
        allRegions.add(beyondTheWall);
        allRegions.add(theEast);
        allRegions.add(dothraki);    
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);
        Territory pracaDeTorrhen = new Territory(null, theNorth);
        Territory karhold = new Territory(null, theNorth);
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);
        Territory jardimDeCima = new Territory(null, theSouth);
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);

        theNorth.addTerritory(winterfell);
        theNorth.addTerritory(portoBranco);
        theNorth.addTerritory(bosqueProfundo);
        theNorth.addTerritory(forteDoPavor);
        theNorth.addTerritory(pracaDeTorrhen);
        theNorth.addTerritory(karhold);
        theSouth.addTerritory(pontaTempestade);
        theSouth.addTerritory(jardimDeCima);
        theSouth.addTerritory(monteChifre);
        theSouth.addTerritory(dorne);
        theSouth.addTerritory(tarth);

        Mission missionRegion1 = new Mission(null, null, Mission.TYPE_REGION);        
        missionRegion1.addRegion(theNorth);
        missionRegion1.addRegion(theSouth);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionRegion1);
        missionRegion1.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(pracaDeTorrhen);
        playerStark.addTerritory(karhold);
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);
        playerStark.addTerritory(jardimDeCima);
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(tarth);

        assertFalse(missionRegion1.isRegionMissionCompleted(allRegions));
    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionQueContenhaRegiaoNulaEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");
        Region tridente = new Region("Tridente");
        Region beyondTheWall = new Region("Beyond the Wall");
        Region theEast = new Region("The East");
        Region dothraki = new Region("Dothraki");
        Region nulo = new Region(null);
        
        ArrayList<Region> allRegions = new ArrayList<Region>();
        allRegions.add(theNorth);
        allRegions.add(theSouth);
        allRegions.add(tridente);
        allRegions.add(beyondTheWall);
        allRegions.add(theEast);
        allRegions.add(dothraki);    
        allRegions.add(nulo);    
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);
        Territory pracaDeTorrhen = new Territory(null, theNorth);
        Territory karhold = new Territory(null, theNorth);
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);
        Territory jardimDeCima = new Territory(null, theSouth);
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);

        theNorth.addTerritory(winterfell);
        theNorth.addTerritory(portoBranco);
        theNorth.addTerritory(bosqueProfundo);
        theNorth.addTerritory(forteDoPavor);
        theNorth.addTerritory(pracaDeTorrhen);
        theNorth.addTerritory(karhold);
        theSouth.addTerritory(pontaTempestade);
        theSouth.addTerritory(jardimDeCima);
        theSouth.addTerritory(monteChifre);
        theSouth.addTerritory(dorne);
        theSouth.addTerritory(tarth);
        tridente.addTerritory(portoReal);
        tridente.addTerritory(correrio);

        Mission missionRegion1 = new Mission(null, null, Mission.TYPE_REGION);        
        missionRegion1.addRegion(theNorth);
        missionRegion1.addRegion(theSouth);
        missionRegion1.addRegion(nulo);
        
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionRegion1);
        missionRegion1.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);
        playerStark.addTerritory(pracaDeTorrhen);
        playerStark.addTerritory(karhold);
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);
        playerStark.addTerritory(jardimDeCima);
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(dorne);
        playerStark.addTerritory(tarth);

        assertTrue(missionRegion1.isRegionMissionCompleted(allRegions));
    }
    
    @Test
    public void isRegionTerritoryCompletedDeveRetornarTrueCasoAMissaoDeTerritorioEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);        
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);      
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);        

        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);

        missionTerritory.territories = 10;
            
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);       
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);        
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(dorne);
        playerStark.addTerritory(tarth);

        assertTrue(missionTerritory.isTerritoryMissionCompleted());
    }
    
    @Test
    public void isRegionTerritoryCompletedDeveRetornarTrueCasoAMissaoDeTerritorioCom18ExercitosEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);        
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);      
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);        
        Territory rochedoCasterly = new Territory(null, theSouth);        
        Territory ninhoDaAguia = new Territory(null, theSouth);        
        Territory harrenhal = new Territory(null, theSouth);        
        Territory guardamar = new Territory(null, theSouth);        
        Territory pyke = new Territory(null, theSouth);        
        Territory casteloNegro = new Territory(null, theSouth);        
        Territory pentos = new Territory(null, theSouth);        
        Territory qarth = new Territory(null, theSouth);        
        Territory arvore = new Territory(null, theSouth);        
        
        winterfell.setNumArmies(2);
        portoBranco.setNumArmies(2);
        bosqueProfundo.setNumArmies(2);
        forteDoPavor.setNumArmies(2);
        portoReal.setNumArmies(13);
        correrio.setNumArmies(6);
        pontaTempestade.setNumArmies(2);      
        monteChifre.setNumArmies(5);
        dorne.setNumArmies(7);
        tarth.setNumArmies(2);        
        rochedoCasterly.setNumArmies(2);        
        ninhoDaAguia.setNumArmies(4);        
        harrenhal.setNumArmies(8);        
        guardamar.setNumArmies(4);      
        pyke.setNumArmies(5);       
        casteloNegro.setNumArmies(2);     
        pentos.setNumArmies(7);  
        qarth.setNumArmies(5);    
        arvore.setNumArmies(2);   

        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
        
        missionTerritory.territories = 18;
     
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);       
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);        
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(dorne);
        playerStark.addTerritory(rochedoCasterly);
        playerStark.addTerritory(ninhoDaAguia);
        playerStark.addTerritory(harrenhal);
        playerStark.addTerritory(pyke);
        playerStark.addTerritory(guardamar);
        playerStark.addTerritory(casteloNegro);
        playerStark.addTerritory(qarth);
        playerStark.addTerritory(pentos);
        playerStark.addTerritory(arvore);

        assertTrue(missionTerritory.isTerritoryMissionCompleted());
    }
    
    @Test
    public void isRegionTerritoryCompletedDeveRetornarFalseCasoAMissaoDeTerritorioCom18ExercitosNaoEstiverCompleta() {
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);        
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        Territory pontaTempestade = new Territory(null, theSouth);      
        Territory monteChifre = new Territory(null, theSouth);
        Territory dorne = new Territory(null, theSouth);
        Territory tarth = new Territory(null, theSouth);        
        Territory rochedoCasterly = new Territory(null, theSouth);        
        Territory ninhoDaAguia = new Territory(null, theSouth);        
        Territory harrenhal = new Territory(null, theSouth);        
        Territory guardamar = new Territory(null, theSouth);        
        Territory pyke = new Territory(null, theSouth);        
        Territory casteloNegro = new Territory(null, theSouth);        
        Territory pentos = new Territory(null, theSouth);        
        Territory qarth = new Territory(null, theSouth);        
        Territory arvore = new Territory(null, theSouth);        
        
        winterfell.setNumArmies(2);
        portoBranco.setNumArmies(2);
        bosqueProfundo.setNumArmies(2);
        forteDoPavor.setNumArmies(2);
        portoReal.setNumArmies(13);
        correrio.setNumArmies(6);
        pontaTempestade.setNumArmies(2);      
        monteChifre.setNumArmies(5);
        dorne.setNumArmies(7);
        tarth.setNumArmies(2);        
        rochedoCasterly.setNumArmies(2);        
        ninhoDaAguia.setNumArmies(1);        
        harrenhal.setNumArmies(8);        
        guardamar.setNumArmies(4);      
        pyke.setNumArmies(5);       
        casteloNegro.setNumArmies(2);     
        pentos.setNumArmies(7);  
        qarth.setNumArmies(1);    
        arvore.setNumArmies(2);   

        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
        
        missionTerritory.territories = 18;
     
        Player playerStark = new HumanPlayer(null);
        playerStark.setMission(missionTerritory);
        missionTerritory.setPlayer(playerStark);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);       
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
        playerStark.addTerritory(pontaTempestade);        
        playerStark.addTerritory(monteChifre);
        playerStark.addTerritory(dorne);
        playerStark.addTerritory(rochedoCasterly);
        playerStark.addTerritory(ninhoDaAguia);
        playerStark.addTerritory(harrenhal);
        playerStark.addTerritory(pyke);
        playerStark.addTerritory(guardamar);
        playerStark.addTerritory(casteloNegro);
        playerStark.addTerritory(qarth);
        playerStark.addTerritory(pentos);
        playerStark.addTerritory(arvore);

        assertFalse(missionTerritory.isTerritoryMissionCompleted());
    }
    
    @Test
    public void isRegionHouseCompletedDeveRetornarTrueCasoAMissaoDeCasaEstiverCompleta() {   
        Board board = new Board();
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);        
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        
        House stark = new House();
        House lannister = new House();
        House greyjoy = new House();
        Mission missionHouse = new Mission(null, null, Mission.TYPE_HOUSE);
        missionHouse.setHouse(lannister);
        
        Player playerStark = new HumanPlayer("Robb Stark");
        Player playerLannister = new HumanPlayer("tyrion");
        Player playerGreyjoy = new HumanPlayer("Theon");
        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        board.addPlayer(playerGreyjoy, 2, Board.HUMAN_PLAYER);
        
        playerStark.setMission(missionHouse);
        missionHouse.setPlayer(playerStark);

        playerStark.setHouse(stark);
        playerLannister.setHouse(lannister);
        playerGreyjoy.setHouse(greyjoy);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);       
        playerStark.addTerritory(portoReal);
        playerStark.addTerritory(correrio);
       
        assertTrue(missionHouse.isHouseMissionCompleted(board));
    }
    
    @Test
    public void isRegionHouseCompletedDeveRetornarFalseCasoAMissaoDeCasaNaoEstiverCompleta() {   
        Board board = new Board();
        Region theNorth = new Region("The North");
        Region theSouth = new Region("The South");        
        
        Territory winterfell = new Territory(null, theNorth);
        Territory portoBranco = new Territory(null, theNorth);
        Territory bosqueProfundo = new Territory(null, theNorth);
        Territory forteDoPavor = new Territory(null, theNorth);        
        Territory portoReal = new Territory(null, theNorth);
        Territory correrio = new Territory(null, theNorth);
        
        House stark = new House();
        House lannister = new House();
        House greyjoy = new House();
        Mission missionHouse = new Mission(null, null, Mission.TYPE_HOUSE);
        missionHouse.setHouse(lannister);
        
        Player playerStark = new HumanPlayer("Robb Stark");
        Player playerLannister = new HumanPlayer("tyrion");
        Player playerGreyjoy = new HumanPlayer("Theon");
        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        board.addPlayer(playerGreyjoy, 2, Board.HUMAN_PLAYER);
        
        playerStark.setMission(missionHouse);
        missionHouse.setPlayer(playerStark);

        playerStark.setHouse(stark);
        playerLannister.setHouse(lannister);
        playerGreyjoy.setHouse(greyjoy);
        
        playerStark.addTerritory(winterfell);
        playerStark.addTerritory(portoBranco);
        playerStark.addTerritory(bosqueProfundo);
        playerStark.addTerritory(forteDoPavor);       
        playerStark.addTerritory(portoReal);        
        playerLannister.addTerritory(correrio);
       
        assertFalse(missionHouse.isHouseMissionCompleted(board));
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

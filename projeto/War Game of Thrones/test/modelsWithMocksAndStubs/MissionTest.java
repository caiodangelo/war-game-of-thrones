///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package modelsWithMocksAndStubs;
//
//import models.*;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//import static org.testng.Assert.*;
//import static org.mockito.Mockito.*;
//
///**
// *
// * @author Rodrigo
// */
//public class MissionTest {
//
//    Mission mission;
//    private House stark;
//
//    @BeforeClass
//    public void setUp() {
//    }
//
//    @Test
//    public void construtorDeveriaSetarVariaveisCorretamente() {
//        String name = "Missão Impossível";
//        String description = "Com Tom Cruise!";
//        mission = new Mission(name, description, Mission.TYPE_TERRITORY);
//        assertEquals( mission.description, name);
////        assertEquals(mission.description, description);
//        assertEquals(mission.type, Mission.TYPE_TERRITORY);
//    }
//
//    @Test
//    public void construtorDeveInicializarVariaveisParaMissaoDeTerritorios() {
//        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
//        assertNotNull(mission.territories);
//    }
//
//    @Test
//    public void construtorDeveInicializarVariaveisParaMissaoDeContinentes() {
//        mission = new Mission(null, null, Mission.TYPE_REGION);
//        assertNotNull(mission.regions);
//    }
//
//    @Test
//    public void construtorDeveInicializarVariaveisParaMissaoDeHouse() {
//        mission = new Mission(null, null, Mission.TYPE_HOUSE);
//        assertNotNull(mission.getHouse());
//    }
//
//    @Test
//    public void addRegionDeveAdicionarRegionParaMissaoDeRegion() {
//        mission = new Mission(null, null, Mission.TYPE_REGION);
//        Region region = mock(Region.class);
//        assertTrue(mission.addRegion(region));
//    }
//
//    @Test
//    public void addRegioneNaoDeveAdicionarRegionParaOutrasMissoes() {
//        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
//        Region region = mock(Region.class);
//        assertFalse(mission.addRegion(region));
//    }
//
//    @Test
//    public void hasSameHouseDeveRetornarTrueQuandoOJogadorTemAMesmaCasaDaMissao() {
//        Player playerStark = mock(Player.class);
//        Mission m = new Mission(null, null, Mission.TYPE_HOUSE);
//        House stark = mock(House.class);
//
//        when(playerStark.getHouse()).thenReturn(stark);
//        m.setHouse(stark);
//
//        assertTrue(m.hasSameHouse(playerStark));
//    }
//
//    @Test
//    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionEstiverCompleta() {
//        Board b = new Board();
////        Region theNorth = new Region("The North", Region.O_NORTE);
////        Region theSouth = new Region("The South", Region.O_SUL);
////        Region tridente = new Region("Tridente", Region.TRIDENTE);
////        Region beyondTheWall = new Region("Beyond the Wall", Region.ALEM_DA_MURALHA);
////        Region theEast = new Region("The East", Region.CIDADES_LIVRES);
////        Region dothraki = new Region("Dothraki", Region.O_MAR_DOTHRAKI);
//        
////        Region [] allRegions = b.getRegions();
////        allRegions[0] = theSouth;
////        allRegions[1] = tridente;
////        allRegions[2] = beyondTheWall;
////        allRegions[3] = theEast;
////        allRegions[4] = dothraki;
////        allRegions[5] = theNorth;
//        
////        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
////        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
////        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pracaDeTorrhen = new BackEndTerritory(null, theNorth);
////        BackEndTerritory karhold = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
////        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);
////        BackEndTerritory jardimDeCima = new BackEndTerritory(null, theSouth);
////        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
////        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
////        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);
////
////        theNorth.addTerritory(winterfell);
////        theNorth.addTerritory(portoBranco);
////        theNorth.addTerritory(bosqueProfundo);
////        theNorth.addTerritory(forteDoPavor);
////        theNorth.addTerritory(pracaDeTorrhen);
////        theNorth.addTerritory(karhold);
////        theSouth.addTerritory(pontaTempestade);
////        theSouth.addTerritory(jardimDeCima);
////        theSouth.addTerritory(monteChifre);
////        theSouth.addTerritory(dorne);
////        theSouth.addTerritory(tarth);
//
////        Mission missionRegion1 = new Mission(null, null, Mission.TYPE_REGION);        
////        missionRegion1.addRegion(theNorth);
////        missionRegion1.addRegion(theSouth);
//        
//        Mission missionRegion1 = new Mission("", Mission.TYPE_REGION);        
//        missionRegion1.addRegion(b.getRegions()[0]);
//        missionRegion1.addRegion(b.getRegions()[1]);
//        
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionRegion1);
//        missionRegion1.setPlayer(playerStark);
//        
//        playerStark.addTerritory(b.getTerritories()[0]);
//        playerStark.addTerritory(b.getTerritories()[1]);
//        playerStark.addTerritory(b.getTerritories()[2]);
//        playerStark.addTerritory(b.getTerritories()[3]);
//        playerStark.addTerritory(b.getTerritories()[4]);
//        playerStark.addTerritory(b.getTerritories()[5]);
//        playerStark.addTerritory(b.getTerritories()[6]);
//        playerStark.addTerritory(b.getTerritories()[7]);
//        playerStark.addTerritory(b.getTerritories()[8]);
//        playerStark.addTerritory(b.getTerritories()[9]);
//        playerStark.addTerritory(b.getTerritories()[10]);
//        playerStark.addTerritory(b.getTerritories()[11]);
//        playerStark.addTerritory(b.getTerritories()[12]);
//        playerStark.addTerritory(b.getTerritories()[13]);
//        playerStark.addTerritory(b.getTerritories()[14]);
//        
//        assertEquals(missionRegion1.getPlayer(), playerStark);
//        assertEquals(missionRegion1.getRegions().size(), 2);
//        assertEquals(playerStark.getMission(), missionRegion1);
//        assertEquals(playerStark.getTerritories().size(), 15);
////        playerStark.addTerritory(winterfell);
////        playerStark.addTerritory(portoBranco);
////        playerStark.addTerritory(bosqueProfundo);
////        playerStark.addTerritory(forteDoPavor);
////        playerStark.addTerritory(pracaDeTorrhen);
////        playerStark.addTerritory(karhold);
////        playerStark.addTerritory(portoReal);
////        playerStark.addTerritory(correrio);
////        playerStark.addTerritory(pontaTempestade);
////        playerStark.addTerritory(jardimDeCima);
////        playerStark.addTerritory(monteChifre);
////        playerStark.addTerritory(dorne);
////        playerStark.addTerritory(tarth);
//        assertTrue(missionRegion1.isRegionMissionCompleted());
//    }
//    
//    @Test
//    public void isRegionMissionCompletedDeveRetornarFalseCasoAMissaoDeRegionNaoEstiverCompleta() {
//        Board b = new Board();
////        Region theNorth = new Region("The North", Region.O_NORTE);
////        Region theSouth = new Region("The South", Region.O_SUL);
////        Region tridente = new Region("Tridente", Region.TRIDENTE);
////        Region beyondTheWall = new Region("Beyond the Wall", Region.ALEM_DA_MURALHA);
////        Region theEast = new Region("The East", Region.CIDADES_LIVRES);
////        Region dothraki = new Region("Dothraki", Region.O_MAR_DOTHRAKI);
////
////        Region [] allRegions = b.getRegions();
////        allRegions[0] = theSouth;
////        allRegions[1] = tridente;
////        allRegions[2] = beyondTheWall;
////        allRegions[3] = theEast;
////        allRegions[4] = dothraki;
////        allRegions[5] = theNorth;
////        
////        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
////        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
////        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pracaDeTorrhen = new BackEndTerritory(null, theNorth);
////        BackEndTerritory karhold = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
////        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);
////        BackEndTerritory jardimDeCima = new BackEndTerritory(null, theSouth);
////        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
////        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
////        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);
////
////        theNorth.addTerritory(winterfell);
////        theNorth.addTerritory(portoBranco);
////        theNorth.addTerritory(bosqueProfundo);
////        theNorth.addTerritory(forteDoPavor);
////        theNorth.addTerritory(pracaDeTorrhen);
////        theNorth.addTerritory(karhold);
////        theSouth.addTerritory(pontaTempestade);
////        theSouth.addTerritory(jardimDeCima);
////        theSouth.addTerritory(monteChifre);
////        theSouth.addTerritory(dorne);
////        theSouth.addTerritory(tarth);
//
////        Mission missionRegion1 = new Mission(null, null, Mission.TYPE_REGION);        
////        missionRegion1.addRegion(theNorth);
////        missionRegion1.addRegion(theSouth);
//        
//        Mission missionRegion1 = new Mission("", Mission.TYPE_REGION);        
//        missionRegion1.addRegion(b.getRegions()[0]);
//        missionRegion1.addRegion(b.getRegions()[1]);
//
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionRegion1);
//        missionRegion1.setPlayer(playerStark);
//        
//        playerStark.addTerritory(b.getTerritories()[0]);
//        playerStark.addTerritory(b.getTerritories()[1]);
//        playerStark.addTerritory(b.getTerritories()[2]);
//        playerStark.addTerritory(b.getTerritories()[3]);
//        playerStark.addTerritory(b.getTerritories()[4]);
//        playerStark.addTerritory(b.getTerritories()[5]);
//        playerStark.addTerritory(b.getTerritories()[6]);
//        playerStark.addTerritory(b.getTerritories()[7]);
//        playerStark.addTerritory(b.getTerritories()[8]);
// //       playerStark.addTerritory(b.getTerritories()[9]);
//        playerStark.addTerritory(b.getTerritories()[10]);
//        playerStark.addTerritory(b.getTerritories()[11]);
//        playerStark.addTerritory(b.getTerritories()[12]);
//        playerStark.addTerritory(b.getTerritories()[13]);
//        playerStark.addTerritory(b.getTerritories()[14]);
////        playerStark.addTerritory(winterfell);
////        playerStark.addTerritory(portoBranco);
////        playerStark.addTerritory(bosqueProfundo);
////        playerStark.addTerritory(pracaDeTorrhen);
////        playerStark.addTerritory(karhold);
////        playerStark.addTerritory(portoReal);
////        playerStark.addTerritory(correrio);
////        playerStark.addTerritory(pontaTempestade);
////        playerStark.addTerritory(jardimDeCima);
////        playerStark.addTerritory(monteChifre);
////        playerStark.addTerritory(tarth);
//
//        assertFalse(missionRegion1.isRegionMissionCompleted());
//    }
//    
//    @Test
//    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionQueContenhaRegiaoNulaEstiverCompleta() {
//        Board b = new  Board();
////        Region theNorth = new Region("The North", Region.O_NORTE);
////        Region theSouth = new Region("The South", Region.O_SUL);
////        Region tridente = new Region("Tridente", Region.TRIDENTE);
////        Region beyondTheWall = new Region("Beyond the Wall", Region.ALEM_DA_MURALHA);
////        Region theEast = new Region("The East", Region.CIDADES_LIVRES);
////        Region dothraki = new Region("Dothraki", Region.O_MAR_DOTHRAKI);
//        Region nulo = new Region(null, 0);
////        
////        Region [] allRegions = b.getRegions();
////        allRegions[0] = theSouth;
////        allRegions[1] = tridente;
////        allRegions[2] = beyondTheWall;
////        allRegions[3] = theEast;
////        allRegions[4] = dothraki;
////        allRegions[5] = theNorth;    
////      
////        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
////        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
////        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pracaDeTorrhen = new BackEndTerritory(null, theNorth);
////        BackEndTerritory karhold = new BackEndTerritory(null, theNorth);
////        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
////        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
////        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);
////        BackEndTerritory jardimDeCima = new BackEndTerritory(null, theSouth);
////        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
////        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
////        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);
////
////        theNorth.addTerritory(winterfell);
////        theNorth.addTerritory(portoBranco);
////        theNorth.addTerritory(bosqueProfundo);
////        theNorth.addTerritory(forteDoPavor);
////        theNorth.addTerritory(pracaDeTorrhen);
////        theNorth.addTerritory(karhold);
////        theSouth.addTerritory(pontaTempestade);
////        theSouth.addTerritory(jardimDeCima);
////        theSouth.addTerritory(monteChifre);
////        theSouth.addTerritory(dorne);
////        theSouth.addTerritory(tarth);
////        tridente.addTerritory(portoReal);
////        tridente.addTerritory(correrio);
//
//        Mission missionRegion1 = new Mission("", Mission.TYPE_REGION);        
//        missionRegion1.addRegion(b.getRegions()[0]);
//        missionRegion1.addRegion(b.getRegions()[1]);
//        missionRegion1.addRegion(nulo);
//        
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionRegion1);
//        missionRegion1.setPlayer(playerStark);
//        
//        playerStark.addTerritory(b.getTerritories()[0]);
//        playerStark.addTerritory(b.getTerritories()[1]);
//        playerStark.addTerritory(b.getTerritories()[2]);
//        playerStark.addTerritory(b.getTerritories()[3]);
//        playerStark.addTerritory(b.getTerritories()[4]);
//        playerStark.addTerritory(b.getTerritories()[5]);
//        playerStark.addTerritory(b.getTerritories()[6]);
//        playerStark.addTerritory(b.getTerritories()[7]);
//        playerStark.addTerritory(b.getTerritories()[8]);
//        playerStark.addTerritory(b.getTerritories()[9]);
//        playerStark.addTerritory(b.getTerritories()[10]);
//        playerStark.addTerritory(b.getTerritories()[11]);
//        playerStark.addTerritory(b.getTerritories()[12]);
//        playerStark.addTerritory(b.getTerritories()[13]);
//        playerStark.addTerritory(b.getTerritories()[14]);
//        playerStark.addTerritory(b.getTerritories()[15]);
//        playerStark.addTerritory(b.getTerritories()[16]);
//        playerStark.addTerritory(b.getTerritories()[17]);
//        playerStark.addTerritory(b.getTerritories()[18]);
//        playerStark.addTerritory(b.getTerritories()[19]);
//        
////        playerStark.addTerritory(winterfell);
////        playerStark.addTerritory(portoBranco);
////        playerStark.addTerritory(bosqueProfundo);
////        playerStark.addTerritory(forteDoPavor);
////        playerStark.addTerritory(pracaDeTorrhen);
////        playerStark.addTerritory(karhold);
////        playerStark.addTerritory(portoReal);
////        playerStark.addTerritory(correrio);
////        playerStark.addTerritory(pontaTempestade);
////        playerStark.addTerritory(jardimDeCima);
////        playerStark.addTerritory(monteChifre);
////        playerStark.addTerritory(dorne);
////        playerStark.addTerritory(tarth);
//
//        assertTrue(missionRegion1.isRegionMissionCompleted());
//    }
//    
//    @Test
//    public void isTerritoryMissionCompletedDeveRetornarTrueCasoAMissaoDeTerritorioEstiverCompleta() {
//        Region theNorth = new Region("The North", Region.O_NORTE);
//        Region theSouth = new Region("The South", Region.O_SUL);        
//        
//        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
//        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
//        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
//        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);        
//        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
//        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
//        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);      
//        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
//        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
//        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);        
//
//        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
//
//        missionTerritory.territories = 10;
//            
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionTerritory);
//        missionTerritory.setPlayer(playerStark);
//        
//        playerStark.addTerritory(winterfell);
//        playerStark.addTerritory(portoBranco);
//        playerStark.addTerritory(bosqueProfundo);
//        playerStark.addTerritory(forteDoPavor);       
//        playerStark.addTerritory(portoReal);
//        playerStark.addTerritory(correrio);
//        playerStark.addTerritory(pontaTempestade);        
//        playerStark.addTerritory(monteChifre);
//        playerStark.addTerritory(dorne);
//        playerStark.addTerritory(tarth);
//
//        assertTrue(missionTerritory.isTerritoryMissionCompleted());
//    }
//    
//    @Test
//    public void isTerritoryMissionCompletedDeveRetornarTrueCasoAMissaoDeTerritorioCom17ExercitosEstiverCompleta() {
//        Region theNorth = new Region("The North", Region.O_NORTE);
//        Region theSouth = new Region("The South", Region.O_SUL);
//        
//        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
//        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
//        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
//        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);        
//        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
//        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
//        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);      
//        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
//        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
//        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory rochedoCasterly = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory ninhoDaAguia = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory harrenhal = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory guardamar = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory pyke = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory casteloNegro = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory pentos = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory qarth = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory arvore = new BackEndTerritory(null, theSouth);        
//        
//        winterfell.setNumArmies(2);
//        portoBranco.setNumArmies(2);
//        bosqueProfundo.setNumArmies(2);
//        forteDoPavor.setNumArmies(2);
//        portoReal.setNumArmies(13);
//        correrio.setNumArmies(6);
//        pontaTempestade.setNumArmies(2);      
//        monteChifre.setNumArmies(5);
//        dorne.setNumArmies(7);
//        tarth.setNumArmies(2);        
//        rochedoCasterly.setNumArmies(2);        
//        ninhoDaAguia.setNumArmies(4);        
//        harrenhal.setNumArmies(8);        
//        guardamar.setNumArmies(4);      
//        pyke.setNumArmies(5);       
//        casteloNegro.setNumArmies(2);     
//        pentos.setNumArmies(7);  
//        qarth.setNumArmies(5);    
//        arvore.setNumArmies(2);   
//
//        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
//        
//        missionTerritory.territories = 17;
//     
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionTerritory);
//        missionTerritory.setPlayer(playerStark);
//        
//        playerStark.addTerritory(winterfell);
//        playerStark.addTerritory(portoBranco);
//        playerStark.addTerritory(bosqueProfundo);
//        playerStark.addTerritory(forteDoPavor);       
//        playerStark.addTerritory(portoReal);
//        playerStark.addTerritory(correrio);
//        playerStark.addTerritory(pontaTempestade);        
//        playerStark.addTerritory(monteChifre);
//        playerStark.addTerritory(dorne);
//        playerStark.addTerritory(rochedoCasterly);
//        playerStark.addTerritory(ninhoDaAguia);
//        playerStark.addTerritory(harrenhal);
//        playerStark.addTerritory(pyke);
//        playerStark.addTerritory(guardamar);
//        playerStark.addTerritory(casteloNegro);
//        playerStark.addTerritory(qarth);
//        playerStark.addTerritory(pentos);
//        playerStark.addTerritory(arvore);
//
//        assertTrue(missionTerritory.isTerritoryMissionCompleted());
//    }
//    
//    @Test
//    public void isTerritoryMissionCompletedDeveRetornarFalseCasoAMissaoDeTerritorioCom18ExercitosNaoEstiverCompleta() {
//        Region theNorth = new Region("The North", Region.O_NORTE);
//        Region theSouth = new Region("The South", Region.O_SUL);       
//        
//        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
//        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
//        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
//        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);        
//        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
//        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
//        BackEndTerritory pontaTempestade = new BackEndTerritory(null, theSouth);      
//        BackEndTerritory monteChifre = new BackEndTerritory(null, theSouth);
//        BackEndTerritory dorne = new BackEndTerritory(null, theSouth);
//        BackEndTerritory tarth = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory rochedoCasterly = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory ninhoDaAguia = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory harrenhal = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory guardamar = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory pyke = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory casteloNegro = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory pentos = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory qarth = new BackEndTerritory(null, theSouth);        
//        BackEndTerritory arvore = new BackEndTerritory(null, theSouth);        
//        
//        winterfell.setNumArmies(2);
//        portoBranco.setNumArmies(2);
//        bosqueProfundo.setNumArmies(2);
//        forteDoPavor.setNumArmies(2);
//        portoReal.setNumArmies(13);
//        correrio.setNumArmies(6);
//        pontaTempestade.setNumArmies(2);      
//        monteChifre.setNumArmies(5);
//        dorne.setNumArmies(7);
//        tarth.setNumArmies(2);        
//        rochedoCasterly.setNumArmies(2);        
//        ninhoDaAguia.setNumArmies(1);        
//        harrenhal.setNumArmies(8);        
//        guardamar.setNumArmies(4);      
//        pyke.setNumArmies(5);       
//        casteloNegro.setNumArmies(2);     
//        pentos.setNumArmies(7);  
//        qarth.setNumArmies(1);    
//        arvore.setNumArmies(2);   
//
//        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);
//        
//        missionTerritory.territories = 17;
//     
//        Player playerStark = new HumanPlayer(null);
//        playerStark.setMission(missionTerritory);
//        missionTerritory.setPlayer(playerStark);
//        
//        playerStark.addTerritory(winterfell);
//        playerStark.addTerritory(portoBranco);
//        playerStark.addTerritory(bosqueProfundo);
//        playerStark.addTerritory(forteDoPavor);       
//        playerStark.addTerritory(portoReal);
//        playerStark.addTerritory(correrio);
//        playerStark.addTerritory(pontaTempestade);        
//        playerStark.addTerritory(monteChifre);
//        playerStark.addTerritory(dorne);
//        playerStark.addTerritory(rochedoCasterly);
//        playerStark.addTerritory(ninhoDaAguia);
//        playerStark.addTerritory(harrenhal);
//        playerStark.addTerritory(pyke);
//        playerStark.addTerritory(guardamar);
//        playerStark.addTerritory(casteloNegro);
//        playerStark.addTerritory(qarth);
//        playerStark.addTerritory(pentos);
//        playerStark.addTerritory(arvore);
//
//        assertFalse(missionTerritory.isTerritoryMissionCompleted());
//    }
//    
//    @Test
//    public void isHouseMissionCompletedDeveRetornarTrueCasoAMissaoDeCasaEstiverCompleta() {   
//        Board board = new Board();
//        Region theNorth = new Region("The North", Region.O_NORTE);
//        Region theSouth = new Region("The South", Region.O_SUL);
//                        
//        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
//        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
//        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
//        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);        
//        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
//        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
//        
//        House stark = new House();
//        House lannister = new House();
//        House greyjoy = new House();
//        Mission missionHouse = new Mission(null, null, Mission.TYPE_HOUSE);
//        missionHouse.setHouse(lannister);
//        
//        Player playerStark = new HumanPlayer("Robb Stark");
//        Player playerLannister = new HumanPlayer("tyrion");
//        Player playerGreyjoy = new HumanPlayer("Theon");
//        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
//        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
//        board.addPlayer(playerGreyjoy, 2, Board.HUMAN_PLAYER);
//        
//        playerStark.setMission(missionHouse);
//        missionHouse.setPlayer(playerStark);
//
//        playerStark.setHouse(stark);
//        playerLannister.setHouse(lannister);
//        playerGreyjoy.setHouse(greyjoy);
//        
//        playerStark.addTerritory(winterfell);
//        playerStark.addTerritory(portoBranco);
//        playerStark.addTerritory(bosqueProfundo);
//        playerStark.addTerritory(forteDoPavor);       
//        playerStark.addTerritory(portoReal);
//        playerStark.addTerritory(correrio);
//       
//        assertTrue(missionHouse.isHouseMissionCompleted());
//    }
//    
//    @Test
//    public void isHouseMissionCompletedDeveRetornarFalseCasoAMissaoDeCasaNaoEstiverCompleta() {   
//        Board board = new Board();
//        Region theNorth = new Region("The North", Region.O_NORTE);
//        Region theSouth = new Region("The South", Region.O_SUL);       
//        
//        BackEndTerritory winterfell = new BackEndTerritory(null, theNorth);
//        BackEndTerritory portoBranco = new BackEndTerritory(null, theNorth);
//        BackEndTerritory bosqueProfundo = new BackEndTerritory(null, theNorth);
//        BackEndTerritory forteDoPavor = new BackEndTerritory(null, theNorth);        
//        BackEndTerritory portoReal = new BackEndTerritory(null, theNorth);
//        BackEndTerritory correrio = new BackEndTerritory(null, theNorth);
//        
//        House stark = new House();
//        House lannister = new House();
//        House greyjoy = new House();
//        Mission missionHouse = new Mission(null, Mission.TYPE_HOUSE, lannister);
//        
//        Player playerStark = new HumanPlayer("Robb Stark");
//        Player playerLannister = new HumanPlayer("tyrion");
//        Player playerGreyjoy = new HumanPlayer("Theon");
//        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
//        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
//        board.addPlayer(playerGreyjoy, 2, Board.HUMAN_PLAYER);
//        
//        playerStark.setMission(missionHouse);
//        missionHouse.setPlayer(playerStark);
//
//        playerStark.setHouse(stark);
//        playerLannister.setHouse(lannister);
//        playerGreyjoy.setHouse(greyjoy);
//        
//        playerStark.addTerritory(winterfell);
//        playerStark.addTerritory(portoBranco);
//        playerStark.addTerritory(bosqueProfundo);
//        playerStark.addTerritory(forteDoPavor);       
//        playerStark.addTerritory(portoReal);        
//        playerLannister.addTerritory(correrio);
//       
//        assertFalse(missionHouse.isHouseMissionCompleted());
//    }
//    
//    @AfterClass
//    public void cleanUp() {
//        // code that will be invoked after this test ends
//    }
//}
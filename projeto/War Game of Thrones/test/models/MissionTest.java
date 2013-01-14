/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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

    @BeforeClass
    public void setUp() {
    }

    @Test
    public void construtorDeveriaSetarVariaveisCorretamente() {
        String name = "Missão Impossível";
        String description = "Com Tom Cruise!";
        mission = new Mission(name, description, Mission.TYPE_TERRITORY);
        assertEquals(mission.name, name);
        assertEquals(mission.description, description);
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
        assertNotNull(mission.houses);
    }

    @Test
    public void addHouseDeveAdicionarHouseParaMissaoDeHouse() {
        mission = new Mission(null, null, Mission.TYPE_HOUSE);
        House house = mock(House.class);
        assertTrue(mission.addHouse(house));
    }

    @Test
    public void addHouseNaoDeveAdicionarHouseParaOutrasMissoes() {
        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
        House house = mock(House.class);
        assertFalse(mission.addHouse(house));
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
    public void addTerritoryDeveAdicionarTerritoryParaMissaoDeTerritory() {
        mission = new Mission(null, null, Mission.TYPE_TERRITORY);
        Territory territory = mock(Territory.class);
        assertTrue(mission.addTerritory(territory));
    }

    @Test
    public void addTerritoryNaoDeveAdicionarTerritoryParaOutrasMissoes() {
        mission = new Mission(null, null, Mission.TYPE_HOUSE);
        Territory territory = mock(Territory.class);
        assertFalse(mission.addTerritory(territory));
    }

    @Test
    public void removeMissionsDeveRemoverAsMissoesDasCasasAusentesDoJogo() {
        Board board = new Board();
        mission = new Mission();

        House stark = mock(House.class);
        House lannister = mock(House.class);
        House baratheon = mock(House.class);
        House targaryen = mock(House.class);
        House freeFolk = mock(House.class);
        House greyjoy = mock(House.class);

        Player playerStark = mock(Player.class);
        Player playerLannister = mock(Player.class);
        Player playerTargaryen = mock(Player.class);

        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        board.addPlayer(playerTargaryen, 2, Board.HUMAN_PLAYER);
        when(playerStark.getHouse()).thenReturn(stark);
        when(playerLannister.getHouse()).thenReturn(lannister);
        when(playerTargaryen.getHouse()).thenReturn(targaryen);


        LinkedList<House> allHouses = new LinkedList<House>();
        allHouses.add(stark);
        allHouses.add(lannister);
        allHouses.add(targaryen);
        allHouses.add(baratheon);
        allHouses.add(freeFolk);
        allHouses.add(greyjoy);

        Mission missionStark = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionLannister = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionBaratheon = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionRegion = new Mission(null, null, Mission.TYPE_REGION);
        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);

        missionStark.getHouses().add(stark);
        missionLannister.getHouses().add(lannister);
        missionBaratheon.getHouses().add(baratheon);

        LinkedList<Mission> allMissions = new LinkedList<Mission>();
        allMissions.add(missionStark);
        allMissions.add(missionLannister);
        allMissions.add(missionBaratheon);
        allMissions.add(missionRegion);
        allMissions.add(missionTerritory);

        LinkedList<Mission> r = mission.removeMissions(board, allMissions, allHouses);

        assertEquals(missionStark, r.get(0));
        assertEquals(missionLannister, r.get(1));
        assertEquals(missionRegion, r.get(2));
        assertEquals(missionTerritory, r.get(3));
    }

    @Test
    public void CadaJogadorDeveReceberUmaMissao() {
        Board board = new Board();

        House stark = mock(House.class);
        House lannister = mock(House.class);
        House baratheon = mock(House.class);
        House targaryen = mock(House.class);
        House freeFolk = mock(House.class);
        House greyjoy = mock(House.class);

        Player playerStark = new HumanPlayer("Stark", stark);
        Player playerLannister = new HumanPlayer("Lannister", lannister);
        Player playerTargaryen = new HumanPlayer("Targaryen", targaryen);

        board.addPlayer(playerStark, 0, Board.HUMAN_PLAYER);
        board.addPlayer(playerLannister, 1, Board.HUMAN_PLAYER);
        board.addPlayer(playerTargaryen, 2, Board.HUMAN_PLAYER);

        LinkedList<House> allHouses = new LinkedList<House>();
        allHouses.add(stark);
        allHouses.add(lannister);
        allHouses.add(targaryen);
        allHouses.add(baratheon);
        allHouses.add(freeFolk);
        allHouses.add(greyjoy);

        Mission missionStark = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionLannister = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionBaratheon = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionT = new Mission(null, null, Mission.TYPE_HOUSE);
        Mission missionRegion = new Mission(null, null, Mission.TYPE_REGION);
        Mission missionTerritory = new Mission(null, null, Mission.TYPE_TERRITORY);

        missionStark.getHouses().add(stark);
        missionLannister.getHouses().add(lannister);
        missionBaratheon.getHouses().add(baratheon);
        missionT.getHouses().add(targaryen);

        LinkedList<Mission> allMissions = new LinkedList<Mission>();
        allMissions.add(missionStark);
        allMissions.add(missionLannister);
        allMissions.add(missionBaratheon);
        allMissions.add(missionT);
        allMissions.add(missionRegion);
        allMissions.add(missionTerritory);

        Mission missionTest = new Mission();
        missionTest.raffleMission(board, allMissions, allHouses);

        assertNotNull(playerStark.getMission());
        assertNotNull(playerLannister.getMission());
        assertNotNull(playerTargaryen.getMission());
//        assertNotEquals(playerStark.getMission(), missionStark);
//        assertNotEquals(playerLannister.getMission(), missionLannister);
//        assertNotEquals(playerTargaryen.getMission(), missionT);
    }

    @Test
    public void hasSameHouseDeveRetornarTrueQuandoOJogadorTemAMesmaCasaDaMissao() {
        Player playerStark = mock(Player.class);
        Mission m = new Mission(null, null, Mission.TYPE_HOUSE);
        House stark = mock(House.class);

        when(playerStark.getHouse()).thenReturn(stark);
        m.getHouses().add(stark);

        assertTrue(m.hasSameHouse(playerStark));
    }

    @Test
    public void isRegionMissionCompletedDeveRetornarTrueCasoAMissaoDeRegionEstiverCompleta() {
        Region theNorth = new Region();
        Region theSouth = new Region();
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

        assertTrue(missionRegion1.isRegionMissionCompleted());
    }
    
    @Test
    public void isRegionMissionCompletedDeveRetornarFalseCasoAMissaoDeRegionNaoEstiverCompleta() {
        Region theNorth = new Region();
        Region theSouth = new Region();
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

        assertFalse(missionRegion1.isRegionMissionCompleted());
    }
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

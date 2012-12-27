/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
    
    @AfterClass
    public void cleanUp() {
        // code that will be invoked after this test ends
    }
}

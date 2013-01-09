package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Mission {

    public static final int TYPE_TERRITORY = 1;
    public static final int TYPE_REGION = 2;
    public static final int TYPE_HOUSE = 3;
    protected String name;
    protected String description;
    protected List<Territory> territories;
    protected List<Region> regions;
    protected List<House> houses;
    protected Player player;
    protected int type;

    public Mission(String name, String description, int type) {
        this.type = type;
        this.name = name;
        this.description = description;
        // Inicializa apenas a lista dos objetivos em questão
        switch (type) {
            case TYPE_TERRITORY:
                territories = new ArrayList<Territory>();
                break;
            case TYPE_REGION:
                regions = new ArrayList<Region>();
                break;
            case TYPE_HOUSE:
                houses = new ArrayList<House>();
                break;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public List<House> getHouses() {
        return houses;
    }

    public boolean addHouse(House house) {
        if (type == TYPE_HOUSE && !houses.contains(house)) {
            return houses.add(house);
        }
        return false;
    }

    public boolean addTerritory(Territory territory) {
        if (type == TYPE_TERRITORY && !territories.contains(territory)) {
            return territories.add(territory);
        }
        return false;
    }

    public boolean addRegion(Region region) {
        if (type == TYPE_REGION && !regions.contains(region)) {
            return regions.add(region);
        }
        return false;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Territory> getTerritories() {
        return territories;
    }
    
    public void shuffleMissions (LinkedList<Mission> mission){
        Collections.shuffle(mission);
    }

    public void raffleMission (Board board, LinkedList<Mission> allMissions, LinkedList<House> presentHouses){
        int size = board.getPlayers().size();

        if (size != 6){
            ArrayList<House> absentHouses = null; // TO DO: pegar as casas que não participam do jogo
            for (Mission mission : allMissions) {
                for (House house : absentHouses) {
                    if (mission.getHouses().contains(house)){
                        allMissions.remove(mission);
                    }
                }
            }
        }

        shuffleMissions(allMissions);

        for (int i = 0; i < size; i++) {
            Player p = board.getPlayer(size);
            p.setMission(allMissions.removeFirst());
        }
    }
}
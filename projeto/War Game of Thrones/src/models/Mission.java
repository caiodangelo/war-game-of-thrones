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
    protected House house;
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
                house = new House();
                break;
        }
    }

    public Mission() {
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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void shuffleMissions(LinkedList<Mission> mission) {
        Collections.shuffle(mission);
    }

    public boolean hasSameHouse(Player player) {
        return ((this.getType() == Mission.TYPE_HOUSE) && (this.getHouse().equals(player.getHouse())));
    }

    public LinkedList<Mission> raffleMission(Board board, LinkedList<Mission> allMissions, LinkedList<House> allHouses) {
        int size = board.getPlayers().size();

        LinkedList<Mission> r = removeMissions(board, allMissions, allHouses);
        shuffleMissions(r);

        for (int i = 0; i < size; i++) {
            Player p = board.getPlayer(i);
            Mission mission = r.peekFirst();
            while (mission.hasSameHouse(p)) {
                shuffleMissions(r);
                mission = r.peekFirst();
            }
            p.setMission(r.removeFirst());
        }
        return r;
    }

    public LinkedList<Mission> removeMissions(Board board, LinkedList<Mission> allMissions, LinkedList<House> allHouses) {
        List<House> absentHouses = board.getAbsentHouses(allHouses);
        LinkedList<Mission> answer = (LinkedList<Mission>) allMissions.clone();

        for (int i = 0; i < answer.size(); i++) {
            Mission mission = answer.get(i);
            for (House h : absentHouses) {
                if ((mission.getType() == Mission.TYPE_HOUSE) && (mission.getHouse().equals(h))) {
                    answer.remove(answer.get(i));
                    i--;
                }
            }
        }
        return answer;
    }

    public boolean isCompletedMission(Board board, ArrayList<Region> allRegions) {
        switch (this.getType()) {
            case TYPE_REGION:
                return isRegionMissionCompleted(allRegions);

            case TYPE_TERRITORY:
                return isTerritoryMissionCompleted();

            case TYPE_HOUSE:
                return isHouseMissionCompleted(board);
        }
        return false;
    }

    public boolean isRegionMissionCompleted(ArrayList<Region> allRegions) {
        boolean answer = false;
        Region aux = new Region("");
        List<Region> conqueredRegions = new ArrayList<Region>(); //Regioes que foram conquistadas pelo jogador      
        List<Territory> territoriesPlayer = player.getTerritories();

        for (Region region : allRegions) {
            if (territoriesPlayer.containsAll(region.getTerritories())) {
                conqueredRegions.add(region);
            }
        }

        for (Region regionMission : this.getRegions()) {
            if (regionMission.getName() != null) {
                if (conqueredRegions.contains(regionMission)) {
                    conqueredRegions.remove(regionMission);
                    answer = true;
                } else {
                    return false;
                }
            } else {
                aux = regionMission;
            }
        }
        if ((aux.getName() == null) && (conqueredRegions.isEmpty())) {
            answer = false;
        }

        return answer;
    }

    public boolean isTerritoryMissionCompleted() {
        boolean answer;
        List<Territory> playerTerritories = this.getPlayer().getTerritories();
        answer = (this.getTerritories().size() == playerTerritories.size());
        
        if((this.getTerritories().size() == 18) &&(answer)){
            for (Territory territory : playerTerritories) {
                if(!(territory.getNumArmies() >= 2)) {
                    return false;
                }
            }
        }
        return answer;
    }

    public boolean isHouseMissionCompleted(Board board) {
        Player defeated = null;
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (board.getPlayer(i).getHouse().equals(this.getHouse())) {
                defeated = board.getPlayer(i);
            }
        }
        return (defeated.getTerritories().isEmpty());
    }
}
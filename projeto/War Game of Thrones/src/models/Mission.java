package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Mission implements Serializable {

    public static final int TYPE_TERRITORY = 1;
    public static final int TYPE_REGION = 2;
    public static final int TYPE_HOUSE = 3;
    protected String name;
    protected int territories;
    protected List<Region> regions;
    protected House house;
    protected Player player;
    protected int type;

    public Mission(String name, int type) {
        this.name = name;
        this.type = type;
        // Inicializa apenas a lista dos objetivos em questão
        switch (type) {
            case TYPE_TERRITORY:
                break;
            case TYPE_REGION:
                regions = new ArrayList<Region>();
                break;
            case TYPE_HOUSE:
                house = new House();
                break;
        }
    }

    @Deprecated
    public Mission(String name, String description, int type) {
        this.name = name;
        this.type = type;
//        this.description = description;
        // Inicializa apenas a lista dos objetivos em questão
        switch (type) {
            case TYPE_TERRITORY:
                break;
            case TYPE_REGION:
                regions = new ArrayList<Region>();
                break;
            case TYPE_HOUSE:
                house = new House();
                break;
        }
    }

    public String getDescription() {
        switch (type) {
            case TYPE_TERRITORY:
                return "Seu objetivo é conquistar " + territories + " territórios à sua escolha";
            case TYPE_HOUSE:
                return "Seu objetivo é destruir todos os exércitos da casa " + house.getName();
            case TYPE_REGION:
                String objective = "Seu objetivo é conquistar completamente os seguintes continentes: ";
                for (Region region : regions) {
                    objective += region.getName() + ", ";
                }
                return objective.substring(0, objective.length() - 1);
        }
        return "Seu objetivo ainda não foi definido";
    }

    @Deprecated
    /**
     * A descrição agora é criada dinamicamente no getDescription()
     */
    public void setDescription(String description) {
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

    public boolean addRegion(Region region) {
        if (type == TYPE_REGION && !regions.contains(region)) {
            return regions.add(region);
        }
        return false;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public int getTerritories() {
        return territories;
    }

    public void setTerritories(int territories) {
        this.territories = territories;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean hasSameHouse(Player player) {
        return ((this.getType() == Mission.TYPE_HOUSE) && (this.getHouse().equals(player.getHouse())));
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
        Region aux = new Region("", 0);
        List<Region> conqueredRegions = new ArrayList<Region>(); //Regioes que foram conquistadas pelo jogador      
        List<Territory> territoriesPlayer = player.getTerritories();

        for (Region region : allRegions) {
            if (territoriesPlayer.containsAll(region.getTerritories())) {
                conqueredRegions.add(region);
            }
        }
        
        for (Region regionMission : this.getRegions()) {   
            //Para as missões do tipo que tenham que conquistar uma região a sua escolha
            //é definido o nome da região como null
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
        // Para as missões que tenham que conquistar uma região a sua escolha
        if ((aux.getName() == null) && (conqueredRegions.isEmpty())) {
            answer = false;
        }

        return answer;
    }

    public boolean isTerritoryMissionCompleted() {
        boolean answer;
        List<Territory> playerTerritories = this.getPlayer().getTerritories();
        answer = (this.getTerritories() == playerTerritories.size());

        if ((this.getTerritories() == 18) && (answer)) {
            for (Territory territory : playerTerritories) {
                if (!(territory.getNumArmies() >= 2)) {
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
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Mission implements Serializable {

    public static final int TYPE_TERRITORY = 1;
    public static final int TYPE_REGION = 2;
    public static final int TYPE_HOUSE = 3;
    protected String description;
    protected int territories;
    protected List<Region> regions;
    protected House house;
    protected Player player;
    protected int type;

    public Mission(String desc, int type) {
        this.description = desc;
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

//    public Mission(String desc, int type, int territories) {
//        this.description = desc;
//        this.type = type;
//        this.territories = territories;
//    }
//
//    public Mission(String desc, int type, List<Region> regions) {
//        this.description = desc;
//        this.type = type;
//        this.regions = regions;
//    }
//
//    public Mission(String desc, int type, House house) {
//        this.description = desc;
//        this.type = type;
//        this.house = house;
//    }
    
    public String getDescription() {
        if (description == null || description.equals("")) {
            switch (type) {
                case TYPE_TERRITORY:
                    if (territories == 23) {
                        description = "conquistar " + territories + " territórios a sua escolha!";
                    }
                    if (territories == 17) {
                        description = "conquistar " + territories + " territórios a sua escolha com 2 exércitos em cada um deles!";
                    }
                    break;
                case TYPE_HOUSE:
                    String houseOwner = getHouseOwner();
                    description = "destruir todos os exércitos da casa \"" + house.getName() +
                            "\" (" + houseOwner + ")!";
                    break;
                case TYPE_REGION:
                    description = "conquistar completamente os seguintes continentes: ";
                    for (int i = 0; i < regions.size(); i++) {
                        Region region = regions.get(i);
                        if (i == regions.size() - 1) {
                            description = description.substring(0, description.length() - 2) + " e ";
                            if ((region.getName()) == null)
                                description += "um continente à sua escolha!";
                            else
                                description += "\"" + region.getName() + "\"!";
                        }
                        else
                            description += "\"" + region.getName() + "\", ";
                    }
                    break;
            }
        }
        return description;
    }

    @Deprecated
    /**
     * A descrição agora é criada dinamicamente no getDescription()
     */
    public void setDescription(String description) {
    }

    public String getName() {
        return description;
    }

    public void setName(String name) {
        this.description = name;
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

    public boolean isCompleted() {
        switch (this.getType()) {
            case TYPE_REGION:
                return isRegionMissionCompleted();

            case TYPE_TERRITORY:
                return isTerritoryMissionCompleted();

            case TYPE_HOUSE:
                return isHouseMissionCompleted();
        }
        return false;
    }

    public boolean isRegionMissionCompleted() {
        Region[] allRegions = Board.getInstance().getRegions();
        List<Region> missionRegions = new ArrayList<Region>(regions);
        boolean conqueredRegionOfChoice = false;
        for (int i = 0; i < allRegions.length; i++) {
            if (allRegions[i].conqueredByPlayer(player)) {
                if (!missionRegions.remove(allRegions[i]))
                    conqueredRegionOfChoice = true;
            }
        }
        if (missionRegions.isEmpty())
            return true;
        else if ((missionRegions.size() == 1 && missionRegions.get(0).getName() == null))
            return conqueredRegionOfChoice;
        else
            return false;
    }

    public boolean isTerritoryMissionCompleted() {
        int count = 0;
        List<BackEndTerritory> playerTerritories = this.getPlayer().getTerritories();
        if(this.getTerritories() == 23)
            return playerTerritories.size() >= 23;
       
        if (this.getTerritories() == 17) {
            for (BackEndTerritory territory : playerTerritories) {
                if (territory.getNumArmies() >= 2) 
                    count++;                
            }
            if(count >= 17) return true;
            else return false;
        }
        return false;
    }

    public boolean isHouseMissionCompleted() {
        Board board = Board.getInstance();
        Player defeated = new HumanPlayer("");
        for (int i = 0; i < board.getPlayers().size(); i++) {
            if (board.getPlayer(i).getHouse().getName().equals(this.getHouse().getName())) {
                defeated = board.getPlayer(i);
            }
        }
        return (defeated.getTerritories().isEmpty() || defeated.numArmies() == 0);
    }

    private String getHouseOwner() {
        LinkedList<Player> players = Board.getInstance().getPlayers();
        for(Player p : players){
            if(p.getHouse() == this.getHouse())
                return p.getName();
        }
        return null;
    }
}
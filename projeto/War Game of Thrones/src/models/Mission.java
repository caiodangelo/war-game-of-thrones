package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Mission {

    public static final int TYPE_TERRITORY = 1;
    public static final int TYPE_REGION = 2;
    public static final int TYPE_HOUSE = 3;
    private String name;
    private String description;
    private List<Territory> territories;
    private List<Region> regions;
    private List<House> houses;
    private Player player;
    private int type;

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
            houses.add(house);
        }
        return false;
    }

    public boolean addTerritory(Territory territory) {
        if (type == TYPE_TERRITORY && !territories.contains(territory)) {
            territories.add(territory);
        }
        return false;
    }

    public boolean addRegion(Region region) {
        if (type == TYPE_REGION && !regions.contains(region)) {
            regions.add(region);
        }
        return false;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public List<Territory> getTerritories() {
        return territories;
    }
}
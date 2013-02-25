package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import util.TerritoriesGraphStructure;
import org.apache.commons.lang.SerializationUtils;

/**
 *
 * @author rodrigo
 */
public class Board implements Serializable {

    public static final int AI_PLAYER = 1;
    public static final int HUMAN_PLAYER = 2;
    public static final String LANNISTER = "Lannister";
    public static final String STARK = "Stark";
    public static final String GREYJOY = "Greyjoy";
    public static final String FREE_FOLK = "Free Folk";
    public static final String BARATHEON = "Baratheon";
    public static final String TARGARYEN = "Targaryen";
    protected LinkedList<Player> players;
    protected static Board instance;
    protected int currentPlayer;
    protected int numberOfSwaps;
    protected StatisticGameManager statistic;
    private Region[] regions;
    private Territory[] territories;
    private ArrayList<House> houses;
    private ArrayList<Mission> missions;
    private boolean isOnInitialSetup;

    protected Board() {
        instance = this;
        players = new LinkedList<Player>();
        currentPlayer = 0;
        numberOfSwaps = 0;
        statistic = new StatisticGameManager();
        isOnInitialSetup = true;
        houses = new ArrayList<House>();
        missions = new ArrayList<Mission>();
        
        if (regions == null) {
            retrieveTerritories();
        }
    }

    private void retrieveTerritories() {
        System.out.println("Filling board territories");
        regions = new Region[6];
        String[] regionNames = {"Além da Muralha", "Cidades Livres", "O Norte", "Sul", "Tridente", "O Mar Dothraki"};
        int[] bonus = {Region.ALEM_DA_MURALHA, Region.CIDADES_LIVRES, Region.O_NORTE, Region.O_SUL, Region.TRIDENTE, Region.O_MAR_DOTHRAKI};
        for (int i = 0; i < regionNames.length; i++) {
            regions[i] = new Region(regionNames[i], bonus[i]);
        }

        //alem da muralha
        Region current = regions[0];
        territories = new Territory[39];
        int tIndex = 0;
        TerritoriesGraphStructure struct = TerritoriesGraphStructure.getInstance();
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.COSTA_GELADA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.FLORESTA_ASSOMBRADA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.SEMPRE_INVERNO, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.ILHA_DOS_URSOS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.SKAGOS, struct));

        //cidades livres
        current = regions[1];
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.COSTA_LARANJA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.TERRAS_DISPUTADAS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.CAMPOS_DOURADOS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.COLINAS_DE_NORVOS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.COSTA_BRAVOSIANA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.FLORESTA_DE_QOHOR, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.THE_FLATLANDS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.VALIRIA, struct));

        //o norte
        current = regions[2];
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.A_DADIVA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.BARROWLANDS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.COSTA_PEDREGOSA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.MATA_DE_LOBOS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.PENHASCO_SOMBRIO, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.THE_HILLS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.WINTERFELL, struct));

        //o sul
        current = regions[3];
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.ARVORE, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.DORNE, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.JARDIM_DE_CIMA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.MATA_DO_REI, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.MONTE_CHIFRE, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.TARTH, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.TERRAS_DA_TEMPESTADE, struct));

        //tridente
        current = regions[4];
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.MONTANHAS_DA_LUA, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.CAPE_KRAKENTT, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.GARGALO, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.VALE_DE_ARRYN, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.PORTO_REAL, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.ROCHEDO_CASTERLY, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.TERRAS_FLUVIAIS, struct));

        //o mar dothraki
        current = regions[5];
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.GHISCAR, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.DESERTO_VERMELHO, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.MAR_DOTHRAKI, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.OROS, struct));
        current.addTerritory(territories[tIndex++] = new Territory(TerritoryID.FOOTPRINT, struct));
    }

    public void createMissions() {
        Region empty = new Region(null, 0);
        ArrayList<Region> allRegions = new ArrayList<Region>();
        String[] regionNames = {"Além da Muralha", "Cidades Livres", "O Norte", "Sul", "Tridente", "O Mar Dothraki"};
        allRegions.add(regions[0]);
        allRegions.add(regions[3]);
        allRegions.add(empty);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        allRegions.clear();
        allRegions.add(regions[1]);
        allRegions.add(regions[0]);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        allRegions.clear();
        allRegions.add(regions[2]);
        allRegions.add(regions[5]);
        allRegions.add(empty);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        allRegions.clear();
        allRegions.add(regions[1]);
        allRegions.add(regions[4]);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        allRegions.clear();
        allRegions.add(regions[2]);
        allRegions.add(regions[3]);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        allRegions.clear();
        allRegions.add(regions[4]);
        allRegions.add(regions[5]);
        missions.add(new Mission("", Mission.TYPE_REGION, allRegions));
        missions.add(new Mission("", Mission.TYPE_TERRITORY, 23));
        missions.add(new Mission("", Mission.TYPE_TERRITORY, 17));
        for (int i = 0; i < houses.size(); i++) {
            missions.add(new Mission("", Mission.TYPE_HOUSE, houses.get(i)));
        }
    }

    public Territory[] getTerritories() {
        return territories;
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public Board getClone() {
        return (Board) SerializationUtils.clone(this);
    }

    public boolean addPlayer(Player player, int playingOrder, int type) {
        if (players.size() < 6) {
            if (!players.contains(player)) {
                players.addLast(player);
                return true;
            }
        }
        return false;
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    public void addHouse(House house) {
        houses.add(house);
    }

    public Player getPlayer(int playingOrder) {
        return players.get(playingOrder);
    }

    public int getPlayerOrder(Player p) {
        return players.indexOf(p);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public void setMissions(ArrayList<Mission> missions) {
        this.missions = missions;
    }
    
    public List<House> getHouses() {
        List<House> houses = new ArrayList<House>();
        for (Player player : getPlayers()) {
            houses.add(player.getHouse());
        }
        return houses;
    }

    public int getNumberOfSwaps() {
        return numberOfSwaps;
    }

    public void increaseNumberOfSwaps() {
        numberOfSwaps++;
    }

    public boolean isPlayerCountValid() {
        return (players.size() >= 2 && players.size() <= 6);
    }

    public boolean isOnInitialSetup() {
        return isOnInitialSetup;
    }

//    public List<House> getAbsentHouses() {
//        List<House> absentHouses = new ArrayList<House>();
//        List<House> presentHouses = getHouses();
//
//        if (players.size() != 6) {
//            for (House house : houses) {
//                if (!presentHouses.contains(house)) {
//                    absentHouses.add(house);
//                }
//            }
//            return absentHouses;
//        } else {
//            return null;
//        }
//    }

    public Player nextPlayer(Player player) {
        int i;
        for (i = 0; i < this.getPlayers().size(); i++) {
            if (player == this.getPlayer(i)) {
                break;
            }
        }
        if (i == this.getPlayers().size()) {
            i = 0;
        } else {
            i++;
        }
        return players.get(i);
    }

    public void changePlayer() {
        int oldPlayer = this.currentPlayer;

        int playersCount = this.getPlayers().size();
        if (oldPlayer == this.getPlayers().size() - 1) {
            currentPlayer = 0;
            isOnInitialSetup = false;
        } else {
            currentPlayer++;
        }
        addPlayerArmies();
    }

    private void addPlayerArmies(Player curr) {
        int territoryCount = curr.getTerritories().size();
        int pendingArmies = curr.getPendingArmies();
        pendingArmies += territoryCount / 2;
        for (Region r : regions) {
            if (r.conqueredByPlayer(curr)) {
                pendingArmies += r.getBonus();
                System.out.println("receiving " + r.getBonus() + " from " + r);
            }
        }
        curr.setPendingArmies(pendingArmies);
    }

    private void addPlayerArmies() {
        addPlayerArmies(getCurrentPlayer());
    }

    public StatisticGameManager getStatistic() {
        return statistic;
    }

    public void distributeInitialTerritories() {
        RepositoryCardsTerritory.getInstance().initialRaffle();
        for (Player player : players) {
            for (CardTerritory card : player.getCards()) {
                player.addTerritory(card.getTerritory());
            }
            player.getCards().clear();
        }
        addPlayerArmies();
    }

    public void shuffleMissions(ArrayList<Mission> mission) {
        Collections.shuffle(mission);
    }

    public ArrayList<Mission> raffleMission() {
        int size = players.size();

       // ArrayList<Mission> r = removeMissions();
        shuffleMissions(missions);

        for (int i = 0; i < size; i++) {
            Player p = this.getPlayer(i);
            Mission mission = missions.get(0);
            while (mission.hasSameHouse(p)) {
                shuffleMissions(missions);
                mission = missions.get(0);
            }
            p.setMission(missions.remove(0));
        }
        return missions;
    }

//    public ArrayList<Mission> removeMissions() {
//        //List<House> absentHouses = this.getAbsentHouses();
//        boolean found = false;
//        ArrayList<Mission> answer = new ArrayList<Mission>();
//
//        answer = missions;
//
//        for (int i = 0; i < answer.size(); i++) {
//            Mission mission = answer.get(i);
//            if ((mission.getType() == Mission.TYPE_HOUSE)) {
//                for (int j = 0; j < houses.size(); j++) {
//                    if (mission.getHouse().equals(houses.get(j))) {
//                        found = true;
//                    }
//                }
//                if (!found) {
//                    answer.remove(answer.get(i));
//                    i--;
//                }
//            }
//        }
//        return answer;
//    }

    public Region[] getRegions() {
        return regions;
    }
}
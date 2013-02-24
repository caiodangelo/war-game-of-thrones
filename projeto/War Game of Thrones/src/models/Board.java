package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Board {

    public static final int AI_PLAYER = 1;
    public static final int HUMAN_PLAYER = 2;
    
    protected LinkedList<Player> players;
    protected static Board instance;
    protected int currentPlayer;
    protected int numberOfSwaps;
    protected StatisticGameManager statistic;

    protected Board() {
        players = new LinkedList<Player>();
        currentPlayer = 0;
        numberOfSwaps = 0;
        statistic = new StatisticGameManager();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
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

    public Player getPlayer(int playingOrder) {
        return players.get(playingOrder);
    }

    public LinkedList<Player> getPlayers() {
        return players;
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

    public List<House> getAbsentHouses(LinkedList<House> allHouses) {
        List<House> absentHouses = new ArrayList<House>();
        List<House> presentHouses = getHouses();

        if (players.size() != 6) {
            for (House house : allHouses) {
                if (!presentHouses.contains(house)) {
                    absentHouses.add(house);
                }
            }
            return absentHouses;
        } else {
            return null;
        }
    }
    
    public Player nextPlayer(Player player) {
        int i;
        for (i = 0; i < this.getPlayers().size(); i++) {
            if (player == this.getPlayer(i)) {
                break;
            }            
        }
        if (i == this.getPlayers().size()) { 
            i = 0;
        }
        else {
            i++;
        }
        return this.getPlayer(i);
    }
    
    public int changePlayer () {
        int newPlayer;
        int oldPlayer = this.currentPlayer;
        if (oldPlayer == this.getPlayers().size()) {
            currentPlayer = 0;
            newPlayer = 0;
        } else {
            currentPlayer++;
            newPlayer = oldPlayer + 1;
        }
        return newPlayer;
    }

    public StatisticGameManager getStatistic() {
        return statistic;
    }
    
    public void distributeInitialTerritory() {
        for (Player player : players) {
            for (CardTerritory card : player.getCards()) {
                player.addTerritory(card.getTerritory());                
            }
        }
    }
    
     public void shuffleMissions(LinkedList<Mission> mission) {
        Collections.shuffle(mission);
    }
    
    public LinkedList<Mission> raffleMission(LinkedList<Mission> allMissions, LinkedList<House> allHouses) {
        int size = players.size();

        LinkedList<Mission> r = removeMissions(allMissions, allHouses);
        shuffleMissions(r);

        for (int i = 0; i < size; i++) {
            Player p = this.getPlayer(i);
            Mission mission = r.peekFirst();
            while (mission.hasSameHouse(p)) {
                shuffleMissions(r);
                mission = r.peekFirst();
            }
            p.setMission(r.removeFirst());
        }
        return r;
    }

    public LinkedList<Mission> removeMissions(LinkedList<Mission> allMissions, LinkedList<House> allHouses) {
        List<House> absentHouses = this.getAbsentHouses(allHouses);
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
}
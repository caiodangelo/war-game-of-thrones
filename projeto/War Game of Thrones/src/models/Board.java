package models;

import java.util.ArrayList;
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

    protected Board() {
        players = new LinkedList<Player>();
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
}
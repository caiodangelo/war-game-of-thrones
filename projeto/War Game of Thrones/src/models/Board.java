package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Board {

    private LinkedList<Player> players;

    private static Board instance;

    private Board() {
        players = new LinkedList<Player>();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public boolean addPlayer(Player player, int playingOrder) {
        if (!players.contains(player)) {
            players.addLast(player);
            return true;
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
        for (Player player : players) {
            houses.add(player.getHouse());
        }
        return houses;
    }
}
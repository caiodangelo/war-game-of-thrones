/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rodrigo
 */
public class Board {

    // A chave do hash indica a posição de jogada do jogador
    private HashMap<Integer, Player> players;

    private static Board instance;

    private Board() {
        players = new HashMap<Integer, Player>();
    }

    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public boolean addPlayer(Player player, int playingOrder) {
        if (!players.containsKey(playingOrder)) {
            players.put(playingOrder, player);
            return true;
        }
        return false;
    }

    public Player getPlayer(int playingOrder) {
        return players.get(playingOrder);
    }

    public List<Player> getPlayers() {
        return (List<Player>) players.values();
    }

    public List<House> getHouses() {
        List<House> houses = new ArrayList<House>();
        for (Player player : players.values()) {
            houses.add(player.getHouse());
        }
        return houses;
    }
}
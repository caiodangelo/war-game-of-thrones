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

    public boolean addPlayer(Player player, int playingOrder, int type) {
        if (players.size() < 6){
            switch (type) {
                case AI_PLAYER:
                    addAIPlayer((AIPlayer) player, playingOrder);
                    break;
                case HUMAN_PLAYER:
                    addHumanPlayer((HumanPlayer) player, playingOrder);
                    break;
            }
        }
        return false;
    }

    public boolean addAIPlayer(AIPlayer aiPlayer, int playingOrder) {
        if (players.size() < 6) {
            if (!players.contains(aiPlayer)) {
                players.addLast(aiPlayer);
                return true;
            }
        }
        return false;
    }

    public boolean addHumanPlayer(HumanPlayer human, int playingOrder) {
        if (players.size() < 6) {
            if (!players.contains(human)) {
                players.addLast(human);
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
        for (Player player : players) {
            houses.add(player.getHouse());
        }
        return houses;
    }

    public boolean managePlayers() {
        if (players.size() >= 2 && players.size() <= 6)
            return true;
        return false;
    }   
   
}
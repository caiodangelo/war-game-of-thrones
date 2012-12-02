/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author rodrigo
 */
public class Army {

    public static final int TYPE_SINGLE = 1;
    public static final int TYPE_MULTI = 2;

    private int type;
    private Player player;
    private Territory territory;

    public Army(int type, Player player, Territory territory) {
        this.type = type;
        this.player = player;
        this.territory = territory;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
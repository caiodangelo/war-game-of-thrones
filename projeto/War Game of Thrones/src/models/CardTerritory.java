/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package models;

/**
 *
 * @author anderson
 */
public class CardTerritory {

    public static final int SQUARE = 1;
    public static final int CIRCLE = 2;
    public static final int TRIANGLE = 3;
    public static final int JOKER = 4;

    private int type;
    private Territory territory;

    public CardTerritory(int geometricFigure, Territory territory) {
        this.type = geometricFigure;
        this.territory = territory;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

}